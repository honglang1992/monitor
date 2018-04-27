package com.vrv.monitor.datapicker.service.imp.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.vrv.monitor.core.quartz.JobStatus;
import com.vrv.monitor.core.quartz.QuartzStatus;
import com.vrv.monitor.datapicker.controller.InitJob;
import com.vrv.monitor.datapicker.service.api.RunStatusService;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;

import java.util.Hashtable;
import java.util.List;

/**
 * Created by Dendi on 2017/10/26.
 */
@Service
public class RunStatusServiceImp implements RunStatusService{
    @Override
    public QuartzStatus getQuartzStatus() throws NoSuchFieldException, IllegalAccessException, SchedulerException {

        //获取quartz调度的基本信息
        QuartzStatus quartzStatus = InitJob.quartzManager.getQuartzStatus();

        //获取每个job的额外信息
        Hashtable<String,Hashtable<String,Object>> extraData = InitJob.extraData;
        List<JobStatus> jobStatusList = quartzStatus.getJobStatuses();
        for(JobStatus jobStatus : jobStatusList){
            if(extraData.containsKey(jobStatus.getJobName())){
                jobStatus.setExtraData(extraData.get(jobStatus.getJobName()));
            }
        }
        return quartzStatus;
    }
}
