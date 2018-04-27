package com.vrv.monitor.datapicker.service.imp.common;

import com.vrv.monitor.datapicker.common.PolicyKey;
import com.vrv.monitor.datapicker.controller.InitJob;
import com.vrv.monitor.datapicker.model.common.MonitorPolicy;
import com.vrv.monitor.datapicker.service.common.MonitorPolicyService;
import com.vrv.monitor.datapicker.service.job.*;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Dendi on 2017/10/17.
 */
@Service
public class MonitorPolicyServiceImpl implements MonitorPolicyService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final Map<String,Class<? extends Job>> policyMap ;
    static {
        policyMap = new HashMap<>();
        policyMap.put(PolicyKey.zabbix,ZabbixMonitor.class);
        policyMap.put(PolicyKey.host,HostMonitor.class);
        policyMap.put(PolicyKey.refresh,TriggerRefreshJob.class);
        policyMap.put(PolicyKey.renCheZheng,RenCheZhengMonitor.class);
    }

    @Override
    public void init(List<MonitorPolicy> policies) {
        if (policies == null || policies.size() == 0) {
            logger.warn("monitor policies is empty");
            return;
        }

        for (MonitorPolicy policy : policies) {
            if(policy.getValid()) add(policy);
        }
    }

    @Override
    public void merge(List<MonitorPolicy> policies) {
        if (policies == null || policies.size() == 0) {
            return;
        }

        for (MonitorPolicy policy : policies) {
            try {
                if (InitJob.quartzManager.exist(policy.getKey())) {
                    if(policy.getValid()){
                        InitJob.quartzManager.modifyJobTime(policy.getKey(), policy.getCronExpression());
                    }else{
                        InitJob.quartzManager.removeJob(policy.getKey());
                    }
                } else  {
                    if(policy.getValid()){
                        add(policy);
                    }
                }
            } catch (Exception e) {
                logger.error("MonitorPolicy merge error", e);
            }
        }
    }

    private void add(MonitorPolicy policy) {
        try {
            logger.warn("add {} job to schedule  started", policy.getKey());
            if(policyMap.containsKey(policy.getKey())){
                InitJob.quartzManager.addJob(policy.getKey(), policyMap.get(policy.getKey()),policy.getCronExpression());
                logger.warn("add {} job to schedule success", policy.getKey());
            }else{
                logger.error("add {} job to schedule failed because of type mismatch ", policy.getKey());
            }
        } catch (Exception e) {
            logger.error("add job to schedule fail because of exception", policy.getKey(), e);
        }
    }

    @Override
    public void deleteAll() {
        try {
            InitJob.quartzManager.clear();
        } catch (SchedulerException e) {
            logger.error("deleteAll job failed", e);
        }
    }

    @Override
    public List<MonitorPolicy> query() throws SchedulerException {
        List<MonitorPolicy> policies = new ArrayList<>();
        List<Trigger> triggers = InitJob.quartzManager.getAllTriggers();
        for (Trigger trigger : triggers) {
            MonitorPolicy monitorPolicy  = new MonitorPolicy();
            CronTrigger ct = (CronTrigger)trigger;
            monitorPolicy.setKey(ct.getKey().getName());
            monitorPolicy.setCronExpression(ct.getCronExpression());
        }
        return policies;
    }
}
