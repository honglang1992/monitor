package com.vrv.monitor.datapicker.service.job.event;

import com.vrv.monitor.datapicker.common.PolicyKey;
import com.vrv.monitor.datapicker.controller.InitJob;
import com.vrv.monitor.datapicker.model.job.event.ZabbixEmptyResultEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by Dendi on 2017/11/8.
 */
public class ZabbixEventListener extends Thread implements Observer {

    private final static Logger logger = LoggerFactory.getLogger(ZabbixEventListener.class);

    /**
     * zabbix http请求获取不到数据 三分钟内触发了多少次
     */
    public static List<ZabbixEmptyResultEvent> threeMinsEvent = new Vector<>();

    public ZabbixEventListener(){
        this.start();
    }

    @Override
    public void update(Observable observable, Object o) {
        if(o instanceof ZabbixEmptyResultEvent){
            threeMinsEvent.add((ZabbixEmptyResultEvent)o);
        }
    }

    /**
     * 确保存储的数据都是3min之内的
     */
    @Override
    public void run(){
        while(true){
            //清除非3分钟内的数据
            for(int i = 0; i< threeMinsEvent.size();i++ ){
                ZabbixEmptyResultEvent event = threeMinsEvent.get(i);
                if(System.currentTimeMillis()-event.getFireDate().getTime()>3*60*1000){
                    threeMinsEvent.remove(event);
                    i--;
                }else{
                    //因为后面的触发时间 越来越近 所以不用继续遍历了
                    break;
                }
            }

            Hashtable<String,Object> zabbixTable = InitJob.extraData.get(PolicyKey.zabbix);
            if(zabbixTable==null){
                zabbixTable = new Hashtable<>();
                InitJob.extraData.put(PolicyKey.zabbix,zabbixTable);
            }
            zabbixTable.put("threeMinutesHttpErrorCount",threeMinsEvent.size());

            try {
                TimeUnit.MILLISECONDS.sleep(5000);
            } catch (InterruptedException e) {
                logger.warn("线程休眠失败",e);
            }
        }
    }
}
