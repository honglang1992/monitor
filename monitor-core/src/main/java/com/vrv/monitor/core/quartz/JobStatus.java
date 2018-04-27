package com.vrv.monitor.core.quartz;

import org.quartz.Trigger;

/**
 * Created by Dendi on 2017/10/27.
 */
public class JobStatus {
    private String jobName;

    private String timeExpression;

    private Trigger.TriggerState triggerState;

    private String lastFireTime;

    private Object ExtraData;

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Trigger.TriggerState getTriggerState() {
        return triggerState;
    }

    public void setTriggerState(Trigger.TriggerState triggerState) {
        this.triggerState = triggerState;
    }

    public String getLastFireTime() {
        return lastFireTime;
    }

    public void setLastFireTime(String lastFireTime) {
        this.lastFireTime = lastFireTime;
    }

    public Object getExtraData() {
        return ExtraData;
    }

    public void setExtraData(Object extraData) {
        ExtraData = extraData;
    }

    public String getTimeExpression() {
        return timeExpression;
    }

    public void setTimeExpression(String timeExpression) {
        this.timeExpression = timeExpression;
    }
}
