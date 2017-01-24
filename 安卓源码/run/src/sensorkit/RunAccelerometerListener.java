package sensorkit;

import java.util.Date;
import java.util.LinkedList;
import java.util.Vector;

import servicekit.AccelerometerListenerService;
import tools.PublicSrc;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.SystemClock;

import com.lxw.baidumapdemo.MainHandleTool;
import com.lxw.baidumapdemo.R;
import com.lxw.baidumapdemo.RunHanbleTool;

/**
 * һ �ܲ�ʱ�������ļ�����
 * 1.����ʱ��Ӧ�����ܲ�����ʱ������
 * 2.����ʱ��Ӧ�����ܲ�������ȡ��
 * 
 * �� �������ԣ�
 * ����Ҫ̫���еļ��ٶ����ݣ��Ͼ��ܲ�����Բ����Ĺ���
 * 
 * 
 * @���� ������
 *
 * @ʱ�� 2016-11-23
 */
public class RunAccelerometerListener implements SensorEventListener {


	Vector<AccelerometerPoint> accelerometers = new Vector<AccelerometerPoint>();
	//��¼״̬
	Vector<Integer> states = new Vector<Integer>();

	//��¼���ٶȷ���
	Vector<Integer> points = new Vector<Integer>();

	//��ֹ
	static int Static = 0;

	//��ͷ���
	int minError = 10;
	//�˶���
	final static int  Movement = 3;

	static int  Movementcount = 0;

	static int state = -1;

	//ִ�����ʱ����
	int timeSpace = 100;

	//���һ��ִ��ʱ��
	long lastTimeChange = 0;

	//ִ�д���
	int doCount = 0 ;
	//ִ�ж��ٴν���һ�β�������
	int doSpace = 10;

	boolean isFirst = true;
	
	//���������ľ�ȷ���Ѿ��ı����
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {



	}
	float ROTATION =0;
	private static Context context;

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
			if (isFirst) {
			}
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
		}
		maxsquared = (int) (xsquarederror+ysquarederror+zsquarederror);
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
			//ʮ���жϣ�4������Ϊ��ֹ����ǰ��ֹ
			if (staticcount>4) {
				if (state!=Static) {
					//ֱ����Ϊ��ֹ
					state = Static;
				}
				if (states.size()>100) {
					states.removeAllElements();
				}
				if (points.size()>100) {
					points.removeAllElements();
				}
			}else {
				setMoveCount(getMoveCount()+1);
				state = Movement;
				if (getMoveCount()>4) {
					setMoveCount(0);
				}
			}
		}
	}

	/**
	 * �����˶�ͳ��
	 * 
	 * @param     
	 * @return void   
	 * 
	 */
	public static synchronized void setMoveCount(int number) {
		Movementcount =  number ;
	}
	/**
	 * ��ȡ�˶�ͳ��
	 * 
	 * 
	 * @param @return    
	 * @return int   
	 * 
	 */
	public static synchronized int getMoveCount() {
		return Movementcount;
	}

}
