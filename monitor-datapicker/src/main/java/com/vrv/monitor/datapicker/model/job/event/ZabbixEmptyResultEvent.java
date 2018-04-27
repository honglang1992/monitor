package com.vrv.monitor.datapicker.model.job.event;

import java.util.Date;

/**
 * Created by Dendi on 2017/11/8.
 */
public class ZabbixEmptyResultEvent{
    private Date fireDate;

    public ZabbixEmptyResultEvent(Date fireDate) {
        this.fireDate = fireDate;
    }

    public Date getFireDate() {
        return fireDate;
    }

    public void setFireDate(Date fireDate) {
        this.fireDate = fireDate;
    }
}
