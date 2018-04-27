package com.vrv.monitor.datapicker.common;

import com.vrv.monitor.datapicker.service.job.ZabbixMonitor;
import org.quartz.Job;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dendi on 2017/10/17.
 */
public interface PolicyKey {
    String zabbix = "zabbix";

    String host = "host";

    String renCheZheng = "renCheZheng";

    String processInfo = "processInfo";

    String refresh = "refreshJob";
}
