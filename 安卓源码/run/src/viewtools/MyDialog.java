package viewtools;

import com.lxw.baidumapdemo.R;
import com.lxw.baidumapdemo.RunActivity;

import android.app.Dialog;
import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MyDialog extends Dialog {

	private Button positiveButton, negativeButton;
	private TextView title,msg;
	private boolean isCanBack =true ;


	public synchronized boolean isCanBack() {
		return isCanBack;
	}

	public synchronized void setCanBack(boolean isCanBack) {
		this.isCanBack = isCanBack;
	}

	public MyDialog(Context context) {
		super(context);
		setCustomDialog();
		setCanceledOnTouchOutside(false);//不能点击外面取消
	}

	public MyDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		setCustomDialog();
	}

	public MyDialog(Context context, int theme) {
		super(context, theme);
		setCustomDialog();
	}
	private void setCustomDialog() {
		//设置不显示对话框标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		View mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog, null);
		title = (TextView) mView.findViewById(R.id.title);
		msg = (TextView) mView.findViewById(R.id.massage);
		msg.setWidth((int) (DisplayUtil.getWidth(getContext())*0.8));
		positiveButton = (Button) mView.findViewById(R.id.positiveButton);
		negativeButton = (Button) mView.findViewById(R.id.negativeButton);
		super.setContentView(mView);
	}

	public void settitle(String title){
		this.title.setText(title);
	}
	public void setmsg(String content){
		this.msg.setText(content);
	}


	/** 
	 * 确定键监听器 
	 * @param listener 
	 */  
	public void setOnPositiveListener(View.OnClickListener listener){  
		positiveButton.setOnClickListener(listener);  
	}  
	/** 
	 * 取消键监听器 
	 * @param listener 
	 */  
	public void setOnNegativeListener(View.OnClickListener listener){  
		negativeButton.setOnClickListener(listener);  
	}
	
	//屏蔽返回键
		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {

			if (keyCode == KeyEvent.KEYCODE_BACK) {
				if (isCanBack) {
					cancel();
				}
				
				return false ;
			}
			return super.onKeyDown(keyCode, event);
		}
}
