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
 * ������Դ�࣬�洢������Դ
 * @���� ������
 *
 * @ʱ�� 2016-11-14
 */
public final class PublicSrc {
	//������
	public static Context context ;
	//������
	public static SensorManager sensorManager ;
	
	public static User user ;
	public static WalkData walkData  ;
	public static WalkDataPK walkDataPK ;
	//״̬��������
	public static NotificationManager notificationManager;
	
	public static SharedPreferences userPreferences ;
	public static LocalRunData RUNDATA;
}
