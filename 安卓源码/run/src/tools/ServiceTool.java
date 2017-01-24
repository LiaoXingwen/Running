package tools;

import servicekit.AccelerometerListenerService;
import android.content.Context;
import android.content.Intent;

public class ServiceTool {

	static Context context ; 
	public ServiceTool(Context context ){
		this.context = context ; 
	}

	public static void startWalkSercive() {
		if (context!=null) {
			context.startService(new Intent(context, AccelerometerListenerService.class));
		}
	}
	public static void stopWalkSercive() {
		if (context!=null)
			context.stopService(new Intent(context, AccelerometerListenerService.class));
	}

	public static synchronized Context getContext() {
		return context;
	}

	public static synchronized void setContext(Context context) {
		ServiceTool.context = context;
	}
}
