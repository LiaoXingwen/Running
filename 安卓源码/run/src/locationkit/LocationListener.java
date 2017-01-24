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
 * ��λ�¼�
 * ʹ�ã�
 * ������ã�
 * setContext��������ʼ��Context
 * 
 * @���� ������
 *
 * @ʱ�� 2016-10-26
 */
public class LocationListener implements BDLocationListener {

	HandleLocationList handleLocationList = new HandleLocationList();
	boolean isLocation = false ;
	boolean istest = false ;
	@Override
	public void onReceiveLocation(BDLocation location) {
		isLocation =false ; 
		StringBuffer sb = new StringBuffer(256);
		if (location.getLocType() == BDLocation.TypeGpsLocation){// GPS��λ���
			isLocation = true;

		} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){// ���綨λ���
			isLocation = true;
		} else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// ���߶�λ���
			isLocation = true;
		} else if (location.getLocType() == BDLocation.TypeServerError) {
			sb.append("\ndescribe : ");
			sb.append("��������綨λʧ�ܣ����Է���IMEI�źʹ��嶨λʱ�䵽loc-bugs@baidu.com��������׷��ԭ��");
		} else if (location.getLocType() == BDLocation.TypeNetWorkException) {
			sb.append("\ndescribe : ");
			sb.append("���粻ͬ���¶�λʧ�ܣ����������Ƿ�ͨ��");
		} else if (location.getLocType() == BDLocation.TypeCriteriaException) {
			sb.append("\ndescribe : ");
			sb.append("�޷���ȡ��Ч��λ���ݵ��¶�λʧ�ܣ�һ���������ֻ���ԭ�򣬴��ڷ���ģʽ��һ���������ֽ�����������������ֻ�");
		}

		if (isLocation) {
			handleLocationList.addLoactionData(new LoactionData(new LatLng(location.getLatitude(), location.getLongitude()), (int) (System.currentTimeMillis()/1000), location.getRadius()));
		}
	}
	public void getLatLngs() {

	}
}