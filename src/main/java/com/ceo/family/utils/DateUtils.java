package com.ceo.family.utils;

//import com.sun.org.apache.regexp.internal.RE;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.regex.Pattern;


/**
 * 	时间工具类
 * @author: 化阵
 * @date: 2018/06/01
 */
public class DateUtils {

	public static long getDefultNullTimestamp(){
		return 631123200000L;
	}

	public static Timestamp getNowTimestamp(){
		return new Timestamp(System.currentTimeMillis());
	}

	public static String nowDate() {
		GregorianCalendar calenda = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(calenda.getTime());
	}

	/**
	 * 得到当前日期时间
	 */
	public static String now() {
		GregorianCalendar calenda = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(calenda.getTime());
	}
	
	/**
	 * 得到昨天的日期
	 * @param format
	 * @return
	 */
	public static String getYestoday(String format) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String yesterday = new SimpleDateFormat(format).format(cal.getTime());
		
		return yesterday;
		
	}

	/**
	 * 得到当前日期，默认格式2014-07-11
	 * 
	 * @param format
	 *            日期格式
	 * @return
	 */
	public static String currentDate(String format) {
		GregorianCalendar calenda = new GregorianCalendar();
		SimpleDateFormat sdf;
		if (null == format || "".equals(format)) {
			sdf = new SimpleDateFormat("yyyy-MM-dd");
		} else {
			sdf = new SimpleDateFormat(format);
		}
		return sdf.format(calenda.getTime());
	}
	
	public static String formatInterviewDate(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("HH-mm-ss");
		String s = sdf.format(date);
		return  s;
	}
	
	/**
	 * 得到day后的日期
	 */
	public static Date addDay(Date inDate, int day) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(inDate);
		calendar.add(Calendar.DATE, day);
		return calendar.getTime();
	}

	/**
	 * 得到day后的日期
	 */
	public static Date addDay(Date inDate, int day,int second) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(inDate);
		calendar.add(Calendar.DATE, day);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND,second);
		return calendar.getTime();
	}

	/**
	 * 得到day后的日期
	 */
	public static String addDay(String strDate, String pattern, int day) {
		return date2Str(addDay(str2Date(strDate, pattern), day), pattern);
	}

	/**
	 * 得到day后的日期
	 */
	public static String addDay(Date date, String pattern, int day) {
		return date2Str(addDay(date, day), pattern);
	}

	public static String addMonth(Date date,String pattern,int month){

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH,month);

		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(calendar.getTime());
	}

	/**
	 * 将日期转换为字符串
	 */
	public static String date2Str(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	/**
	 * 字符串转化为日期
	 */
	public static Date str2Date(String dateStr, String pattern) {
		try {
			DateFormat parser = new SimpleDateFormat(pattern);
			return parser.parse(dateStr);
		} catch (ParseException ex) {
			return null;
		}
	}

	/**
	 * 得到今天是星期几
	 */
	public static int getWeek() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.DAY_OF_WEEK) - 1;
	}

	/**
	 * 当前是哪一年
	 * @return
	 */
	public static int getYear(){
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 得到当前月
	 * 
	 * @return
	 */
	public static String getMonth() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1);
	}

	public static int getHourOfDay(){
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 判断是否是润年
	 */
	public static boolean isLeapYear(int year) {
		if ((year % 4) == 0 && ((year % 100) != 0 || (year % 400) == 0)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断当前时间是否在时间date2之前 时间格式 2005-4-21 16:16:34
	 */
	public static boolean isDateBefore(String date2) {
		try {
			Date date1 = new Date();
			DateFormat df = DateFormat.getDateTimeInstance();
			return date1.before(df.parse(date2));
		} catch (ParseException e) {
			return false;
		}
	}

	/**
	 * 判断时间date1是否在时间date2之前 时间格式 2005-4-21 16:16:34
	 */
	public static boolean isDateBefore(String date1, String date2) {
		try {
			DateFormat df = DateFormat.getDateTimeInstance();
			return df.parse(date1).before(df.parse(date2));
		} catch (ParseException e) {
			return false;
		}
	}

	/**
	 * 将一个长整数转化为日前 比如1169713094344l转成日期2007-01-25 16:18:14
	 */
	public static String getDate(long d, String pattern) {
		Date c = new Date(d);
		return date2Str(c, pattern);
	}

	/**
	 * 计算两个日期相差的天数，如果date2 > date1 返回正数，否则返回负数
	 */
	public static long dayDiff(Date date1, Date date2) {
		return (date2.getTime() - date1.getTime()) / 86400000;
	}

	/**
	 * 计算两个日期的月份差
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int monthDiff(Date date1, Date date2){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date1);
		int year1 = calendar.get(Calendar.YEAR);
		int month1 = calendar.get(Calendar.MONTH);
		calendar.setTime(date2);
		int year2 = calendar.get(Calendar.YEAR);
		int month2 = calendar.get(Calendar.MONTH);
		return (year1-year2)*12+(month1-month2);
	}

	/**
	 * 计算date1-date2，结果以年为单位
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int yearDiff(Date date1, Date date2){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date1);
		int year1 = calendar.get(Calendar.YEAR);
		calendar.setTime(date2);
		int year2 = calendar.get(Calendar.YEAR);
		return year1 - year2;
	}
	/**
	 * 根据生日获取星座
	 */
	public static String getAstro(String birth) {
		int month = Integer.parseInt(birth.substring(birth.indexOf("-") + 1, birth.lastIndexOf("-")));
		int day = Integer.parseInt(birth.substring(birth.lastIndexOf("-") + 1));
		String s = "魔羯水瓶双鱼牡羊金牛双子巨蟹狮子处女天秤天蝎射手魔羯";
		int[] arr = { 20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22, 22 };
		int start = month * 2 - (day < arr[month - 1] ? 2 : 0);
		return s.substring(start, start + 2) + "座";
	}

	/**
	 * 判断日期是否有效,包括闰年的情况
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

	/**
	 * 获得整点或半点的时间
	 * 
	 * @param 	addHours 增加的小时数
	 * @return	返回调用本方法时的整点或半点时间，时间戳格式
	 */
	public static long getHourlyTime(int addHours) {
		Calendar calendar = Calendar.getInstance();
		// 如果分钟数不是0或30，则向下取到0或30
		int minute = calendar.get(Calendar.MINUTE);
		if (minute != 0 && minute != 30) {
			if (minute - 30 > 0) {
				calendar.set(Calendar.MINUTE, 30);
			} else {
				calendar.set(Calendar.MINUTE, 0);
			}
		}
		calendar.add(Calendar.HOUR_OF_DAY, addHours);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}

	public static boolean checkDateInLimit(Date date,Date begin,Date end){
		if(begin.getTime()<=date.getTime() && end.getTime()>=date.getTime()){
			return true;
		}
		return false;
	}

//	public static  Date transLong2Date(long time){
//		return DateTimeUtil.str2Date(DateTimeUtil.getDate(time,YYYY_MM_DD_HH_MM_SS),YYYY_MM_DD_HH_MM_SS);
//	}

	/**
	 * 将时间戳转成日期，是毫秒级别的时间戳
	 * @param timeStamp
	 * @return
	 */
	public static LocalDateTime transferLongToLocalDateTime(long timeStamp){
		Instant instant = Instant.ofEpochMilli(timeStamp);
		LocalDateTime slocal = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
		return  slocal;
	}

	/**
	 *  将localDateTime转换成时间戳
	 *  统一转成毫秒形式的
	 * @param localDateTime
	 * @return long 时间戳
	 */
	public static long transferLocalDateTimeToLong(LocalDateTime localDateTime){
		if (localDateTime == null){
			return 0;
		}
		return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}

	/**
	 * 获取date所在周的周一的日期
	 * @param date
	 * @return Date
	 * @author zhangxianruo
	 * @date 20180424
     */
	public static Date getMondayDate(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(date.getYear(), date.getMonth(), date.getDate()));
		// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		// 获得当前日期是一个星期的第几天
		int day = cal.get(Calendar.DAY_OF_WEEK);
		// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
		return cal.getTime();
	}


	/**
	 * 获取从date所在周开始，一年（53周）内每周周一的日期，返回时间格式为毫秒数
	 * 为容错，将边界值扩大至54周
	 * @param date
	 * @return List 53周的周一日期毫秒数
	 * @author zhangxianruo
	 * @date 20180424
     */
	public static List<Long> getMondayDateByYear(Date date){
		//先获取本周周一日期
		Date thisMonday = getMondayDate(date);
		List<Long> list = new ArrayList<Long>();

		for(int i = 0; i < 54; i++){
			Calendar cal = Calendar.getInstance();
			cal.setTime(thisMonday);
			cal.add(Calendar.DATE, 7*i);
			list.add(cal.getTime().getTime());
		}

		return list;
	}

	/**
	 *
	 * 功能描述：将时间戳转换成instant类型
	 *
	 * @author: renxiaoya
	 * @date: 2018/6/11 下午8:47
	 */
	public static Instant long2Instant(long longTime) {
		return Instant.ofEpochSecond(longTime);
	}


	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		List<Long> list = getMondayDateByYear(new Date());
		for(Long l : list){
			Date date = new Date();
			date.setTime(l);
			System.out.println(sdf.format(date));
		}
	}

	/**
	 * 把时间戳转成数字，单位为秒
	 * @param timestamp
	 * @return
	 */
	public static long transferTimestampToLong(Timestamp timestamp) {
		if(timestamp == null){
			return 0;
		}
		long mil = timestamp.getTime();
		return mil/1000;
	}

	public static long getCurrentTime(){
		return new Date().getTime();
	}

	public static long getCurrentSeconds(){
		return System.currentTimeMillis() / 1000;
	}

	/**
	 * 把秒转换成时间戳
	 * @param seconds
	 * @return
	 */
	public static Timestamp transferLongToTimestamp(long seconds) {
		return new Timestamp(seconds*1000);
	}
}