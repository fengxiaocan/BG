package com.evil.bg;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 * 
 * @author 风小灿
 * @date 2016-6-19
 */
public class TimeUtils {
	/**
	 * 格式:2016-08-20 11:11:11
	 */
	public static final String DATE_TYPE1 = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 格式:2016-08-20 Tuesday
	 */
	public static final String DATE_TYPE2 = "yyyy-MM-dd E";
	/**
	 * 格式:2016-08-20
	 */
	public static final String DATE_TYPE3 = "yyyy-MM-dd";

	/**
	 * 格式:2016年 第181天
	 */
	public static final String DATE_TYPE4 = "yyyy年 第D天";
	/**
	 * 格式:12:24:36
	 */
	public static final String DATE_TYPE5 = "HH:mm:ss";
	/**
	 * 格式:2016年6月19日 星期日
	 */
	public static final String DATE_TYPE6 = "yyyy年MM月dd日 E";
	/**
	 * 格式:2015年12月23日
	 */
	public static final String DATE_TYPE7 = "yyyy年MM月dd日";
	/**
	 * 格式:2015年12月23日 星期日 下午
	 */
	public static final String DATE_TYPE8 = "yyyy年MM月dd日 E a";
	/**
	 * 格式:12:23:34 星期日 下午
	 */
	public static final String DATE_TYPE9 = "HH:mm:ss E a";
	/**
	 * 格式:21时12分18秒
	 */
	public static final String DATE_TYPE10 = "HH时mm分ss秒";
	/**
	 * 格式:20150223
	 */
	public static final String DATE_TYPE11 = "yyyyMMdd";

	/**
	 * 格式:20150213 211002
	 */
	public static final String DATE_TYPE12 = "yyyyMMdd HHmmss";

	/**
	 * 获取当前时间
	 * 
	 * @return 当前时间的毫秒值
	 */
	public static long getNowTime() {
		long nowTime = System.currentTimeMillis();
		return nowTime;
	}

	/**
	 * 获取当前时间
	 * 
	 * @param type
	 *            时间的格式
	 * @return 返回格式化后的时间
	 */
	public static String getNowTime(String type) {
		long nowTime = System.currentTimeMillis();
		return formatTime(nowTime, type);
	}

	/**
	 * 根据传入的格式来格式化时间
	 * 
	 * @param date
	 *            时间 单位:毫秒
	 * @param type
	 *            日期格式
	 * @return 传入格式类型的时间字符串
	 */
	public static String formatTime(long date, String type) {
		SimpleDateFormat sdf = new SimpleDateFormat(type);
		String time = sdf.format(date);
		return time;
	}

	/**
	 * 传入对应的时间字符串和对应的时间格式,转化为long类型的时间毫秒值
	 * 
	 * @param date
	 *            例如:2016-02-23
	 * @param type
	 *            则选择对应的 DATE_TYPE2 格式
	 */
	public static long strFormatTime(String date, String type) {
		SimpleDateFormat sdf = new SimpleDateFormat(type);
		try {
			Date parse = sdf.parse(date);
			long time = parse.getTime();
			return time;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 将时间字符串 转化为 对应格式的 时间Date
	 * 
	 * @param strDate
	 *            要转化的时间字符串
	 * @param type
	 *            时间字符串对应的格式类型
	 * @return Date类型的时间格式
	 * @throws ParseException
	 *             格式转换异常
	 */
	public static Date strToDate(String strDate, String type)
			throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(type);
		Date parse = formatter.parse(strDate);
		return parse;
	}

	/**
	 * 把Date时间转换为指定格式的时间字符串
	 * 
	 * @param date
	 *            要转化的时间Date
	 * @param type
	 *            转化为目标格式时间类型
	 * @return 返回指定格式的字符串时间
	 */
	public static String dateToStr(Date date, String type) {
		SimpleDateFormat formatter = new SimpleDateFormat(type);
		String string = formatter.format(date);
		return string;
	}

	/**
	 * 得到现在小时
	 * 
	 * @return int 类型的现在小时数
	 */
	public static int getNowHour() {
		Date date = new Date(getNowTime());
		int hours = date.getHours();
		return hours;
	}

	/**
	 * 得到现在分钟数
	 * 
	 * @return int 类型的现在分钟数
	 */
	public static int getNowMinutes() {
		Date date = new Date(getNowTime());
		int minutes = date.getMinutes();
		return minutes;
	}

	/**
	 * 得到现在秒数
	 * 
	 * @return int 类型的现在秒数
	 */
	public static int getNowSeconds() {
		Date date = new Date(getNowTime());
		int seconds = date.getSeconds();
		return seconds;
	}

	/**
	 * 得到现在是一个星期内的第几天
	 * 
	 * @return int 类型的现在星期几的天数 0为星期天
	 */
	public static int getNowDay() {
		Date date = new Date(getNowTime());
		int day = date.getDay();
		return day;
	}

	/**
	 * 得到现在星期几
	 * 
	 * @return String 类型的现在星期几的天数:星期几
	 */
	public static String getNowDayStr() {
		Date date = new Date(getNowTime());
		int day = date.getDay();
		String str = "";
		switch (day) {
		case 0:
			str = "星期天";
			break;
		case 1:
			str = "星期一";
			break;
		case 2:
			str = "星期二";
			break;
		case 3:
			str = "星期三";
			break;
		case 4:
			str = "星期四";
			break;
		case 5:
			str = "星期五";
			break;
		case 6:
			str = "星期六";
			break;
		}
		return str;
	}

	/**
	 * 得到现在是一个月内的第几天
	 * 
	 * @return int 一个月内的第几天
	 */
	public static int getNowDate() {
		Date date = new Date(getNowTime());
		int dates = date.getDate();
		return dates;
	}

	/**
	 * 得到现在是一年内第几个月
	 * 
	 * @return int 现在的一年内第几个月 1为第一个月
	 */
	public static int getNowMonth() {
		Date date = new Date(getNowTime());
		int month = date.getMonth() + 1;
		return month;
	}

	/**
	 * 得到现在的第几年
	 * @return int 现在是哪一年
     */
	public static int getNowYear() {
		Date date = new Date(getNowTime());
		int year = date.getYear();
		year = year + 1900;
		return year;
	}

}
