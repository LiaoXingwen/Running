package viewtools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import android.R.integer;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 下面多菜单选择界面，仅在代码使用，一般作为主界面使用
 * 使用注意事项：
 * 界面必须调用init
 * 
 * @author 廖兴文
 */
public class ButtomMenusPanel extends LinearLayout {

	LinkedHashMap<String, View> menus = new LinkedHashMap<String, View>();
	private int width;
	private int height;
	Context context ;
	//按钮颜色为未选择的颜色
	private int InCheicebuttomColor = 0xccffffff;
	//按钮颜色为未选择的颜色
	private int NOCheicebuttomColor = 0xccffffff;
	//为内容颜色
	private int contentColor = 0xffffffff; 
	//未选择的按钮字体颜色
	private int noCheiceButtonTextColor = 0xffCFCFCF;
	//选择中的字体颜色
	private int InCheiceButtonTextColor = 0xff000000; 
	//选中的下标
	private int InCheiceIndex = 0 ;

	//文字上面带有图标
	ArrayList<Integer> iconList = new ArrayList<Integer>();

	//初始高度
	public static int menuheightdip = 50;

	int menuheightpx;



	//布局文件：
	LinearLayout content ;

	LinearLayout menuslLayout;



	public ButtomMenusPanel(Context context) {
		super(context);

		this.context = context ;
		//设置线性 的属性为垂直属性
		setOrientation(LinearLayout.VERTICAL);
		//设置宽高
		setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

		content = new LinearLayout(context);
		content.setOrientation(LinearLayout.VERTICAL);

		init(menus,iconList);

	}



	/**
	 * 界面使用必须调用这方法生成界面
	 * 设置图标资源，必须是按钮数的两倍，
	 * 按map的顺序添加两个资源，一个为没有点击中的图标id，另一个为点击中的id（顺序不可调转）
	 * @param @param menus   布局资源
	 * @param @param iconLis    上面的图标
	 * @return void   
	 * 
	 */
	public void init(LinkedHashMap<String, View> menus , ArrayList<Integer> iconLis) {

		this.menus = menus ;
		//判断是否有图标
		if (iconLis!=null) {
			iconList =iconLis;
		}else {
			iconList = null;
		}

		//对局面进行处理
		removeAllViews();

		menuheightpx = DisplayUtil.dip2px(context, menuheightdip);

		width = DisplayUtil.getWidth(context);

		height = DisplayUtil.getHeight(context);
		//判断是否使用初始值
		if ((height*0.08)>menuheightpx) {
			menuheightpx=(int) (height*0.08);
		}
		//内容面板的布局
		LinearLayout.LayoutParams contentparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				height-menuheightpx	);
		//菜单面板的布局
		LinearLayout.LayoutParams menusparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				menuheightpx);

		content.setBackgroundColor(contentColor);
		content.setLayoutParams(contentparams);


		setBackgroundColor(NOCheicebuttomColor);

		menuslLayout = makeButtomMenus();

		menuslLayout.setLayoutParams(menusparams);

		addView(content);
		addView(menuslLayout);

	}





	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		width = getMySize(400, widthMeasureSpec);
		height = getMySize(400, heightMeasureSpec);
		setMeasuredDimension(width, height);
	}





	/**
	 * 获取我的大小设置
	 *
	 * @param @param defaultSize
	 * @param @param measureSpec
	 * @param @return    
	 *
	 */
	private int getMySize(int defaultSize, int measureSpec) {
		int mySize = defaultSize;

		int mode = MeasureSpec.getMode(measureSpec);
		int size = MeasureSpec.getSize(measureSpec);

		switch (mode) {
		case MeasureSpec.UNSPECIFIED: {//如果没有指定大小，就设置为默认大小
			mySize = defaultSize;
			break;
		}
		case MeasureSpec.AT_MOST: {//如果测量模式是最大取值为size
			//我们将大小取最大值,你也可以取其他值
			mySize = size;
			break;
		}
		case MeasureSpec.EXACTLY: {//如果是固定的大小，那就不要去改变它
			mySize = size;
			break;
		}
		}
		return mySize;
	}

	/**
	 * 生成菜单面板
	 * 
	 * @param @return    
	 * @return LinearLayout   
	 * 
	 */
	private LinearLayout makeButtomMenus() {
		//清除原有元素

		LinearLayout menusLayout = new LinearLayout(context);
		menusLayout.setBackgroundColor(NOCheicebuttomColor);
		//为线性布局设置属性
		menusLayout.setOrientation(LinearLayout.HORIZONTAL);
		menusLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				menuheightpx));
		if (menus.isEmpty()) {
			Button nullbButton = new Button(context);
			nullbButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
					menuheightpx));
			nullbButton.setText("空");
			nullbButton.setTextColor(InCheiceButtonTextColor);
			nullbButton.setBackgroundColor(InCheicebuttomColor);
			menusLayout.addView(nullbButton);
		}else {

			Iterator<String> menunames = menus.keySet().iterator();
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
					menuheightpx);

			LinearLayout.LayoutParams imageViewparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
					(int) (menuheightpx*0.5));
			LinearLayout.LayoutParams textViewparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
					(int) (menuheightpx*0.5));
			params.weight = 1;
			params.gravity =Gravity.CENTER;

			imageViewparams.gravity = Gravity.CENTER;
			imageViewparams.setMargins(0, 5, 0, 0);

			textViewparams.gravity = Gravity.CENTER;

			int id = 0 ;
			while (menunames.hasNext()) {


				if (iconList!=null) {
					LinearLayout linearLayout = new LinearLayout(context);
					linearLayout.setLayoutParams(params);
					linearLayout.setOrientation(LinearLayout.VERTICAL);

					linearLayout.setId(id);

					ImageView imageView = new ImageView(context);
					imageView.setLayoutParams(imageViewparams);
					imageView.setId(id);

					//获取map里面的名字，放进button
					String string = (String) menunames.next();
					Button button = new Button(context);
					button.setText(string);

					if (InCheiceIndex == id) {
						button.setTextColor(InCheiceButtonTextColor);
						imageView.setImageResource(iconList.get(id*2+1));
						button.setBackgroundColor(InCheicebuttomColor);
						imageView.setBackgroundColor(InCheicebuttomColor);
						button.setTextColor(InCheiceButtonTextColor);
						content.removeAllViews();
						content.addView(menus.get(string));
					}else {
						imageView.setImageResource(iconList.get(id*2));
						button.setBackgroundColor(NOCheicebuttomColor);
						imageView.setBackgroundColor(NOCheicebuttomColor);
						button.setTextColor(noCheiceButtonTextColor);
					}
					button.setTextSize(DisplayUtil.px2dip(context, (int) (menuheightpx*0.48)/2));
					button.setPadding(0, 0, 0, 0);
					button.setLayoutParams(textViewparams);
					button.setId(id);
					linearLayout.addView(imageView);
					linearLayout.addView(button);

					id++;

					menusLayout.addView(linearLayout);


					button.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Button  b = (Button) v;
							int vid = b.getId();

							if (vid==InCheiceIndex) {
								return;
							}
							InCheiceIndex = vid ; 

							init(menus,iconList);
						}
					});


					imageView.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							ImageView  b = (ImageView) v;
							int vid = b.getId();

							InCheiceIndex = vid ; 

							init(menus,iconList);
						}
					});



				}




			}
		}
		return menusLayout;
	}





	/**
	 * 设置选中按钮的颜色
	 *
	 * 
	 * @param @param color    
	 * @return void   
	 * 
	 */
	public void setInCheicebuttomColor(int color) {
		this.InCheicebuttomColor = color;
		init(menus,iconList);
	}

	/**
	 * 设置下方按钮的颜色
	 *
	 * 
	 * @param @param color    
	 * @return void   
	 * 
	 */
	public void setNOCheicebuttomColor(int color) {
		this.NOCheicebuttomColor = color;
		init(menus,iconList);
	}




	/**
	 * 设置内容面板和选中按钮的背景颜色
	 * 
	 * @param @param contentColor    
	 * @return void   
	 * 
	 */
	public void setContentColor(int contentColor) {
		this.contentColor = contentColor;
		init(menus,iconList);
	}




	/**
	 * 设置没有选中菜单的字体颜色
	 * 
	 * @param @param buttomTextColor    
	 * @return void   
	 * 
	 */
	public void setnoCheiceButtonTextColor(int buttomTextColor) {
		noCheiceButtonTextColor = buttomTextColor;
		init(menus,iconList);
	}



	/**
	 * 设置选中项
	 * 
	 * @param @param inCheiceIndex    
	 * @return void   
	 * 
	 */
	public void setInCheiceIndex(int inCheiceIndex) {
		InCheiceIndex = inCheiceIndex;
		init(menus,iconList);
	}

	/**
	 * 设置选中按钮的字体颜色
	 * 
	 * @param @param inCheiceButtonTextColor    
	 * @return void   
	 * 
	 */
	public void setInCheiceButtonTextColor(int inCheiceButtonTextColor) {
		InCheiceButtonTextColor = inCheiceButtonTextColor;
		init(menus,iconList);
	}




}
