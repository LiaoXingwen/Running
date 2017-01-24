package viewtools;

import com.lxw.baidumapdemo.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

public class CircleBar extends View {

	int width , height , viewHieght ; 
	double progress = 0;
	int imageid = R.drawable.nowalk;
	public CircleBar(Context context) {
		super(context);
		initView(context);
	}
	public CircleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	private void initView(Context context) {

		width = DisplayUtil.getWidth(context);
		height = DisplayUtil.getHeight(context);

		viewHieght = (int) (height*0.4);
	}



	@Override
	protected void onDraw(Canvas canvas) {

		int cx = width/2;
		int cy = viewHieght/2;


		Paint paint = new Paint();
		int radius = (int) (viewHieght/3); //圆环的半径  
		paint.setColor(0xccffffff); //设置圆环的颜色  
		paint.setStyle(Paint.Style.STROKE); //设置空心  
		paint.setStrokeWidth(30); //设置圆环的宽度  
		paint.setAntiAlias(true);  //消除锯齿   
		canvas.drawCircle(cx, cy, radius, paint); //画出圆环


		if (progress<90) {
			paint.setColor(0xcc00ffff); //设置圆环的颜色  
		}else {
			if (progress<180) {
				paint.setColor(0xccFF69B4); //设置圆环的颜色  
			}else {
				if (progress<270) {
					paint.setColor(0xccFF4040); //设置圆环的颜色
				}else {
					paint.setColor(0xccFF3030); //设置圆环的颜色
				}
			}
		}

		RectF rect = new RectF(cx - radius,cy - radius, cx+radius, cy+radius);
		canvas.drawArc(rect, -90, (float) progress, false, paint);

		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imageid);
		int bitheight = bitmap.getHeight(),bitwidth = bitmap.getWidth();

		canvas.drawBitmap(bitmap, new Rect(cx - bitwidth,cy - bitheight, cx+bitwidth, cy+bitheight),
				new Rect((int) (cx - radius*0.4),cy - (int)(radius*0.4), cx+(int)(radius*0.4), cy+(int)(radius*0.4)), paint);

		bitmap.recycle();
	}

	public void setType(int imageid,double progress) {

		if (progress*2<360) {
			this.progress = progress*2;
		}else {
			this.progress = 360;
		}
		this.imageid = imageid;
		invalidate();
	}

}
