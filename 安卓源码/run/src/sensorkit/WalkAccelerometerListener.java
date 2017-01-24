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
	//��¼���ٶ�
	Vector<AccelerometerPoint> accelerometers = new Vector<AccelerometerPoint>();
	//��¼״̬
	Vector<Integer> states = new Vector<Integer>();

	//��¼���ٶȷ���
	Vector<Integer> points = new Vector<Integer>();

	//��ֹ
	static int Static = 0;

	int imageid = R.drawable.nowalk; 
	//��ͷ���
	int minError = 10;
	//�˶���
	static int Movement = 3;

	static int state = -1;

	//ִ�����ʱ����
	int timeSpace = 60;

	//���һ��ִ��ʱ��
	long lastTimeChange = 0;

	//ִ�д���
	int doCount = 0 ;
	//ִ�ж��ٴν���һ�β�������
	int doSpace = 6;

	int walkCount = 0 ;

	//���������ľ�ȷ���Ѿ��ı����
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {



	}
	float ROTATION =0;

	//��������ֵ�����仯ʱ���á�
	@Override
	public void onSensorChanged(SensorEvent event) {

		synchronized (this) {
			long when = System.currentTimeMillis();
			if (when-lastTimeChange>timeSpace) {
				Sensor sensor  = event.sensor;
				//��������ֹͬһʱ����ִ��
				//�ж����ͣ��˴�Ӧ��Ϊ���ٶȴ���������
				if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

					//				����ֵ����SI��λ����/��^ 2��
					//				ֵ[0]��x��
					//				ֵ[1]��y��
					//				ֵ[2]������z����ٶȼ�ȥGZ

					//��ȡ�������ĸı�ֵ

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
	 * �������Ϣ
	 * 
	 * @param     
	 * @return void   
	 * 
	 */
	private void handleaccelerometers() {
		//xyz��ƽ��ֵ
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


		//�󷽲�
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


		//����ƽ����Ĵ�С�ж��Ƿ��˶�
		if (minError>=maxsquared) {
			states.add(Static);
			MainHandleTool.sendMessage(MainHandleTool.UPDATE_WALKTYPE, new String[]{String.valueOf(imageid),String.valueOf(maxsquared)});
			return ;
		}else {
			//�����˶�״̬
			states.add(Movement);
		}


		int statecount = states.size();
		if (statecount >=10) {
			Vector<Integer> temp = new Vector<Integer>();
			int staticcount = 0;
			//��ȡ���ʮ�����е�ֵ,��¼ʮ���д�������״̬
			for (int j =statecount-1; j > statecount-11; j--) {
				temp.add(states.get(j));
				if (states.get(j)==Static) {
					staticcount++;
				}
			}
			//ʮ���жϣ�7������Ϊ��ֹ����ǰ��ֹ
			if (staticcount>=6) {
				if (state!=Static) {
					imageid = R.drawable.nowalk;
					//ֱ����Ϊ��ֹ
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

				//���һ����ͬͳ��
				int lastpointcount = 0 ;
				int pointsize = points.size();
				if (pointsize>10) {
					//ȡ����ʮ����
					Vector<Integer> temp1 = new Vector<Integer>();
					//��ȡ���ʮ�����е�ֵ,���˶��ķ����Ƿ������ͬ��������ͬ��Ӳ�
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
					//ֱ����Ϊ��ֹ
					state = Static;
				}
			}
		}


		MainHandleTool.sendMessage(MainHandleTool.UPDATE_WALKTYPE, new String[]{String.valueOf(imageid),String.valueOf(maxsquared)});
	}

}
