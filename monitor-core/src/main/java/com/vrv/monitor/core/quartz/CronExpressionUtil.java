package com.vrv.monitor.core.quartz;

import org.springframework.util.Assert;

/**
 * Created by Dendi on 2017/10/13.
 */
public class CronExpressionUtil {
    /**
     * 每隔多少秒执行
     * 其中最大间隔是1天 超过1天按照 一天来算
     * @param seconds
     * @return
     */
    public static String intervalSeconds(int seconds) {
        Assert.state(seconds > 0, "seconds should be greater than 0 ");

        String cronExpression;
        if (seconds < 60) {
            cronExpression = "0/" + String.valueOf(seconds) + " * * * * ?";
        } else if (seconds < 3600) {
            int minus = seconds/60;
            cronExpression = "0 0/"+String.valueOf(minus)+" * * * ?";
        } else if (seconds < 3600 * 24) {
            int hours = seconds/3600;
            cronExpression = "0 0 0/"+String.valueOf(hours)+" * * ?";
        } else {
            cronExpression = "0 0 0 0/1 * ?";
        }
        return cronExpression;
    }
}
