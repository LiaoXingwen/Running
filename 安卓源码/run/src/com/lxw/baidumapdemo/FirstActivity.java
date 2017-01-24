package com.lxw.baidumapdemo;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

import com.baidu.mapapi.SDKInitializer;

import tools.NetTool;
import tools.PublicSrc;
import tools.RunURL;
import tools.ServiceTool;
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
 * 展示界面，逻辑判断界面，初始化数据
 * @作者 廖兴文
 *
 * @时间 2016-12-17
 */
public class FirstActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		new RunJsonTool();
		new ServiceTool(getApplicationContext());
		setContentView(R.layout.first);
		//初始化数据
		if (PublicSrc.userPreferences ==null) {
			PublicSrc.userPreferences = getSharedPreferences("user", getApplicationContext().MODE_PRIVATE);
		}
		if (PublicSrc.user==null) {
			PublicSrc.user = User.sharedPreferencesParseUser();
		}
		isLogin();

	}


	//最后一次按返回键的时间
	long lastBack = 0;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			//两次按返回键少于两秒
			if (System.currentTimeMillis()-lastBack<2000) {
				this.finish();
			}else {
				lastBack = System.currentTimeMillis();
				Toast.makeText(getApplicationContext(), "再按一次将退出程序", Toast.LENGTH_SHORT).show();
			}
			return false ;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void isLogin(){
		if (PublicSrc.user.getPhone()>1000000000) {
			if (PublicSrc.user.getCode().length()!=9) {
				LoginWithPhone loginWithPhone = new LoginWithPhone();
				loginWithPhone.execute(String.valueOf(PublicSrc.user.getPhone()));
			}else {
				if (PublicSrc.user.getName().length()>1) {
					startActivity(new Intent(this, MainActivity.class));
					finish();
				}else {
					startActivity(new Intent(this, PerfectActivity.class));
					finish();
				}
			}

		}else {
			startActivity(new Intent(this, LoginActivity.class));
			finish();
		}
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
				Toast.makeText(getApplicationContext(), "连接服务器异常", 0).show();	
			}else {
				if (values[0]==50) {
					Toast.makeText(getApplicationContext(), "服务器返回数据异常", 0).show();		
				}else {
					PublicSrc.user = u;
					if (u.getCode().length()==9) {
						if (u.getName().equals("空")||u.getSex().equals("空")) {
							PublicSrc.user.saveUser();
							//跳转到完善资料
							startActivity(new Intent(FirstActivity.this, PerfectActivity.class));
							finish();
						}else {
							//跳转到主界面
							startActivity(new Intent(FirstActivity.this, MainActivity.class));
							finish();
						}
					}else {
						Toast.makeText(getApplicationContext(), "数据异常", 0).show();
						startActivity(new Intent(FirstActivity.this, LoginActivity.class));
						finish();
					}
				}
			}
		}




	};
}
