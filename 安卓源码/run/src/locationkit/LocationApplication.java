package locationkit;

import android.app.Application;
import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

/**
 * 定位组件
 * 
 * @作者 廖兴文
 *
 * @时间 2016-12-23
 */
public class LocationApplication {


	private LocationClient locationClient;
	public LocationApplication(Context context) {
		locationClient = new LocationClient(context); 
	}
	/**
	 * 默认定位器数据:高精度，一次获取，不需要语义信息
	 * 
	 * @param     
	 * @return void   
	 * 
	 */
	private void setDefaultLocationClientOption(){
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
		//定位的时间间隙
		int span=0;
		option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
		option.setOpenGps(true);//可选，默认false,设置是否使用gps
		option.setLocationNotify(false);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
		option.setIsNeedLocationDescribe(false);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
		option.setIsNeedLocationPoiList(false);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
		option.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死  
		option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
		option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
		locationClient.setLocOption(option);
	}



	/**
	* 设置定位器信息
	* 
	* @param 定位模式，数值如：LocationMode.Hight_Accuracy
	* @param 0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
	* @param 设置是否需要地址信息
	* @param 设置是否使用gps
	* @param 设置是否需要位置语义化结果
	* @param ，设置是否需要POI结果，可以在BDLocation.getPoiList里得到 
	* @return void   
	* 
	*/
	public	void setLocationClientOption(LocationMode locationMode , int span ,boolean IsNeedAddress, boolean isOpenGps , boolean isNeedLocationDescribe , boolean isNeedLocationPoiList){
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(locationMode);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
		option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		option.setIsNeedAddress(IsNeedAddress);//可选，设置是否需要地址信息，默认不需要
		option.setOpenGps(isOpenGps);//可选，默认false,设置是否使用gps
		option.setLocationNotify(false);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
		option.setIsNeedLocationDescribe(isNeedLocationDescribe);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
		option.setIsNeedLocationPoiList(isNeedLocationPoiList);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
		option.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死  
		option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
		option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
		locationClient.setLocOption(option);
	}

	/**
	 * 绑定定位监听器
	 * 
	 * @param @param lister    
	 * @return void   
	 * 
	 */
	public void registerLocationListener(BDLocationListener lister){

		if (locationClient != null) {
			locationClient.registerLocationListener(lister);
		}

	}

	/**
	 * 解绑定位监听器
	 * 
	 * @param @param lister    
	 * @return void   
	 * 
	 */
	public void unRegisterLocationListener(BDLocationListener lister){

		if (locationClient != null) {
			locationClient.unRegisterLocationListener(lister);
		}
	}

	/**
	 * 开始定位
	 * 
	 * @param     
	 * @return void   
	 * 
	 */
	public void startLocation(){
		if (locationClient != null)
			locationClient.start();
	}

	/**
	 * 结束定位
	 * 
	 * @param     
	 * @return void   
	 * 
	 */
	public void stopLocation(){
		if (locationClient != null)
			locationClient.stop();
	}

}
