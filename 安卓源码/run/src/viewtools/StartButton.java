package viewtools;

import java.util.Timer;
import java.util.TimerTask;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

public class StartButton extends Button {


	int width,height ,arcwidth =100,buttonheight= 0,r = 0;
	Timer timer ; 
	String showText = "开始";
	public StartButton(Context context) {
		super(context);
		width = DisplayUtil.getWidth(context);
		height= DisplayUtil.getHeight(context);

		timer = new Timer();
		arcwidth = width/5;
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				if (!isFocusableInTouchMode()) {
					if (arcwidth>(width/4)||arcwidth<width/5) {
						arcwidth = width/5;

					}else {
						arcwidth +=1;
					}
					postInvalidate();
				}

			}
		}, 1000,25);

	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		r = width/5;
		setMeasuredDimension(width, buttonheight = (int)(width*0.5));
	}
	@Override
	public void draw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setDither(true);
		paint.setAntiAlias(true);
		paint.setColor(0xccffffff);
		
		canvas.drawCircle(width/2, buttonheight/2, width/5, paint);
		paint.setColor(0xcc000000);
		//此处单位为px
		paint.setTextSize(width/12);
		canvas.drawText(showText, width/2-width/12,
				buttonheight/2+width/36, paint);

		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(0xccffffff);
		canvas.drawCircle(width/2, buttonheight/2, arcwidth+0, paint);
		canvas.drawCircle(width/2, buttonheight/2, arcwidth+40, paint);
		canvas.drawCircle(width/2, buttonheight/2, arcwidth+80, paint);

	}

	
	public void setShowText(String showText) {
		this.showText = showText ; 
		invalidate();
	}
}
