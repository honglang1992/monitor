package com.vrv.monitor.datapicker.model.job;

/**
 * Created by Dendi on 2017/10/24.
 */
public class CpuInfoVO {

    // 设备平均CPU利用率（纵坐标）
    private String usedRate;

    // CPU的核数
    private int count;

    // 进程数
    private int processorNum;

    public String getUsedRate() {
        return usedRate;
    }

    public void setUsedRate(String usedRate) {
        this.usedRate = usedRate;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getProcessorNum() {
        return processorNum;
    }

    public void setProcessorNum(int processorNum) {
        this.processorNum = processorNum;
    }
}
