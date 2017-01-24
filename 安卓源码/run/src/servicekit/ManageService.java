package servicekit;

import sensorkit.WalkAccelerometerListener;
import tools.PublicSrc;

import com.lxw.baidumapdemo.MainActivity;
import com.lxw.baidumapdemo.MainHandleTool;

import entity.LocalRunData;
import entity.User;
import entity.WalkData;
import entity.WalkDataPK;

import SqlLiteDAO.DatabaseHelper;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

/**
 * 管理所有的服务
 * 开机自动启动
 * 将app置于前台
 * @作者 廖兴文
 *
 * @时间 2016-11-14
 */
public class ManageService extends Service {

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


		//启动服务
		//初始化数据
		try{
			PublicSrc.context = getApplicationContext() ;
			PublicSrc.userPreferences = getApplicationContext().getSharedPreferences("user", getApplicationContext().MODE_PRIVATE);

			DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
			PublicSrc.walkDataPK=new WalkDataPK();
			PublicSrc.walkDataPK.setUid(PublicSrc.user.getPhone());
			PublicSrc.walkData = new WalkData();
			PublicSrc.walkData.setWalkDataPK(PublicSrc.walkDataPK);
			
			PublicSrc.walkData.query();
			PublicSrc.RUNDATA = LocalRunData.SqlParseLocalRunData(PublicSrc.user.getPhone());
			MainHandleTool.sendMessage(MainHandleTool.UPDATE_RUN, null);
			startService(new Intent(getApplicationContext(), AccelerometerListenerService.class));
		}catch(Exception exception){
			stopService(new Intent(getApplicationContext(), AccelerometerListenerService.class));
		}
		
		onDestroy();
		
		return super.onStartCommand(intent, flags, startId);
	}

	@Override  
	public void onDestroy() {  
		super.onDestroy();  
	}  
	
	public static void updatdNotification(String title) {
		if (PublicSrc.context!=null&&MainHandleTool.notificationManager!=null) {
			AccelerometerListenerService.notification.setLatestEventInfo(PublicSrc.context, "", title,  
					AccelerometerListenerService.pendingintent); 
			MainHandleTool.notificationManager.notify(0x111, AccelerometerListenerService.notification);

		}
	}

}
