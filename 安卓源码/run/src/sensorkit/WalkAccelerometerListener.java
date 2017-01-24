package sensorkit;

import java.util.Date;
import java.util.Vector;

import servicekit.AccelerometerListenerService;
import tools.PublicSrc;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.Toast;

import com.lxw.baidumapdemo.MainHandleTool;
import com.lxw.baidumapdemo.R;

public class WalkAccelerometerListener implements SensorEventListener {
	//记录加速度
	Vector<AccelerometerPoint> accelerometers = new Vector<AccelerometerPoint>();
	//记录状态
	Vector<Integer> states = new Vector<Integer>();

	//记录加速度方向
	Vector<Integer> points = new Vector<Integer>();

	//静止
	static int Static = 0;

	int imageid = R.drawable.nowalk; 
	//最低方差
	int minError = 10;
	//运动中
	static int Movement = 3;

	static int state = -1;

	//执行最短时间间距
	int timeSpace = 60;

	//最后一次执行时间
	long lastTimeChange = 0;

	//执行次数
	int doCount = 0 ;
	//执行多少次进行一次步伐计算
	int doSpace = 6;

	int walkCount = 0 ;

	//当传感器的精确度已经改变调用
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {



	}
	float ROTATION =0;

	//当传感器值发生变化时调用。
	@Override
	public void onSensorChanged(SensorEvent event) {

		synchronized (this) {
			long when = System.currentTimeMillis();
			if (when-lastTimeChange>timeSpace) {
				Sensor sensor  = event.sensor;
				//加锁，防止同一时间多次执行
				//判断类型，此处应该为加速度传感器类型
				if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

					//				所有值都以SI单位（米/秒^ 2）
					//				值[0]：x轴
					//				值[1]：y轴
					//				值[2]：关于z轴加速度减去GZ

					//获取传感器的改变值

					accelerometers.add(new AccelerometerPoint(when, event.values[0], event.values[1], event.values[2],state));

					lastTimeChange = when ; 
					paintTable(event);
					doCount++;
				}
			}
		}


	}



	public void paintTable(SensorEvent event) {
		if (doCount==doSpace) {
			handleaccelerometers();
			doCount=0;
			accelerometers.removeAllElements();
		}
	}

	/**
	 * 处理点信息
	 * 
	 * @param     
	 * @return void   
	 * 
	 */
	private void handleaccelerometers() {
		//xyz的平均值
		float xmean = 0;
		float ymean = 0;
		float zmean = 0;
		int i = 0;
		for (; i < accelerometers.size(); i++) {
			AccelerometerPoint point = accelerometers.get(i);
			xmean +=point.x;
			ymean +=point.y;
			zmean +=point.z;
		}
		xmean = xmean/i;
		ymean = ymean/i;
		zmean = zmean/i;


		//求方差
		float xsquarederror = 0;
		float ysquarederror = 0;
		float zsquarederror = 0;
		int maxsquared = 0;
		i=0;
		//-1 012xyz
		int type = -1;
		for (; i < accelerometers.size(); i++) {
			AccelerometerPoint point = accelerometers.get(i);
			xsquarederror +=((point.x-xmean)*(point.x-xmean));
			ysquarederror +=((point.y-ymean)*(point.y-ymean));
			zsquarederror +=((point.z-zmean)*(point.z-zmean));
			maxsquared = (int) (xsquarederror+ysquarederror+zsquarederror);
		}
		if (maxsquared==0) {
			type = -1 ;
		}else {
			if (xsquarederror>ysquarederror) {
				if (xsquarederror>zsquarederror) {
					maxsquared =(int) xsquarederror;
					type = 0;
				}else {
					maxsquared = (int) zsquarederror;
					type = 2;
				}
			}else {
				if (ysquarederror>zsquarederror) {
					maxsquared =(int) ysquarederror;
					type = 1;
				}else {
					maxsquared = (int) zsquarederror;
					type = 2;
				}
			}
		}


		points.add(type);


		//根据平方差的大小判断是否运动
		if (minError>=maxsquared) {
			states.add(Static);
			MainHandleTool.sendMessage(MainHandleTool.UPDATE_WALKTYPE, new String[]{String.valueOf(imageid),String.valueOf(maxsquared)});
			return ;
		}else {
			//处于运动状态
			states.add(Movement);
		}


		int statecount = states.size();
		if (statecount >=10) {
			Vector<Integer> temp = new Vector<Integer>();
			int staticcount = 0;
			//获取最近十次所有的值,记录十次中处于那种状态
			for (int j =statecount-1; j > statecount-11; j--) {
				temp.add(states.get(j));
				if (states.get(j)==Static) {
					staticcount++;
				}
			}
			//十次判断，7次以上为静止，当前静止
			if (staticcount>=6) {
				if (state!=Static) {
					imageid = R.drawable.nowalk;
					//直接判为静止
					state = Static;
				}

				if (states.size()>50) {
					states.removeAllElements();
				}
				if (points.size()>50) {
					points.removeAllElements();
				}

			}else {
				imageid = R.drawable.nowalk;

				//最后一点相同统计
				int lastpointcount = 0 ;
				int pointsize = points.size();
				if (pointsize>10) {
					//取方向十个点
					Vector<Integer> temp1 = new Vector<Integer>();
					//获取最近十次所有的值,看运动的方向是否基本相同，基本相同则加步
					if (type>-1){
						for (int j =pointsize-1; j > pointsize-11; j--) {
							temp1.add(points.get(j));

							if (points.get(j)==type) 
								lastpointcount++;
						}
						if (
								lastpointcount>=5||
								(lastpointcount>3&&state == Movement)
								) {
							if (state == Movement||(lastpointcount<5&&points.get(points.size()-1)== type&&points.get(points.size()-2)!= type)) {
								if (states.get(states.size()-1)==Movement) {
									PublicSrc.walkData.addWalk(1);
								}
							}else {
								if (states.get(states.size()-1)==Movement)
								PublicSrc.walkData.addWalk(2);

							}
							state = Movement;
							imageid = R.drawable.walk;
						}
					}

				}else {
					imageid = R.drawable.nowalk;
					//直接判为静止
					state = Static;
				}
			}
		}


		MainHandleTool.sendMessage(MainHandleTool.UPDATE_WALKTYPE, new String[]{String.valueOf(imageid),String.valueOf(maxsquared)});
	}

}
