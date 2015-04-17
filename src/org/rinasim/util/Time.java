package org.rinasim.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间
 * @author 刘旭涛
 * @date 2015年3月17日 上午10:08:35
 * @since v1.0
 */
public class Time {

	/**
	 * 获取时间到秒
	 * @return 当前时间
	 */
	public static String getDateTime(){
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date=new Date();
		String strDate=format.format(date);
		return strDate;
	}

	/**
	 * 获取时间到分钟
	 * @return 当前时间
	 */
	public static String getDate(){
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date=new Date();
		String strDate=format.format(date);
		return strDate;
	}
	
}
