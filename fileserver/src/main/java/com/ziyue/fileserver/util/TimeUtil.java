package com.ziyue.fileserver.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @描述：
 *
 * @author 作者 : huang_kangjie
 * @date 创建时间：2017年12月28日
 * @version v1.0.
 * 
 */
public class TimeUtil {

	
	/**
	 * 201712280857150763
	 * 
	 * @param date
	 * @param pattern yyyyMMddHHmmssSSSS
	 * @return
	 */
	public static String format(Date date, String pattern) {
        String returnValue = "";
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            returnValue = df.format(date);
        }
        return (returnValue);
    }
	
	public static void main(String[] args) {
		System.out.println(format(new Date(), "yyyyMMddHHmmssSSSS"));
	}
	
}
