package com.vrv.monitor.core.jvm;

import java.util.Date;

/**
 * Created by Dendi on 2017/10/23.
 */
public class ClientStatus {
    /**
     * 当前进程运行的主机名
     */
    private String host;
    /**
     * 当前进程所在的IP地址
     */
    private String ipAddress;
    /**
     * 空闲内存
     */
    private long freeMemory;
    /**
     * 内存总量
     */
    private long totalMemory;
    /**
     * java虚拟机允许开启的最大的内存
     */
    private long maxMemory;

    /**
     * 操作系统名称
     */
    private String osVersion;

    /**
     * 操作系统名称
     */
    private String osName;

    private String osArch;

    /**
     * 进程号
     */
    private long pid;


    /**
     * 程序启动时间
     */
    private String startTime;

    /**
     * 类所在路径
     */
    private String classPath;

    private String projectPath;

    /**
     * 程序运行时间，单位毫秒
     */
    private long runtime;
    /**
     * 线程总量
     */
    private int threadCount;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public long getFreeMemory() {
        return freeMemory;
    }

    public void setFreeMemory(long freeMemory) {
        this.freeMemory = freeMemory;
    }

    public long getTotalMemory() {
        return totalMemory;
    }

    public void setTotalMemory(long totalMemory) {
        this.totalMemory = totalMemory;
    }

    public long getMaxMemory() {
        return maxMemory;
    }

    public void setMaxMemory(long maxMemory) {
        this.maxMemory = maxMemory;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    public String getProjectPath() {
        return projectPath;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }

    public long getRuntime() {
        return runtime;
    }

    public void setRuntime(long runtime) {
        this.runtime = runtime;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getOsArch() {
        return osArch;
    }

    public void setOsArch(String osArch) {
        this.osArch = osArch;
    }
}
