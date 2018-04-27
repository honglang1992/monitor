package com.vrv.monitor.datapicker.service.job;

import com.alibaba.fastjson.JSONObject;
import com.vrv.monitor.core.snmp.SnmpService;
import com.vrv.monitor.core.snmp.SnmpServiceV2Impl;
import com.vrv.monitor.core.snmp.SnmpTargetLoad;
import com.vrv.monitor.datapicker.common.SysConstants;
import com.vrv.monitor.datapicker.model.assetConfig.MonitorSnmpConfig;
import com.vrv.monitor.datapicker.model.snmp.ProcessInfo;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

/**
 * Created by Dendi on 2017/11/15.
 */
public class SnmpMonitor implements Job {

    private final static Logger logger = LoggerFactory.getLogger(SnmpMonitor.class);

    @Autowired
    private SnmpTargetLoad snmpTargetLoad;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        //TODO 目前只是预留,此功能还未启用
        MonitorSnmpConfig config = new MonitorSnmpConfig();
        config.setIp("192.168.114.46");
        config.setPort(161);
        config.setReadcommunityString("public");

        SnmpService snmpService = new SnmpServiceV2Impl(config.getIp(), config.getPort(), config.getReadcommunityString());

        String snmpTarget= SysConstants.SNMP_PROCESSINFO;
        List<ProcessInfo> result = null;
        try {
            result = (List<ProcessInfo>)snmpTargetLoad.get(snmpTarget,snmpService);
        } catch (Exception e) {
            logger.error("SnmpMonitor error",e);
        }
        logger.info("result str is :{}", JSONObject.toJSONString(result));
    }
}
