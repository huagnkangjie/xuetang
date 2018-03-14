package com.ziyue.xuetang.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时期
 * 
 * 
 */
public class DateUtil {

	public static long getCurrentTimeMillis() {

		return System.currentTimeMillis();
	}

	public static String formatAddDayDate(String d, int days) {

		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + days);

		String dayAfter = format2Date2(c.getTime().getTime());

		return dayAfter;

	}

	/**
	 * 将长整型毫秒数转换为日期格式yyyy-MM-dd
	 * 
	 * @param t
	 *            毫秒数
	 * @return
	 */
	public static String format2Date(long t) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String time = sdf.format(t);
		return time;
	}

	public static String format3Date(long t) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
		String time = sdf.format(t);
		return time;
	}

	public static String format4Date(long t) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		String time = sdf.format(t);
		return time;
	}

	/**
	 * 将长整型毫秒数转换为日期格式yyyy-MM-dd hh:mm
	 * 
	 * @param t
	 *            毫秒数
	 * @return
	 */
	public static String format2Date1(long t) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String time = sdf.format(t);
		return time;
	}

	/**
	 * 将长整型毫秒数转换为日期格式yyyy-MM-dd hh:mm:ss
	 * 
	 * @param t
	 *            毫秒数
	 * @return
	 */
	public static String format2Date2(long t) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = sdf.format(t);
			return time;
		} catch (Exception e) {
			return "";
		}
	}

	public static int getDay(String strTime) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			long time = format.parse(strTime).getTime();

			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(time);

			return c.get(Calendar.DAY_OF_MONTH);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return -1;

	}

	/**
	 * 
	 * @param strTime
	 *            字符串时间
	 * @param pattern
	 *            时间格式
	 * @return
	 */
	public static long getTime(String strTime, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			return format.parse(strTime).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return 0;
	}

	public static long getTime(String strTime) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return format.parse(strTime).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return 0;
	}

	public static long getTime2(String strTime) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return format.parse(strTime).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return 0;
	}

	/**
	 * 将yyyy-MM-dd String类型 转换为Date
	 */

	public static Date string2Date(String str) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

	public  static String getDateY4M2D2H2M2S2() {

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String time = sdf.format(System.currentTimeMillis());
			return time;
		} catch (Exception e) {
			return "";
		}
	}

	public static void main(String[] args) {

		// System.out.println(format2Date2(System.currentTimeMillis()) );
		System.out.println(format2Date1(1474337475273l));

	//	long c = System.currentTimeMillis() - 1447348403918l;
		// System.out.println(c);
		// String c1=format2Date2(c);
		//
		// System.out.println(c1);
		// System.out.println(formatAddDayDate(c1,7));

	}

}