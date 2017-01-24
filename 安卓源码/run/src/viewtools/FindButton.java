package viewtools;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import entity.OpenUserData;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.widget.Button;
import android.widget.LinearLayout;
import android.renderscript.Int2;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;

public abstract class FindButton extends Button implements OnClickListener{


	int width,height ,arcwidth =100,buttonheight= 0,r = 0;
	Timer timer ; 
	int count = 0 ;
	private boolean isStop = true ;
	
	int[] f = new int[]{-1,1};
	List<OpenUserData> list;//数据列表

	public FindButton(Context context) {
		super(context);
		width = DisplayUtil.getWidth(context);
		height= DisplayUtil.getHeight(context);
		buttonheight= (int) (height*0.6);
		timer = new Timer();
		start();
		setOnClickListener(this);
		
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		r = width/6;
		setMeasuredDimension(width, buttonheight);
	}
	
	@Override
	public void draw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setDither(true);
		paint.setAntiAlias(true);
		paint.setColor(0xaaF8F8FF);
		canvas.drawCircle(width/2, buttonheight/2, r, paint);

		paint.setStrokeWidth(3);
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawCircle(width/2, buttonheight/2, (float) (arcwidth), paint);
		canvas.drawCircle(width/2, buttonheight/2, (float) (arcwidth+width*0.25/3), paint);
		canvas.drawCircle(width/2, buttonheight/2, (float) (arcwidth+width*0.25/3*2), paint);





		count++;
		if (count==10) {
				count = 0;
				for (int i = 0; i < 10; i++) {
					int x = (int) ((arcwidth+80)*2*Math.random()+width/4); 
					int y = (int) (height/4*Math.random()+height/6); 
					if (x>(width/2-r)&&x<(width/2+r)) {
						continue;
					}
					if (y>(height/2-r)&&y<(height/2+r)) {
						continue ; 
					}
					paint.setColor(ColorTool.getRandColorCode("ee"));
					paint.setStyle(Paint.Style.FILL);
					canvas.drawCircle(x, y,(float) (Math.random()*12), paint);
				
			}
		}
	}
	public synchronized void stop(){
		if (timer!=null) {
			isStop = true ;
		}
	}


	public synchronized void start(){
		if (isStop) {
			isStop = false ;
			arcwidth = width/6;
			if (timer!=null) 
				timer.cancel();
			timer = null ; 
			timer  = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					if (!isFocusableInTouchMode()) {
						if (arcwidth>(width/4) ) {

							if (isStop) {
								cancel();
							}else {
								arcwidth = width/6;
							}
						}else {
							arcwidth +=1;
						}
						postInvalidate();
					}

				}
			}, 25,25);
		}
		
	}

	public void setList(List<OpenUserData> list) {
		this.list = list;
		invalidate();
	}
	

}
