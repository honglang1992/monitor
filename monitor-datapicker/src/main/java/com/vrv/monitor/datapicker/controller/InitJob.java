package com.vrv.monitor.datapicker.controller;

import com.vrv.monitor.datapicker.common.SysConstants;
import com.vrv.monitor.datapicker.common.PolicyKey;
import com.vrv.monitor.datapicker.model.assetConfig.MonitorSuite;
import com.vrv.monitor.datapicker.model.common.MonitorPolicy;
import com.vrv.monitor.datapicker.service.assetConfig.MonitorSuiteService;
import com.vrv.monitor.datapicker.service.common.MonitorPolicyService;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Controller;
import com.vrv.monitor.core.quartz.CronExpressionUtil;
import com.vrv.monitor.core.quartz.QuartzManager;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by Dendi on 2017/10/11.
 */
@Controller
public class InitJob implements InitializingBean {
    private final static Logger logger = LoggerFactory.getLogger(InitializingBean.class);

    @Autowired
    private MonitorSuiteService monitorSuiteService;

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Value(value = "${assetConfig.networkClass}")
    private String networkClass;

    @Value(value = "${assetConfig.areaCode}")
    private String areaCode;

    @Value(value="${job.trigger.refresh.period}")
    private String triggerRefreshPeriod;

    @Autowired
    private MonitorPolicyService monitorPolicyService;

    public static QuartzManager quartzManager;

    /**
     * 用于存储每个job的额外监控信息
     */
    public static Hashtable<String,Hashtable<String,Object>> extraData = new Hashtable<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("采集设备 所属网络:{} 地区码{}", networkClass, areaCode);

        quartzManager = new QuartzManager(schedulerFactoryBean.getScheduler(), SysConstants.Job_Group_Name, SysConstants.Trigger_Group_Name);

        initMonitorJob();

        addTriggerRefreshJob();
    }

    /**
     * 按照配置 生成多个job 执行
     */
    private void initMonitorJob() throws SchedulerException {
        List<MonitorSuite> onMonitorList = monitorSuiteService.getItemsOnMonitor();
        List<MonitorPolicy> policies = new ArrayList<>();
        for (MonitorSuite item : onMonitorList) {
            MonitorPolicy policy = new MonitorPolicy();
            policy.setKey(item.getSuiteName());
            policy.setCronExpression(CronExpressionUtil.intervalSeconds(item.getMonitor_freq()));
            policy.setValid(true);
            policies.add(policy);
        }
        monitorPolicyService.init(policies);
    }

    /**
     * 添加 时间触发刷新job
     */
    private void addTriggerRefreshJob() throws ParseException, SchedulerException {
        logger.warn("add addTriggerRefreshJob job to schedule  started");
        List<MonitorPolicy> policies = new ArrayList<>();
        MonitorPolicy policy = new MonitorPolicy();
        policy.setKey(PolicyKey.refresh);
        policy.setCronExpression(triggerRefreshPeriod);
        policy.setValid(true);
        policies.add(policy);
        monitorPolicyService.merge(policies);
        logger.warn("add addTriggerRefreshJob job to schedule  success");
    }
}
