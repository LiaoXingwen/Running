package viewtools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tools.GetAboutUser;
import tools.NetTool;
import tools.PublicSrc;
import tools.RunURL;

import com.lxw.baidumapdemo.AboutActivity;
import com.lxw.baidumapdemo.MainActivity;
import com.lxw.baidumapdemo.R;

import entity.OpenUserData;
import android.content.Context;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class ViewAboutUsers extends LinearLayout {

	
	int width ,height;
	FindButton findButton ;
	LinearLayout layout ; 
	public ViewAboutUsers(final Context context) {
		super(context);
		setOrientation(LinearLayout.VERTICAL);
		setBackgroundResource(R.drawable.findbg);
		width = DisplayUtil.getWidth(context);
		height = DisplayUtil.getHeight(context);
		 new GetAboutUser().execute();
		 findButton = new FindButton(context){

			@Override
			public void onClick(View v) {
				start();
				if (MainActivity.latLng!=null) {
					GetAboutUser u = new GetAboutUser();
					u.execute(MainActivity.latLng.latitude,MainActivity.latLng.longitude);
				}else {
					Toast.makeText(context, "还没有获取到位置", 0).show();
				}
			}};
		addView(findButton);
		layout = new LinearLayout(context);
		addView(layout);  
		//获取附近的人列表
		if (MainActivity.latLng!=null) {
			GetAboutUser u = new GetAboutUser();
			u.execute(MainActivity.latLng.latitude,MainActivity.latLng.longitude);
		}else {
			Toast.makeText(context, "还没有获取到位置", 0).show();
		}
		
		
	}
	
	
	public class GetAboutUser extends AsyncTask<Double, Integer, List<OpenUserData>> {
		List<OpenUserData> list = new ArrayList<OpenUserData>();
		@Override
		protected List<OpenUserData> doInBackground(Double... params) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("phone", String.valueOf(PublicSrc.user.getPhone()));
			map.put("code", PublicSrc.user.getCode());
			map.put("order", "findAboutUsers");
//			map.put("lat",String.valueOf(params[0]));
//			map.put("lng", String.valueOf(params[1]));
			map.put("lat","23.456826");
			map.put("lng", "113.498412");
			//			113.501206
			//			map.put("lng", "113.501206");
			String re = NetTool.sendPostRequest(RunURL.UserOther, map, "utf-8");
			System.out.println("re:"+re);
			if (re.equals("404")) {
				publishProgress(0);
			}else {
				if (re.equals("空")) {
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
						publishProgress(50);
					}

					return list;
				}
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {

			switch (values[0]) {
			case 0:
				findButton.stop(); 
				Toast.makeText(getContext(), "连接异常", 0).show();
				break;
			case 50:
				findButton.stop();
				Toast.makeText(getContext(), "没有找到附近用户", 0).show();
				break;
			case 100:
//				AboutActivity.this.setContentView(new ViewAboutUsers(getApplicationContext(), list));
				findButton.stop();
				findButton.setList(list);
				addUserPagers(new ViewUserPagers(getContext(),list));
				break;
			}


			super.onProgressUpdate(values);
		}

	}
	
	public void addUserPagers(ViewUserPagers pagers){
		layout.removeAllViews();
		layout.addView(pagers);
	}
}
