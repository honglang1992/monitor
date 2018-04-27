package com.vrv.monitor.datapicker.model.api.AssetMonitorInfo;

import com.vrv.monitor.datapicker.model.api.ApiResponse;

/**
 * Created by Dendi on 2018/1/11.
 */
public class AssetMonitorPeriodGetResponse extends ApiResponse {
    private String period;

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}
