package viewtools;

import java.util.List;

import entity.OpenUserData;
import android.R.integer;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class ViewUserPagers extends HorizontalScrollView {

	LinearLayout layout ;
	private int pagerWidth , pagersWidth = 0 , width;
	private int pagerHeight;
	private int size = 0 ;

	public ViewUserPagers(Context context ,List<OpenUserData> list) {
		super(context);
		width = DisplayUtil.getWidth(context);
		pagerWidth = (int) (width*0.5);
		pagerHeight = (int) (DisplayUtil.getHeight(context)*0.4);
		layout = new LinearLayout(context);
		size = 0;
		if (list!=null&&list.size()>0) {
			pagersWidth = list.size()*pagerWidth;
			for(int i= 0 ; i<list.size();i++){
				OpenUserData u = list.get(i);
				UserPager pager = new UserPager(context,u.getName(),u.getDistance(), u.getWalkNum(), u.getRunNum());
				layout.addView(pager);
				size++;
			}
		}
		layout.setPadding(pagerWidth/2,0, pagerWidth/2, 0);
		setHorizontalScrollBarEnabled(false);
		addView(layout);
		scrollBy(0);
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(DisplayUtil.getWidth(getContext()), pagerHeight);
	}



	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (ev.getAction()==MotionEvent.ACTION_UP) {
			int x = getScrollX() ; 

			if (x%(pagerWidth)<pagerWidth*0.2||x%(pagerWidth)>pagerWidth*0.8) {
				if (x%(pagerWidth)<pagerWidth*0.2) {
					scrollBy(x/pagerWidth);
				}else {
					scrollBy(x/pagerWidth+1);
				}
			}else {
				scrollBy(x/pagerWidth);
			}
			return true;
		}


		return super.onTouchEvent(ev);
	}


	/**
	 * 
	 * 
	 * @param     
	 * @return void   
	 * 
	 */
	public void scrollBy(int index){
		smoothScrollTo(index*pagerWidth, 0);
		for (int i = 0; i < size; i++) {
			View view = layout.getChildAt(i);
			if (index==i) {
				view.setScaleX((float) 1.2);
				view.setScaleY((float) 1.2);
				view.setAlpha((float) 1.0);
			}else {
				view.setScaleX((float) 0.8);
				view.setScaleY((float) 0.8);
				view.setAlpha((float) 0.8);
			}
			
		}
	}


}
