package com.vrv.monitor.core.quartz;

import org.quartz.Job;

import java.util.List;

/**
 * Created by Dendi on 2017/10/23.
 */
public class QuartzStatus {
    private String startTime;

    private int threadCount;

    private int executingThread;

    private List<JobStatus> jobStatuses;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    public List<JobStatus> getJobStatuses() {
        return jobStatuses;
    }

    public void setJobStatuses(List<JobStatus> jobStatuses) {
        this.jobStatuses = jobStatuses;
    }

    public int getExecutingThread() {
        return executingThread;
    }

    public void setExecutingThread(int executingThread) {
        this.executingThread = executingThread;
    }
}
