package viewtools;


import tools.DateTool;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 高度为30dp
 * 宽度为屏幕宽度
 * 
 * @作者 廖兴文
 *
 * @时间 2016-12-9
 */
public class TimeShow extends LinearLayout {

	int starttime = 0 ;
	public int getStarttime() {
		return starttime;
	}

	String spaceTime = "00:00:00";

	int TimeHeightDp = 30 ; 
	int TimeHeightPx = 0 ;
	int TimeWidth ;
	private TextView timeText;

	TimeThread thread ; 

	public TimeShow(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);


	}



	public TimeShow(Context context) {
		super(context);
		initView(context);
	}



	private void initView(Context context) {
		setOrientation(LinearLayout.VERTICAL);
		TimeHeightPx = DisplayUtil.dip2px(getContext(), TimeHeightDp);
		TimeWidth = DisplayUtil.getWidth(context);


		timeText = new TextView(context);
		timeText.setTextSize(TimeHeightPx/3);
		timeText.setGravity(Gravity.CENTER);
		timeText.setTextColor(Color.MAGENTA);
		setBackgroundColor(0xffffff);
		timeText.setText(spaceTime);
		addView(timeText);


	}

	public String getSpaceTime() {
		return spaceTime;
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(TimeWidth, TimeHeightPx);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		timeText.setText(spaceTime);
	}

	/**
	 * 开始
	 * 
	 * @param     
	 * @return void   
	 * 
	 */
	public void start() {
		if (thread!=null) {
			thread.setIsStop(true);
			thread = null;
		}
		starttime = 0;
		thread = new TimeThread();
		thread.start();
	}
	/**
	 * 暂停
	 * 
	 * @param     
	 * @return void   
	 * 
	 */
	public void stop() {
		if (thread!=null)
			thread.setIsAdd(false);
	}

	/**
	 * 继续
	 * 
	 * @param     
	 * @return void   
	 * 
	 */
	public void reDo() {
		if (thread!=null)
			thread.setIsAdd(true);
	}
	/**
	 * 停止
	 * 
	 * @param     
	 * @return void   
	 * 
	 */
	public void cacel() {
		if (thread!=null) {
			thread.setIsStop(true);
		}
	}



	class TimeThread extends Thread{


		boolean isAdd = true ;
		boolean isStop = false ;

		public TimeThread() {
		}

		@Override
		public void run() {

			while (!isStop) {

				if (isAdd) {
					spaceTime = DateTool.getTimeWithHSM(starttime++);
					postInvalidate();
				}

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}


			}

		}

		private void setIsAdd(boolean isadd) {
			this.isAdd = isadd ;

		}
		private void setIsStop(boolean isStop) {
			this.isStop = isStop ;

		}
	}

}
