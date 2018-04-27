package com.vrv.monitor.snmp;

import com.alibaba.fastjson.JSONObject;
import com.vrv.monitor.core.snmp.SnmpServiceV2Impl;
import com.vrv.monitor.core.util.ClassUtil;
import com.vrv.monitor.datapicker.common.SysConstants;
import com.vrv.monitor.datapicker.model.job.DiskInfoVO;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Dendi on 2017/11/7.
 */
public class SnmpServiceTest {

    private final static Logger logger = LoggerFactory.getLogger(SnmpServiceTest.class);

    @Test
    public void testLocalHostSnmp() throws IOException {
        SnmpServiceV2Impl snmpService = new SnmpServiceV2Impl("192.168.114.46",161,"public");
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

        logger.info("cpuRate:{}", JSONObject.toJSONString(oidTables));
    }

    @Test
    public void  testOid(){
        SnmpServiceV2Impl snmpService = new SnmpServiceV2Impl("192.168.114.46",161,"public");
        String pId = "1.3.6.1.2.1.25.4.2.1.1";
        String pName = "1.3.6.1.2.1.25.4.2.1.2";
        String pPath = "1.3.6.1.2.1.25.4.2.1.4";
        String pParams = "1.3.6.1.2.1.25.4.2.1.5";
        String pRunStatus = "1.3.6.1.2.1.25.4.2.1.7";
        String pType = "1.3.6.1.2.1.25.4.2.1.6";
        String[] oids = new String[]{pId,pName,pPath,pParams,pRunStatus,pType};
        String[] ooids = new String[]{pId,pName,pPath,pParams,pRunStatus,pType};
        String[] names = new String[]{"pId","pName","pPath","pParams","pRunStatus","pType"};
        try {
            List<Map<String, Object>> oidTables = snmpService.oidTables(oids,ooids,names,10);
            logger.info("result is {}", JSONObject.toJSONString(oidTables));
        } catch (IOException e) {
            logger.error("com.vrv.monitor.snmp error",e);
        }
    }

    @Test
    public void testClassUtilLoad(){
        // 包下面的类
        Set<Class<?>> clazzs = ClassUtil.getClasses("com.vrv.monitor.datapicker.model.snmp",false);
        if (clazzs == null) {
            return;
        }
        logger.debug("clazzsSize:{}",clazzs.size());
        // 某类或者接口的子类
        Set<Class<?>> inInterface = ClassUtil.getByInterface(Object.class, clazzs);
        logger.debug("inInterfaceSize:{}",inInterface.size());

        for (Class<?> clazz : clazzs) {

            // 获取类上的注解
            Annotation[] annos = clazz.getAnnotations();
            for (Annotation anno : annos) {
                System.out.println(clazz.getSimpleName().concat(".").concat(anno.annotationType().getSimpleName()));
            }

            // 获取方法上的注解
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                Annotation[] annotations = method.getDeclaredAnnotations();
                for (Annotation annotation : annotations) {
                    System.out.println(clazz.getSimpleName().concat(".").concat(method.getName()).concat(".")
                            .concat(annotation.annotationType().getSimpleName()));
                }
            }
        }
    }

    @Test
    public void testSnmpTargetLoad(){

    }
}
