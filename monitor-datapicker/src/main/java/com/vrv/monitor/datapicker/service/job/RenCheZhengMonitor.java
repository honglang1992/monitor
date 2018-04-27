package com.vrv.monitor.datapicker.service.job;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.vrv.monitor.core.util.DateUtil;
import com.vrv.monitor.core.util.HttpUtil;
import com.vrv.monitor.datapicker.dao.assetConfig.MonitorHttpConfigDao;
import com.vrv.monitor.datapicker.model.assetConfig.MonitorHttpConfig;
import com.vrv.monitor.datapicker.model.assetConfig.MonitorSnmpConfig;
import com.vrv.monitor.datapicker.model.job.MonitorRenCheZhengData;
import com.vrv.monitor.datapicker.service.common.ActiveMQConnectionListenerImpl;
import org.apache.http.HttpException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import javax.jms.JMSException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Dendi on 2017/11/25.
 */
public class RenCheZhengMonitor implements Job {

    @Autowired
    MonitorHttpConfigDao httpConfigDao ;

    @Value(value = "${assetConfig.networkClass}")
    private String networkClass;

    @Value(value = "${assetConfig.areaCode}")
    private String areaCode;

    @Resource(name="activeMQConnectionListener")
    private ActiveMQConnectionListenerImpl mqConnectionListener;

    private final static Logger logger = LoggerFactory.getLogger(HostMonitor.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        long startTime = System.currentTimeMillis();
        //读取asset表的 人车证的监控配置
        List<MonitorHttpConfig> configs = httpConfigDao.query("RenCheZhengHardware", networkClass, areaCode);
        //遍历执行
        for (MonitorHttpConfig config : configs) {
            try {
                String str = getData(config);
                mqConnectionListener.sendMsg(str);
                logger.debug("RenCheZheng monitor str : {}",str);
            } catch (JMSException e) {
                logger.error("send msg to mq failed",e);
            } catch (Exception e){
                logger.error("host monitor job excute failed",e);
            }
        }
        long spend = System.currentTimeMillis()-startTime;
        logger.info("renchezheng job spend time {}",spend);
    }

    /**
     * 获取指定指定数据
     * @param config
     * @return
     */
    private String getData(MonitorHttpConfig config)  {
        MonitorRenCheZhengData data = new MonitorRenCheZhengData();

        JSONObject jsonObject = JSONObject.parseObject(config.getHttpConfigJson());
        String apiUrl = jsonObject.getString("apiUrl");
        try {
            String httpResponseStr = HttpUtil.getInstance().get(apiUrl);
            JSONObject httpResponse = JSONObject.parseObject(httpResponseStr);
            JSONObject StateObject =  httpResponse.getJSONObject("State");
            if(0 != StateObject.getIntValue("Code")){
                data.setIcmpPing(false);
            }else{
                JSONArray models = httpResponse.getJSONArray("Models");
                for(int i =0 ; i < models.size(); i++){
                    JSONObject model = models.getJSONObject(i);
                    int type = model.getIntValue("Type");
                    int isConnected = model.getIntValue("IsConnected");
                    //1车牌识别器、2道闸 、11人证摄像头、12身份证阅读器、14一体式门禁
                    switch (model.getIntValue("Type")){
                        case 1: data.setLicensePlateRecognizer(isConnected);break;
                        case 2: data.setBarrier(isConnected);break;
                        case 11:data.setPersonnelCamera(isConnected);break;
                        case 12://由于有可能有两个身份证阅读器 ，所以只要有一个异常，则身份证阅读器异常
                            if(data.getIdCardReader()==null || data.getIdCardReader()==1){
                                data.setIdCardReader(isConnected);
                            }
                            break;
                        case 14:data.setAccessController(isConnected);break;
                        default: break;
                    }
                }
            }
        } catch (Exception e) {
            data.setIcmpPing(false);
            logger.error("RenCheZheng http接口异常",e);
        }

        data.setAssetGuid(config.getAssetGuid());
        String result = JSONObject.toJSONString(data, SerializerFeature.WriteMapNullValue);
        return result;
    }
}
