package tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.lxw.baidumapdemo.MainHandleTool;

import entity.GsonService;
import entity.OpenUserData;

import android.R.integer;
import android.os.AsyncTask;

public class GetAboutUser extends AsyncTask<Double, Integer, List<OpenUserData>> {

	@Override
	protected ArrayList<OpenUserData> doInBackground(Double... params) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("phone", String.valueOf(PublicSrc.user.getPhone()));
		map.put("code", PublicSrc.user.getCode());
		map.put("order", "findAboutUsers");
		map.put("lat", String.valueOf(params[0]));
		map.put("lng", String.valueOf(params[1]));
		String re = NetTool.sendPostRequest(RunURL.UserOther, map, "utf-8");
		System.out.println("re:"+re);
		if (re.equals("404")) {
			
		}else {
			if (re.equals("Пе")) {
				
			}else {
				ArrayList<OpenUserData> openUserDatas = GsonService.parseJson(re, new ArrayList<OpenUserData>().getClass());
				System.out.println("re:"+openUserDatas.size());
				return openUserDatas;
			}
		}
		return null;
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		if (values[0]==100) {
		}
		super.onProgressUpdate(values);
	}

}
