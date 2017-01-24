package viewtools;

import com.lxw.baidumapdemo.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class WevaFrame extends FrameLayout {
	int width , height ;
	private ImageView up;
	private ImageView down;
	private StartLayout startLayout;
	private TranslateAnimation upAniamtion;
	private TranslateAnimation downAniamtion;
	private int max;
	private ReStartAnimationListener upAnimationListener=new ReStartAnimationListener();
	private ReStartAnimationListener downAnimationListener =new ReStartAnimationListener();
	public WevaFrame(Context context) {
		super(context);
		init(context);
	}



	public WevaFrame(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}


	private void init(Context context) {
		width = DisplayUtil.getWidth(context);
		height = DisplayUtil.getHeight(context);








		startLayout = new StartLayout(context);
		startLayout.setWevaFrame(this);
		addView(startLayout);

		up = new ImageView(context);
		up.setImageDrawable(getResources().getDrawable(R.drawable.up));
		LayoutParams upParams = new LayoutParams(width, 30);
		addView(up,upParams);

		down = new ImageView(context);
		down.setImageDrawable(getResources().getDrawable(R.drawable.down));
		LayoutParams downParams = new LayoutParams(width, 30);
		downParams.gravity =Gravity.BOTTOM;
		addView(down,downParams);
		up.setVisibility(View.GONE);
		down.setVisibility(View.GONE);
		
		downAnimationListener.setView(down);
		upAnimationListener.setView(up);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		//此句不能删除，否则添加布局无效
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(width, height);
	}


	public synchronized void setIndex(int index ,int max) {
		upAnimationListener.setReset();
		downAnimationListener.setReset();
		if (max==1) {
			return ; 
		}
		if (index!=0&&index !=max-1) {
			if (upAniamtion!=null) {
				up.setVisibility(View.VISIBLE);
				upAnimation();
			}
			if (downAniamtion!=null) {
				down.setVisibility(View.VISIBLE);
				downAnimation();
			}
		}else {
			if (index == 0) {
				up.setVisibility(View.GONE);
				up.clearAnimation();
				down.setVisibility(View.VISIBLE);
				downAnimation();
			}
			if (index == max-1){
				up.setVisibility(View.VISIBLE);
				down.clearAnimation();
				down.setVisibility(View.GONE);
				upAnimation();
			}
		}
	}
	void upAnimation(){
		if (up!=null) {
			upAniamtion =null;
			upAniamtion = new TranslateAnimation(0,
					0,
					30, 
					0);
			upAniamtion.setAnimationListener(upAnimationListener);
			upAniamtion.setDuration(1000);
			up.startAnimation(upAniamtion);
		}

	}

	public StartLayout getStartLayout() {
		return startLayout;
	}



	void downAnimation(){
		if (down!=null) {
			downAniamtion = null;
			downAniamtion = new TranslateAnimation(0,
					0,
					-30, 
					0);
			downAniamtion.setAnimationListener(downAnimationListener);
			downAniamtion.setDuration(1000);
			down.startAnimation(downAniamtion);
		}

	}

}
