package com.dodopal.common.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Pattern;

public class DateUtils {

	public static final String DATE_FULL_STR = "yyyy-MM-dd HH:mm:ss";// 格式：年－月－日
																		// 小时：分钟：秒

	public static final String DATE_FULL_STR_DB = "yyyy/MM/dd HH:mm:ss";// 格式：年－月－日
																		// 小时：分钟：秒

	public static final String DATE_SMALL_STR = "yyyy-MM-dd";// 格式：年－月－日
	public static final String DATE_FORMAT_YYMMDDHHMMSS_STR = "yyyyMMddHHmmss";// 格式：年月日
																				// 小时分钟秒
	public static final String DATE_FORMAT_YYMMDD_STR = "yyyyMMdd";
	public static final String DATE_FORMAT_HHMMSS_STR = "HH:mm:ss";
	public static final String DATE_FORMAT_HHMM_STR = "HH:mm";//小时，分钟
	public static final String DATE_HHMM_STR = "HHmm";//小时，分钟
	public static final String DATE_FORMAT_YYMMDDHHMMSSSSS_STR = "yyyyMMddHHmmssSSS";// 格式：年月日
																						// 小时分钟秒毫秒

	public static final String DAY_TIME_START = "00:00:00";
	public static final String DAY_TIME_END = "23:59:59";

	private DateUtils() {

	}

	/**
	 * 把符合日期格式的字符串转换为日期类型
	 */
	public static Date stringtoDate(String dateStr, String format) {
		Date d = null;
		SimpleDateFormat formater = new SimpleDateFormat(format);
		try {
			formater.setLenient(false);
			d = formater.parse(dateStr);
		} catch (Exception e) {
			d = null;
		}
		return d;
	}

	/**
	 * 把符合日期格式的字符串转换为日期类型
	 */
	public static Date stringtoDate(String dateStr, String format, ParsePosition pos) {
		Date d = null;
		SimpleDateFormat formater = new SimpleDateFormat(format);
		try {
			formater.setLenient(false);
			d = formater.parse(dateStr, pos);
		} catch (Exception e) {
			d = null;
		}
		return d;
	}

	/**
	 * 把日期转换为字符串
	 */
	public static String dateToString(Date date, String format) {
		String result = "";
		SimpleDateFormat formater = new SimpleDateFormat(format);
		try {
			result = formater.format(date);
		} catch (Exception e) {
		}
		return result;
	}

	/**
	 * 获取当前时间的指定格式
	 */
	public static String getCurrDate(String format) {
		return dateToString(new Date(), format);
	}

	public static String dateSub(int dateKind, String dateStr, int amount) {
		Date date = stringtoDate(dateStr, DATE_FULL_STR);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(dateKind, amount);
		return dateToString(calendar.getTime(), DATE_FULL_STR);
	}

	/**
	 * 两个日期相减
	 * 
	 * @return 相减得到的秒数
	 */
	public static long timeSub(String firstTime, String secTime) {
		long first = stringtoDate(firstTime, DATE_FULL_STR).getTime();
		long second = stringtoDate(secTime, DATE_FULL_STR).getTime();
		return (second - first) / 1000;
	}

	/**
	 * 两个日期相减
	 * 
	 * @return 相减得到的秒数
	 */
	public static long timeSub(String firstTime, String secTime, String formater) {
		long first = stringtoDate(firstTime, formater).getTime();
		long second = stringtoDate(secTime, formater).getTime();
		return (second - first) / 1000;
	}

	/**
	 * 获得某月的天数
	 */
	public static int getDaysOfMonth(String year, String month) {
		int days = 0;
		if (month.equals("1") || month.equals("3") || month.equals("5") || month.equals("7") || month.equals("8")
				|| month.equals("10") || month.equals("12")) {
			days = 31;
		} else if (month.equals("4") || month.equals("6") || month.equals("9") || month.equals("11")) {
			days = 30;
		} else {
			if ((Integer.parseInt(year) % 4 == 0 && Integer.parseInt(year) % 100 != 0)
					|| Integer.parseInt(year) % 400 == 0) {
				days = 29;
			} else {
				days = 28;
			}
		}

		return days;
	}

	/**
	 * 获取某年某月的天数
	 */
	public static int getDaysOfMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, 1);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获得当前日期
	 */
	public static int getToday() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.DATE);
	}

	/**
	 * 获得当前月份
	 */
	public static int getToMonth() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获得当前年份
	 */
	public static int getToYear() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 返回日期的天
	 */
	public static int getDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DATE);
	}

	/**
	 * 返回日期的年
	 */
	public static int getYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 返回日期的月份，1-12
	 */
	public static int getMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 计算两个日期相差的天数，如果date2 > date1 返回正数，否则返回负数
	 */
	public static long dayDiff(Date date1, Date date2) {
		return (date2.getTime() - date1.getTime()) / 86400000;
	}

	/**
	 * 比较两个日期的年差
	 */
	public static int yearDiff(String before, String after) {
		Date beforeDay = stringtoDate(before, DATE_SMALL_STR);
		Date afterDay = stringtoDate(after, DATE_SMALL_STR);
		return getYear(afterDay) - getYear(beforeDay);
	}

	/**
	 * 比较指定日期与当前日期的差
	 */
	public static int yearDiffCurr(String after) {
		Date beforeDay = new Date();
		Date afterDay = stringtoDate(after, DATE_SMALL_STR);
		return getYear(beforeDay) - getYear(afterDay);
	}

	/**
	 * 获取每月的第一周
	 */
	public static int getFirstWeekdayOfMonth(int year, int month) {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.SATURDAY); // 星期天为第一天
		c.set(year, month - 1, 1);
		return c.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 获取每月的最后一周
	 */
	public static int getLastWeekdayOfMonth(int year, int month) {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.SATURDAY); // 星期天为第一天
		c.set(year, month - 1, getDaysOfMonth(year, month));
		return c.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 获得当前日期字符串，格式"yyyy-MM-dd HH:mm:ss"
	 * 
	 * @return
	 */
	public static String getNow() {
		Calendar today = Calendar.getInstance();
		return dateToString(today.getTime(), DATE_FULL_STR);
	}

	/**
	 * 判断日期是否有效,包括闰年的情况
	 * 
	 * @param date
	 *            YYYY-mm-dd
	 * @return
	 */
	public static boolean isDate(String date) {
		StringBuffer reg = new StringBuffer("^((\\d{2}(([02468][048])|([13579][26]))-?((((0?");
		reg.append("[13578])|(1[02]))-?((0?[1-9])|([1-2][0-9])|(3[01])))");
		reg.append("|(((0?[469])|(11))-?((0?[1-9])|([1-2][0-9])|(30)))|");
		reg.append("(0?2-?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][12");
		reg.append("35679])|([13579][01345789]))-?((((0?[13578])|(1[02]))");
		reg.append("-?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))");
		reg.append("-?((0?[1-9])|([1-2][0-9])|(30)))|(0?2-?((0?[");
		reg.append("1-9])|(1[0-9])|(2[0-8]))))))");
		Pattern p = Pattern.compile(reg.toString());
		return p.matcher(date).matches();
	}

	/*
	 * 返回当前日期String，格式：yyyyMMddHHmmss
	 */
	public static String getCurrTime() {
		return getCurrentDateByFormat("yyyyMMddHHmmss");
	}

	/*
	 * 功能：取应用服务器日期 返回当前日期String，格式：yyyy-MM-dd HH:mm:ss
	 */
	public static String getDateTime() {
		return getCurrentDateByFormat("yyyy-MM-dd HH:mm:ss");
	}

	/*
	 * 返回当前日期String，格式：yyyyMMddHHmmssSSS
	 */
	public static String getCurrSdTime() {
		return getCurrentDateByFormat("yyyyMMddHHmmssSSS");
	}

	public static String getCurrentDateByFormat(String formatStr) {
		long currentTime = System.currentTimeMillis();
		java.util.Date date = new java.util.Date(currentTime);
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(formatStr);
		return formatter.format(date);
	}

	/*
	 * 功能：转换long型为日期型字串 yan_add－20061212
	 */
	public static String getDateTime(long al_datetime) {
		java.util.Date date = new java.util.Date(al_datetime);
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(date);
	}

	public static String getDateString(java.util.Date inDate) {
		return inDate.toString();
	}

	public static java.util.Date getDateNDays(java.util.Date date, int days) {// 把给定日期与给定天数进行加减运算,返回一个新日期
		if (days > 36500 || days < -36500) {
			System.out.println("请把日期限制在100年内");
			return null;
		}
		long l1 = 24, l2 = 60, l3 = 1000, l4 = days;
		long lDays = l1 * l2 * l2 * l3 * l4; // 所改变天数的毫秒数
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		long lCurrentDate = calendar.getTimeInMillis(); // 给定日期的毫秒日期
		long lUpdatedDate = lCurrentDate + lDays; // 给定日期与给定天数运算后的毫秒日期
		java.util.Date dateNew = new java.util.Date(lUpdatedDate); // 结果日期
		return dateNew;
	}

	// 把给定日期与给定月数进行运算,月数可以是负数.返回给定日期与给定日期的差或和
	// 若形成的新日期非法,则自动对新日期进行调整,例如:2004年1月31日加1个月为2004年2月31日,系统自动调整为2004年2月29日
	public static Date getDateNMonths(java.util.Date date, int months) {
		if (months == 0) { // 月数为零,直接返回给定日期
			return date;
		}
		if (months > 1200 || months < -1200) { // 日期限制在100年以内
			System.out.println("请把日期限制在100年内");
			return null;
		}
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.add(Calendar.MONTH, months);
		java.util.Date date2 = gc.getTime();
		return date2;
	}

	/*
	 * 对日期进行格式化
	 * 
	 * @适应PB客户端编程调用
	 * 
	 * @wang_xm
	 */
	public static String formatDate(Date date, String format) {
		if (date == null) {
			return "";
		}
		if (format == null || format.trim().equals("")) {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		Locale locale = new Locale("en", "US");
		SimpleDateFormat formatter = new SimpleDateFormat(format.trim(), locale);
		return formatter.format(date);
	}

	/**
	 * 得到当前日期(java.sql.Date类型)，注意：没有时间，只有日期
	 * 
	 * @return 当前日期
	 */
	public static java.sql.Date getDate() {
		Calendar oneCalendar = Calendar.getInstance();
		return getDate(oneCalendar.get(Calendar.YEAR), oneCalendar.get(Calendar.MONTH) + 1,
				oneCalendar.get(Calendar.DATE));
	}

	public static int getIntervalDay(java.sql.Date startDate, java.sql.Date endDate) {// 根据所给的起始,终止时间来计算间隔天数
		long startdate = startDate.getTime();
		long enddate = endDate.getTime();
		long interval = enddate - startdate;
		int intervalday = (int) interval / (1000 * 60 * 60 * 24);
		return intervalday;
	}

	public static java.sql.Date getDate(int yyyy, int MM, int dd) {
		// 根据所给年、月、日，得到日期(java.sql.Date类型)，注意：没有时间，只有日期。
		// 年、月、日不合法，会抛IllegalArgumentException(不需要catch)
		if (!verityDate(yyyy, MM, dd)) {
			throw new IllegalArgumentException("This is illegimate date!");
		}

		Calendar oneCalendar = Calendar.getInstance();
		oneCalendar.clear();
		oneCalendar.set(yyyy, MM - 1, dd);
		return new java.sql.Date(oneCalendar.getTime().getTime());
	}

	public static boolean verityDate(int yyyy, int MM, int dd) {// 根据所给年、月、日，检验是否为合法日期。
		boolean flag = false;

		if (MM >= 1 && MM <= 12 && dd >= 1 && dd <= 31) {
			if (MM == 4 || MM == 6 || MM == 9 || MM == 11) {
				if (dd <= 30) {
					flag = true;
				}
			} else if (MM == 2) {
				if (yyyy % 100 != 0 && yyyy % 4 == 0 || yyyy % 400 == 0) {
					if (dd <= 29) {
						flag = true;
					}
				} else if (dd <= 28) {
					flag = true;
				}
			} else {
				flag = true;
			}
		}
		return flag;
	}

	// 功能：根据所给的起始,终止时间来计算间隔月数
	public static int getIntervalMonth(java.util.Date startDate, java.util.Date endDate) {
		java.text.SimpleDateFormat mmformatter = new java.text.SimpleDateFormat("MM");
		int monthstart = Integer.parseInt(mmformatter.format(startDate));
		int monthend = Integer.parseInt(mmformatter.format(endDate));
		java.text.SimpleDateFormat yyyyformatter = new java.text.SimpleDateFormat("yyyy");
		int yearstart = Integer.parseInt(yyyyformatter.format(startDate));
		int yearend = Integer.parseInt(yyyyformatter.format(endDate));
		return (yearend - yearstart) * 12 + (monthend - monthstart);
	}

	/**
	 * 功能：取应用服务器时间并以"yyyyMMdd"格式返回
	 */
	public static String getDateForLong() {
		return getCurrentDateByFormat("yyyyMMdd");
	}

	/**
	 * 获取现在时间
	 * 
	 * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
	 */
	public static Date getNowDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		ParsePosition pos = new ParsePosition(8);
		Date currentTime_2 = formatter.parse(dateString, pos);
		return currentTime_2;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return返回短时间格式 yyyy-MM-dd
	 */
	public static Date getNowDateShort() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		ParsePosition pos = new ParsePosition(8);
		Date currentTime_2 = formatter.parse(dateString, pos);
		return currentTime_2;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
	 */
	public static String getStringDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return 返回短时间字符串格式yyyy-MM-dd
	 */
	public static String getStringDateShort() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 获取时间 小时:分;秒 HH:mm:ss
	 * 
	 * @return
	 */
	public static String getTimeShort() {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		Date currentTime = new Date();
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 获取时间 小时:分;秒 HH:mm:ss
	 * 
	 * @return
	 */
	public static String getDateShort(java.util.Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	/**
	 * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDateLong(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 将长时间格式字符串转换为时间 yyyyMMddHHmmss
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDateLongs(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	public static Date strToDateLongs(Long longDate) {
		String strDate = longDate.toString();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param dateDate
	 * @return
	 */
	public static String dateToStrLong(java.util.Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	/**
	 * 将长时间格式时间转换为字符串 yyyyMMddHHmmss
	 * 
	 * @param dateDate
	 * @return
	 */
	public static String dateToStrLongs(java.util.Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	/**
	 * 将短时间格式时间转换为字符串 yyyy-MM-dd
	 * 
	 * @param dateDate
	 * @param k
	 * @return
	 */
	public static String dateToStr(java.util.Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	/**
	 * 将短时间格式字符串转换为时间 yyyy-MM-dd
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDate(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 将短时间格式时间转换为字符串 yyyyMMdd
	 * 
	 * @param dateDate
	 * @param k
	 * @return
	 */
	public static String dayToStr(java.util.Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	/**
	 * 得到现在时间
	 * 
	 * @return
	 */
	public static Date getNowByDate() {
		Date currentTime = new Date();
		return currentTime;
	}

	/**
	 * 提取一个月中的最后一天
	 * 
	 * @param day
	 * @return
	 */
	public static Date getLastDate(long day) {
		Date date = new Date();
		long date_3_hm = date.getTime() - 3600000 * 34 * day;
		Date date_3_hm_date = new Date(date_3_hm);
		return date_3_hm_date;
	}

	/**
	 * 得到现在时间
	 * 
	 * @return 字符串 yyyyMMdd-HHmmss
	 */
	public static String getStringToday() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd-HHmmss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 得到现在小时
	 */
	public static String getHour() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		String hour;
		hour = new String(dateString.substring(11, 13));
		return hour;
	}

	/**
	 * 得到现在分钟
	 * 
	 * @return
	 */
	public static String getTime() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		String min;
		min = new String(dateString.substring(14, 16));
		return min;

	}

	/**
	 * 获取当前日期之前日期或者之后日期
	 * @param amount:把日期往后增加一天.整数往后推,负数往前移动
	 * @param format:要返回的日期格式
	 * @return
	 */
	public static String getCurrentDateSub(int amount,String format) {
		Date date = new Date();// 取时间
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, amount);
		date = calendar.getTime();
		return DateUtils.dateToString(date, format);
	}
	
	
	/**
	 * @author  Dingkuiyuan@dodopal.com 
     * @date 创建时间：2016年3月23日 下午8:16:31 
	 * 获取当前日期的星期（当前周几）
	 */
	public static String getThisWeekName(Date date){
	    String formater = "yyyy-MM-dd HH:mm:ss";
	    SimpleDateFormat format = new SimpleDateFormat(formater);
        format.applyPattern("E");
        return format.format(new Date());
	}
	
	/**
	 * 产生随机的三位数
	 * @return
	 */
	public static String getThree(){
		Random rad=new Random();
		return rad.nextInt(1000)+"";
	}
	
}
