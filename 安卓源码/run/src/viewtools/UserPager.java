package viewtools;

import android.content.Context;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 用户小贴士，显示用户基本信息
 * 
 * @作者 廖兴文
 *
 * @时间 2016-12-26
 */
public class UserPager extends LinearLayout {

	public synchronized int getPagerWidth() {
		return pagerWidth;
	}

	public synchronized void setPagerWidth(int pagerWidth) {
		this.pagerWidth = pagerWidth;
	}

	public synchronized int getPagerHeight() {
		return pagerHeight;
	}

	public synchronized void setPagerHeight(int pagerHeight) {
		this.pagerHeight = pagerHeight;
	}

	private int pagerWidth , pagerHeight ;
	TextView name ,dis,walk,run; 
	
	public UserPager(Context context,String name , String disString , String walk , String run) {
		super(context);
		pagerWidth = (int) (DisplayUtil.getWidth(context)*0.5);
		pagerHeight = (int) (DisplayUtil.getHeight(context)*0.4);
		
		setBackgroundColor(0x00ffffff);
		LinearLayout layout = new LinearLayout(context);
		layout.setBackgroundColor(0xff2b2b2b);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setLayoutParams(new LinearLayout.LayoutParams((int)(pagerWidth*0.5), (int)(pagerWidth*0.8)));
		layout.setGravity(Gravity.CENTER);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int)(pagerWidth*0.5), (int)(pagerWidth*0.8/4));
		this.name = new TextView(context);
		this.name.setText(name);
		this.name.setTextColor(0xffEE6363);
		this.name.setTextSize(18);
		this.name.setLayoutParams(params);
		this.name.setGravity(Gravity.CENTER);
		this.dis = new TextView(context);
		this.dis.setText("距您"+disString+"m");
		this.dis.setTextColor(0xffDDA0DD);
		this.dis.setTextSize((float) (pagerWidth*0.5*0.5/(disString.length()+3)));
		this.dis.setLayoutParams(params);
		this.dis.setGravity(Gravity.CENTER);;
		this.walk = new TextView(context);
		this.walk.setText(walk+"步");
		this.walk.setTextSize(15);
		this.walk.setGravity(Gravity.CENTER);;
		this.walk.setTextColor(0xff696969);
		this.walk.setLayoutParams(params);
		this.run = new TextView(context);
		this.run.setText(run+"m");
		this.run.setTextSize(15);
		this.run.setTextColor(0xff696969);
		this.run.setGravity(Gravity.CENTER);;
		this.run.setLayoutParams(params);
		
		layout.addView(this.name);
		layout.addView(this.walk);
		layout.addView(this.run);
		layout.addView(this.dis);
		
		addView(layout);
		setGravity(Gravity.CENTER);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(pagerWidth, pagerHeight);
	}
}
