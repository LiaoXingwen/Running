package tools;

import java.util.HashMap;
import java.util.Map;

import com.lxw.baidumapdemo.MainHandleTool;

import android.R.integer;
import android.os.AsyncTask;

public class UploadLoaction extends AsyncTask<Double, integer, Void> {

	@Override
	protected Void doInBackground(Double... params) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("phone", String.valueOf(PublicSrc.user.getPhone()));
		map.put("code", PublicSrc.user.getCode());
		map.put("order", "uploadLatLng");
		map.put("lat", String.valueOf(params[0]));
		map.put("lng", String.valueOf(params[1]));
		String re = NetTool.sendPostRequest(RunURL.UserOther, map, "utf-8");
		return null;
	}

}
