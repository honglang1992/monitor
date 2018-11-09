package com.crazyitn.test.date;

import com.vrv.monitor.core.util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author dendi
 * @date 2018/10/11 14:53
 */
public class DateUtilTest {
    public static void main(String[] args){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();// 获取当前日期
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        calendar.set(Calendar.MONTH,calendar.get(Calendar.MONTH)+12);

        System.out.println(format.format(calendar.getTime()));
    }
}
