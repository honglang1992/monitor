package com.vrv.monitor.datapicker.service.api;

import com.vrv.monitor.core.quartz.QuartzStatus;
import org.quartz.SchedulerException;

/**
 * Created by Dendi on 2017/10/26.
 */
public interface RunStatusService {

    QuartzStatus getQuartzStatus() throws NoSuchFieldException, IllegalAccessException, SchedulerException;
}
