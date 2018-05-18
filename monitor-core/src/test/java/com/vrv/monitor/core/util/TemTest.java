package com.vrv.monitor.core.util;

import org.junit.Test;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TemTest {
    @Test
    public void test(){
        double a = 0.0000001d;
        DecimalFormat df = new DecimalFormat("#0.00");
        System.out.println(df.format(a));
    }

    @Test
    public void test2() throws ParseException {
        String date = "2018-04-25";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = simpleDateFormat.parse(date);

        String endDate = DateUtil.addDays(date,15);
        System.out.println(endDate);
    }
}
