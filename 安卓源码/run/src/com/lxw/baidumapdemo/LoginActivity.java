package com.lxw.baidumapdemo;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

import tools.NetTool;
import tools.PublicSrc;
import tools.RunURL;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import entity.User;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.Toast;

public class LoginActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SMSSDK.initSDK(this, "1948a810f3b52", "0e285bdc841b28b0c2799beef6e19922");
		//��ע��ҳ��
		RegisterPage registerPage = new RegisterPage();
		registerPage.setRegisterCallback(new EventHandler()  {
			public void afterEvent(int event, int result, Object data) {
				// ����ע����
				if (result == SMSSDK.RESULT_COMPLETE) {
					@SuppressWarnings("unchecked")
					HashMap<String,Object> phoneMap = (HashMap<String, Object>) data;
					String country = (String) phoneMap.get("country");
			        String phone = (String) phoneMap.get("phone");
					PublicSrc.user.setPhone(Long.parseLong(phone));
					PublicSrc.user.saveUser();
					
					//������������½��֤
					LoginWithPhone login = new LoginWithPhone();
					login.execute(phone);

				}
			}
		});
		registerPage.show(LoginActivity.this);
		finish();
	}


	class LoginWithPhone extends AsyncTask<String, Integer, Void>{

		User u ;
		@Override
		protected Void doInBackground(String... params) {
			String phoneString = params[0];
			Map<String, String> map = new HashMap<String, String>();
			map.put("phone", phoneString);
			String result = NetTool.sendPostRequest(RunURL.LOGINURL,map , "UTF-8");
			if (result.equals("404")) {
				publishProgress(0);
			}else {
				try {
					u = User.jsonParseUser(result);
					publishProgress(100);
				} catch (JSONException e) {
					publishProgress(50);
				}
			}
			return null;
		}
		@Override
		protected void onProgressUpdate(Integer... values) {
			if (values[0]==0) {
				Toast.makeText(getApplicationContext(), "���ӷ������쳣", 0).show();	
				}else {
					if (values[0]==50) {
						Toast.makeText(getApplicationContext(), "���������������쳣", 0).show();		
					}else {
						PublicSrc.user = u;
						if (u.getCode().length()==9) {
							if (u.getName().equals("��")||u.getSex().equals("��")) {
								PublicSrc.user.saveUser();
								//��ת����������
								LoginActivity.this.startActivity(new Intent(LoginActivity.this, PerfectActivity.class));
							}else {
								//��ת��������
								LoginActivity.this.startActivity(new Intent(LoginActivity.this, MainActivity.class));
							}
						}else {
							Toast.makeText(getApplicationContext(), "�����쳣", 0).show();
							LoginActivity.this.startActivity(new Intent(LoginActivity.this, LoginActivity.class));
						}
					}
				}
		}




	};
}
