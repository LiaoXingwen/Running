package servicekit;

import com.lxw.baidumapdemo.MainActivity;
import com.lxw.baidumapdemo.MainHandleTool;

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

public class AccelerometerListenerService extends Service {
	SensorManager sensorManager ;
	WalkAccelerometerListener accelerometerListener ; 
	static PowerManager pm ;
	private WakeLock mWakeLock;
	public static Notification notification;
	public static PendingIntent pendingintent;
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
		accelerometerListener = new sensorkit.WalkAccelerometerListener();
		MainHandleTool.notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		

		pm = (PowerManager) getSystemService(Context.POWER_SERVICE); 
		mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "S"); 

		
		MainHandleTool.notificationManager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		notification = new Notification(com.lxw.baidumapdemo.R.drawable.ic_launcher,  
				getString(com.lxw.baidumapdemo.R.string.app_name), System.currentTimeMillis());  

		pendingintent = PendingIntent.getActivity(this, 0,  
				new Intent(this, MainActivity.class), 0);  
		try {
			notification.setLatestEventInfo(this, "", "今天步行"+PublicSrc.walkData.getWalkNumber()+" 步",  
					pendingintent); 	
		} catch (Exception e) {
			notification.setLatestEventInfo(this, "", "今天步行"+"0"+" 步",  
					pendingintent); 
		}
		
		startForeground(0x111, notification);
		sensorManager.registerListener(accelerometerListener,
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_FASTEST,
				null);
		super.onCreate();

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		flags  = START_STICKY;
		
		mWakeLock.acquire();
		
//		mWakeLock.release();
		return START_STICKY;
	}


	@Override
	public void onDestroy() {
		mWakeLock.release();
		stopForeground(true);
		sensorManager.unregisterListener(accelerometerListener);
		super.onDestroy();
	}



}
