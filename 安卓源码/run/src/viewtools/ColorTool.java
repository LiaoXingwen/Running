package viewtools;

import java.util.Random;

import android.graphics.Color;

public class ColorTool {
	/**
	* 
	* ��ȡ�����ɫ
	* @param @param alpha ͸���� ��FF��͸����00ȫ͸��
	* @param @return    
	* @return String   
	* 
	*/
	public static int getRandColorCode(String alpha){  
		String r,g,b;  
		Random random = new Random();  
		r = Integer.toHexString(random.nextInt(256)).toUpperCase();  
		g = Integer.toHexString(random.nextInt(256)).toUpperCase();  
		b = Integer.toHexString(random.nextInt(256)).toUpperCase();  

		r = r.length()==1 ? "0" + r : r ;  
		g = g.length()==1 ? "0" + g : g ;  
		b = b.length()==1 ? "0" + b : b ;  

		return Color.parseColor("#"+alpha+r+g+b);  
	}
}
