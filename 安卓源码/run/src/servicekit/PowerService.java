package servicekit;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class PowerService extends Service {
	


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
		

		return START_STICKY;
	}

	@Override
	public void onDestroy() {
	
		super.onDestroy();
		
	}


	
}
