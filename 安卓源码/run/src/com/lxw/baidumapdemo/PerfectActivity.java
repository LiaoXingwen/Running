package com.lxw.baidumapdemo;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

import tools.NetTool;
import tools.PublicSrc;
import tools.RunURL;
import entity.RunJsonTool;
import entity.User;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;



/**
 * 展示界面，逻辑判断界面，初始化数据
 * @作者 廖兴文
 *
 * @时间 2016-12-17
 */
public class PerfectActivity extends Activity {

	EditText editText;
	RadioButton men ,women;
	Button sumitButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		//服务器验证码的长度为9
		if (!(PublicSrc.user.getCode().length()==9)) {
			Toast.makeText(getApplicationContext(), "验证异常，请短信认证"+PublicSrc.user.getCode(), 0).show();
			startActivity(new Intent(this, LoginActivity.class));
			finish();
		}

		editText = (EditText) findViewById(R.id.Pname);
		men = (RadioButton) findViewById(R.id.men);
		women = (RadioButton) findViewById(R.id.women);
		sumitButton = (Button) findViewById(R.id.Psumit);

		sumitButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String name = editText.getText().toString();
				if (name.trim().length()>=2&&!sql_inj(name)) {
					PublicSrc.user.setName(name);
					if (men.isChecked()) {
						PublicSrc.user.setSex("男");
					}else {
						PublicSrc.user.setSex("女");
					}
					UploadData u = new UploadData();
					u.execute();
					
				}else {
					Toast.makeText(getApplicationContext(), "数据不符合系统要求，请重新输入", Toast.LENGTH_SHORT).show();
				}
			}
		});

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
	/**
	 * 
	 * 防止sql注入
	 * @param @param str
	 * @param @return    
	 * @return boolean   
	 * 
	 */
	public static boolean sql_inj(String str)

	{
		String inj_str = "'|and|exec|insert|select|delete|update|count|*|%|chr|mid|master|truncate|char|declare|;|or|-|+|,";
		//这里的东西还可以自己添加
		String[] inj_stra=inj_str.split("\\|");

		for (int i=0 ; i <inj_stra.length ; i++ )
		{
			if (str.indexOf(inj_stra[i])==0)
			{
				return true;
			}
		}
		return false;
	}



	class UploadData extends AsyncTask<Void, Integer, Void>{

		User u ;
		String result;
		@Override
		protected Void doInBackground(Void... params) {
			result = NetTool.sendPostRequest(RunURL.DATAURL,PublicSrc.user.paserMap() , "UTF-8");
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
					Toast.makeText(getApplicationContext(), result, 0).show();
					Toast.makeText(getApplicationContext(), "服务器返回数据异常", 0).show();		
				}else {
					finish();
					if (u.getCode().length()==9) {
						if (u.getName().equals(PublicSrc.user.getName())&&u.getSex().equals(PublicSrc.user.getSex())) {
							//成功
							PublicSrc.user = u;
							PublicSrc.user.saveUser();
							startActivity(new Intent(PerfectActivity.this, MainActivity.class));
						}else {
							Toast.makeText(getApplicationContext(), "服务器返回数据异常", 0).show();
							PerfectActivity.this.finish();
						}
					}else {
						Toast.makeText(getApplicationContext(), "数据异常", 0).show();
					}
				}
			}
		}
	};
}
