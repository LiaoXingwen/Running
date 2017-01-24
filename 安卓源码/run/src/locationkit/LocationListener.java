package locationkit;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import android.R.bool;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import entity.LoactionData;




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
public class LocationListener implements BDLocationListener {

	HandleLocationList handleLocationList = new HandleLocationList();
	boolean isLocation = false ;
	boolean istest = false ;
	@Override
	public void onReceiveLocation(BDLocation location) {
		isLocation =false ; 
		StringBuffer sb = new StringBuffer(256);
		if (location.getLocType() == BDLocation.TypeGpsLocation){// GPS定位结果
			isLocation = true;

		} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){// 网络定位结果
			isLocation = true;
		} else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
			isLocation = true;
		} else if (location.getLocType() == BDLocation.TypeServerError) {
			sb.append("\ndescribe : ");
			sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
		} else if (location.getLocType() == BDLocation.TypeNetWorkException) {
			sb.append("\ndescribe : ");
			sb.append("网络不同导致定位失败，请检查网络是否通畅");
		} else if (location.getLocType() == BDLocation.TypeCriteriaException) {
			sb.append("\ndescribe : ");
			sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
		}

		if (isLocation) {
			handleLocationList.addLoactionData(new LoactionData(new LatLng(location.getLatitude(), location.getLongitude()), (int) (System.currentTimeMillis()/1000), location.getRadius()));
		}
	}
	public void getLatLngs() {

	}
}