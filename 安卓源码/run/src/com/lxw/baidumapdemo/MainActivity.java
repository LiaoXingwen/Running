package com.lxw.baidumapdemo;


import locationkit.LocationApplication;
import locationkit.WeatherLocationListener;
import sensorkit.WalkAccelerometerListener;
import servicekit.AccelerometerListenerService;
import servicekit.ManageService;
import tools.GetWeather;
import tools.UploadLoaction;
import viewtools.MyDialog;
import viewtools.StartLayout;
import viewtools.WevaFrame;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;



public class MainActivity extends Activity {  

	LocationApplication locationApplication ; 
	
	public static LatLng latLng ; 

	private WevaFrame main;
	public static StartLayout runPanel;
	@Override  
	protected void onCreate(Bundle savedInstanceState) {  


		super.onCreate(savedInstanceState); 
		MainHandleTool mainHandleTool = new MainHandleTool(this);
		startService(new Intent(getApplicationContext(), ManageService.class));

		//������
		main =  new WevaFrame(getApplicationContext());

		//�ܲ����
		runPanel = main.getStartLayout();
		runPanel.setMainActivity(this);

		MainHandleTool.setStartLayout(runPanel);


		initLocationApp();

		runPanel.getStarView().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(MainActivity.this,RunActivity.class);

				MainActivity.this.startActivity(intent);
			}
		}); 

		setContentView(main);
	}  


	private void initLocationApp() {
		//������λ
		locationApplication = new LocationApplication(getApplicationContext()) ; 
		locationApplication.setLocationClientOption(LocationMode.Hight_Accuracy, 0, true, true, true, false);
		locationApplication.registerLocationListener(new BDLocationListener() {

			@Override
			public void onReceiveLocation(BDLocation location) {
				boolean isLocation = false ;
				if (location.getLocType() == BDLocation.TypeGpsLocation){// GPS��λ���
					isLocation = true;
				} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){// ���綨λ���
					isLocation = true;
				} else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// ���߶�λ���
					isLocation = true;
				} 
				if (isLocation) {
					latLng = new LatLng(location.getLatitude(), location.getLongitude());
					UploadLoaction uploaction = new UploadLoaction();
					uploaction.execute(location.getLatitude(),location.getLongitude());
					String cityname = location.getAddrStr().substring(location.getAddrStr().indexOf("ʡ")+1,location.getAddrStr().indexOf("��"));
					GetWeather getWeather = new GetWeather();
					if (getWeather.isFindCityIds(cityname)) {
						getWeather.execute();
						locationApplication.stopLocation(); 
					}
				}
			}
		});

		locationApplication.startLocation();

	}


	@Override
	protected void onResume() {
		super.onResume();

		if (RunActivity.ISOPENRUN) {
			Intent intent = new Intent(MainActivity.this,RunActivity.class);
			MainActivity.this.startActivity(intent);
		}
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	//���һ�ΰ����ؼ���ʱ��
	long lastBack = 0;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			//���ΰ����ؼ���������
			if (System.currentTimeMillis()-lastBack<2000) {
				showExit();
			}else {
				lastBack = System.currentTimeMillis();
				Toast.makeText(getApplicationContext(), "�ٰ�һ�ν��˳�����", Toast.LENGTH_SHORT).show();
			}
			return false ;
		}
		return super.onKeyDown(keyCode, event);
	}


	public void showExit() {
		final MyDialog dialog = new MyDialog(MainActivity.this);
		dialog.settitle("ϵͳ��ʾ");
		dialog.setmsg("�Ƿ��ں�̨���мƲ����򣿣�");
		//ȡ��
		dialog.setOnNegativeListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MainActivity.this.stopService(new Intent(MainActivity.this, AccelerometerListenerService.class));
				dialog.cancel();
				MainActivity.this.finish();
			}
		});
		dialog.setOnPositiveListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.cancel();
				MainActivity.this.finish();
			}
		});

		dialog.show();
	}

}