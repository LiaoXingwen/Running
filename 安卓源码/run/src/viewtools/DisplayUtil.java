package viewtools;

import android.content.Context;
import android.view.WindowManager;
import android.view.View.MeasureSpec;

/** 
     * dp��sp ת��Ϊ px �Ĺ����� 
     *  
     * @author fxsky 2012.11.12 
     * 
     */  
    public class DisplayUtil {  
        /** 
         * ��pxֵת��Ϊdip��dpֵ����֤�ߴ��С���� 
         *  
         * @param pxValue 
         * @param scale 
         *            ��DisplayMetrics��������density�� 
         * @return 
         */  
        public static int px2dip(Context context, float pxValue) {  
            final float scale = context.getResources().getDisplayMetrics().density;  
            return (int) (pxValue / scale + 0.5f);  
        }  
      
        /** 
         * ��dip��dpֵת��Ϊpxֵ����֤�ߴ��С���� 
         *  
         * @param dipValue 
         * @param scale 
         *            ��DisplayMetrics��������density�� 
         * @return 
         */  
        public static int dip2px(Context context, float dipValue) {  
            final float scale = context.getResources().getDisplayMetrics().density;  
            return (int) (dipValue * scale + 0.5f);  
        }  
      
        /** 
         * ��pxֵת��Ϊspֵ����֤���ִ�С���� 
         *  
         * @param pxValue 
         * @param fontScale 
         *            ��DisplayMetrics��������scaledDensity�� 
         * @return 
         */  
        public static int px2sp(Context context, float pxValue) {  
            final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;  
            return (int) (pxValue / fontScale + 0.5f);  
        }  
      
        /** 
         * ��spֵת��Ϊpxֵ����֤���ִ�С���� 
         *  
         * @param spValue 
         * @param fontScale 
         *            ��DisplayMetrics��������scaledDensity�� 
         * @return 
         */  
        public static int sp2px(Context context, float spValue) {  
            final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;  
            return (int) (spValue * fontScale + 0.5f);  
        }  
        
        
        /**
         * ��ȡ��Ļ���
        * @Title: getWidth
        * @Description: 
        * @param @param context
        * @param @return    
        * @return float   
        * @throws
        */
        public static int getWidth(Context context) {
        	WindowManager wm = (WindowManager) context
                    .getSystemService(Context.WINDOW_SERVICE);
			return wm.getDefaultDisplay().getWidth();
		}
        
        /**
         * ��ȡ��Ļ�߶�
         * 
         * @Title: getWidth
         * @Description: 
         * @param @param context
         * @param @return    
         * @return float   
         * @throws
         */
         public static int getHeight(Context context) {
         	WindowManager wm = (WindowManager) context
                     .getSystemService(Context.WINDOW_SERVICE);
 			return wm.getDefaultDisplay().getHeight();
 		}
     	/**
     	 * ��ȡ�ҵĴ�С����
     	 *
     	 * @param @param defaultSize
     	 * @param @param measureSpec
     	 * @param @return    
     	 *
     	 */
     	public static int getMySize(int defaultSize, int measureSpec) {
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
    }  