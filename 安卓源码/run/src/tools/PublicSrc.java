package tools;

import entity.LocalRunData;
import entity.User;
import entity.WalkData;
import entity.WalkDataPK;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.SensorManager;

/**
 * 公共资源类，存储公用资源
 * @作者 廖兴文
 *
 * @时间 2016-11-14
 */
public final class PublicSrc {
	//上下文
	public static Context context ;
	//传感器
	public static SensorManager sensorManager ;
	
	public static User user ;
	public static WalkData walkData  ;
	public static WalkDataPK walkDataPK ;
	//状态栏管理器
	public static NotificationManager notificationManager;
	
	public static SharedPreferences userPreferences ;
	public static LocalRunData RUNDATA;
}
