package servicekit;

import sensorkit.WalkAccelerometerListener;
import tools.PublicSrc;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;

/**
 * 开机启动
 * @作者 廖兴文
 *
 * @时间 2016-11-14
 */
public class BootReceiver extends BroadcastReceiver { 
	 // 系统启动完成  
    static final String ACTION = "android.intent.action.BOOT_COMPLETED";  
	public void onReceive(Context ctx, Intent intent) { 
		
		initSrc(ctx);
		
		Intent myIntent = new Intent();
        myIntent.setAction("servicelit.ManageService");
        ctx.startService(myIntent);
	}

	private static void initSrc(Context c) {
		PublicSrc.context = c ;
	} 
} 
