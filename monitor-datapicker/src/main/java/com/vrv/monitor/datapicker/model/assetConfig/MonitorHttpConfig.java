package com.vrv.monitor.datapicker.model.assetConfig;

/**
 * Created by Dendi on 2017/11/28.
 * Http接口监控的设备
 */
public class MonitorHttpConfig {

    private String assetGuid;

    private String httpConfigJson;

    public String getAssetGuid() {
        return assetGuid;
    }

    public void setAssetGuid(String assetGuid) {
        this.assetGuid = assetGuid;
    }

    public String getHttpConfigJson() {
        return httpConfigJson;
    }

    public void setHttpConfigJson(String httpConfigJson) {
        this.httpConfigJson = httpConfigJson;
    }
}
