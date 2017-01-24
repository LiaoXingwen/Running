package viewtools;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

public class DataShow extends View {

	int ShowWidth ,ShowHeight ; 
	int geSize = 0 ;
	
	public DataShow(Context context) {
		super(context);
		
		ShowWidth = DisplayUtil.getWidth(context);
		geSize = ShowWidth/20;
		ShowHeight = geSize*10;
		
	}
	
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		DisplayUtil.getMySize(widthMeasureSpec, heightMeasureSpec);
	}
	
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		
		
	}
	
	
	

}
