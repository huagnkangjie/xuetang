package com.ziyue.xuetang.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormat {

	
	
	
	public static String formatDate(Long t) {
		if(t==0)return "";
		String strDate = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String systemTime = sdf.format(new Date()).toString();

		String time = sdf.format(t);

		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -1);

		String yesterday = sdf.format(c.getTime()).toString();
		c.add(Calendar.DAY_OF_MONTH, -1);

		String yesterday_before = sdf.format(c.getTime()).toString();

		try {
			java.util.Date begin = sdf.parse(time);

			java.util.Date end = sdf.parse(systemTime);

			long between = (end.getTime() - begin.getTime()) / (1000 * 60);

			String todayTime = time.substring(0, 10);

			if (todayTime.equals(yesterday.substring(0, 10))) {
				strDate = "昨天";
			} else if (todayTime.equals(yesterday_before.substring(0, 10))) {
				strDate = "前天";
			} else if (between <= 0) {
				strDate = "1分钟前";
			} else if (between < 60 && between > 0) {
				strDate = Math.round(between) + "分钟前";
			} else if (between >= 60 && between < 60 * 24) {
				strDate = Math.round(between / 60) + "小时前";
			} else {

				String y1 = time.substring(0, 4);
				String y2 = systemTime.substring(0, 4);

				if (y1.equals(y2)) {

					strDate = time.substring(5,10);
				} else {

					strDate = time.substring(0, 10);

				}

			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return strDate;
	}

	public static void main(String[] args) {
		// System.out.println(System.currentTimeMillis() );
		System.out.println(formatDate(1457713190145l));

	}

}
