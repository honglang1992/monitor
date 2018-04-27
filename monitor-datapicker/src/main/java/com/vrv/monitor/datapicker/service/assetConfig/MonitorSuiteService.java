package com.vrv.monitor.datapicker.service.assetConfig;

import com.vrv.monitor.datapicker.dao.assetConfig.MonitorSuiteDao;
import com.vrv.monitor.datapicker.model.assetConfig.MonitorSuite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Dendi on 2017/10/11.
 */
@Service("monitorSuiteService")
public class MonitorSuiteService {
    @Resource
    private MonitorSuiteDao monitorSuiteDao ;

    public MonitorSuite getItem (int id){
        MonitorSuite item = monitorSuiteDao.getMonitorSuite(id);
        return item;
    }

    public List<MonitorSuite> getItemsOnMonitor(){
        return monitorSuiteDao.getItemsOnMonitor();
    }

}
