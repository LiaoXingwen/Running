package servicekit;

import com.lxw.baidumapdemo.MainActivity;
import com.lxw.baidumapdemo.MainHandleTool;
import com.lxw.baidumapdemo.RunActivity;

import sensorkit.RunAccelerometerListener;
import sensorkit.WalkAccelerometerListener;
import tools.PublicSrc;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

public class RunAccelerometerListenerService extends Service {
	SensorManager sensorManager ;
	RunAccelerometerListener accelerometerListener ; 
	static PowerManager pm ;
	private WakeLock mWakeLock;
	public Notification notification;
	public PendingIntent pendingintent;
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
		accelerometerListener = new RunAccelerometerListener();
		MainHandleTool.notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		pm = (PowerManager) getSystemService(Context.POWER_SERVICE); 
		mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "S"); 
		
		//开始跑步时关闭记步器
		stopService(new Intent(getApplicationContext(), AccelerometerListenerService.class));
		sensorManager.registerListener(accelerometerListener,
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_UI,
				null);
		
		flags  = START_STICKY;
		notification = new Notification(com.lxw.baidumapdemo.R.drawable.ic_launcher,  
				getString(com.lxw.baidumapdemo.R.string.app_name), System.currentTimeMillis());  
//防止开启多个activityd的方法：<activity android:launchMode="singleInstance" >
		pendingintent = PendingIntent.getActivity(this, 0,  
				new Intent(this, RunActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);  
		notification.setLatestEventInfo(this, "", "跑步模式：  已跑0.00 km",  
				pendingintent); 
		startForeground(0x111, notification);
		mWakeLock.acquire();
		return START_STICKY;
	}
	

	@Override
	public void onDestroy() {
		//重新开启
		startService(new Intent(getApplicationContext(), AccelerometerListenerService.class));
		mWakeLock.release();
		stopForeground(true);
		sensorManager.unregisterListener(accelerometerListener);
		super.onDestroy();
	}

}
