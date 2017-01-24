package viewtools;

import com.lxw.baidumapdemo.AboutActivity;
import com.lxw.baidumapdemo.MainActivity;
import com.lxw.baidumapdemo.MyLocatDataActivity;
import com.lxw.baidumapdemo.RankingActivity;
import com.lxw.baidumapdemo.SetterActivity;

import sensorkit.WalkAccelerometerListener;
import servicekit.AccelerometerListenerService;
import tools.GetAboutUser;
import tools.PublicSrc;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class StartLayout extends LinearLayout {

	private int width;
	private int height,weathheight;

	LinearLayout weatherLayout ,walkLayout , runLayout;

	Context context ;
	private TextView wendu;
	private TextView weathercontet;
	private TextView location;


	int bg = 0xff9BCD9B;

	public synchronized int getBg() {
		return bg;
	}




	public synchronized void setBg(int bg) {
		this.bg = bg;
		setBackgroundColor(bg);
	}




	private TextView allmileage;
	private TextView Todaymileage;
	private TextView allmileagenumber;
	private TextView Todaymileagenumber;
	private TextView allRunmileage;
	private TextView TodayRunmileage;
	private TextView allRunmileagenumber;
	private TextView TodayRunmileagenumber;
	private StartButton starView;
	private TextView gpsType;
	private MyDataLayout dataLayout;

	private MainActivity mainActivity ; 
	public MainActivity getMainActivity() {
		return mainActivity;
	}




	public void setMainActivity(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
	}




	private WevaFrame wevaFrame ; 
	
	int checkIndex = 0 ;
	private CircleBar circleBar;
	private int max = 2;  

	public StartLayout(Context context) {
		super(context);
		makeView(context);


	}

	
	
	
	public StartLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		makeView(context);
		
	}
	private void makeView(Context context) {
		setOrientation(LinearLayout.VERTICAL);
		this.context = context;
		width = DisplayUtil.getWidth(context);
		height = DisplayUtil.getHeight(context);
		weathheight = (int) (height*0.10);

		dataLayout = new MyDataLayout(context){

			@Override
			public void exit() {
				if (mainActivity!=null) {
					mainActivity.showExit();
				}
				
			}

			@Override
			public void myData() {
				mainActivity.startActivity(new Intent(mainActivity, MyLocatDataActivity.class));
			}

			@Override
			public void ph() {
				mainActivity.startActivity(new Intent(mainActivity,RankingActivity.class));
			}

			@Override
			public void pq() {
				Toast.makeText(getContext(), "功能尚未完全", 0).show();
			}

			@Override
			public void fj() {
//				if (MainActivity.latLng!=null) {
					mainActivity.startActivity(new Intent(mainActivity,AboutActivity.class));
//				}else {
//					Toast.makeText(mainActivity, "没有获取到你的位置", 0).show();
//				}
			}

			@Override
			public void setter() {
				mainActivity.startActivity(new Intent(mainActivity, SetterActivity.class));
			}
			
		};
		
		
		setBackgroundColor(bg);

		makeweather();

		makeContent();

		makeRunContent();
		

		ScrollView scrollView = new ScrollView(getContext());

		scrollView.setVerticalScrollBarEnabled(false);

		LinearLayout all = new LinearLayout(getContext());
		all.setOrientation(LinearLayout.VERTICAL);
		all.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

		all.addView(weatherLayout);
		all.addView(walkLayout);
		all.addView(runLayout);
		all.addView(dataLayout);

		
		
		
		scrollView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				ScrollView scrollView = (ScrollView) v;
				if (event.getAction()==MotionEvent.ACTION_UP) {
					int y = (int) scrollView.getScrollY();
					
					if (y>(checkIndex*height+height/8)) {
						checkIndex++;
						scrollView.smoothScrollTo(0, checkIndex*height);
//						Toast.makeText(getContext(), String.valueOf(checkIndex),0).show();
						wevaFrame.setIndex(checkIndex, 3);
						return true;
					}else {
						checkIndex--;
						scrollView.smoothScrollTo(0, ((int)y/height)*height);
						wevaFrame.setIndex(scrollView.getScrollY()/height, 3);
//						Toast.makeText(getContext(), String.valueOf(y/height),0).show();
						return true;
					}
				}
				return false;
			}
		});

		scrollView.addView(all);

		addView(scrollView);

	}
	private void makeContent() {
		walkLayout = new LinearLayout(context);
		walkLayout.setOrientation(LinearLayout.VERTICAL);
		walkLayout.setLayoutParams(new LinearLayout.LayoutParams(width, (int) (height*0.9)));

		gpsType = new TextView(context);
		gpsType.setTextSize(12);
		gpsType.setGravity(Gravity.RIGHT);
		gpsType.setHeight((int) (height*0.05));
		walkLayout.addView(gpsType);

		allmileage = new TextView(context);

		allmileage.setText("总步行");
		allmileage.setTextSize(DisplayUtil.px2dip(getContext(), (int) (height*0.08)/2));
		allmileage.setHeight((int) (height*0.08));
		allmileage.setGravity(Gravity.CENTER);
		allmileage.setTextColor(0xffA0522D);



		allmileagenumber = new TextView(context);
		allmileagenumber.setGravity(Gravity.CENTER);
		allmileagenumber.setTextSize(DisplayUtil.px2dip(getContext(), (int) (height*0.10)/2));
		allmileagenumber.setTextColor(0xffF0FFFF);
		allmileagenumber.setHeight((int) (height*0.10));
		walkLayout.addView(allmileage);
		walkLayout.addView(allmileagenumber);


		Todaymileage = new TextView(context); 
		Todaymileage.setText("今天步行");
		Todaymileage.setTextSize(DisplayUtil.px2dip(getContext(), (int) (height*0.1)/2));
		Todaymileage.setHeight((int) (height*0.10));
		Todaymileage.setGravity(Gravity.CENTER);
		Todaymileage.setTextColor(0xffFF8C69);


		Todaymileagenumber = new TextView(context);
		Todaymileagenumber.setGravity(Gravity.CENTER);
		Todaymileagenumber.setTextSize(DisplayUtil.px2dip(getContext(), (int) (height*0.12)/2));
		Todaymileagenumber.setTextColor(0xffffffff);
		Todaymileagenumber.setHeight((int) (height*0.12));
		walkLayout.addView(Todaymileage);
		walkLayout.addView(Todaymileagenumber);
		
		//显示当前的运动的情况
		LinearLayout contentLayout = new LinearLayout(context);
		contentLayout.setLayoutParams(new LinearLayout.LayoutParams(width, (int) (height*0.45)+6));
		circleBar = new CircleBar(context);
		contentLayout.addView(circleBar);
		
		walkLayout.addView(contentLayout);
		
//		ProgressBar speed = new ProgressBar(context);
		

	}


	private void makeRunContent() {
		//除去自定义的开始按钮的高度，剩下的宽度
		int runLayoutheight = (int) (height - width*0.6) ; 
				
		
		runLayout = new LinearLayout(context);
		runLayout.setOrientation(LinearLayout.VERTICAL);
		runLayout.setLayoutParams(new LinearLayout.LayoutParams(width, height));

		allRunmileage = new TextView(context);

		allRunmileage.setText("总 里 程");
		allRunmileage.setTextSize(20);
		allRunmileage.setHeight((int) (runLayoutheight*0.2));
		allRunmileage.setGravity(Gravity.CENTER);
		allRunmileage.setTextColor(0xffA0522D);



		allRunmileagenumber = new TextView(context);
		allRunmileagenumber.setGravity(Gravity.CENTER);
		allRunmileagenumber.setTextSize(25);
		allRunmileagenumber.setHeight((int) (runLayoutheight*0.15));
		allRunmileagenumber.setTextColor(0xffF0FFFF);

		runLayout.addView(allRunmileage);
		runLayout.addView(allRunmileagenumber);


		TodayRunmileage = new TextView(context); 
		TodayRunmileage.setText("今天里程");
		TodayRunmileage.setTextSize(30);
		TodayRunmileage.setHeight((int) (runLayoutheight*0.15));
		TodayRunmileage.setGravity(Gravity.CENTER);
		TodayRunmileage.setTextColor(0xffFF8C69);


		TodayRunmileagenumber = new TextView(context);
		TodayRunmileagenumber.setGravity(Gravity.CENTER);
		TodayRunmileagenumber.setTextSize(35);
		TodayRunmileagenumber.setHeight((int) (runLayoutheight*0.2));
		TodayRunmileagenumber.setTextColor(0xffffffff);

		TextView spaceTextView =new TextView(context);
		spaceTextView.setHeight((int) (runLayoutheight*0.1));


		starView = new StartButton(context);
		starView.setGravity(Gravity.CENTER);
		starView.setHeight((int) (width*0.6));
		starView.setWidth(width);


		runLayout.addView(TodayRunmileage);
		runLayout.addView(TodayRunmileagenumber);
		runLayout.addView(spaceTextView);
		runLayout.addView(starView);
	}

	public void setTodaymileage(String todaymileage) {
		Todaymileagenumber.setText(todaymileage+"  步");
	}

	public void setAllmileage(String allmileage) {
		this.allmileagenumber.setText(allmileage+" 步");
	}
	public void setRunTodaymileage(String todaymileage) {
		TodayRunmileagenumber.setText(todaymileage+"  KM");
	}

	public void setRunAllmileage(String allmileage) {
		this.allRunmileagenumber.setText(allmileage+" KM");
	}
	private void makeweather() {
		weatherLayout = new LinearLayout(context);
		weatherLayout.setOrientation(LinearLayout.HORIZONTAL);
		weatherLayout.setLayoutParams(new LinearLayout.LayoutParams(width, weathheight));
		weatherLayout.setBackgroundColor(0x22ffffff);

		//温度
		wendu = new TextView(context);

		setWendu("00");

		wendu.setLayoutParams(new LinearLayout.LayoutParams((int) (weathheight*1.5), weathheight));
		wendu.setGravity(Gravity.CENTER);
		wendu.setTextColor(0xffffffff);

		wendu.setTextSize(DisplayUtil.px2dip(context, (float) (weathheight/2.5)));


		LinearLayout otherLayout = new LinearLayout(context);
		otherLayout.setLayoutParams(new LinearLayout.LayoutParams(width-weathheight, weathheight));
		otherLayout.setOrientation(LinearLayout.VERTICAL);

		weathercontet = new TextView(context);
		setWeathercontet("获取中");
		weathercontet.setTextSize(DisplayUtil.px2dip(context, (float) (weathheight/4)));
		weathercontet.setTextColor(0xffffffff);
		weathercontet.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,weathheight/2));
		weathercontet.setGravity(Gravity.CENTER);

		location = new TextView(context);
		setLocation("获取中");
		location.setTextSize(DisplayUtil.px2dip(context, (float) (weathheight/4)));
		location.setTextColor(0xffffffff);
		location.setGravity(Gravity.CENTER);
		location.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, weathheight/2));
		otherLayout.addView(weathercontet);
		otherLayout.addView(location);


		weatherLayout.addView(wendu);
		weatherLayout.addView(otherLayout);

	}

	public void setWlakType(int imageid,double progress){
		circleBar.setType(imageid, progress);
	} 

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(width,height);
	}

	public int getWeathheight() {
		return weathheight;
	}

	public void setWeathheight(int weathheight) {
		this.weathheight = weathheight;
		
	}



	public void setWendu(String number) {
		wendu.setText(number +"°c");
	}

	public void setWeathercontet(String weather) {
		this.weathercontet.setText(weather);
	} 

	public void setLocation(String location) {
		this.location.setText("位置："+location);
	}

	public View getStarView() {
		return starView;
	}

	public void setGpsType(String gpsType) {
		this.gpsType.setText(gpsType);
	}


	String getwalk(String key){
		//获取
		SharedPreferences wirte = context.getSharedPreferences("data", context.MODE_PRIVATE);

		return String.valueOf(wirte.getInt(key, 0));

	}




	public void setWevaFrame(WevaFrame wevaFrame) {
		this.wevaFrame = wevaFrame;
	}
	
	


}
