package viewtools;

import tools.PublicSrc;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;

public abstract class MyDataLayout extends LinearLayout {

	
	int myheight , mywidth ;
	TextView username;
	public MyDataLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	public MyDataLayout(Context context) {
		super(context);
		init(context);
	}
	
	
	
	private void init(Context context) {
		
		myheight = DisplayUtil.getHeight(context);
		mywidth = DisplayUtil.getWidth(context);
		
		setOrientation(LinearLayout.VERTICAL);
		LinearLayout layout = new LinearLayout(context);
		layout.setOrientation(LinearLayout.HORIZONTAL);
		layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		username = new TextView(context);
		username.setHeight((int) (myheight*0.4));
		username.setWidth(mywidth);
		username.setText(PublicSrc.user.getName()+"  你好");
		username.setTextSize(20);
		username.setTextColor(0xffffffff);
		username.setGravity(Gravity.CENTER);
		layout.addView(username);
		addView(layout);
		
		
		LinearLayout layout2 = new LinearLayout(getContext());
		
		layout2.setOrientation(LinearLayout.VERTICAL);
		layout2.setLayoutParams(new LinearLayout.LayoutParams(mywidth, (int) (myheight*0.6)));
		layout2.setGravity(Gravity.CENTER);
		
		TableLayout tableLayout = new TableLayout(context);
		tableLayout.setLayoutParams(new TableRow.LayoutParams(mywidth, (int) (myheight*0.6)));
		TableRow row = new TableRow(context);
		row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT));
		
		
		
		Button myList = new Button(context);
		myList.setText("跑步数据");
		myList.setWidth(mywidth/2);
		myList.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				myData();
			}
		});
		myList.setBackgroundColor(0xaa7CCD7C);
		myList.setTextColor(0xff696969);
		myList.setHeight((int) (myheight*0.6/5));
		Button allList= new Button(context);
		allList.setWidth(mywidth/2);
		allList.setText("排行榜");
		allList.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ph();
				
			}
		});
		allList.setTextColor(0xff696969);
		allList.setBackgroundColor(0xaa66CDAA);
		allList.setHeight((int) (myheight*0.6/5));
		
		row.addView(myList);
		row.addView(allList);
		
		
		TableRow row1 = new TableRow(context);
		row1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT));
		Button playlist = new Button(context);
		playlist.setText("跑 圈");
		playlist.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pq();
			}
		});
		playlist.setTextColor(0xff696969);
		playlist.setBackgroundColor(0xaaF0E68C);
		playlist.setWidth(mywidth/2);
		playlist.setHeight((int) (myheight*0.6/5));
		Button fj= new Button(context);
		fj.setWidth(mywidth/2);
		fj.setText("附近的人");
		fj.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				fj();
			}
		});
		fj.setBackgroundColor(0xaa63B8FF);
		fj.setTextColor(0xff696969);
		fj.setHeight((int) (myheight*0.6/5));
		
		row1.addView(playlist);
		row1.addView(fj);
		
		
		TableRow row2 = new TableRow(context);
		row2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT));
		Button setter = new Button(context);
		setter.setText("设 置");
		setter.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			setter();
			}
		});
		setter.setTextColor(0xff696969);
		setter.setBackgroundColor(0xaa7AC5CD);
		setter.setWidth(mywidth/2);
		setter.setHeight((int) (myheight*0.6/5));
		Button exit= new Button(context);
		exit.setWidth(mywidth/2);
		exit.setText("退出");
		exit.setBackgroundColor(0xaaADADAD);
		exit.setTextColor(0xff696969);
		exit.setHeight((int) (myheight*0.6/5));
		
		
		exit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				exit();
			}
		});
		
		row2.addView(setter);
		row2.addView(exit);
		
		
		tableLayout.addView(row);
		tableLayout.addView(row1);
		tableLayout.addView(row2);
		
		layout2.addView(tableLayout);
		
		addView(layout2);
		
		
	}
	
	
	public abstract void exit();
	public abstract void myData();
	public abstract void ph();
	public abstract void pq();
	public abstract void fj();
	public abstract void setter();
	
	
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(mywidth, myheight);
	}

}
