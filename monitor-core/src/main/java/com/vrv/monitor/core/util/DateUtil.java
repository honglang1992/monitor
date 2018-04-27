package com.vrv.monitor.core.util;

import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {

	public static final int YEAR_RETURN = 0;

	public static final int MONTH_RETURN = 1;

	public static final int DAY_RETURN = 2;

	public static final int HOUR_RETURN = 3;

	public static final int MINUTE_RETURN = 4;

	public static final int SECOND_RETURN = 5;

	public static final String DATE_PATTERN = "yyyy-MM-dd";
	public static final String DATE_MM = "yyyy-MM";

	public static final String TIME_PATTERN = "HH:mm:ss";

	public static final String DATE_PATTERN_DB = "yyyy-MM-dd HH:mm:ss";

	public static final String DATE_PATTERN_STR = "yyyyMMddHHmmss";
	public static final String DATE_PATTERN_NOW = "yyyyMMdd";
	/**
	 * 精确到毫秒的完整时间 如：yyyy-MM-dd HH:mm:ss.S
	 */
	public static final String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";
	/**
	 * 元年时间 0000-01-01 00:00:00
	 */
	public static final String FIRST_TIME = "0000-01-01 00:00:00";

	/**
	 * 末年时间 9999-12-31 23:59:59
	 */
	public static final String LAST_TIME = "9999-12-31 23:59:59";

	public static HashMap<String, String> numMapWeek = new HashMap<String, String>();

	static {

		numMapWeek.put("1", "星期一");
		numMapWeek.put("2", "星期二");
		numMapWeek.put("3", "星期三");
		numMapWeek.put("4", "星期四");
		numMapWeek.put("5", "星期五");
		numMapWeek.put("6", "星期六");
		numMapWeek.put("0", "星期日");

	}

	public static Date format(String strDate, String pattern) {
		try {
			return StringUtils.isBlank(strDate) ? null : new SimpleDateFormat(pattern).parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取当年的第一天
x	 * @return
	 */
	public static Date getCurrYearFirst() {
		Calendar currCal = Calendar.getInstance();
		int currentYear = currCal.get(Calendar.YEAR);
		return getYearFirst(currentYear);
	}

	/**
	 * 获取某年第一天日期
	 * 
	 * @param year
	 *            年份
	 * @return Date
	 */
	public static Date getYearFirst(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		Date currYearFirst = calendar.getTime();
		return currYearFirst;
	}

	/**
	 * 得到当前本周的周一时间
	 * 
	 * @return yyyy-MM-dd
	 */
	public static String getMondayOfThisWeek() {
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0)
			day_of_week = 7;
		c.add(Calendar.DATE, -day_of_week + 1);
		return df2.format(c.getTime());
	}

	/**
	 *  得到当天的00点00分00秒
	 */
	public static Date getBeginTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String yyyymmdd = df.format(new Date());
		String yyyymmdd000000 = yyyymmdd.concat(" 00:00:00");
		Date dtBegin = DateUtil.format(yyyymmdd000000, DateUtil.DATE_PATTERN_DB);
		return dtBegin;
	}
	
	/**
	 * 得到本周周日
	 * 
	 * @return yyyy-MM-dd
	 */
	public static String getSundayOfThisWeek() {
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0)
			day_of_week = 7;
		c.add(Calendar.DATE, -day_of_week + 7);
		return df2.format(c.getTime());
	}

	/**
	 * @method: 得到本月第一天
	 * @Description: yyyy-MM-dd
	 * @author whliujinhui
	 * @throws
	 */
	public static String getMonthFirstDay() {
		// 获取前月的第一天
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();// 获取当前日期
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		return format.format(calendar.getTime());
	}

	
	public static String getMonthFirstDayYYYYMMDDHHMISS() {
		// 获取前月的第一天
		SimpleDateFormat format = new SimpleDateFormat(DateUtil.DATE_PATTERN_DB);
		Calendar calendar = Calendar.getInstance();// 获取当前日期
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		return format.format(calendar.getTime());
	}
	
	/**
	 * 获得当前月份
	 * 
	 * @return
	 */
	public static int getCurrentMouth() {
		Calendar instance = Calendar.getInstance();
		int mouth = instance.get(Calendar.MONTH) + 1;
		return mouth;
	}

	/**
	 * 获得当前的年份
	 * 
	 * @return
	 */
	public static int getCurrentYear() {
		Calendar instance = Calendar.getInstance();
		int year = instance.get(Calendar.YEAR);
		return year;
	}
    
	public static int getCurrentDay(){
		Calendar instance = Calendar.getInstance();
		int day = instance.get(Calendar.DATE);
		return day;
	}
	
	/**
	 * 判断是否是今年
	 * 
	 * @param time
	 * @return
	 */
	public static boolean isExistYear(String time) {
		Calendar instance = Calendar.getInstance();
		int year = instance.get(Calendar.YEAR);
		String value = String.valueOf(year);
		if (time.contains(value)) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 获得某一个月份的天数
	 * 
	 * @return
	 */
	public static int getMouthDays(String strDate) {
		Calendar instance = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date date = null;
		try {
			date = sdf.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		instance.setTime(date);
		int maximum = instance.getActualMaximum(Calendar.DAY_OF_MONTH);
		return maximum;
	}

	/**
	 * @method: 得到本月最后一天
	 * @Description: TODO
	 * @author whliujinhui
	 * @throws
	 */
	public static String getLastDayOfThisMonth() {
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		String last = df2.format(ca.getTime());
		return last;
	}

	public static Date getbeforeYear() {
		Calendar calendar = Calendar.getInstance();
		Date date = new Date(System.currentTimeMillis());
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, -1);
		date = calendar.getTime();
		return date;
	}

	/**
	 * 获取时间戳
	 */
	public static String getTimeString() {
		SimpleDateFormat df = new SimpleDateFormat(FORMAT_FULL);
		Calendar calendar = Calendar.getInstance();
		return df.format(calendar.getTime());
	}

	/*
	 * 星期转换
	 */
	public static String convertWeekByNum(String weekNum) {
		return numMapWeek.get(weekNum);
	}

	/**
	 * 功能：格式时间为指定样式(yyyy-MM-dd HH:mm:ss)
	 * 
	 * @param date
	 *            ：需要格式化时间
	 * @return 返回格式后的字符串，样式 DATE_PATTERN_DB
	 */
	public static String format(Date date) {
		SimpleDateFormat formatTool = new SimpleDateFormat();
		formatTool.applyPattern(DATE_PATTERN_DB);
		return formatTool.format(date);
	}

	/**
	 * 将yyyymmdd转换为时间戳进行运算处理
	 */
	public static long getTimeTostamp(String DateTime) {
		long time = 0;
		String strDateTime = String.valueOf(DateTime);
		SimpleDateFormat formatTool = new SimpleDateFormat();
		formatTool.applyPattern(DATE_PATTERN_NOW);
		try {
			Date date = formatTool.parse(strDateTime);
			time = date.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return time;

	}

	/**
	 * 将标准时间yyyy-mm-dd HH:mm:ss转换成时间戳格式
	 * 
	 * @param DateTime
	 * @return
	 */
	public static long getStandradTimeTostamp(String DateTime) {
		long time = 0;
		String strDateTime = String.valueOf(DateTime);
		SimpleDateFormat formatTool = new SimpleDateFormat();
		formatTool.applyPattern(DATE_PATTERN_DB);
		try {
			Date date = formatTool.parse(strDateTime);
			time = date.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return time;

	}

	/**
	 * 获取YYYYmmdd
	 * 
	 * @param date
	 * @return
	 */
	public static String formatPattern(Date date) {
		SimpleDateFormat formatTool = new SimpleDateFormat();
		formatTool.applyPattern(DATE_PATTERN_NOW);
		return formatTool.format(date);
	}

	/**
	 * 格式化日期
	 * 
	 * @param date
	 *            日期
	 * @param pattern
	 *            格式化字符串
	 * @return
	 */
	public static String format(Date date, String pattern) {
		SimpleDateFormat formatTool = new SimpleDateFormat();
		formatTool.applyPattern(pattern);
		return formatTool.format(date);
	}

	/**
	 * 功能：格式化为没有分隔符的时间（yyMMddHHmmSS）
	 * 
	 * @param date
	 * @return
	 */
	public static String formatStr(Date date) {
		SimpleDateFormat formatTool = new SimpleDateFormat();
		formatTool.applyPattern(DATE_PATTERN_STR);
		return formatTool.format(date);
	}

	public static Date parseDate(String str, String patten) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(patten);
		Calendar cd = Calendar.getInstance();
		cd.setTime(sdf.parse(str));

		return cd.getTime();
	}

	/*
	 * 功能：获得当前星期几 周日返回1，周六返回7
	 */
	public static int getweek(Date date) {

		Calendar a = Calendar.getInstance();
		a.setTime(date);
		int week = a.get(Calendar.DAY_OF_WEEK);
		if (week == 1) {
			return 7;
		} else {
			return week - 1;
		}

	}

	public static String addDays(String s, int n) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
		Calendar cd = Calendar.getInstance();
		cd.setTime(sdf.parse(s));
		cd.add(Calendar.DATE, n);// 增加一天
		// cd.add(Calendar.MONTH, n);//增加一个月
		return sdf.format(cd.getTime());
	}

	public static Date addDays(Date date, int n) {
		Calendar cd = Calendar.getInstance();
		cd.setTime(date);
		cd.add(Calendar.DATE, n);// 增加一天
		// cd.add(Calendar.MONTH, n);//增加一个月
		return cd.getTime();
	}

	public static Date addYears(Date date, int n) {
		Calendar cd = Calendar.getInstance();
		cd.setTime(date);
		cd.add(Calendar.YEAR, n);// 增加一天
		// cd.add(Calendar.MONTH, n);//增加一个月
		return cd.getTime();
	}

	public static Date addMinutes(Date date, int n) {
		Calendar cd = Calendar.getInstance();
		cd.setTime(date);
		cd.add(Calendar.MINUTE, n);// 增加分钟
		return cd.getTime();
	}

	public static String addMonths(String s, int n) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
		Calendar cd = Calendar.getInstance();
		cd.setTime(sdf.parse(s));
		// cd.add(Calendar.DATE, n);// 增加一天
		cd.add(Calendar.MONTH, n);// 增加一个月
		return sdf.format(cd.getTime());
	}

	public static Date addMonths(Date date, int n) {
		Calendar cd = Calendar.getInstance();
		cd.setTime(date);
		// cd.add(Calendar.DATE, n);// 增加一天
		cd.add(Calendar.MONTH, n);// 增加一个月
		return cd.getTime();
	}

	public static long getBetween(String beginTime, String endTime, String formatPattern, int returnPattern) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatPattern);
		Date beginDate = simpleDateFormat.parse(beginTime);
		Date endDate = simpleDateFormat.parse(endTime);

		Calendar beginCalendar = Calendar.getInstance();
		Calendar endCalendar = Calendar.getInstance();
		beginCalendar.setTime(beginDate);
		endCalendar.setTime(endDate);
		switch (returnPattern) {
		case YEAR_RETURN:
			return getByField(beginCalendar, endCalendar, Calendar.YEAR);
		case MONTH_RETURN:
			return getByField(beginCalendar, endCalendar, Calendar.YEAR) * 12 + getByField(beginCalendar, endCalendar, Calendar.MONTH);
		case DAY_RETURN:
			return getTime(beginDate, endDate) / (24 * 60 * 60 * 1000);
		case HOUR_RETURN:
			return getTime(beginDate, endDate) / (60 * 60 * 1000);
		case MINUTE_RETURN:
			return getTime(beginDate, endDate) / (60 * 1000);
		case SECOND_RETURN:
			return getTime(beginDate, endDate) / 1000;
		default:
			return 0;
		}
	}

	private static long getByField(Calendar beginCalendar, Calendar endCalendar, int calendarField) {
		return endCalendar.get(calendarField) - beginCalendar.get(calendarField);
	}

	private static long getTime(Date beginDate, Date endDate) {
		return endDate.getTime() - beginDate.getTime();
	}

	public static String getMouthTime(String[] values) {
		Date date = new Date();
		String dateString = format(date);
		String replaceObject = dateString.substring(8, 10);
		values[0] = addString(values[0]);
		dateString = dateString.replace(replaceObject, values[0]).substring(0, 10);
		dateString = dateString + " " + values[1];
		return dateString;
	}

	private static String addString(String value) {
		if (value.length() == 1) {
			value = "0" + value;
		}
		return value;

	}

	/**
	 * 获得一年前
	 * 
	 * @return
	 */
	public static String getBeforeYear() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, -1);
		String dateString = format(calendar.getTime());
		return dateString;
	}

	public static String getBeforeMouth() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		String dateString = format(calendar.getTime());
		return dateString;

	}

	public static String getBeforeSixMouth() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -6);
		String dateString = format(calendar.getTime());
		return dateString;

	}
	
	
	// 获得前一个月的当期时间(kpi专用)
	public static String getBeforeMouth(String value) {

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		String dateString = format(calendar.getTime());
		dateString = dateString.substring(0, 10) + " " + value;
		return dateString;
	}

	// 获得下个月的当前时间
	public static String getAfterMouth(String value) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, +1);
		String dateString = format(calendar.getTime());
		String replaceObject = dateString.substring(8, 10);
		String targetObject = value.substring(8, 10);
		dateString = dateString.replace(replaceObject, targetObject).substring(0, 10);
		dateString = dateString.substring(0, 10) + " " + value.substring(11, 19);
		return dateString;

	}

	
	
	public static String getAfterMouth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, +1);
		String dateString = format(calendar.getTime());
		return dateString;
	}
    /**
     * 上一天的时间
     * @param date
     * @return
     */
	public static String getNextDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		date = calendar.getTime();
		String defaultStartDate = format(date);
		return defaultStartDate;
	}

	public static String getAfterDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, +1);
		date = calendar.getTime();
		String defaultStartDate = format(date);
		return defaultStartDate;
	}

	/**
	 * 添加小时
	 * 
	 * @param date
	 *            当前时间
	 * @param i
	 *            小时数
	 * @return
	 */
	public static Date addHours(Date date, int i) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, i);
		date = calendar.getTime();

		return date;
	}

	/**
	 * 添加秒
	 * 
	 * @param date
	 *            当前时间
	 * @param i
	 *            小时数
	 * @return
	 */
	public static Date addSeconds(Date date, int i) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, i);
		date = calendar.getTime();

		return date;
	}

	/**
	 * 毫秒
	 * @param date
	 * @param i
	 * @return
	 */
	public static Date addMillSeconds(Date date, long time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if (time > 0) {
			while (time > 0) {
				int span = 0;
				if (time >= (long)Integer.MAX_VALUE) {
					span = Integer.MAX_VALUE;
					time = time - (long)Integer.MAX_VALUE;
				} else {
					span = (int) time + 0;
					time = 0;
				}
				calendar.add(Calendar.MILLISECOND, span);
			}
		} else if (time < 0) {
			while (time < 0) {
				int span = 0;
				if (time <= (long)Integer.MIN_VALUE) {
					span = Integer.MIN_VALUE;
					time = time - (long)Integer.MIN_VALUE;
				} else {
					span = (int) time + 0;
					time = 0;
				}
				calendar.add(Calendar.MILLISECOND, span);
			}
		}
		date = calendar.getTime();

		return date;
	}
	
	
	/**
	 * 获得当前时间多少分钟之前的时间
	 * @param date
	 * @param count
	 * @return
	 */
	public static String getBeforeMinutes(Date date,int count) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, -1*count);
		date = calendar.getTime();
		String defaultStartDate = format(date);
		return defaultStartDate;
	}

	public static String getAfterHour(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, 1);
		date = calendar.getTime();
		String defaultStartDate = format(date);
		return defaultStartDate;
	}

	
	public static String getBeforeHour(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, -1);
		date = calendar.getTime();
		String defaultStartDate = format(date);
		return defaultStartDate;
	}
	
	/**
	 * 获得上一周
	 * 
	 * @param date
	 * @return
	 */
	public static Date getNextWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -7);
		date = calendar.getTime();
		return date;
	}

	public static String getAfterWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, +7);
		date = calendar.getTime();
		String defaultStartDate = format(date);
		return defaultStartDate;
	}

	// 通过星期几，知道当前日期
	public static Date getDateByWeek(String value) {
		Calendar c = Calendar.getInstance();
		Date date = new Date();
		// 默认时，每周第一天为星期日，需要更改一下
		c.setFirstDayOfWeek(Calendar.MONDAY);
		if (value.equals("MON")) {
			c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			date = c.getTime();
		}
		if (value.equals("TUE")) {
			c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
			date = c.getTime();
		}
		if (value.equals("WED")) {
			c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
			date = c.getTime();
		}
		if (value.equals("THU")) {
			c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
			date = c.getTime();
		}
		if (value.equals("FRI")) {
			c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
			date = c.getTime();
		}
		return date;
	}

	/**
	 * 判断设置时间是否超过当前时间
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static boolean timeProblem(String date) throws ParseException {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 设置的时间
		Date setdate = format.parse(date);
		Date nowdata = new Date();
		// 如果当前的时间大于设置的时间
		if (nowdata.getTime() - setdate.getTime() > 0) {
			return false;
		}

		return true;

	}

	/**
	 * 计算两个日期之间相差的天数"MM-dd"
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static boolean daysBetweenOther(String beginDate, String endDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		try {
			Date begin = sdf.parse(beginDate);
			Date end = sdf.parse(endDate);
			if (begin.getTime() - end.getTime() > 0) {
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return false;

	}

	/**
	 * 计算两个日期之间相差的天数(yyyy-MM-dd)
	 * 
	 * @param beginDate
	 *            较小的时间
	 * @param endDate
	 *            较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static boolean daysBetween(String beginDate, String endDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date begin = sdf.parse(beginDate);
			Date end = sdf.parse(endDate);
			if (begin.getTime() - end.getTime() > 0) {
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return false;

	}

	public static int daysBetween(Date smdate, Date bdate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			smdate = sdf.parse(sdf.format(smdate));
			bdate = sdf.parse(sdf.format(bdate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	public static long getMillis(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		long timeInMillis = cal.getTimeInMillis();

		return timeInMillis;
	}

	public static String calunateTime(String dept, String time) {
		Date d = new Date();
		String nowTime = null;
		int times = Integer.parseInt(time);
		if (dept.equals("hour")) {
			nowTime = DateUtil.format(new Date(d.getTime() + times * 60 * 60 * 1000));
			System.out.println(nowTime);
			return nowTime;
		} else {
			Calendar cd = Calendar.getInstance();
			cd.setTime(d);
			cd.add(Calendar.DATE, times);
			nowTime = DateUtil.format(cd.getTime());

		}
		return nowTime;

	}

	/**
	 * 根据日期获得对应的月份
	 */
	public static int calunateMouth(String dateTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int mouth = 0;
		try {
			Date date = sdf.parse(dateTime);
			mouth = date.getMonth() + 1;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return mouth;

	}

	public static String getInitDateTime() {
		Calendar currentDate = new GregorianCalendar();
		currentDate.set(Calendar.HOUR_OF_DAY, 0);
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 0);
		currentDate.set(Calendar.MILLISECOND, 0);
		Date time = currentDate.getTime();
		String format = DateUtil.format(currentDate.getTime());
		return format;
	}

	// TODO
	@SuppressWarnings("deprecation")
	public static void main(String[] argv) throws ParseException {
		//getBetweenMounths("2016-06", "2017-06");
//		String beforeMinutes = getBeforeMinutes(new Date(), 30);
//		System.out.println(beforeMinutes);
		long num = -(long)Integer.MAX_VALUE-(long)100;
		Date addMillSeconds = addMillSeconds(new Date(), num);
		System.out.println(addMillSeconds);
		//
		// String beforeYear = getBeforeYear();
		// System.out.println(beforeYear);
		// String beforeMouth = DateUtil.getBeforeMouth().substring(0,10);
		// String nowTime = DateUtil.format(new Date()).substring(0, 10);
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// Date dBegin = sdf.parse(beforeMouth);
		// Date dEnd = sdf.parse(nowTime);
		// List<Date> date = getDatesBetweenTwoDate(dBegin, dEnd);
		// for(int i=0;i<date.size();i++){
		// System.out.println(sdf.format(date.get(i)));
		// }
		// Date d = new Date();
		// int hours = d.getHours();
		// System.out.println(beforeMouth);
		// System.out.println(nowTime);
		//System.out.println(beforeSixMouth.substring(0, 7));

	}

	/**
	 * 获得两个时间时间的月份
	 */
	public static List<String> getBetweenMounths(String beginTime, String endTime) throws ParseException {
		List<String> list = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date d1 = sdf.parse(beginTime);// 定义起始日期
		Date d2 = sdf.parse(endTime);// 定义结束日期
		Calendar dd = Calendar.getInstance();// 定义日期实例
		dd.setTime(d1);// 设置日期起始时间
		while (dd.getTime().before(d2)) {// 判断是否到结束日期
			String str = sdf.format(dd.getTime());
			list.add(str);
			dd.add(Calendar.MONTH, 1);// 进行当前日期月份加1
		}
		return list;

	}

	/**
	 * 封装获得两个时间内的间隔天数集合
	 * 
	 * @param beginDay
	 * @param endDay
	 * @return
	 */
	public static List<String> getDatesBetweenDays(String beginDay, String endDay) {
		List<String> list = new ArrayList<String>();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
			Date dBegin = sdf.parse(beginDay);
			Date dEnd = sdf.parse(endDay);
			List<Date> date = getDatesBetweenTwoDate(dBegin, dEnd);
			for (int i = 0; i < date.size(); i++) {
				list.add(sdf.format(date.get(i)));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 
	 * @param dateFirst "2008-08-08"
	 * @param dateSecond  "2008-08-24"
	 * @return 返回2008-08-08， 2008-08-09 ， 2008-08-10  到  2008-08-24
	 */
	public static  List<Date> getEvenydayByInterval(String dateFirst, String dateSecond) {
		List<Date> list = new ArrayList<Date>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dateOne = dateFormat.parse(dateFirst);
			Date dateTwo = dateFormat.parse(dateSecond);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dateOne);
			while (calendar.getTime().compareTo(dateTwo) <= 0) {
				list.add(calendar.getTime());
				calendar.add(Calendar.DAY_OF_MONTH, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 两个时间内的 单位间隔中的数据集合
     * @param beginDate 起始时间
     * @param endDate  结束时间
     * @param calendar  时间间隔的单位  如 Calendar.DAY
     * @param interaval  时间间隔数量
	 * @return
	 */
	public static List<Date> getDatesBetweenTwoDate(Date beginDate, Date endDate,int calendar,int interaval) {
		List<Date> lDate = new ArrayList<Date>();
		lDate.add(beginDate);// 把开始时间加入集合
		Calendar cal = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		cal.setTime(beginDate);
		boolean bContinue = true;
		while (bContinue) {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			cal.add(calendar, interaval);
			// 测试此日期是否在指定日期之后
			if (endDate.after(cal.getTime())) {
				lDate.add(cal.getTime());
			} else {
				break;
			}
		}
		lDate.add(endDate);// 把结束时间加入集合
		return lDate;
	}
	
	
	
	/**
	 * java 根据开始和结束日期得到之间所有日期集合
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static List<Date> getDatesBetweenTwoDate(Date beginDate, Date endDate) {
		List<Date> lDate = new ArrayList<Date>();
		lDate.add(beginDate);// 把开始时间加入集合
		Calendar cal = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		cal.setTime(beginDate);
		boolean bContinue = true;
		while (bContinue) {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			cal.add(Calendar.DAY_OF_MONTH, 1);
			// 测试此日期是否在指定日期之后
			if (endDate.after(cal.getTime())) {
				lDate.add(cal.getTime());
			} else {
				break;
			}
		}
		lDate.add(endDate);// 把结束时间加入集合
		return lDate;
	}

	public static Date formatDate() {
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = DateUtil.format(new Date());
		try {
			date = sdf.parse(format);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 计算时间差 参数类型yyyyMMdd
	 * 
	 * @return
	 */
	public static int getAfterTenDays(String dateString) {

		String year = dateString.substring(0, 4);
		String month = dateString.substring(4, 6);
		String day = dateString.substring(6, 8);

		Calendar calendar1 = Calendar.getInstance();
		calendar1.set(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day));
		calendar1.set(Calendar.HOUR_OF_DAY, 0);
		calendar1.set(Calendar.SECOND, 0);
		calendar1.set(Calendar.MINUTE, 0);
		// System.out.println(calendar1.getTime());

		Calendar calendar2 = Calendar.getInstance();
		calendar2.add(Calendar.DATE, 10);
		// System.out.println(calendar2.getTime());
		long l = calendar2.getTimeInMillis() - calendar1.getTimeInMillis();
		int days = new Long(l / (1000 * 60 * 60 * 24)).intValue();

		return days;

	};

	// 判断时间
	public static String dateForStringIsNotNull(boolean date) {

		SimpleDateFormat sf = new SimpleDateFormat(DATE_PATTERN_DB);
		String str = "";
		if (date) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.YEAR, -1);
			str = sf.format(calendar.getTime());
		} else {
			str = sf.format(new Date());
		}
		return str;

	}

	// 获得当前几点钟
	public static int getCurrentHour() {
		Date date = new Date();
		int hour = date.getHours();
		return hour;

	}
	
    /**
     * String转date
     * @param dateTime
     * @return
     */
    public static Date getStringToDate(String dateTime) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN_DB);
        Date date = null;
        try {
            date = sdf.parse(dateTime);            
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    
    /**
     * String转date
     * @param dateTime
     * @return
     */
    public static Date getStringToDateFormat(String dateTime) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
        Date date = null;
        try {
            date = sdf.parse(dateTime);            
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    
    /**
     * String转date
     * @param dateTime
     * @return
     */
    public static String getDateToStringFormat(Date dateTime) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
        String timeString = sdf.format(dateTime);
        return timeString;
    }
    
    
	/**
	 * 获得日期的年份
	 * 
	 * @param dateTime
	 * @return
	 */
	public static int calunateYear(String dateTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		Date dt = null;
		try {
			dt = sdf.parse(dateTime);
			cal.setTime(dt);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int year = cal.get(Calendar.YEAR);
		return year;
	}

	public static int calunateDay(String dateTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		Date dt = null;
		try {
			dt = sdf.parse(dateTime);
			cal.setTime(dt);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int year = cal.get(Calendar.DAY_OF_MONTH);
		return year;
	}
}
