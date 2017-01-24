package tools;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.lxw.baidumapdemo.MainHandleTool;

import android.R.integer;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * 
 * 上传数据，需要参数：
 * 指令，初始名，值
 * @作者 廖兴文
 *
 * @时间 2016-12-27
 */
public class UploadData extends AsyncTask<String, Integer, Void> {

	@Override
	protected Void doInBackground(String... params) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("phone", String.valueOf(PublicSrc.user.getPhone()));
		map.put("code", PublicSrc.user.getCode());
		map.put("order", params[0]);
		map.put(params[1], params[2]);
		String re = NetTool.sendPostRequest(RunURL.UserOther, map, "utf-8");
		System.out.println("re:"+re);
		if (re.equals("登陆失效，请重新登陆")) {
			publishProgress(0);
		}
		return null;
	}
	
	@Override
	protected void onProgressUpdate(Integer... values) {
	if (values[0]==0) {
		MainHandleTool.sendMessage(MainHandleTool.UPDATE_TOAST, new String[]{"登陆失效，请重新登陆,3秒后系统自动将自动退出"});
		PublicSrc.user.setCode(" ");
		PublicSrc.user.saveUser();
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				System.exit(0);
			}
		}, 3000);
		
	}
	}

}
