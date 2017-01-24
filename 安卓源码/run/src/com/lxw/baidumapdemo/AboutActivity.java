package com.lxw.baidumapdemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.mapapi.SDKInitializer;

import tools.GetAboutUser;
import tools.NetTool;
import tools.PublicSrc;
import tools.RunURL;
import viewtools.ViewAboutUsers;
import entity.GsonService;
import entity.OpenUserData;
import entity.RunJsonTool;
import entity.User;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;



/**
 * չʾ���棬�߼��жϽ��棬��ʼ������
 * @���� ������
 *
 * @ʱ�� 2016-12-17
 */
public class AboutActivity extends Activity {
	
	ViewAboutUsers aboutUsers;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		aboutUsers = new ViewAboutUsers(getApplicationContext());
		
		setContentView(aboutUsers);
	}


	//���һ�ΰ����ؼ���ʱ��
	long lastBack = 0;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			//���ΰ����ؼ���������
			if (System.currentTimeMillis()-lastBack<2000) {
				this.finish();
			}else {
				lastBack = System.currentTimeMillis();
				Toast.makeText(getApplicationContext(), "�ٰ�һ�ν��˳�����", Toast.LENGTH_SHORT).show();
			}
			return false ;
		}
		return super.onKeyDown(keyCode, event);
	}

	
}
