package com.vrv.monitor.datapicker.service.job;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.vrv.monitor.core.event.EventSource;
import com.vrv.monitor.core.util.GsonUtil;
import com.vrv.monitor.datapicker.dao.assetConfig.MonitorZabbixConfigDao;
import com.vrv.monitor.datapicker.model.assetConfig.MonitorZabbixConfig;
import com.vrv.monitor.datapicker.model.job.MonitorZabbixData;
import com.vrv.monitor.datapicker.model.job.event.ZabbixEmptyResultEvent;
import com.vrv.monitor.datapicker.service.common.ActiveMQConnectionListenerImpl;
import com.zabbix4j.item.ItemGetResponse;
import com.zabbix4j.trigger.TriggerGetResponse;
import org.apache.commons.lang.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import com.vrv.monitor.core.zabbix.ZabbixApiProxy;
import com.vrv.monitor.core.zabbix.ZabbixApiProxyFactory;

import javax.annotation.Resource;
import javax.jms.JMSException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Dendi on 2017/10/13.
 */
public class ZabbixMonitor implements Job {
    private final static Logger logger = LoggerFactory.getLogger(ZabbixMonitor.class);

    @Autowired
    private MonitorZabbixConfigDao zabbixConfigDao;

    @Resource(name = "activeMQConnectionListener")
    private ActiveMQConnectionListenerImpl mqConnectionListener;

    @Value(value = "${assetConfig.networkClass}")
    private String networkClass;

    @Value(value = "${assetConfig.areaCode}")
    private String areaCode;

    @Resource(name="eventSource")
    private EventSource eventSource;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        long startTime = System.currentTimeMillis();
        List<MonitorZabbixConfig> configs = zabbixConfigDao.query(networkClass, areaCode);
        //每个配置生成 一条数据 发送到 mq
        for (MonitorZabbixConfig config : configs) {
            String str = getZabbixData(config);
            if(StringUtils.isEmpty(str)){
                eventSource.addEvent(new ZabbixEmptyResultEvent(new Date()));
                logger.warn("zabbix str is empty ,throw it. config:{}", JSONObject.toJSONString(config));
            }else{
                try {
                    mqConnectionListener.sendMsg(str);
                    logger.debug("zabbix monitor str : {}", str);
                } catch (JMSException e) {
                    logger.error("send msg to mq failed", e);
                }
            }
        }
        long spend = System.currentTimeMillis() - startTime;
        logger.info("zabbix job spend time {}", spend);
    }

    private String getZabbixData(MonitorZabbixConfig config) {
        String result = null;

        ZabbixApiProxy zabbixApiProxy = ZabbixApiProxyFactory.getZabbixApiProxy(config.getServerUrl(), config.getUsername(), config.getPwd());
        MonitorZabbixData data = new MonitorZabbixData();
        data.setAssetGuid(config.getAssetGuid());
        long startTime = System.currentTimeMillis();
        List<ItemGetResponse.Result> items = zabbixApiProxy.getAllItems(config.getHostId());
        long spend = System.currentTimeMillis() - startTime;
        logger.info("zabbix api spend time {}", spend);
        if (items != null && items.size() > 0) {
            //由于目前trigger用不到，这里直接初始化一个空的
//            List<TriggerGetResponse.Result> triggers = zabbixApiProxy.getAllTriggers(config.getHostId());
            List<TriggerGetResponse.Result> triggers = new ArrayList<>();
            zabbixApiProxy.converToModel(items, triggers, data);

            result = JSONObject.toJSONString(data, SerializerFeature.WriteMapNullValue);
        }
        return result;
    }
}