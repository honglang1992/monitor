package com.vrv.monitor.core.util;

import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

import static org.junit.Assert.*;

public class DateUtilTest {

    @Test
    public void compareDays() throws ParseException {
        Date startDate= DateUtil.parseDate("2019-02-01",DateUtil.DATE_PATTERN);
        Date endDate = DateUtil.parseDate("2019-03-01",DateUtil.DATE_PATTERN);
        System.out.println(DateUtil.compareDays(startDate,endDate));
    }
}