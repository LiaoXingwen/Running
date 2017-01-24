package viewtools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lxw.baidumapdemo.MainHandleTool;

import tools.NetTool;
import tools.PublicSrc;
import tools.RunURL;
import entity.OpenUserData;
import android.content.Context;
import android.os.AsyncTask;
import android.text.InputFilter.AllCaps;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewRanking extends LinearLayout {

	int width ,height;
	Button all ,today;
	LinearLayout content ;
	
	List<OpenUserData> todaylist = new LinkedList<OpenUserData>();
	List<OpenUserData> alllist = new LinkedList<OpenUserData>();
	
	
	
	public ViewRanking(Context context) {
		super(context);
		width = DisplayUtil.getWidth(context);
		height = DisplayUtil.getHeight(context);
		setBackgroundColor(0xffffffff);
		setOrientation(LinearLayout.VERTICAL);
		LinearLayout butons = new LinearLayout(context);
		butons.setOrientation(LinearLayout.HORIZONTAL);

		today = new Button(context);
		today.setWidth(width/2);
		today.setText("今天排行");
		today.setBackgroundColor(0xcc2b2b2b);
		today.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				today.setBackgroundColor(0xcc2b2b2b);
				all.setBackgroundColor(0x772b2b2b);
				if (todaylist.size()<=0) {
					new GetRankingForAll().execute();
				}else {
					addContent(makeLayout(todaylist));
				}
			}
		});

		all = new Button(context);
		all.setWidth(width/2);
		all.setText("总排行");
		all.setBackgroundColor(0x772b2b2b);
		all.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				today.setBackgroundColor(0x772b2b2b);
				all.setBackgroundColor(0xcc2b2b2b);
				if (alllist.size()<=0) {
					new GetRankingForAll().execute();
				}else {
					addContent(makeLayout(alllist));
				}
			}
		});


		new GetRankingForToday().execute();

		butons.addView(today);
		butons.addView(all);
		addView(butons);
		content = new LinearLayout(context);
		ScrollView scrollView = new ScrollView(context);
		scrollView.setVerticalScrollBarEnabled(false);
		scrollView.addView(content);
		addView(scrollView);

	}

	public class GetRankingForToday extends AsyncTask<Void, Integer, List<OpenUserData>> {
		List<OpenUserData> list = new ArrayList<OpenUserData>();
		@Override
		protected List<OpenUserData> doInBackground(Void... params) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("phone", String.valueOf(PublicSrc.user.getPhone()));
			map.put("code", PublicSrc.user.getCode());
			map.put("order", "getRankingForToday");
			String re = NetTool.sendPostRequest(RunURL.UserOther, map, "utf-8");
			if (re.equals("404")) {
				publishProgress(-1);
			}else {
				if (re.equals("空") ){
					publishProgress(0);	
				}else {
					if (re.equals("登陆失效，请重新登陆")) {
						publishProgress(50);
					}else {
						JSONArray jsonArray;
						try {
							jsonArray = new JSONArray(re);
							for (int i = 0; i < jsonArray.length(); i++) {
								JSONObject Object = jsonArray.getJSONObject(i);
								OpenUserData openUserData = new OpenUserData();
								openUserData.setDistance(Object.getString("distance"));
								openUserData.setName(Object.getString("name"));
								openUserData.setPhone(Object.getLong("phone"));
								openUserData.setRunNum(Object.getString("runNum"));
								openUserData.setWalkNum(Object.getString("walkNum"));
								list.add(openUserData);
							}
						} catch (JSONException e) {
						}

						if (list.size()>0) {
							publishProgress(100);

						}else {
							publishProgress(0);
						}

					}
					return list;
				}
			}
			return null;
		}

		
		
		
		
		@Override
		protected void onProgressUpdate(Integer... values) {

			switch (values[0]) {
			case -1:
				Toast.makeText(getContext(), "连接异常", 0).show();
				break;
			case 0:
				Toast.makeText(getContext(), "列表为空", 0).show();
				break;
			case 50:
				Toast.makeText(getContext(), "登陆失效，请重新登陆,3秒后系统自动将自动退出", 0).show();
				PublicSrc.user.setCode(" ");
				PublicSrc.user.saveUser();
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						System.exit(0);
					}
				}, 3000);
				break;
			case 100:
				todaylist = list ; 
				addContent(makeLayout(todaylist));
				break;
			}


			super.onProgressUpdate(values);
		}

	}

	
	
	public class GetRankingForAll extends AsyncTask<Void, Integer, List<OpenUserData>> {
		List<OpenUserData> list = new ArrayList<OpenUserData>();
		@Override
		protected List<OpenUserData> doInBackground(Void... params) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("phone", String.valueOf(PublicSrc.user.getPhone()));
			map.put("code", PublicSrc.user.getCode());
			map.put("order", "getRankingForAll");
			String re = NetTool.sendPostRequest(RunURL.UserOther, map, "utf-8");
			if (re.equals("404")) {
				publishProgress(-1);
			}else {
				if (re.equals("空") ){
					publishProgress(0);	
				}else {
					if (re.equals("登陆失效，请重新登陆")) {
						publishProgress(50);
					}else {
						JSONArray jsonArray;
						try {
							jsonArray = new JSONArray(re);
							for (int i = 0; i < jsonArray.length(); i++) {
								JSONObject Object = jsonArray.getJSONObject(i);
								OpenUserData openUserData = new OpenUserData();
								openUserData.setDistance(Object.getString("distance"));
								openUserData.setName(Object.getString("name"));
								openUserData.setPhone(Object.getLong("phone"));
								openUserData.setRunNum(Object.getString("runNum"));
								openUserData.setWalkNum(Object.getString("walkNum"));
								list.add(openUserData);
							}
						} catch (JSONException e) {
						}

						if (list.size()>0) {
							publishProgress(100);

						}else {
							publishProgress(0);
						}
					}
					return list;
				}
			}
			return null;
		}

		
		
		
		
		@Override
		protected void onProgressUpdate(Integer... values) {

			switch (values[0]) {
			case -1:
				Toast.makeText(getContext(), "连接异常", 0).show();
				break;
			case 0:
				Toast.makeText(getContext(), "列表为空", 0).show();
				break;
			case 50:
				Toast.makeText(getContext(), "登陆失效，请重新登陆,3秒后系统自动将自动退出", 0).show();
				PublicSrc.user.setCode(" ");
				PublicSrc.user.saveUser();
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						System.exit(0);
					}
				}, 3000);
				break;
			case 100:
				alllist = list ; 
				addContent(makeLayout(alllist));
				break;
			}

			super.onProgressUpdate(values);
		}

	}

	
	
	public LinearLayout makeLayout(List<OpenUserData> list) {
		LinearLayout layout = new LinearLayout(getContext());
		layout.setOrientation(LinearLayout.VERTICAL);
		TextView textView = new TextView(getContext());
		textView.setText("步行排行");
		textView.setTextColor(0xff8F8F8F);
		textView.setWidth(width);
		textView.setGravity(Gravity.CENTER);
		textView.setBackgroundColor(0xffffffff);
		layout.addView(textView);
		
		int index = 0 ; 
		for (int i = 0; i < list.size(); i++) {
			OpenUserData openUserData = list.get(i);
			if (!openUserData.getDistance().equals("w")) {
				index = i ;
				break;
			}
			RankingPager pager = new RankingPager(getContext(), Double.parseDouble(list.get(0).getWalkNum()), openUserData);
			layout.addView(pager);

		}
		TextView textView1 = new TextView(getContext());
		textView1.setText("跑步排行");
		textView1.setWidth(width);
		textView1.setTextColor(0xff8F8F8F);
		textView1.setGravity(Gravity.CENTER);
		textView1.setBackgroundColor(0xffffffff);
		layout.addView(textView1);
		for (int i = index; i < list.size(); i++) {
			OpenUserData openUserData = list.get(i);
			if (!openUserData.getDistance().equals("r")) {
				continue;
			}
			RankingPager pager = new RankingPager(getContext(), Double.parseDouble(list.get(index).getRunNum()), openUserData);
			layout.addView(pager);

		}

		return layout;
	}

	public void addContent(LinearLayout layout) {
		content.removeAllViews();
		content.addView(layout);
	}

}
