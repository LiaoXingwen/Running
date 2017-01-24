package locationkit;

import android.app.Application;
import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

/**
 * ��λ���
 * 
 * @���� ������
 *
 * @ʱ�� 2016-12-23
 */
public class LocationApplication {


	private LocationClient locationClient;
	public LocationApplication(Context context) {
		locationClient = new LocationClient(context); 
	}
	/**
	 * Ĭ�϶�λ������:�߾��ȣ�һ�λ�ȡ������Ҫ������Ϣ
	 * 
	 * @param     
	 * @return void   
	 * 
	 */
	private void setDefaultLocationClientOption(){
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);//��ѡ��Ĭ�ϸ߾��ȣ����ö�λģʽ���߾��ȣ��͹��ģ����豸
		option.setCoorType("bd09ll");//��ѡ��Ĭ��gcj02�����÷��صĶ�λ�������ϵ
		//��λ��ʱ���϶
		int span=0;
		option.setScanSpan(span);//��ѡ��Ĭ��0��������λһ�Σ����÷���λ����ļ����Ҫ���ڵ���1000ms������Ч��
		option.setIsNeedAddress(true);//��ѡ�������Ƿ���Ҫ��ַ��Ϣ��Ĭ�ϲ���Ҫ
		option.setOpenGps(true);//��ѡ��Ĭ��false,�����Ƿ�ʹ��gps
		option.setLocationNotify(false);//��ѡ��Ĭ��false�������Ƿ�GPS��Чʱ����1S/1��Ƶ�����GPS���
		option.setIsNeedLocationDescribe(false);//��ѡ��Ĭ��false�������Ƿ���Ҫλ�����廯�����������BDLocation.getLocationDescribe��õ�����������ڡ��ڱ����찲�Ÿ�����
		option.setIsNeedLocationPoiList(false);//��ѡ��Ĭ��false�������Ƿ���ҪPOI�����������BDLocation.getPoiList��õ�
		option.setIgnoreKillProcess(true);//��ѡ��Ĭ��true����λSDK�ڲ���һ��SERVICE�����ŵ��˶������̣������Ƿ���stop��ʱ��ɱ��������̣�Ĭ�ϲ�ɱ��  
		option.SetIgnoreCacheException(false);//��ѡ��Ĭ��false�������Ƿ��ռ�CRASH��Ϣ��Ĭ���ռ�
		option.setEnableSimulateGps(false);//��ѡ��Ĭ��false�������Ƿ���Ҫ����GPS��������Ĭ����Ҫ
		locationClient.setLocOption(option);
	}



	/**
	* ���ö�λ����Ϣ
	* 
	* @param ��λģʽ����ֵ�磺LocationMode.Hight_Accuracy
	* @param 0��������λһ�Σ����÷���λ����ļ����Ҫ���ڵ���1000ms������Ч��
	* @param �����Ƿ���Ҫ��ַ��Ϣ
	* @param �����Ƿ�ʹ��gps
	* @param �����Ƿ���Ҫλ�����廯���
	* @param �������Ƿ���ҪPOI�����������BDLocation.getPoiList��õ� 
	* @return void   
	* 
	*/
	public	void setLocationClientOption(LocationMode locationMode , int span ,boolean IsNeedAddress, boolean isOpenGps , boolean isNeedLocationDescribe , boolean isNeedLocationPoiList){
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(locationMode);//��ѡ��Ĭ�ϸ߾��ȣ����ö�λģʽ���߾��ȣ��͹��ģ����豸
		option.setCoorType("bd09ll");//��ѡ��Ĭ��gcj02�����÷��صĶ�λ�������ϵ
		option.setScanSpan(span);//��ѡ��Ĭ��0��������λһ�Σ����÷���λ����ļ����Ҫ���ڵ���1000ms������Ч��
		option.setIsNeedAddress(IsNeedAddress);//��ѡ�������Ƿ���Ҫ��ַ��Ϣ��Ĭ�ϲ���Ҫ
		option.setOpenGps(isOpenGps);//��ѡ��Ĭ��false,�����Ƿ�ʹ��gps
		option.setLocationNotify(false);//��ѡ��Ĭ��false�������Ƿ�GPS��Чʱ����1S/1��Ƶ�����GPS���
		option.setIsNeedLocationDescribe(isNeedLocationDescribe);//��ѡ��Ĭ��false�������Ƿ���Ҫλ�����廯�����������BDLocation.getLocationDescribe��õ�����������ڡ��ڱ����찲�Ÿ�����
		option.setIsNeedLocationPoiList(isNeedLocationPoiList);//��ѡ��Ĭ��false�������Ƿ���ҪPOI�����������BDLocation.getPoiList��õ�
		option.setIgnoreKillProcess(true);//��ѡ��Ĭ��true����λSDK�ڲ���һ��SERVICE�����ŵ��˶������̣������Ƿ���stop��ʱ��ɱ��������̣�Ĭ�ϲ�ɱ��  
		option.SetIgnoreCacheException(false);//��ѡ��Ĭ��false�������Ƿ��ռ�CRASH��Ϣ��Ĭ���ռ�
		option.setEnableSimulateGps(false);//��ѡ��Ĭ��false�������Ƿ���Ҫ����GPS��������Ĭ����Ҫ
		locationClient.setLocOption(option);
	}

	/**
	 * �󶨶�λ������
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
	 * ���λ������
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
	 * ��ʼ��λ
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
	 * ������λ
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
