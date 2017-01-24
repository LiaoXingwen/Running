package locationkit;

import tools.GetWeather;
import tools.UploadLoaction;
import viewtools.DistanceCom;
import viewtools.StartLayout;
import android.content.Context;
import android.content.SharedPreferences;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.lxw.baidumapdemo.MainHandleTool;




/**
 * 定位事件
 * 使用：
 * 必须调用：
 * setContext方法来初始化Context
 * 
 * @作者 廖兴文
 *
 * @时间 2016-10-26
 */
public class WeatherLocationListener implements BDLocationListener {


	private boolean isLocation = false;
	String Location ="定位失败";
	String cityname  = "北京";

	@Override
	public void onReceiveLocation(BDLocation location) {

		if (location.getLocType() == BDLocation.TypeGpsLocation){// GPS定位结果
			//城市名字
			cityname = location.getAddrStr().substring(location.getAddrStr().indexOf("省")+1,location.getAddrStr().indexOf("市"));
			isLocation = true;
		} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){// 网络定位结果
			cityname = location.getAddrStr().substring(location.getAddrStr().indexOf("省")+1,location.getAddrStr().indexOf("市"));
			isLocation = true;
		} else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
			cityname = location.getAddrStr().substring(location.getAddrStr().indexOf("省")+1,location.getAddrStr().indexOf("市"));
			isLocation = true;
		} 



		if (isLocation) {
			UploadLoaction uploaction = new UploadLoaction();
			uploaction.execute(location.getLatitude(),location.getLongitude());
			GetWeather getWeather = new GetWeather();
			if (getWeather.isFindCityIds(cityname)) {
				getWeather.execute();
			}
			
		}
		
	}




}