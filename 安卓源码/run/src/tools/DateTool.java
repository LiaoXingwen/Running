package tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * ���õ�ʱ�乤�� 
 * 
 * 
 * @���� ������
 *
 * @ʱ�� 2016-12-9
 */
public final class DateTool {

	/**
	* ��ȡϵͳʱ�� ����ʽ��yyyy-MM-dd HH:mm:ss
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
	* ��ȡϵͳʱ�� ����ʽ��yyyy-MM-dd HH:mm:ss
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
	* ��ȡʱ�� ����ʽ��yyyy-MM-dd HH:mm:ss
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
	* ��ȡʱ����ʱ�� ����ʽ��HH:mm:ss
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
