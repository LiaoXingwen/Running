package viewtools;

import java.util.Iterator;
import java.util.List;

import com.lxw.baidumapdemo.MapActivity;

import entity.RunJsonTool;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class ViewLocatData extends LinearLayout {

	List<String[]> list;
	int myheight,mywidth;
	
	public ViewLocatData(Context context) {
		super(context);
		
		setOrientation(LinearLayout.VERTICAL);
		setBackgroundColor(0xfffffff);
		list = RunJsonTool.readFileList();
		myheight = DisplayUtil.getHeight(context);
		mywidth = DisplayUtil.getWidth(context);
		setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
		ScrollView scrollView = new ScrollView(context);
		scrollView.setVerticalScrollBarEnabled(false);
		scrollView.addView(makeList(list));
		addView(scrollView);
		
		
	}
	public LinearLayout makeList(List<String[]> list) {
		LinearLayout layout = new LinearLayout(getContext());
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setBackgroundColor(0xffffffff);
		layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
		
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			final String[] strings = (String[]) iterator.next();
			if (strings.length==3) {
				LinearLayout linearLayout = new LinearLayout(getContext());
				linearLayout.setOrientation(LinearLayout.VERTICAL);
				
				TextView textView = new TextView(getContext());
				textView.setTextColor(0xffEE7AE9);
				textView.setText("里程："+ strings[0]+" KM");
				
				TextView spaceTime = new TextView(getContext());
				spaceTime.setText("耗时时间："+ strings[2]);
				spaceTime.setTextColor(0xff9BCD9B);
				
				
				TextView starttime = new TextView(getContext());
				starttime.setText("开始时间："+ strings[1]);
				starttime.setTextColor(0xff7CCD7C);
				
				Button button = new Button(getContext());
				button.setText("在地图查看轨迹");
				button.setBackgroundColor(0xffffffff);
				button.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(getContext(), MapActivity.class);
						intent.putExtra("data", strings);
						getContext().startActivity(intent);
					}
				});
				
				TextView spaceTextView = new TextView(getContext());
				spaceTextView.setBackgroundColor(0xffCFCFCF);
				spaceTextView.setHeight(5);
				
				linearLayout.addView(textView);
				linearLayout.addView(starttime);
				linearLayout.addView(spaceTime);
				linearLayout.addView(button);
				linearLayout.addView(spaceTextView);
				layout.addView(linearLayout);
				
			}
			
		}
		
		
		
		
		
		
		return layout;
		
		
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(mywidth, myheight);
	}
	

}
