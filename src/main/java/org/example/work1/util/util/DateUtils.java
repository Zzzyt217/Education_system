package org.example.work1.util.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @desc:时间处理工具类
 * 
 */
public class DateUtils {
	private static final String[] weeks = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
	/**
	 * 根据指定格式获取当前时间
	 * @author zdk
	 * @date Dec 27, 2022
	 * @param format
	 * @return String
	 */
	public static String getCurrentTime(String format){
		SimpleDateFormat sdf = DateFormatUtils.getFormat(format);
		Date date = new Date();
		return sdf.format(date);
	}
	
	/**
	 * 获取当前时间，格式为：yyyy-MM-dd HH:mm:ss
	 * @author zdk
	 * @date Dec 27, 2022
	 * @return String
	 */
	public static String getCurrentTime(){
		return getCurrentTime(DateFormatUtils.DATE_FORMAT2);
	}
	
	/**
	 * 获取指定格式的当前时间：为空时格式为yyyy-mm-dd HH:mm:ss
	 * @author zdk
	 * @date Dec 30, 2022
	 * @param format
	 * @return Date
	 */
	public static Date getCurrentDate(String format){
		 SimpleDateFormat sdf = DateFormatUtils.getFormat(format);
		 String dateS = getCurrentTime(format);
		 Date date = null;
		 try {
			date = sdf.parse(dateS);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 获取当前时间，格式为yyyy-MM-dd HH:mm:ss
	 * @author zdk
	 * @date Dec 30, 2022
	 * @return Date
	 */
	public static Date getCurrentDate(){
		return getCurrentDate(DateFormatUtils.DATE_FORMAT2);
	}
	
	/**
	 * 给指定日期加入年份，为空时默认当前时间
	 * @author zdk
	 * @date Dec 30, 2022
	 * @param year 年份  正数相加、负数相减
	 * @param date 为空时，默认为当前时间
	 * @param format 默认格式为：yyyy-MM-dd HH:mm:ss
	 * @return String
	 */
	public static String addYearToDate(int year,Date date,String format){
		Calendar calender = getCalendar(date,format);
		SimpleDateFormat sdf = DateFormatUtils.getFormat(format);
		
		calender.add(Calendar.YEAR, year);
		
		return sdf.format(calender.getTime());
	}
	
	/**
	 * 给指定日期加入年份，为空时默认当前时间
	 * @author zdk
	 * @date Dec 30, 2022
	 * @param year 年份  正数相加、负数相减
	 * @param date 为空时，默认为当前时间
	 * @param format 默认格式为：yyyy-MM-dd HH:mm:ss
	 * @return String
	 */
	public static String addYearToDate(int year,String date,String format){
		Date newDate = new Date();
		if(null != date && !"".equals(date)){
			newDate = string2Date(date, format);
		}
		
		return addYearToDate(year, newDate, format);
	}
	
	/**
	 * 给指定日期增加月份 为空时默认当前时间
	 * @author zdk
	 * @date Dec 30, 2022
	 * @param month  增加月份  正数相加、负数相减
	 * @param date 指定时间
	 * @param format 指定格式 为空默认 yyyy-mm-dd HH:mm:ss
	 * @return String
	 */
	public static String addMothToDate(int month,Date date,String format) {
		Calendar calender = getCalendar(date,format);
		SimpleDateFormat sdf = DateFormatUtils.getFormat(format);
		
		calender.add(Calendar.MONTH, month);
		
		return sdf.format(calender.getTime());
	}
	public static Date addMothToDate(int month,Date date) {
		Calendar calender = getCalendar(date,"yyyy-MM-dd");
		calender.add(Calendar.MONTH, month);
		return calender.getTime();
	}
	
	/**
	 * 给指定日期增加月份 为空时默认当前时间
	 * @author zdk
	 * @date Dec 30, 2022
	 * @param month  增加月份  正数相加、负数相减
	 * @param date 指定时间
	 * @param format 指定格式 为空默认 yyyy-mm-dd HH:mm:ss
	 * @return String
	 */
	public static String addMothToDate(int month,String date,String format) {
		Date newDate = new Date();
		if(null != date && !"".equals(date)){
			newDate = string2Date(date, format);
		}
		
		return addMothToDate(month, newDate, format);
	}
	
	/**
	 * 给指定日期增加天数，为空时默认当前时间
	 * @date Dec 31, 2022
	 * @param day 增加天数 正数相加、负数相减
	 * @param date 指定日期
	 * @param format 日期格式 为空默认 yyyy-mm-dd HH:mm:ss
	 * @return String
	 */
	public static String addDayToDate(int day,Date date,String format) {
		Calendar calendar = getCalendar(date, format);
		SimpleDateFormat sdf = DateFormatUtils.getFormat(format);
		
		calendar.add(Calendar.DATE, day);
		
		return sdf.format(calendar.getTime());
	}
	
	/**
	 * 给指定日期增加天数，为空时默认当前时间
	 * @date Dec 31, 2022
	 * @param day 增加天数 正数相加、负数相减
	 * @param date 指定日期
	 * @param format 日期格式 为空默认 yyyy-mm-dd HH:mm:ss
	 * @return String
	 */
	public static String addDayToDate(int day,String date,String format) {
		Date newDate = new Date();
		if(null != date && !"".equals(date)){
			newDate = string2Date(date, format);
		}
		
		return addDayToDate(day, newDate, format);
	}
	
	/**
	 * 给指定日期增加小时，为空时默认当前时间
	 * @author zdk
	 * @date Dec 31, 2022
	 * @param hour 增加小时  正数相加、负数相减
	 * @param date 指定日期
	 * @param format 日期格式 为空默认 yyyy-mm-dd HH:mm:ss
	 * @return String
	 */
	public static String addHourToDate(int hour,Date date,String format) {
		Calendar calendar = getCalendar(date, format);
		SimpleDateFormat sdf = DateFormatUtils.getFormat(format);
		
		calendar.add(Calendar.HOUR, hour);
		
		return sdf.format(calendar.getTime());
	}
	
	/**
	 * 给指定日期增加小时，为空时默认当前时间
	 * @date Dec 31, 2022
	 * @param hour 增加小时  正数相加、负数相减
	 * @param date 指定日期
	 * @param format 日期格式 为空默认 yyyy-mm-dd HH:mm:ss
	 * @return String
	 */
	public static String addHourToDate(int hour,String date,String format) {
		Date newDate = new Date();
		if(null != date && !"".equals(date)){
			newDate = string2Date(date, format);
		}
		
		return addHourToDate(hour, newDate, format);
	}
	
	/**
	 * 给指定的日期增加分钟，为空时默认当前时间
	 * @author zdk
	 * @date Dec 31, 2022
	 * @param minute 增加分钟  正数相加、负数相减
	 * @param date 指定日期 
	 * @param format 日期格式 为空默认 yyyy-mm-dd HH:mm:ss
	 * @return String
	 */
	public static String addMinuteToDate(int minute,Date date,String format) {
		Calendar calendar = getCalendar(date, format);
		SimpleDateFormat sdf = DateFormatUtils.getFormat(format);
		
		calendar.add(Calendar.MINUTE, minute);
		
		return sdf.format(calendar.getTime());
	}
	
	/**
	 * 给指定的日期增加分钟，为空时默认当前时间
	 * @author zdk
	 * @date Dec 31, 2022
	 * @param minute 增加分钟  正数相加、负数相减
	 * @param date 指定日期 
	 * @param format 日期格式 为空默认 yyyy-mm-dd HH:mm:ss
	 * @return String
	 */
	public static String addMinuteToDate(int minute,String date,String format){
		Date newDate = new Date();
		if(null != date && !"".equals(date)){
			newDate = string2Date(date, format);
		}
		
		return addMinuteToDate(minute, newDate, format);
	}
	
	/**
	 * 给指定日期增加秒，为空时默认当前时间
	 * @author zdk
	 * @date Dec 31, 2022
	 * @param second 增加秒 正数相加、负数相减
	 * @param date 指定日期
	 * @param format 日期格式 为空默认 yyyy-mm-dd HH:mm:ss
	 * @return String
	 */
	public static String addSecondToDate(int second,Date date,String format){
		Calendar calendar = getCalendar(date, format);
		SimpleDateFormat sdf = DateFormatUtils.getFormat(format);
		
		calendar.add(Calendar.SECOND, second);
		
		return sdf.format(calendar.getTime());
	}
	
	/**
	 * 给指定日期增加秒，为空时默认当前时间
	 * @author zdk
	 * @date Dec 31, 2022
	 * @param second 增加秒 正数相加、负数相减
	 * @param date 指定日期
	 * @param format 日期格式 为空默认 yyyy-mm-dd HH:mm:ss
	 * @return String
	 * @throws Exception 
	 */
	public static String addSecondToDate(int second,String date,String format){
		Date newDate = new Date();
		if(null != date && !"".equals(date)){
			newDate = string2Date(date, format);
		}
		
		return addSecondToDate(second, newDate, format);
	}
	
	/**
	 * 获取指定格式指定时间的日历
	 * @author zdk
	 * @date Dec 30, 2022
	 * @param date 时间 
	 * @param format 格式
	 * @return Calendar
	 */
	public static Calendar getCalendar(Date date,String format){
		if(date == null){
			date = getCurrentDate(format);
		}
		
		Calendar calender = Calendar.getInstance();
		calender.setTime(date);
		
		return calender;
	}
	
	/**
	 * 字符串转换为日期，日期格式为
	 * 
	 * @date : 2016年5月31日 下午5:20:22
	 *
	 * @param value
	 * @return
	 */
	public static Date string2Date(String value){
		if(value == null || "".equals(value)){
			return null;
		}
		
		SimpleDateFormat sdf = DateFormatUtils.getFormat(DateFormatUtils.DATE_FORMAT2);
		Date date = null;
		
		try {
			value = DateFormatUtils.formatDate(value, DateFormatUtils.DATE_FORMAT2);
			date = sdf.parse(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 将字符串(格式符合规范)转换成Date
	 * @author zdk
	 * @date Dec 31, 2022
	 * @param value 需要转换的字符串
	 * @param format 日期格式 
	 * @return Date
	 */
	public static Date string2Date(String value,String format){
		if(value == null || "".equals(value)){
			return null;
		}
		
		SimpleDateFormat sdf = DateFormatUtils.getFormat(format);
		Date date = null;
		
		try {
			value = DateFormatUtils.formatDate(value, format);
			date = sdf.parse(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 将日期格式转换成String
	 * @author zdk
	 * @date Dec 31, 2022
	 * 
	 * @param value 需要转换的日期
	 * @param format 日期格式
	 * @return String
	 */
	public static String date2String(Date value,String format){
		if(value == null){
			return null;
		}
		
		SimpleDateFormat sdf = DateFormatUtils.getFormat(format);
		return sdf.format(value);
	}
	
	/**
	 * 日期转换为字符串
	 * 
	 * @author : zdk
	 * @date : 2016年5月31日 下午5:21:38
	 *
	 * @param value
	 * @return
	 */
	public static String date2String(Date value){
		if(value == null){
			return null;
		}
		
		SimpleDateFormat sdf = DateFormatUtils.getFormat(DateFormatUtils.DATE_FORMAT2);
		return sdf.format(value);
	}
	
	/**
	 * 获取指定日期的年份
	 * @author zdk
	 * @date Dec 31, 2022
	 * @param value 日期
	 * @return int
	 */
	public static int getCurrentYear(Date value){
		String date = date2String(value, DateFormatUtils.DATE_YEAR);
		return Integer.valueOf(date);
	}
	
	/**
	 * 获取指定日期的年份
	 * @author zdk
	 * @date Dec 31, 2022
	 * @param value 日期
	 * @return int
	 */
	public static int getCurrentYear(String value) {
		Date date = string2Date(value, DateFormatUtils.DATE_YEAR);
		Calendar calendar = getCalendar(date, DateFormatUtils.DATE_YEAR);
		return calendar.get(Calendar.YEAR);
	}
	
	/**
	 * 获取指定日期的月份
	 * @author zdk
	 * @date Dec 31, 2022
	 * @param value 日期
	 * @return int
	 */
	public static int getCurrentMonth(Date value){
		String date = date2String(value, DateFormatUtils.DATE_MONTH);
		return Integer.valueOf(date);
	}
	
	/**
	 * 获取指定日期的月份
	 * @author zdk
	 * @date Dec 31, 2022
	 * @param value 日期
	 * @return int
	 */
	public static int getCurrentMonth(String value) {
		Date date = string2Date(value, DateFormatUtils.DATE_MONTH);
		Calendar calendar = getCalendar(date, DateFormatUtils.DATE_MONTH);
		
		return calendar.get(Calendar.MONTH);
	}
	
	/**
	 * 获取指定日期的天份
	 * @author zdk
	 * @date Dec 31, 2022
	 * @param value 日期
	 * @return int
	 */
	public static int getCurrentDay(Date value){
		String date = date2String(value, DateFormatUtils.DATE_DAY);
		return Integer.valueOf(date);
	}
	
	/**
	 * 获取指定日期的天份
	 * @author zdk
	 * @date Dec 31, 2022
	 * @param value 日期
	 * @return int
	 */
	public static int getCurrentDay(String value){
		Date date = string2Date(value, DateFormatUtils.DATE_DAY);
		Calendar calendar = getCalendar(date, DateFormatUtils.DATE_DAY);
		
		return calendar.get(Calendar.DATE);
	}
	
	/**
	 * 获取当前日期为星期几
	 * @author zdk
	 * @date Dec 31, 2022
	 * @param value 日期
	 * @return String
	 */
	public static String getCurrentWeek(Date value) {
		Calendar calendar = getCalendar(value, DateFormatUtils.DATE_FORMAT1);
		int weekIndex = calendar.get(Calendar.DAY_OF_WEEK) - 1 < 0 ? 0 : calendar.get(Calendar.DAY_OF_WEEK) - 1;
		
		return weeks[weekIndex];
	}
	
	/**
	 * 获取当前日期为星期几
	 * @author zdk
	 * @date Dec 31, 2022
	 * @param value 日期
	 * @return String
	 */
	public static String getCurrentWeek(String value) {
		Date date = string2Date(value, DateFormatUtils.DATE_FORMAT1);
		return getCurrentWeek(date);
	}
	
	/**
	 * 获取指定日期的小时
	 * @author zdk
	 * @date Dec 31, 2022
	 * @param value 日期
	 * @return int
	 */
	public static int getCurrentHour(Date value){
		String date = date2String(value, DateFormatUtils.DATE_HOUR);
		return Integer.valueOf(date);
	}
	
	/**
	 * 获取指定日期的小时
	 * @author zdk
	 * @date Dec 31, 2022
	 * @param value 日期
	 * @return
	 * @return int
	 */
	public static int getCurrentHour(String value) {
		Date date = string2Date(value, DateFormatUtils.DATE_HOUR);
		Calendar calendar = getCalendar(date, DateFormatUtils.DATE_HOUR);
		
		return calendar.get(Calendar.DATE);
	}
	
	/**
	 * 获取指定日期的分钟
	 * @author zdk
	 * @date Dec 31, 2022
	 * @param value 日期
	 * @return int
	 */
	public static int getCurrentMinute(Date value){
		String date = date2String(value, DateFormatUtils.DATE_MINUTE);
		return Integer.valueOf(date);
	}
	
	/**
	 * 获取指定日期的分钟
	 * @author zdk
	 * @date Dec 31, 2022
	 * @param value 日期
	 * @return int
	 */
	public static int getCurrentMinute(String value){
		Date date = string2Date(value, DateFormatUtils.DATE_MINUTE);
		Calendar calendar = getCalendar(date, DateFormatUtils.DATE_MINUTE);
		
		return calendar.get(Calendar.MINUTE);
	}
	
	/**  
	 * 比较两个日期相隔多少天(月、年) <br>
	 * 例：<br>
	 * &nbsp;compareDate("2009-09-12", null, 0);//比较天 <br>
     * &nbsp;compareDate("2009-09-12", null, 1);//比较月 <br> 
     * &nbsp;compareDate("2009-09-12", null, 2);//比较年 <br>
     * 
	 * @author zdk
	 * @date Dec 31, 2022 
     * @param startDay 需要比较的时间 不能为空(null),需要正确的日期格式 ,如：2009-09-12   
     * @param endDay 被比较的时间  为空(null)则为当前时间    
     * @param stype 返回值类型   0为多少天，1为多少个月，2为多少年    
     * @return int
     */    
    public static int compareDate(String startDay,String endDay,int stype) {     
        int n = 0;     
        startDay = DateFormatUtils.formatDate(startDay, "yyyy-MM-dd");
        endDay = DateFormatUtils.formatDate(endDay, "yyyy-MM-dd");
        
        String formatStyle = "yyyy-MM-dd";
        if(1 == stype){
        	formatStyle = "yyyy-MM";
        }else if(2 == stype){
        	formatStyle = "yyyy";
        }   
             
        endDay = endDay==null ? getCurrentTime("yyyy-MM-dd") : endDay;     
             
        DateFormat df = new SimpleDateFormat(formatStyle);     
        Calendar c1 = Calendar.getInstance();     
        Calendar c2 = Calendar.getInstance();     
        try {     
            c1.setTime(df.parse(startDay));     
            c2.setTime(df.parse(endDay));   
        } catch (Exception e) {    
        	e.printStackTrace();
        }     
        while (!c1.after(c2)) {                   // 循环对比，直到相等，n 就是所要的结果     
            n++;     
            if(stype==1){     
                c1.add(Calendar.MONTH, 1);          // 比较月份，月份+1     
            }     
            else{     
                c1.add(Calendar.DATE, 1);           // 比较天数，日期+1     
            }     
        }     
        n = n-1;     
        if(stype==2){     
            n = (int)n/365;     
        }        
        return n;     
    }   
    
    /**
     * 比较两个时间相差多少小时(分钟、秒)
     * @author zdk
     * @date Jan 2, 2014
     * @param startTime 需要比较的时间 不能为空，且必须符合正确格式：2012-12-12 12:12:
     * @param endTime 需要被比较的时间 若为空则默认当前时间
     * @param type 1：小时   2：分钟   3：秒
     * @return int
     */
    public static int compareTime(String startTime , String endTime , int type) {
    	//endTime是否为空，为空默认当前时间
    	if(endTime == null || "".equals(endTime)){
    		endTime = getCurrentTime();
    	}
    	
    	SimpleDateFormat sdf = DateFormatUtils.getFormat("");
    	int value = 0;
    	try {
			Date begin = sdf.parse(startTime);
			Date end = sdf.parse(endTime);
			long between = (end.getTime() - begin.getTime()) / 1000;  //除以1000转换成豪秒
			if(type == 1){   //小时
				value = (int) (between % (24 * 36000) / 3600);
			}
			else if(type == 2){
				value = (int) (between % 3600 / 60);
			}
			else if(type == 3){
				value = (int) (between % 60 / 60);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	return value;
    }
    
    /**
     * 比较两个日期的大小。<br>
     * 若date1 > date2 则返回 1<br>
     * 若date1 = date2 则返回 0<br>
     * 若date1 < date2 则返回-1
     * @autor:zdk
     * @date:2014年9月9日
     *
     * @param date1  
     * @param date2
     * @param format  待转换的格式
     * @return 比较结果
     */
    public static int compare(String date1, String date2,String format) {
        DateFormat df = DateFormatUtils.getFormat(format);
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }
    
    /**
     * 获取指定月份的第一天 
     * 
     * @author : zdk
     * @date : 2016年5月31日 下午5:31:10
     *
     * @param date
     * @return
     */
    public static String getMonthFirstDate(String date){
    	date = DateFormatUtils.formatDate(date);
		return DateFormatUtils.formatDate(date, "yyyy-MM") + "-01";
    }
    
    /**
     * 获取指定月份的最后一天
     * 
     * @author : zdk
     * @date : 2016年5月31日 下午5:32:09
     *
     * @param
     * @return
     */
	public static String getMonthLastDate(String date) {
		Date strDate = DateUtils.string2Date(getMonthFirstDate(date));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(strDate);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		return DateFormatUtils.formatDate(calendar.getTime());
	}
	/**
	 * 获取指定月份的一共有多少天
	 *
	 * @author : yeqiqi
	 * @date : 2023年12月12日 下午3:52:09
	 *
	 * @param
	 * @return
	 */
	public static  int  getMonthDay(Date time){
		//通过Calendar.getInstance()方法创建一个Calendar实例对象。
				Calendar calendar = Calendar.getInstance();
		//使用calendar.setTime(time)方法将指定的时间设置到该Calendar对象中。
				calendar.setTime(time);
		//使用calendar.set(Calendar.DAY_OF_MONTH, 1)方法将日期设置为当月的第一天
				calendar.set(Calendar.DAY_OF_MONTH, 1);
		//使用calendar.add(Calendar.MONTH, 1)方法将月份加1，即切换到下一个月
				calendar.add(Calendar.MONTH, 1);
		//再使用calendar.add(Calendar.DATE, -1)方法将日期减去1天，即得到当前月份的最后一天
				calendar.add(Calendar.DATE, -1);
		//使用calendar.get(Calendar.DAY_OF_MONTH)方法获取最后一天的日期，并将其赋值给变量lastDay
				int lastDay = calendar.get(Calendar.DAY_OF_MONTH);
		//通过System.out.println(lastDay)打印最后一天的日期
				System.out.println(lastDay);
		//返回calendar.getTime()获取最后一天的时间并返回
				return lastDay;
	}

	/**
	 * 获取指定课时结束日期
	 *
	 * @author : yeqiqi
	 * @date : 2023年12月12日 下午4:12:19
	 *
	 * @param
	 * @return
	 */
	public static  Date  getClassHoursEnd(Date time){
		Calendar  clPre=Calendar.getInstance();
		clPre.setTime(time);
		clPre.set(Calendar.DAY_OF_MONTH,15);
		clPre.set(Calendar.HOUR_OF_DAY,23);
		Date now=clPre.getTime();//当前月截止时间
		return  now;
	}
	/**
	 * 获取指定课时开始日期
	 *
	 * @author : yeqiqi
	 * @date : 2023年12月12日 下午4:12:19
	 *
	 * @param
	 * @return
	 */
	public static  Date  getClassHoursStart(Date time){
		Calendar  clPre=Calendar.getInstance();
		clPre.setTime(time);
		clPre.add(Calendar.MONTH,-1);
		clPre.set(Calendar.DAY_OF_MONTH,16);
		clPre.set(Calendar.HOUR,1);
		Date pre=clPre.getTime();//上月开始时间
		return  pre;
	}


	/**
	 * 获取所在星期的第一天
	 * 
	 * @author : zdk
	 * @date : 2016年6月1日 下午12:38:53
	 *
	 * @param date
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static Date getWeekFirstDate(Date date) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		int today = now.get(Calendar.DAY_OF_WEEK);
		int first_day_of_week = now.get(Calendar.DATE) + 2 - today; // 星期一
		now.set(now.DATE, first_day_of_week);
		return now.getTime();
	}
	
	/**
	 * 获取所在星期的最后一天
	 * 
	 * @author : zdk
	 * @date : 2016年6月1日 下午12:40:31
	 *
	 * @param date
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static Date geWeektLastDate(Date date) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		int today = now.get(Calendar.DAY_OF_WEEK);
		int first_day_of_week = now.get(Calendar.DATE) + 2 - today; // 星期一
		int last_day_of_week = first_day_of_week + 6; // 星期日
		now.set(now.DATE, last_day_of_week);
		return now.getTime();
	}

	/**
	 *  设置指定时间
	 * @param date
	 * @param hour
	 * @param minute
	 * @param second
	 * @return
	 */
	public static  Date SpecifyTime(Date date,int hour, int minute,int second){
		Calendar  cl=Calendar.getInstance();
		cl.setTime(date);
		cl.set(Calendar.HOUR_OF_DAY,hour);
		cl.set(Calendar.MINUTE,minute);
		cl.set(Calendar.SECOND,second);
		return  cl.getTime();
	}



}
