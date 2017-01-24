package locationkit;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.baidu.mapapi.model.LatLng;

import entity.LoactionData;

/**
 * 处理定位列表
 * @作者 廖兴文
 *
 * @时间 2016-12-23
 */
public final class HandleLocationList {
	Vector<LoactionData> list ;
	Vector<LatLng> latlngs;
	DecimalFormat decimalFormat=new DecimalFormat("0.00");
	
	String allRun,speed,newspeed,power;
	
	
	final int maxSpeed = 30 ;//最大时速

	public HandleLocationList() {
		this.list = new Vector<LoactionData>();
		latlngs = new Vector<LatLng>();
		allRun ="0.00";
		speed = "0.00";
		newspeed = "0.00";
		power = "0.00";
	}



	List<LatLng> firstfills = new ArrayList<LatLng>();//第一次定位失败的列表
	/**
	 * 添加定位信息
	 * 
	 * @param @return    
	 * @return Vector<LatLng>   
	 * 
	 */
	public boolean addLoactionData(LoactionData loactionData){
		if (loactionData.r<300) {
			//首次定位，确保精度，对精度有比较高要求
			if (list.size()==0) {
				if (loactionData.r<100) {
					list.add(loactionData);
					firstfills =  null;
					return true ;
				}else {
					firstfills.add(loactionData.LatLng);
					if (firstfills.size()>3) {
						double[] b = variance(firstfills);
						list.add(new LoactionData(new LatLng(b[2], b[3]), (int) (System.currentTimeMillis()/1000), loactionData.r));
						firstfills =  null;
					}
				}}else {
					list.add(loactionData);
				}
			makeData();
			return true;
		}
		return false;
	}

	
	public synchronized Vector<LatLng> getLatlngs() {
		return latlngs;
	}


	public synchronized int getLatlngsSize() {
		return latlngs.size();
	}
	
	/**
	* 计算，生成数据
	* 
	* @param @return    
	* @return Vector<LatLng>   
	* 
	*/
	public synchronized void makeData() {
		//清空所有数据
		latlngs.clear();
		latlngs.add(list.get(0).LatLng);
		int[] times = new int[]{0,0};
		int firsttime = list.get(0).time ; 
		if (list.size()>3) {
			for (int i = 1; i < list.size()-2; i++) {
				
				LatLng latLng1 = list.get(i).LatLng;
				LatLng latLng2 = list.get(i+1).LatLng;
				LatLng latLng3 = list.get(i+2).LatLng;
				int time1 = list.get(i+1).time;
				int time2 = list.get(i+2).time;
				if (check(latLng1, latLng2, latLng3)&&checkLatLng(time2, time1, latLng2, latLng3)) {
					times[0] = times[1];
					times[1] = time2 ; 
					latlngs.add(latLng3);
				}
			}
		}
		allRun = decimalFormat.format(calRun(latlngs));
		if (latlngs.size()>1) {
			double all = calRun(latlngs);
			setAllRun(decimalFormat.format(all/1000));
			setNewspeed(decimalFormat.format((gps2m(latlngs.get(latlngs.size()-2), latlngs.lastElement())/(times[1]-times[0]))));
			setSpeed(decimalFormat.format(all/(times[1-firsttime])));
		}
	}
	
	
	public synchronized String getAllRun() {
		return allRun;
	}


	public synchronized void setAllRun(String allRun) {
		this.allRun = allRun;
	}


	public synchronized String getSpeed() {
		return speed;
	}


	public synchronized void setSpeed(String speed) {
		this.speed = speed;
	}


	public synchronized String getNewspeed() {
		return newspeed;
	}


	public synchronized void setNewspeed(String newspeed) {
		this.newspeed = newspeed;
	}


	public synchronized String getPower() {
		return power;
	}


	public synchronized void setPower(String power) {
		this.power = power;
	}


	public synchronized int getMaxSpeed() {
		return maxSpeed;
	}


	public synchronized void setLatlngs(Vector<LatLng> latlngs) {
		this.latlngs = latlngs;
	}


	public boolean checkLatLng(long time,long time1,LatLng latLng ,LatLng latLng2) {

		if (gps2m(latLng, latLng2)>5&&gps2m(latLng, latLng2)<=((time-time1)*maxSpeed)) {
			return true;
		}
		return false;
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




	private final static double EARTH_RADIUS = 6378137.0;

	/**
	* 两点之间的距离
	* 
	* @param @param a
	* @param @param b
	* @param @return    
	* @return double   
	* 
	*/
	public static double gps2m(LatLng a, LatLng b) {
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
	* 计算总距离
	* 
	* @param @param list
	* @param @return    
	* @return double   
	* 
	*/
	public static  double calRun(List<LatLng> list) {
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
	 * 获取坐标平均值，方差
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
	/**
	 * 计算线性表数据的平均值
	 * 
	 * @param @param list
	 * @param @return    
	 * @return LatLng   
	 * 
	 */
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
}
