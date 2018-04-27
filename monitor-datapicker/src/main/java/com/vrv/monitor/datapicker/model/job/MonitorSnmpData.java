package com.vrv.monitor.datapicker.model.job;

import java.util.List;

/**
 * Created by Dendi on 2017/10/24.
 */
public class MonitorSnmpData {
    /** cpu */
    // 设备guid
    private String assetGuid;

    // 收集时间
    private String inDate;


    // 设备平均CPU利用率
    private String cpuUsedRate;

    // CPU的核数
    private Integer cpuCount;

    // 进程数
    private Integer cpuProcessorNum;


    // 逻辑磁盘总容量
    private String diskTotal;
    // 逻辑磁盘已使用容量
    private String diskUsed;
    // 逻辑磁盘利用率
    private String diskUsedRate;
    
    // 内存大小
    private String ramSize;
    // 已用内存
    private String ramUsed;
    // 已用百分比
    private String ramPercentAge;

    //tomcat是否运行(0停止，1运行)
    private String tomcatIsRunning;
    //数据库是否运行(0停止，1运行)
    private String databaseIsRunning;
    
    
    /*新增属性部分 */
    /** 所有磁盘信息JSON字符串*/
    private String  diskInfoVOListJson;
    /** 运行时长*/
    private String runningTime;
    

    public String getAssetGuid() {
        return assetGuid;
    }

    public void setAssetGuid(String assetGuid) {
        this.assetGuid = assetGuid;
    }

    public String getInDate() {
        return inDate;
    }

    public void setInDate(String inDate) {
        this.inDate = inDate;
    }

    public String getCpuUsedRate() {
        return cpuUsedRate;
    }

    public void setCpuUsedRate(String cpuUsedRate) {
        this.cpuUsedRate = cpuUsedRate;
    }

    public Integer getCpuCount() {
        return cpuCount;
    }

    public void setCpuCount(Integer cpuCount) {
        this.cpuCount = cpuCount;
    }

    public Integer getCpuProcessorNum() {
        return cpuProcessorNum;
    }

    public void setCpuProcessorNum(Integer cpuProcessorNum) {
        this.cpuProcessorNum = cpuProcessorNum;
    }

    public String getDiskTotal() {
        return diskTotal;
    }

    public void setDiskTotal(String diskTotal) {
        this.diskTotal = diskTotal;
    }

    public String getDiskUsed() {
        return diskUsed;
    }

    public void setDiskUsed(String diskUsed) {
        this.diskUsed = diskUsed;
    }

    public String getDiskUsedRate() {
        return diskUsedRate;
    }

    public void setDiskUsedRate(String diskUsedRate) {
        this.diskUsedRate = diskUsedRate;
    }

    public String getRamSize() {
        return ramSize;
    }

    public void setRamSize(String ramSize) {
        this.ramSize = ramSize;
    }

    public String getRamUsed() {
        return ramUsed;
    }

    public void setRamUsed(String ramUsed) {
        this.ramUsed = ramUsed;
    }

    public String getRamPercentAge() {
        return ramPercentAge;
    }

    public void setRamPercentAge(String ramPercentAge) {
        this.ramPercentAge = ramPercentAge;
    }

    public String getTomcatIsRunning() {
        return tomcatIsRunning;
    }

    public void setTomcatIsRunning(String tomcatIsRunning) {
        this.tomcatIsRunning = tomcatIsRunning;
    }

    public String getDatabaseIsRunning() {
        return databaseIsRunning;
    }

    public void setDatabaseIsRunning(String databaseIsRunning) {
        this.databaseIsRunning = databaseIsRunning;
    }

	public String getDiskInfoVOListJson() {
		return diskInfoVOListJson;
	}

	public void setDiskInfoVOListJson(String diskInfoVOListJson) {
		this.diskInfoVOListJson = diskInfoVOListJson;
	}

	public String getRunningTime() {
		return runningTime;
	}

	public void setRunningTime(String runningTime) {
		this.runningTime = runningTime;
	}

}
