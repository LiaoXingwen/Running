package tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 常用的时间工具 
 * 
 * 
 * @作者 廖兴文
 *
 * @时间 2016-12-9
 */
public final class DateTool {

	/**
	* 获取系统时间 ，格式：yyyy-MM-dd HH:mm:ss
	* 
	* @param @return    
	* @return String   
	* 
	*/
	public static String getNewTime() {
		String format = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(new Date());
	}
	/**
	* 获取系统时间 ，格式：yyyy-MM-dd HH:mm:ss
	* 
	* @param @return    
	* @return String   
	 * @throws ParseException 
	* 
	*/
	public static long stringParseLong(String date) throws ParseException {
		String format = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.parse(date).getTime();
	}
	
	
	/**
	* 获取时间 ，格式：yyyy-MM-dd HH:mm:ss
	* 
	* @param @return    
	* @return String   
	* 
	*/
	public static String getNewTime(Date date) {
		String format = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}
	
	/**
	* 获取时分秒时间 ，格式：HH:mm:ss
	* 
	* @param @return    
	* @return String   
	* 
	*/
	public static String getTimeWithHSM(int cnt) {
			int hour = cnt/3600;
			int min = cnt % 3600 / 60;
			int second = cnt % 60;
			return String.format(Locale.CHINA,"%02d:%02d:%02d",hour,min,second);
	}
}
