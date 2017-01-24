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
	//��Ҫ�����
	private Context context;
	public static StartLayout startLayout;
	public static Handler mainHandler =null;
	public static NotificationManager notificationManager  ;
	DecimalFormat decimalFormat=new DecimalFormat("0.00");

	//����֪ͨ�ı���
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
		//�����������
		case UPDATE_WEATHER:
			if (startLayout!=null){

				String[] strings = getMessageString(msg);
				startLayout.setWendu(strings[0]);
				startLayout.setWeathercontet(strings[1]);
				startLayout.setLocation(strings[2]);

				break;
			}



			//�������в���·��
		case UPDATE_ALLWALK:
			if (startLayout!=null){

				String[] strings = getMessageString(msg);
				startLayout.setAllmileage(strings[0]);

				break;
			}


			//���½��첽��·��
		case UPDATE_TODAYWALK:
			if (startLayout!=null){

				String[] strings = getMessageString(msg);
				startLayout.setTodaymileage(strings[0]);

				break;
			}

			//����toash
		case UPDATE_TOAST:
			if (context!=null){

				String[] strings = getMessageString(msg);

				Toast.makeText(context, strings[0], 0).show();

				break;
			}


			//���²�������
		case UPDATE_WALKTYPE:
			if (startLayout!=null) {
				String[] strings = getMessageString(msg);
				startLayout.setWlakType(Integer.valueOf(strings[0]), Double.valueOf(strings[1]));
			}
			break;


			//���½��첽��·��
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
	 * ���ͽ������
	 * 1.�¶ȣ���Ҫ�����������¶ȣ������λ��
	 * 
	 * @param @param code �ྲ̬�ĳ�����ʶ��
	 * @param @param string    ��Ҫ���ݵ�ֵ
	 * @return void   
	 * 
	 */
	public static void sendMessage(int code,String string[]) throws NullPointerException {
		if (mainHandler!=null) {
			Message message = new Message();
			message.what = code;
			//װ������
			Bundle bundle = new Bundle();
			bundle.putStringArray("value", string);
			message.setData(bundle);
			mainHandler.sendMessage(message);
		}
	}


	/**
	 * ��ȡ��Ϣ������
	 * 
	 * @param     
	 * @return void   
	 * 
	 */
	public static String[] getMessageString(Message message) {
		return message.getData().getStringArray("value");
	}




}
