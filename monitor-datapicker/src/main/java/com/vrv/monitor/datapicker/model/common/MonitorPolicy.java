package com.vrv.monitor.datapicker.model.common;

/**
 * Created by Dendi on 2017/10/16.
 * 监控策略
 */
public class MonitorPolicy {

    /**
     * 唯一标识
     */
    private String key;

    /**
     * 监控频率 cron表达式
     */
    private String cronExpression;

    /**
     * 当前监控策略是否有效
     */
    private Boolean isValid;

    /**
     * 额外信息
     */
    private String extra;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
