package viewtools;

import entity.OpenUserData;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RankingPager extends LinearLayout {

	OpenUserData openUserData ;
	int width ,height, pagerHeight; 
	double max = 0 ;
	double num ; 
	public RankingPager(Context context,double max ,OpenUserData openUserData) {
		super(context);
		this.max = max;
		setOrientation(LinearLayout.HORIZONTAL);
		setBackgroundColor(0x00000000);
		this.openUserData = openUserData;
		width = DisplayUtil.getWidth(context);
		height= DisplayUtil.getHeight(context);
		pagerHeight=(int) (height*0.07);

		if (openUserData.getDistance().equals("w")) {
			try {
				num = Double.parseDouble(openUserData.getWalkNum());
			} catch (Exception e) {
				num = 0;
			}
		}else {
			try {
				num = Double.parseDouble(openUserData.getRunNum());
			} catch (Exception e) {
				num = 0;
			}
			
		}


	}

	@Override
	public void draw(Canvas canvas) {
		int drawwid = (int)(width*(num/max));
		if (max<=0) {
			drawwid = width ; 
		}
		System.out.println("re:"+drawwid);
		Paint paint = new Paint();
		paint.setColor(ColorTool.getRandColorCode("cc"));
		paint.setStyle(Style.FILL);
		canvas.drawRect(0,0,drawwid ,pagerHeight, paint);
		Paint paint2 = new Paint();
		paint2.setColor(0xff303030);
		paint2.setStrokeWidth(2);
		paint2.setTextSize(pagerHeight/3);
		System.out.println("re:"+openUserData.getName());
		canvas.drawText(openUserData.getName(), 0, pagerHeight/2, paint2);
		paint2.setColor(0xff8B0A50);
		if (openUserData.getDistance().equals("w")) {
			canvas.drawText(String.valueOf((int)num)+"²½", width/2, pagerHeight/2, paint2);
		}else {
			canvas.drawText(String.valueOf(num)+"km", width/2, pagerHeight/2, paint2);
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(width, pagerHeight);
	}
}
