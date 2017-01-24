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
 * �����˵�ѡ����棬���ڴ���ʹ�ã�һ����Ϊ������ʹ��
 * ʹ��ע�����
 * ����������init
 * 
 * @author ������
 */
public class ButtomMenusPanel extends LinearLayout {

	LinkedHashMap<String, View> menus = new LinkedHashMap<String, View>();
	private int width;
	private int height;
	Context context ;
	//��ť��ɫΪδѡ�����ɫ
	private int InCheicebuttomColor = 0xccffffff;
	//��ť��ɫΪδѡ�����ɫ
	private int NOCheicebuttomColor = 0xccffffff;
	//Ϊ������ɫ
	private int contentColor = 0xffffffff; 
	//δѡ��İ�ť������ɫ
	private int noCheiceButtonTextColor = 0xffCFCFCF;
	//ѡ���е�������ɫ
	private int InCheiceButtonTextColor = 0xff000000; 
	//ѡ�е��±�
	private int InCheiceIndex = 0 ;

	//�����������ͼ��
	ArrayList<Integer> iconList = new ArrayList<Integer>();

	//��ʼ�߶�
	public static int menuheightdip = 50;

	int menuheightpx;



	//�����ļ���
	LinearLayout content ;

	LinearLayout menuslLayout;



	public ButtomMenusPanel(Context context) {
		super(context);

		this.context = context ;
		//�������� ������Ϊ��ֱ����
		setOrientation(LinearLayout.VERTICAL);
		//���ÿ��
		setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

		content = new LinearLayout(context);
		content.setOrientation(LinearLayout.VERTICAL);

		init(menus,iconList);

	}



	/**
	 * ����ʹ�ñ�������ⷽ�����ɽ���
	 * ����ͼ����Դ�������ǰ�ť����������
	 * ��map��˳�����������Դ��һ��Ϊû�е���е�ͼ��id����һ��Ϊ����е�id��˳�򲻿ɵ�ת��
	 * @param @param menus   ������Դ
	 * @param @param iconLis    �����ͼ��
	 * @return void   
	 * 
	 */
	public void init(LinkedHashMap<String, View> menus , ArrayList<Integer> iconLis) {

		this.menus = menus ;
		//�ж��Ƿ���ͼ��
		if (iconLis!=null) {
			iconList =iconLis;
		}else {
			iconList = null;
		}

		//�Ծ�����д���
		removeAllViews();

		menuheightpx = DisplayUtil.dip2px(context, menuheightdip);

		width = DisplayUtil.getWidth(context);

		height = DisplayUtil.getHeight(context);
		//�ж��Ƿ�ʹ�ó�ʼֵ
		if ((height*0.08)>menuheightpx) {
			menuheightpx=(int) (height*0.08);
		}
		//�������Ĳ���
		LinearLayout.LayoutParams contentparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				height-menuheightpx	);
		//�˵����Ĳ���
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
	 * ��ȡ�ҵĴ�С����
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
		case MeasureSpec.UNSPECIFIED: {//���û��ָ����С��������ΪĬ�ϴ�С
			mySize = defaultSize;
			break;
		}
		case MeasureSpec.AT_MOST: {//�������ģʽ�����ȡֵΪsize
			//���ǽ���Сȡ���ֵ,��Ҳ����ȡ����ֵ
			mySize = size;
			break;
		}
		case MeasureSpec.EXACTLY: {//����ǹ̶��Ĵ�С���ǾͲ�Ҫȥ�ı���
			mySize = size;
			break;
		}
		}
		return mySize;
	}

	/**
	 * ���ɲ˵����
	 * 
	 * @param @return    
	 * @return LinearLayout   
	 * 
	 */
	private LinearLayout makeButtomMenus() {
		//���ԭ��Ԫ��

		LinearLayout menusLayout = new LinearLayout(context);
		menusLayout.setBackgroundColor(NOCheicebuttomColor);
		//Ϊ���Բ�����������
		menusLayout.setOrientation(LinearLayout.HORIZONTAL);
		menusLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				menuheightpx));
		if (menus.isEmpty()) {
			Button nullbButton = new Button(context);
			nullbButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
					menuheightpx));
			nullbButton.setText("��");
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

					//��ȡmap��������֣��Ž�button
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
	 * ����ѡ�а�ť����ɫ
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
	 * �����·���ť����ɫ
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
	 * ������������ѡ�а�ť�ı�����ɫ
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
	 * ����û��ѡ�в˵���������ɫ
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
	 * ����ѡ����
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
	 * ����ѡ�а�ť��������ɫ
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
