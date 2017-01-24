package entity;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;

/**
 * 定位数据列表单位
 * 
 * @作者 廖兴文
 *
 * @时间 2016-12-23
 */
public final class LoactionData {

	public LatLng LatLng ; 
	public int time ;
	public float r ;
	public LoactionData(com.baidu.mapapi.model.LatLng latLng, int time, float r) {
		LatLng = latLng;
		this.time = time;
		this.r = r;
	}
	
}
