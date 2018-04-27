package com.vrv.monitor.core.quartz;

import com.vrv.monitor.core.util.DateUtil;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: 定时任务管理类
 * 关联的job和trigger 名称一样，方便管理
 * @ClassName: QuartzManager
 * @Copyright: Copyright (c) 2014
 */
public class QuartzManager {

    private Scheduler scheduler;

    private String jobGroupName;

    private String triggerGroupName;

    public QuartzManager(Scheduler scheduler, String jobGroupName, String triggerGroupName) throws SchedulerException {
        this.jobGroupName = jobGroupName;
        this.triggerGroupName = triggerGroupName;
        this.scheduler = scheduler;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    /**
     * @param jobName 任务名
     * @param cls     任务
     * @param time    时间设置，参考quartz说明文档
     * @Description: 添加一个定时任务
     */
    public void addJob(String jobName, Class<? extends Job> cls, String time) throws ParseException, SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(cls).withIdentity(jobName, jobGroupName).build();
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(time);
        cronScheduleBuilder.withMisfireHandlingInstructionDoNothing();
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, triggerGroupName).withSchedule(cronScheduleBuilder).build();
        scheduler.scheduleJob(jobDetail, trigger);
    }

    public boolean exist(String jobName) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, triggerGroupName);
        Trigger trigger = scheduler.getTrigger(triggerKey);
        return trigger == null ? false : true;
    }

    /**
     * @param jobName
     * @param time
     * @Description: 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
     */
    public void modifyJobTime(String jobName, String time) throws Exception {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, triggerGroupName);

        CronTrigger oldTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);

        if (!oldTrigger.getCronExpression().equals(time)) {
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(time);
            Trigger newTrigger = TriggerBuilder.newTrigger().withIdentity(jobName, triggerGroupName).withSchedule(cronScheduleBuilder).build();
            scheduler.rescheduleJob(triggerKey, newTrigger);
        }
    }

    /**
     * @param jobName
     * @Description: 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
     */
    public void removeJob(String jobName) throws SchedulerException {
        scheduler.pauseTrigger(new TriggerKey(jobName, triggerGroupName));
        scheduler.unscheduleJob(new TriggerKey(jobName, triggerGroupName));
        scheduler.deleteJob(new JobKey(jobName, jobGroupName));
    }

    public void clear() throws SchedulerException {
        scheduler.clear();
    }

    public List<Trigger> getAllTriggers() throws SchedulerException {
        List<Trigger> triggers = new ArrayList<>();
        for (TriggerKey triggerKey : scheduler.getTriggerKeys(GroupMatcher.triggerGroupEquals(this.triggerGroupName))) {
            Trigger trigger = scheduler.getTrigger(triggerKey);
            triggers.add(trigger);
        }
        return triggers;
    }

    public QuartzStatus getQuartzStatus() throws SchedulerException, NoSuchFieldException, IllegalAccessException {
        QuartzStatus quartzStatus = new QuartzStatus();

        SchedulerMetaData metaData = scheduler.getMetaData();
        Date startDate = metaData.getRunningSince();
        if (startDate == null) {
            quartzStatus.setStartTime("");
        } else {
            quartzStatus.setStartTime(DateUtil.format(startDate));
        }

        quartzStatus.setThreadCount(metaData.getThreadPoolSize());

        quartzStatus.setExecutingThread(scheduler.getCurrentlyExecutingJobs().size());

        List<JobStatus> jobStatuses = new ArrayList<>();
        List<Trigger> triggers = getAllTriggers();
        for(Trigger trigger : triggers){
            JobStatus jobStatus = new JobStatus();
            jobStatus.setJobName(trigger.getKey().getName());
            jobStatus.setTriggerState( scheduler.getTriggerState(trigger.getKey()));

            if(trigger instanceof CronTrigger){
                jobStatus.setTimeExpression(((CronTrigger)trigger).getCronExpression());
            }

            Date lastFireDate = trigger.getPreviousFireTime();
            if(lastFireDate==null){
                jobStatus.setLastFireTime("");
            }else{
                jobStatus.setLastFireTime(DateUtil.format(lastFireDate));
            }
            jobStatuses.add(jobStatus);
        }
        quartzStatus.setJobStatuses(jobStatuses);

        return quartzStatus;
    }
}