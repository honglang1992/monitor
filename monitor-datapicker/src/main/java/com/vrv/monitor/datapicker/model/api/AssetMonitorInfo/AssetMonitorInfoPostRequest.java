package com.vrv.monitor.datapicker.model.api.AssetMonitorInfo;

/**
 * Created by Dendi on 2018/1/11.
 */
public class AssetMonitorInfoPostRequest {
    private String assetId;

    private String monitorInfo;

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getMonitorInfo() {
        return monitorInfo;
    }

    public void setMonitorInfo(String monitorInfo) {
        this.monitorInfo = monitorInfo;
    }
}
