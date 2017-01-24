package com.lxw.baidumapdemo;

import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.Map;

import tools.PublicSrc;
import viewtools.StartLayout;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class MainHandleTool extends Handler{
	//需要的组件
	private Context context;
	public static StartLayout startLayout;
	public static Handler mainHandler =null;
	public static NotificationManager notificationManager  ;
	DecimalFormat decimalFormat=new DecimalFormat("0.00");

	//定义通知的变量
	public static final int UPDATE_WEATHER = 0X000;
	public static final int UPDATE_GPSTEPY = 0X001;
	public static final int UPDATE_ALLWALK = 0X002;
	public static final int UPDATE_TODAYWALK = 0X003;
	public static final int UPDATE_NOTIFICATION = 0X004;
	public static final int UPDATE_TOAST = 0X005;
	public static final int UPDATE_WALKTYPE = 0X006;
	public static final int UPDATE_RUN = 0X007;
	public static final int UPDATE_TODAYRUN = 0X008;


	public MainHandleTool(Context context) {
		super();
		mainHandler = this;
		this.context = context;

	}

	@Override
	public void handleMessage(Message msg) {

		switch (msg.what) {
		//更新天气板块
		case UPDATE_WEATHER:
			if (startLayout!=null){

				String[] strings = getMessageString(msg);
				startLayout.setWendu(strings[0]);
				startLayout.setWeathercontet(strings[1]);
				startLayout.setLocation(strings[2]);

				break;
			}



			//更新所有步行路程
		case UPDATE_ALLWALK:
			if (startLayout!=null){

				String[] strings = getMessageString(msg);
				startLayout.setAllmileage(strings[0]);

				break;
			}


			//更新今天步行路程
		case UPDATE_TODAYWALK:
			if (startLayout!=null){

				String[] strings = getMessageString(msg);
				startLayout.setTodaymileage(strings[0]);

				break;
			}

			//更新toash
		case UPDATE_TOAST:
			if (context!=null){

				String[] strings = getMessageString(msg);

				Toast.makeText(context, strings[0], 0).show();

				break;
			}


			//更新步行类型
		case UPDATE_WALKTYPE:
			if (startLayout!=null) {
				String[] strings = getMessageString(msg);
				startLayout.setWlakType(Integer.valueOf(strings[0]), Double.valueOf(strings[1]));
			}
			break;


			//更新今天步行路程
		case UPDATE_RUN:
			if (startLayout!=null){
				startLayout.setRunTodaymileage(decimalFormat.format(PublicSrc.RUNDATA.getTodayRun()));
				startLayout.setRunAllmileage(decimalFormat.format(PublicSrc.RUNDATA.getAllRun()));
				break;
			}
		}


	}


	public static void setStartLayout(StartLayout startLayout) {
		MainHandleTool.startLayout = startLayout;
	}


	/**
	 * 发送界面更新
	 * 1.温度，需要三个参数：温度，情况，位置
	 * 
	 * @param @param code 类静态的常量标识码
	 * @param @param string    需要传递的值
	 * @return void   
	 * 
	 */
	public static void sendMessage(int code,String string[]) throws NullPointerException {
		if (mainHandler!=null) {
			Message message = new Message();
			message.what = code;
			//装载数据
			Bundle bundle = new Bundle();
			bundle.putStringArray("value", string);
			message.setData(bundle);
			mainHandler.sendMessage(message);
		}
	}


	/**
	 * 获取信息的数据
	 * 
	 * @param     
	 * @return void   
	 * 
	 */
	public static String[] getMessageString(Message message) {
		return message.getData().getStringArray("value");
	}




}
