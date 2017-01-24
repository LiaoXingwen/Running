package com.lxw.baidumapdemo;

import java.text.ParseException;

import servicekit.ManageService;

import com.baidu.pano.platform.http.t;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.webkit.WebView.FindListener;
import android.widget.Toast;

public class RunHanbleTool extends Handler {

	RunActivity runActivity ;
	public static RunHanbleTool runHanbleTool ; 

	public static final int STARTLOCATION = 0x0003;//��ʼ��λ
	public static final int UPDATE_RUNNUMBER= 0x0004;//����
	public static final int UPDATE_TOAST = 0X005;
	public static final int UPDATE_ALLSPEED = 0X006;

	public RunHanbleTool(RunActivity runActivity) {
		this.runActivity = runActivity ;
		runHanbleTool = this;
	}

	@Override
	public void handleMessage(Message msg) {

		switch (msg.what) {
		case UPDATE_RUNNUMBER:
		{
			ManageService.updatdNotification("�ܲ�ģʽ��  ����"+MainHandleTool.getMessageString(msg)[0]+" km");
			runActivity.setRunNumber(MainHandleTool.getMessageString(msg)[0]);
		}
		break;

		case UPDATE_TOAST:
		{

			String[] strings = MainHandleTool.getMessageString(msg);

			Toast.makeText(runActivity, strings[0], 0).show();

			break;
		}
		case UPDATE_ALLSPEED:
		{
			break;
		}

		}

	}
	public static void sendMessage(int code,String string[]) throws NullPointerException {
		if (runHanbleTool!=null) {
			Message message = new Message();
			message.what = code;
			//װ������
			Bundle bundle = new Bundle();
			bundle.putStringArray("value", string);
			message.setData(bundle);
			runHanbleTool.sendMessage(message);
		}
	}
}
