package com.ziyue.xuetang.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.joda.time.DateTime;
import org.joda.time.Months;
import org.springframework.util.StringUtils;
/**
 * 时间操作类
 * @author Mr-H
 *
 */
public class TimeUtil {
	
	public static void main(String[] args) throws ParseException {
 		
//		System.out.println(getLaterMouthDate(1, getDate()));
		
//		System.out.println(TimeUtil.currentTimeMillis());
//		long epoch = new java.text.SimpleDateFormat ("yyyy-MM-dd HH:mm:ss").parse("2008-12-01 16:33:00").getTime()/1000;
// 		System.out.println(epoch);
// 		System.out.println(getBeforMouthDate(1,"2015-12-11 22:53:37","2016-04-11"));
		System.out.println(getTimeByMillis(1512454523000L));
		System.out.println(getTimeByMillis(currentTimeMillisByTime("2016-06-14 09:45:23") - 172800000));
	}

	/**
	 * HH:mm:ss
	 * @return
	 */
	public static String getTime() {
		return new SimpleDateFormat("HH:mm:ss").format(new Date());
	}

	/**
	 * yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getDate() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}

	/**
	 * yyyy-MM-dd
	 * @return
	 */
	public static String getDates() {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}
	
	/**
	 * 时间戳
	 * @return
	 */
	public static long currentTimeMillis() {
//		Date date = new Date();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//		return Long.valueOf(sdf.format(date));
		return System.currentTimeMillis();
	}
	
	/**
	 * 时间戳   millis / 1000
	 * @return
	 */
	public static String currentTimeMillisStr() {
//		Date date = new Date();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//		return Long.valueOf(sdf.format(date));
		return (System.currentTimeMillis() / 1000) + "";
	}
	//获取当前季度
	//获得本季度
	/**
	 * 获取季度
	 * @return
	 */
    public static String getThisSeasonTime(){  
    	int month = Integer.valueOf(getDates().substring(5, 7));
        int season = 1;  
        if(month>=1&&month<=3){  
            season = 1;  
        }  
        if(month>=4&&month<=6){  
            season = 2;  
        }  
        if(month>=7&&month<=9){  
            season = 3;  
        }  
        if(month>=10&&month<=12){  
            season = 4;  
        } 
        return String.valueOf(getDates().substring(0, 4)+"年第"+season+"季度");
    } 
    
    
    /**
     * 获取一个当前日期 X个月后的日期
     * 
     * @return
     */
    public static String getLaterMouthDate(int renewalsdata, String validatetime){
    	try {
     		//int renewalsdata = 1;
//     		String validatetime = "20120131";
     		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
     		Date now = sdf.parse(validatetime);
     		Calendar calendar = Calendar.getInstance();
     		calendar.setTime(now);
//     		System.out.println("原来  == " + sdf.format(calendar.getTime()));
     		calendar.add(Calendar.MONTH, renewalsdata);
//     		System.out.println("添加  == " + sdf.format(calendar.getTime()));
     		return sdf.format(calendar.getTime());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
    }
    
    /**
     * 获取两个时间段之间的月数
     * @param renewalsdata
     * @param startTime
     * @param endTime
     * @return
     */
    public static String getMouthsBetweenMouth(String startTime,String endTime){
    	String startStr[] = startTime.substring(0, 10).split("-");
	    DateTime end = new DateTime(
	    		Integer.valueOf(startStr[0]), 
	    		Integer.valueOf(startStr[1]), 
	    		Integer.valueOf(startStr[2]) ,00 , 00 , 00);
	    String endTimeStr[] = endTime.split("-");
	    DateTime start = new DateTime(
	    		Integer.valueOf(endTimeStr[0]), 
	    		Integer.valueOf(endTimeStr[1]) , 
	    		Integer.valueOf(endTimeStr[2]) , 00, 00 , 00);
	    int months = Months.monthsBetween(end, start).getMonths(); // 6
	   // System.out.println(months);
	    return String.valueOf(months);
    }
    
    /**
     * 获取当前时间X个月前的日期
     * @return
     */
    public  static String getBeforMouthDate(){
 		Date date = new Date();//当前日期
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//格式化对象
	    Calendar calendar = Calendar.getInstance();//日历对象
	    calendar.setTime(date);//设置当前日期
	    calendar.add(Calendar.MONTH, -12);//月份减一
	    System.out.println(sdf.format(calendar.getTime()));//输出格式化的日期
	    return "";
    }
    
    /**
     * 根据时间戳获取时间   yyyy-MM-dd HH:mm:ss
     * @param currentTimeMillis 时间戳
     * @return 时间
     */
    public static String getTimeByMillis(Long currentTimeMillis){
    	if(StringUtils.isEmpty(currentTimeMillis)){
    		return "";
    	}
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date(currentTimeMillis));
    }
    /**
     * ziyue
     * 根据时间戳获取时间   yyyy-MM-dd HH:mm:ss
     * @param currentTimeMillis 时间戳
     * @return 时间
     */
    public static String getTimeByMillis(Object currentTimeMillis){
    	if(StringUtils.isEmpty(currentTimeMillis)){
    		return "";
    	}
    	Long currentTimeMillisL = Long.valueOf(String.valueOf(currentTimeMillis) + "000");
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	return sdf.format(new Date(currentTimeMillisL));
    }
    
    /**
     * 根据时间获取时间戳
     * @param dateString 时间
     * @return 时间戳
     * @throws ParseException
     */
    public static long currentTimeMillisByTime(String dateString) throws ParseException{
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(dateString);
    	return date.getTime();
    }
}
