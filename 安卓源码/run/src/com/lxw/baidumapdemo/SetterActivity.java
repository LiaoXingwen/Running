package com.lxw.baidumapdemo;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

import com.baidu.mapapi.SDKInitializer;

import tools.NetTool;
import tools.PublicSrc;
import tools.RunURL;
import viewtools.ColorPickerDialog;
import entity.RunJsonTool;
import entity.User;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
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
public class SetterActivity extends Activity {

	private Button btnColorPicker;
	private ColorPickerDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setter);
initViews();
	}
	 /**
     * ��ʼ��UI
     */
    private void initViews() {
    	btnColorPicker = (Button) findViewById(R.id.bgchange);
    	btnColorPicker.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog = new ColorPickerDialog(SetterActivity.this,
						MainActivity.runPanel.getBg(),
						"��ɫѡ����", 
						new ColorPickerDialog.OnColorChangedListener() {
					
					@Override
					public void colorChanged(int color) {
						MainActivity.runPanel.setBg(color);
						SetterActivity.this.finish();  
					}
				});
				dialog.show();
			}
		});
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
