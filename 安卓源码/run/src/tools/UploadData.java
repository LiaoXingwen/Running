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
 * �ϴ����ݣ���Ҫ������
 * ָ���ʼ����ֵ
 * @���� ������
 *
 * @ʱ�� 2016-12-27
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
		if (re.equals("��½ʧЧ�������µ�½")) {
			publishProgress(0);
		}
		return null;
	}
	
	@Override
	protected void onProgressUpdate(Integer... values) {
	if (values[0]==0) {
		MainHandleTool.sendMessage(MainHandleTool.UPDATE_TOAST, new String[]{"��½ʧЧ�������µ�½,3���ϵͳ�Զ����Զ��˳�"});
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
