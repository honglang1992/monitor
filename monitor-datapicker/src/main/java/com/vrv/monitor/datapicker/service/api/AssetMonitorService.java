package com.vrv.monitor.datapicker.service.api;

import com.vrv.monitor.datapicker.dao.assetConfig.MonitorSuiteDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Dendi on 2018/1/12.
 */
@Service
public class AssetMonitorService {
    @Resource
    private MonitorSuiteDao monitorSuiteDao ;

    public String getMonitorPeriodBySuitName(String suitName){
        String period = monitorSuiteDao.getMonitorPeriodBySuitName(suitName);
        if(StringUtils.isEmpty(period)){
            period = "60";
        }
        return period;
    }
}
