package com.vrv.monitor.core.snmp;

import com.vrv.monitor.core.snmp.snnotation.SnmpClassDescription;
import com.vrv.monitor.core.snmp.snnotation.SnmpFieldDescription;
import com.vrv.monitor.core.util.ClassUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.smi.Variable;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

/**
 * 读取指定包下的 所有snmp 指标类 信息
 * 指标类的属性目前只支持 Integer 和 String类型 其它类型会被忽略
 * Created by Dendi on 2017/11/14.
 */
public class SnmpTargetLoad {

    private final static Logger logger = LoggerFactory.getLogger(SnmpTargetLoad.class);
    private String path;

    private HashMap<String,Class> targetClass = new HashMap<>();

    public SnmpTargetLoad(String path){
        this.path=path;
        loadConifg();
    }

    /**
     * 加载指定配置path下的 所有 snmpTarget
     */
    private void loadConifg(){
        logger.info("加载 snmp指标类 start!!!");
        Set<Class<?>> clazzs = ClassUtil.getClasses(path,false);
        for (Class<?> clazz : clazzs) {
            SnmpClassDescription annotation = clazz.getAnnotation(SnmpClassDescription.class);
            if(annotation==null || targetClass.containsKey(annotation.key())){
                logger.info("snmp指标类:{} 无所需注解 或者注解的key已存在",clazz.getName());
            }else{
                targetClass.put(annotation.key(),clazz);
                logger.info("snmp指标类:{}  已加载",clazz.getName());
            }
        }
        logger.info("加载 snmp指标类 end!!!");
    }

    /**
     * 根据一个snmp指标key，获取对应的数据
     * @param snmpTarget
     * @return
     */
    public Object get(String snmpTarget,SnmpService snmpService) throws IllegalAccessException, InstantiationException, IOException {
        if(!targetClass.containsKey(snmpTarget)){
            return null;
        }

        Object result = null;
        Class<?> clazz = targetClass.get(snmpTarget);
        SnmpClassDescription snmpClassDescription = clazz.getAnnotation(SnmpClassDescription.class);
        Field[] fields = clazz.getDeclaredFields();
        String[] oids = new String[fields.length];
        String[] names = new String[fields.length];
        for(int i = 0 ; i < fields.length; i++){
            Field field = fields[i];
            SnmpFieldDescription snmpFieldDescription = field.getAnnotation(SnmpFieldDescription.class);
            oids[i]=snmpFieldDescription.oid();
            names[i]=field.getName();
        }
        if(snmpClassDescription.type()==1){
            Map<String,Object> map = snmpService.oids(oids,names);
            result = snmpMapToObject(map,clazz);
        }else if(snmpClassDescription.type()==2){
            List<Map<String, Object>> sourcelist = snmpService.oidTables(oids,oids,names,10);
            List<Object> targetList = new ArrayList<>();
            for(Map<String,Object> map : sourcelist){
                targetList.add(snmpMapToObject(map,clazz));
            }
            result = targetList;
        }
        return result;
    }

    /**
     * smp的map键值对， 映射到对应的obj
     * @param map
     * @param clazz
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private Object snmpMapToObject(Map<String,Object> map,Class<?> clazz) throws IllegalAccessException, InstantiationException {
        Object result = clazz.newInstance();
        Field[] fields = clazz.getFields();
        for(int i =0 ;i<fields.length;i++){
            Field field = fields[i];
            if(map.containsKey(field.getName())){
                Variable variable = (Variable)map.get(field.getName());
                Class<?> fieldClass = field.getType();
                if(fieldClass==Integer.class){
                    field.set(result,variable.toInt());
                }else if(fieldClass==String.class){
                    field.set(result,variable.toString());
                }//TODO 解析其他数据类型
            }
        }

        return result;
    }
}
