package com.vrv.monitor.datapicker.model.assetConfig;

/**
 * Created by Dendi on 2017/10/11.
 */
public class MonitorSuite {
    private Integer id;

    private String suiteName;

    private String methodName;

    private String inDbMethodName;

    private Integer monitor_freq;

    private Integer in_db_freq;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSuiteName() {
        return suiteName;
    }

    public void setSuiteName(String suiteName) {
        this.suiteName = suiteName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getInDbMethodName() {
        return inDbMethodName;
    }

    public void setInDbMethodName(String inDbMethodName) {
        this.inDbMethodName = inDbMethodName;
    }

    public Integer getMonitor_freq() {
        return monitor_freq;
    }

    public void setMonitor_freq(Integer monitor_freq) {
        this.monitor_freq = monitor_freq;
    }

    public Integer getIn_db_freq() {
        return in_db_freq;
    }

    public void setIn_db_freq(Integer in_db_freq) {
        this.in_db_freq = in_db_freq;
    }
}
