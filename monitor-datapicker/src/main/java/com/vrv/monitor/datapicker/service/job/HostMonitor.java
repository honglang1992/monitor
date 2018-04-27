package com.vrv.monitor.datapicker.service.job;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jms.JMSException;

import org.apache.commons.lang.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.vrv.monitor.core.snmp.PortMonitor;
import com.vrv.monitor.core.snmp.SnmpService;
import com.vrv.monitor.core.snmp.SnmpServiceV2Impl;
import com.vrv.monitor.core.snmp.SnmpServiceV3Impl;
import com.vrv.monitor.core.util.DateUtil;
import com.vrv.monitor.core.util.GsonUtil;
import com.vrv.monitor.datapicker.common.SysConstants;
import com.vrv.monitor.datapicker.dao.assetConfig.MonitorSnmpConfigDao;
import com.vrv.monitor.datapicker.model.assetConfig.MonitorSnmpConfig;
import com.vrv.monitor.datapicker.model.job.CpuInfoVO;
import com.vrv.monitor.datapicker.model.job.DiskInfoVO;
import com.vrv.monitor.datapicker.model.job.MonitorSnmpData;
import com.vrv.monitor.datapicker.service.common.ActiveMQConnectionListenerImpl;

/**
 * Created by Dendi on 2017/10/13.
 */
public class HostMonitor implements Job {
    private final static Logger logger = LoggerFactory.getLogger(HostMonitor.class);

    @Autowired
    private MonitorSnmpConfigDao snmpConfigDao;

    @Value(value = "${assetConfig.networkClass}")
    private String networkClass;

    @Value(value = "${assetConfig.areaCode}")
    private String areaCode;

    @Resource(name="activeMQConnectionListener")
    private ActiveMQConnectionListenerImpl mqConnectionListener;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        long startTime = System.currentTimeMillis();
        //读取asset表的 监控配置
        List<MonitorSnmpConfig> configs = snmpConfigDao.query(networkClass, areaCode);
        //遍历执行
        for (MonitorSnmpConfig config : configs) {
            try {
                String str = getSnmpData(config);
                mqConnectionListener.sendMsg(str);
                logger.debug("host monitor str : {}",str);
            } catch (JMSException e) {
                logger.error("send msg to mq failed",e);
            } catch (Exception e){
                logger.error("host monitor job excute failed",e);
            }
        }
        long spend = System.currentTimeMillis()-startTime;
        logger.info("host job spend time {}",spend);
    }

    private String getSnmpData(MonitorSnmpConfig config) throws Exception {
        long startTime = System.currentTimeMillis();
        SnmpService snmpService;
        String version = config.getSnmpVersion();
        if (version.equalsIgnoreCase("v2") || version.equalsIgnoreCase("v1")) {
            snmpService = new SnmpServiceV2Impl(config.getIp(), config.getPort(), config.getReadcommunityString());
        } else if (version.equalsIgnoreCase("v3")) {
            snmpService = new SnmpServiceV3Impl(config.getIp(), config.getPort(), config.getSecurityLevel(), config.getSecurityName(),
                    config.getAuthAlgorithm(), config.getAuthPassWord(), config.getPrivAlgorithm(), config.getPrivPassWord());
        } else {
            throw new Exception("未实现的snmp版本:"+version);
        }
        MonitorSnmpData data = getInfo(snmpService,config);

        data.setAssetGuid(config.getAssetGuid());
        data.setInDate(DateUtil.format(new Date()));

        String result = JSONObject.toJSONString(data, SerializerFeature.WriteMapNullValue);

        logger.info(result);
        long spend = System.currentTimeMillis()-startTime;
        logger.info("host getSnmpData time {}",spend);
        snmpService.close();
        return result;
    }

    private MonitorSnmpData getInfo(SnmpService snmpService,MonitorSnmpConfig config) throws IOException {
        MonitorSnmpData data = new MonitorSnmpData();
        /**
         * 如果snmp无法联通 直接返回 这里通过取运行时长信息 来判断是否联通
         */
        String runningTime = getRunningTime(snmpService);
        if(null == runningTime){
            return data;
        }
        //新增属性   运行时长
        data.setRunningTime(runningTime);
        
        //cpu信息
        List<CpuInfoVO> cpuVo = this.getCpuInfo(snmpService);
        CpuInfoVO vo = null;
        if (cpuVo != null && cpuVo.size() > 1) {
            vo = cpuVo.get(cpuVo.size() - 1);
        } else if (cpuVo != null && cpuVo.size() == 1) {
            vo = cpuVo.get(0);
        }
        data.setCpuUsedRate(vo.getUsedRate());
        data.setCpuCount(vo.getCount());
        data.setCpuProcessorNum(vo.getProcessorNum());

        List<DiskInfoVO> diskVos = this.getDiskInfo(snmpService);
        data.setDiskInfoVOListJson(GsonUtil.gson.toJson(diskVos));
        
        
        double diskTotal=0f;
        double disUsed = 0f;

        for (DiskInfoVO disk : diskVos) {
            //磁盘信息
            if(disk.getDiskType().equals("1")){
                diskTotal = diskTotal+ Double.valueOf(disk.getDiskTotal());
                disUsed = disUsed + Double.valueOf(disk.getDiskUsed());

            }
            //物理内存
            if (disk.getDiskName().equalsIgnoreCase("Physical Memory")) {
                data.setRamPercentAge(disk.getDiskUsedRate());
                data.setRamSize(disk.getDiskTotal());
                data.setRamUsed(disk.getDiskUsed());
            }
        }
        DecimalFormat df   = new DecimalFormat("######0.00");
        data.setDiskTotal(df.format(diskTotal));
        data.setDiskUsed(df.format(disUsed));
        double disUsedRate = 100 * disUsed/diskTotal;
        data.setDiskUsedRate(df.format(disUsedRate));

        //判断是否跳过判断
        if(StringUtils.isEmpty(config.getAssetExtraInfo())){
            data.setTomcatIsRunning("1");
            data.setDatabaseIsRunning("1");
        }else {
            Map<String, Object> codeMap = GsonUtil.gson.fromJson(config.getAssetExtraInfo(), Map.class);
            Object tomcat = codeMap.get("tomcat");
            if (null != tomcat && tomcat.toString().matches("[0-9]{1,5}")) {
                boolean flag = PortMonitor.testPort(config.getIp(), Integer.parseInt(tomcat.toString()));
                if (flag) {
                    data.setTomcatIsRunning("1");
                } else {
                    data.setTomcatIsRunning("0");
                }
            } else {
                data.setTomcatIsRunning("1");
            }

            Object oracle = codeMap.get("oracle");
            if (null != oracle && oracle.toString().matches("[0-9]{1,5}")) {
                boolean flag = PortMonitor.testPort(config.getIp(), Integer.parseInt(oracle.toString()));
                if (flag) {
                    data.setDatabaseIsRunning("1");
                } else {
                    data.setDatabaseIsRunning("0");
                }
            } else {
                data.setDatabaseIsRunning("1");
            }
        }
        return data;
    }

    private List<CpuInfoVO> getCpuInfo(SnmpService snmpService) throws IOException {
        if(!snmpService.getCanCall()){
            return null;
        }

        List<CpuInfoVO> result = new ArrayList<CpuInfoVO>();
        /** CPU利用率 */
        String oid_usedCpu = "1.3.6.1.2.1.25.3.3.1.2";
        /** 当前系统进程数 */
        String oid_processNum = "1.3.6.1.2.1.25.1.6.0";

        String oid_cpuName = "UsedCpu";

        /** 获取进程数  */
        Object proNumObj = snmpService.oid(oid_processNum);
        int proNum;
        if (proNumObj == null || "NoSuchObject".equalsIgnoreCase(proNumObj.toString())) {
            proNum = 0;
        } else {
            proNum = Integer.parseInt(proNumObj.toString());
        }

        /**
         * 获取CPU利用率
         */
        String[] oids = new String[]{oid_usedCpu};
        String[] ooids = new String[]{oid_usedCpu};
        String[] names = new String[]{oid_cpuName};
        List<Map<String, Object>> cpuInfo = snmpService.oidTables(oids,ooids,names,10);
        int cpuCount = cpuInfo.size();
        float total = 0;
        for (Map<String, Object> map : cpuInfo) {
            CpuInfoVO info = new CpuInfoVO();
            Object cpuUsed = map.get(oid_cpuName);
            float used = Float.parseFloat(cpuUsed.toString());
            info.setUsedRate(String.valueOf(used));
            info.setCount(cpuInfo.size());
            info.setProcessorNum(proNum);
            result.add(info);
            total += used;
        }
        total = total / cpuInfo.size();
        String utiliz = String.valueOf(total);
        CpuInfoVO cpuAve = new CpuInfoVO();
        cpuAve.setUsedRate(utiliz);
        cpuAve.setCount(cpuInfo.size());
        cpuAve.setProcessorNum(proNum);
        result.add(cpuAve);
        return result;
    }

    private List<DiskInfoVO> getDiskInfo(SnmpService snmpService) throws IOException{
        List<DiskInfoVO> infos = new ArrayList<DiskInfoVO>();
        /** 存储器类型 */
        String oid_storageType = "1.3.6.1.2.1.25.2.3.1.2";
        /** 存储器描述 */
        String oid_storageDesc = "1.3.6.1.2.1.25.2.3.1.3";
        /** 存储器分配单元 */
        String oid_storageAllocationUnits = "1.3.6.1.2.1.25.2.3.1.4";
        /** 存储器容量 */
        String oid_storageSize = "1.3.6.1.2.1.25.2.3.1.5";
        /** 存储已用容量 */
        String oid_storageUsed = "1.3.6.1.2.1.25.2.3.1.6";

        String[] oids = new String[]{oid_storageType, oid_storageDesc, oid_storageAllocationUnits, oid_storageSize, oid_storageUsed};
        String[] ooids =new String[]{oid_storageType, oid_storageDesc, oid_storageAllocationUnits, oid_storageSize, oid_storageUsed};
        String[] names =new String[]{SysConstants.STORAGE_TYPE, SysConstants.STORAGE_DESC, SysConstants.STORAGE_ALLOC_UNIT, SysConstants.STORAGE_SIZE, SysConstants.STORAGE_USED};
        List<Map<String, Object>> oidTables = snmpService.oidTables(oids,ooids,names,10);
        for (Map<String, Object> map : oidTables) {
            DiskInfoVO info = new DiskInfoVO();
            String storageType = map.get(SysConstants.STORAGE_TYPE).toString();
            String totalSize = map.get(SysConstants.STORAGE_SIZE).toString();
            String usedSize = map.get(SysConstants.STORAGE_USED).toString();
            String allocUnit = map.get(SysConstants.STORAGE_ALLOC_UNIT).toString();
            String storageDesc = map.get(SysConstants.STORAGE_DESC).toString();
            int inCharNum = this.countInChar(storageDesc);
            if (inCharNum > 5) {
                storageDesc = this.hex2nativeInSnmp(storageDesc);
            }
            double total = Double.parseDouble(totalSize);
            double used = Double.parseDouble(usedSize);
            double alloc = Double.parseDouble(allocUnit);
            double diskTotal = 0;
            double diskUsed = 0;
            double diskUsedRate = 0;
            String utiliz;
            if (total != 0) {
                diskTotal = total * alloc / 1024 / 1024 / 1024;
                diskUsed = used * alloc / 1024 / 1024 / 1024;
                diskUsedRate = used / total * 100;
                info.setDiskTotal(String.format("%.2f", diskTotal));
                info.setDiskUsed(String.format("%.2f", diskUsed));
                utiliz = String.format("%.2f", diskUsedRate);
                info.setDiskUsedRate(utiliz);
            } else {
                info.setDiskTotal("0");
                info.setDiskUsed("0");
                info.setDiskUsedRate("0");
                utiliz = "0";
            }
            info.setDiskName(storageDesc);
            if (storageType.endsWith("4")) {
                // 磁盘
                info.setDiskType("1");
            } else if (storageType.endsWith("7")) {
                // 光驱
                info.setDiskType("2");
            } else if (storageType.endsWith("5")) {
                // 可移动磁盘
                info.setDiskType("3");
            } else {
                info.setDiskType("4");
            }

            infos.add(info);
        }
        return infos;
    }

    public String getRunningTime(SnmpService snmpService) throws IOException{
        if(!snmpService.getCanCall()){
            return null;
        }
        Object rt = snmpService.oid("1.3.6.1.2.1.1.3.0");
        if(rt == null){
            return null;
        }
        StringBuilder result = new StringBuilder();
        String[] arr = rt.toString().trim().split(" ");
        if (arr.length >= 3) {
            String[] time = arr[2].split(":");
            result.append(arr[0]).append("天").append(time[0]).append("小时").append(time[1]).append("分钟").append(time[2]).append("秒");
        } else {
            String[] time = arr[0].split(":");
            result.append(time[0]).append("小时").append(time[1]).append("分钟").append(time[2]).append("秒");
        }
        return result.toString();
    }

    /**
     * 计算字符串中冒号的个数
     * @param str
     * @return
     */
    private int countInChar(String str) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ':') {
                count++;
            }
        }
        return count;
    }

    /**
     * snmp4j中的16进制字符转为常规字符串
     * @param hexStr  16进制字符串
     * @return
     */
    private String hex2nativeInSnmp(String hexStr) {
        try {
            String[] temps = hexStr.split(":");
            byte[] bs = new byte[temps.length];
            for (int i = 0; i < temps.length; i++)
                bs[i] = (byte) Integer.parseInt(temps[i], 16);
            return new String(bs, "GB2312");
        } catch (Exception e) {
            return null;
        }
    }
}