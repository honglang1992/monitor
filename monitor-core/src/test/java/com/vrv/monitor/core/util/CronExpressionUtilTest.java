package com.vrv.monitor.core.util;

import com.vrv.monitor.core.quartz.CronExpressionUtil;
import org.junit.Test;

/**
 * Created by Dendi on 2017/12/18.
 */
public class CronExpressionUtilTest {
    @Test
    public void testGuid(){
        String expression = CronExpressionUtil.intervalSeconds(3700);
        System.out.println(expression);
    }
}
