package locationkit;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import sensorkit.RunAccelerometerListener;
import tools.PublicSrc;

import android.R.bool;
import android.content.Context;
import android.renderscript.Element;
import android.renderscript.Type;
import android.text.format.Time;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.lxw.baidumapdemo.RunActivity;
import com.lxw.baidumapdemo.RunHanbleTool;
import com.lxw.baidumapdemo.R.id;




/**
 * 定位事件
 * 使用：
 * 必须调用：
 * setContext方法来初始化Context
 * setMapView 来初始化MapView
 * 
 * @作者 廖兴文
 *
 * @时间 2016-10-26
 */
public class RunLocationListener implements BDLocationListener {

	private double Latitude = 0.00000000;
	private double Longitude = 0.0000000;
	DecimalFormat decimalFormat=new DecimalFormat("0.00");
	private int maxspeed = 10 ;//人的最大速度
	double k1 = 0.1355 ;//一小时八公里
	double k2 = 0.1797 ;//一小时12公里
	double k3 = 0.1875 ;//一小时15公里
	//判断定位是否成功
	boolean isLocation = false;

	String locationTypeString = "定位失败";

	int nocount = 0;



	//记录有效的坐标和时间
	List<LatLng> list = new LinkedList<LatLng>();

	LatLng oldLatLng ;
	private RunActivity runActivity; 

	boolean isFirst = true ;
	private boolean isTest = false;
	int tipcount = 0 ;
	//地图描绘点
	@Override
	public void onReceiveLocation(BDLocation location) {
		synchronized (this) {
			//重置状态
			isLocation = false;
			locationTypeString = "定位失败";
			if (location.getLocType() == BDLocation.TypeGpsLocation){// GPS定位结果
				isLocation =true;
				locationTypeString = "gps定位成功";
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){// 网络定位结果
				isLocation =true;
				locationTypeString = "网络定位成功";
			} else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
				isLocation =true;
				locationTypeString = "离线定位成功";
			} 
			if (isLocation ||isTest) {
				templasttime = runActivity.getSpaceTime();
				//测试
				if (isTest) { 
//					if (isLocation&&isFirst) {
//						Latitude = location.getLatitude();
//						Longitude = location.getLongitude();
//					}
					testRun();

				}else {
					//定位精度最低要求
					if (location.getRadius()<=100){
						Latitude = location.getLatitude();
						Longitude = location.getLongitude();

						if (list.size()<2) {
							addLatLng(new LatLng(Latitude, Longitude));
						}else {
							handlePoint(Latitude ,Longitude);
						}
					}
				}
			}
			else {
				if (nocount==4) {
					nocount=0;
					tipcount ++ ;
					RunHanbleTool.sendMessage(RunHanbleTool.UPDATE_TOAST, new String[]{"当前定位精度低，不能有效定位，请开启GPS或保持良好网络"});
				}
				nocount++;
			}
		}
	}








	/**
	 * 测试方法
	 * 
	 * @param     
	 * @return void   
	 * 
	 */
	private void testRun() {
		int[] p = new int[]{0,1,2,3,4,5,-1,-2};

		Latitude+=0.000015*p[(int) (p.length*Math.random())];
		Longitude+=0.000015*p[(int) (p.length*Math.random())];

		if (list.size()<2) {
			addLatLng(new LatLng(Latitude, Longitude));
		}else {
			handlePoint(Latitude ,Longitude);
		}

	}






	/**
	 * 	主要对定位点去噪和处理
	 * 
	 * 点处理要求：
	 * 四个点取点不能超过10s
	 * 四个点的位移不超过时间的乘于最大配速
	 * 
	 * 
	 * @param     
	 * @return void   
	 * 
	 */
	private void handleFirstPoint() {
		//	//取时间节点
		//	long time0 = lasttime.get(0);
		//	long time1 = lasttime.get(2);
		//	//对时间进行判断
		//	if(time1-time0<6*1000){
		//		int
		//		int success = 0 ;//符合要 count = 0;//遍历的次数求统计
		//		//拿四个点
		//		for (int i = 0; i < list.size()-1; i++) {
		//			LatLng latLng = list.get(i);
		//			long time = list.get(i);
		//
		//			for (int j = i+1; j < temp.size(); j++) {
		//				count++;
		//				if(checkLatLng(times.get(j), time, latLng, temp.get(j))){
		//					success++;
		//				}
		//			}
		//		}
		//		//所有点符合
		//		if (count==success) {
		//			//取平均值
		//			double[] d = variance(temp);
		//			oldLatLng = new LatLng(d[0], d[1]);
		//			//加到有效列表
		//			addLatLng(oldLatLng);
		//		}else {
		//			//全部清除
		//			temp.clear();
		//			times.clear();
		//			nocount++;
		//			return;
		//		}
		//	}else {
		//		//全部清除
		//		temp.clear();
		//		times.clear();
		//		nocount++;
		//		return;
		//	}
	}




	int check = 0 ;

	/**
	 * 	主要对定位点去噪和处理
	 * 
	 * @param     
	 * @return void   
	 * 
	 */
	private void handlePoint(double lat,double lng) {
		//取时间节点
		LatLng latLng3 = new LatLng(lat, lng);
		if (checkLatLng(templasttime ,lasttime , oldLatLng, latLng3)) {
			LatLng latlng2 = list.get(list.size()-2);
			if (check(latlng2, oldLatLng, latLng3)) {
				addLatLng(latLng3);
				return;
			}
		}
	}




	public boolean checkLatLng(long time,long time1,LatLng latLng ,LatLng latLng2) {

		if (gps2m(latLng, latLng2)>5&&gps2m(latLng, latLng2)<=((time-time1)*maxspeed)) {
			return true;
		}
		return false;
	}

	public boolean checkSensor(LatLng latLng ,LatLng latLng2) {

		if (gps2m(latLng, latLng2)>5&&gps2m(latLng, latLng2)<=(RunAccelerometerListener.getMoveCount()*maxspeed)) {
			return true;
		}
		return false;
	}

	double spacePower = 0;
	private long lasttime = 0 ;
	private long templasttime = 0 ;

	/**
	 * 添加绘制坐标，并停止
	 * 
	 * @param     
	 * @return void   
	 * 
	 */
	void addLatLng(LatLng latLng){
		if (isFirst) {
			isFirst = false;
			if (runActivity!=null) {
				runActivity.startTime();
			}
		}
		String speed = "0.00"; 
		if (list.size()>0) {
			double s = gps2m(oldLatLng, latLng)/((templasttime-lasttime));
			if (s>5) {
				PublicSrc.RUNDATA.addRun(gps2m(oldLatLng, latLng)/1000);
			}
			if (s<15&&s>0) {
				if (s<2) {
					spacePower += (templasttime-lasttime)*k1 ;
				}else {
					if(s<4){
						spacePower += (templasttime-lasttime)*k2 ;
					}else {
						spacePower += (templasttime-lasttime)*k3 ;
					}
				}
				speed = decimalFormat.format(s) ;
			}
		}
		list.add(latLng);
		lasttime = templasttime;
		oldLatLng = latLng ;
		if (runActivity!=null) {
			BDLocation bd = new BDLocation();
			bd.setCoorType("bd09ll");
			bd.setLatitude(latLng.latitude);
			bd.setLongitude(latLng.longitude);
			bd.setRadius(0); 
//			runActivity.paintLatlng(list);
//			runActivity.setNewSpeed(speed);
			runActivity.setSpacePower(decimalFormat.format(spacePower));
			RunHanbleTool.sendMessage(RunHanbleTool.UPDATE_RUNNUMBER,new String[]{calRun()});
		}
		if (!isTest) {
//			RunHanbleTool.sendMessage(RunHanbleTool.STOPLOCAATION, null);
		}
		//重置所有对象
		nocount = 0;
		check = 0;
		//		isFirst = false;
		RunAccelerometerListener.setMoveCount(0);
	}



	private String calRun() {
		if (list.size()>=3) {
			double run = 0;
			for (int i = 0; i < list.size()-2; i++) {
				LatLng tem1 = list.get(i);
				LatLng tem2 = list.get(i+1);
				run += gps2m(tem1,tem2);
			}

			DecimalFormat decimalFormat=new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
			String p=decimalFormat.format(run/1000);//format 返回的是字符串
			return p;
		}else {
			if (list.size()==2) {
				LatLng tem1 = list.get(0);
				LatLng tem2 = list.get(1);
				DecimalFormat decimalFormat=new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
				String p=decimalFormat.format(gps2m(tem1,tem2)/1000);//format 返回的是字符串
				return p;
			}else {
				return "0.00";
			}
		}

	}

	public static double calRun(List<LatLng> list) {
		if (list.size()>=3) {
			double run = 0;
			for (int i = 0; i < list.size()-2; i++) {
				LatLng tem1 = list.get(i);
				LatLng tem2 = list.get(i+1);
				run += gps2m(tem1,tem2);
			}
			return  run;
		}else {
			if (list.size()==2) {
				LatLng tem1 = list.get(0);
				LatLng tem2 = list.get(1);
				return  gps2m(tem1,tem2);
			}else {
				return 0;
			}
		}

	}

	/**
	 * 设置地图
	 * 
	 * @param @param map    
	 * @return void   
	 * 
	 */
	public void setRunActivity(RunActivity runActivity) {

		this.runActivity = runActivity ;

	}


	public void reSet() {
		isFirst=true;
		nocount = 0;
		lasttime = 0 ;
		//记录有效的坐标和时间
		list = new LinkedList<LatLng>();
		oldLatLng = null;

	}







	//	 -------------------------计算距离--------------------------

	//		    private double sum_distance = 0;
	private final static double EARTH_RADIUS = 6378137.0;

	private double gps2m(double lat_a, double lng_a, double lat_b, double lng_b) {
		double radLat1 = (lat_a * Math.PI / 180.0);
		double radLat2 = (lat_b * Math.PI / 180.0);
		double a = radLat1 - radLat2;
		double b = (lng_a - lng_b) * Math.PI / 180.0;
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		//s = Math.round(s * 10000) / 10000;
		return 1.38*s;

	}


	private static double gps2m(LatLng a, LatLng b) {
		double radLat1 = (a.latitude * Math.PI / 180.0);
		double radLat2 = (b.latitude * Math.PI / 180.0);
		double x = radLat1 - radLat2;
		double y = (a.longitude - b.longitude) * Math.PI / 180.0;
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(x / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(y / 2), 2)));
		s = s * EARTH_RADIUS;
		return 1.38*s;

	}


	/**
	 * 获取坐标平方差，方差
	 * 
	 * 
	 * @param @param list
	 * @param @return    
	 * @return int[]   
	 * 
	 */
	public double[] variance(List<LatLng> list){
		double[] result = new double[4];
		LatLng mean = mean(list);

		double meanlat = mean.latitude;
		double meanlng = mean.longitude;

		double latpow = 0 ; //精度
		double lngpow = 0 ; //纬度
		for (int i = 0; i < list.size(); i++) {
			LatLng latLng = list.get(i);
			latpow +=Math.pow(latLng.latitude-meanlat, 2);
			lngpow += Math.pow(latLng.longitude-meanlng, 2);
		}
		result[0] =  meanlat;
		result[1] = meanlng;
		result[2] =  latpow;
		result[3] =  lngpow;

		return result; 


	}
	//计算线性表数据的平均值
	public LatLng mean(List<LatLng> list){ 
		int latLngcount ;
		if ((latLngcount = list.size())>0) {

			double latcount = 0 ;
			double Lngcount = 0 ;
			for (int i = 0; i < latLngcount; i++) {
				LatLng latLng = list.get(i);
				latcount +=latLng.latitude;
				Lngcount +=latLng.longitude;

			}

			return new LatLng(latcount/latLngcount, Lngcount/latLngcount);

		}
		return null;
	}

	public List<LatLng> IteratorToList(Iterator<LatLng> iterator){
		ArrayList<LatLng> list = new ArrayList<LatLng>();
		while (iterator.hasNext()) {
			list.add(iterator.next());
		}
		return list;
	}


	/**
	 * 距离判断
	 * 
	 * @param     
	 * @return void   
	 * 
	 */
	private boolean check(LatLng latLng ,LatLng latLng1 ,LatLng latLng2  ) {
		if ((gps2m(latLng, latLng1)+gps2m(latLng1, latLng2))*0.6<gps2m(latLng2, latLng)) {
			return true;
		}else {
			return false ;
		}
	}


}