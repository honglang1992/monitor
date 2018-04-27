package com.vrv.monitor.datapicker.model.job;

/**
 * Created by Dendi on 2017/10/24.
 */
public class DiskInfoVO {
    // 设备id
    private String assetId;
    // 磁盘描述
    private String diskName;
    // 磁盘类型
    private String diskType;
    // 逻辑磁盘总容量
    private String diskTotal;
    // 逻辑磁盘已使用容量
    private String diskUsed;
    // 逻辑磁盘利用率
    private String diskUsedRate;
    // 磁盘的单位，如果单位太小，会显示为0 ， 如果磁盘大小为 total1: 198337, free1: 163033, used1:35304 单位为byte， 换算成M为193M， G就为0.18
    private String utils;

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getDiskName() {
        return diskName;
    }

    public void setDiskName(String diskName) {
        this.diskName = diskName;
    }

    public String getDiskType() {
        return diskType;
    }

    public void setDiskType(String diskType) {
        this.diskType = diskType;
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

    public String getUtils() {
        return utils;
    }

    public void setUtils(String utils) {
        this.utils = utils;
    }
}
