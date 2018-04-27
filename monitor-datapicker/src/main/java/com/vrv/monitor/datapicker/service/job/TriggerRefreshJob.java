
package com.vrv.monitor.datapicker.service.job;

import com.vrv.monitor.datapicker.model.assetConfig.MonitorSuite;
import com.vrv.monitor.datapicker.model.common.MonitorPolicy;
import com.vrv.monitor.datapicker.service.assetConfig.MonitorSuiteService;
import com.vrv.monitor.datapicker.service.common.MonitorPolicyService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import com.vrv.monitor.core.quartz.CronExpressionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dendi on 2017/10/13.
 */
@DisallowConcurrentExecution
public class TriggerRefreshJob implements Job {

    private final static Logger logger = LoggerFactory.getLogger(InitializingBean.class);

    @Autowired
    private MonitorSuiteService monitorSuiteService;

    @Autowired
    private MonitorPolicyService monitorPolicyService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        List<MonitorSuite> onMonitorList = monitorSuiteService.getItemsOnMonitor();
        List<MonitorPolicy> policies = new ArrayList<>();
        for(MonitorSuite item : onMonitorList){
            MonitorPolicy policy = new MonitorPolicy();
            policy.setKey(item.getSuiteName());
            policy.setCronExpression(CronExpressionUtil.intervalSeconds(item.getMonitor_freq()));
            policy.setValid(true);
            policies.add(policy);
        }
        monitorPolicyService.merge(policies);
    }
}
