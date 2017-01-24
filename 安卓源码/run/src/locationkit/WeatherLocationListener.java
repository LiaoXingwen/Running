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
 * ��λ�¼�
 * ʹ�ã�
 * ������ã�
 * setContext��������ʼ��Context
 * 
 * @���� ������
 *
 * @ʱ�� 2016-10-26
 */
public class WeatherLocationListener implements BDLocationListener {


	private boolean isLocation = false;
	String Location ="��λʧ��";
	String cityname  = "����";

	@Override
	public void onReceiveLocation(BDLocation location) {

		if (location.getLocType() == BDLocation.TypeGpsLocation){// GPS��λ���
			//��������
			cityname = location.getAddrStr().substring(location.getAddrStr().indexOf("ʡ")+1,location.getAddrStr().indexOf("��"));
			isLocation = true;
		} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){// ���綨λ���
			cityname = location.getAddrStr().substring(location.getAddrStr().indexOf("ʡ")+1,location.getAddrStr().indexOf("��"));
			isLocation = true;
		} else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// ���߶�λ���
			cityname = location.getAddrStr().substring(location.getAddrStr().indexOf("ʡ")+1,location.getAddrStr().indexOf("��"));
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