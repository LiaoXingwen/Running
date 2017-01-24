package viewtools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import com.lxw.baidumapdemo.R;

import android.R.integer;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
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
public class RightMenusPanel extends FrameLayout {

	private int width;
	private int height;

	private int menuwidth;
	private int menuheight;
	Context context ;

	LinearLayout menu ;
	private List<Integer> Images;


	public RightMenusPanel(Context context) {
		super(context);

//		this.context = context ;

		width = DisplayUtil.getWidth(context);
		height = DisplayUtil.getHeight(context);

//		menu = new LinearLayout(context);
//
//		menu.setOrientation(LinearLayout.VERTICAL);
//
//		menu.setBackgroundColor(0xff000000);
//
//		addView(menu);

		
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(width, height);
	}

	public void init(List<Integer> image){
//		if (!image.isEmpty()) {
//			int size = image.size();
//			menuheight = height*size/10;
//			menuwidth = width/10;
//			FrameLayout.LayoutParams menuParams = new LayoutParams(menuwidth, menuheight);
//
//			menuParams.gravity =Gravity.RIGHT|Gravity.CENTER_VERTICAL;
//
//			menu.setLayoutParams(menuParams);
//
//
//
//			for (int i = 0; i < size; i++) {
//				ImageView imageView = new ImageView(context);
//				imageView.setBackgroundResource(image.get(i));
//				imageView.setLayoutParams(new LinearLayout.LayoutParams(
//						menuwidth,
//						menuheight/size));
//				menu.addView(imageView);
//			}
//
//
//		}
	}
	public void setImageList(List<Integer> list) {
		this.Images = list;
		init(list);
	}
}
