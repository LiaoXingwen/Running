package sensorkit;

/**
 * �����¼��ֵ
 * @���� ������
 *
 * @ʱ�� 2016-11-12
 */
public class AccelerometerPoint {
	//��Ҫ��¼����ֵ
	public long time = 0;
	public float x = 0;
	public float y = 0;
	public float z = 0;
	public int state = 0;
	/**
	 * ֱ�ӳ�ʼ����������
	 * @param time
	 * @param x 
	 * @param y
	 * @param z
	 */
	public AccelerometerPoint(long time , float x,float y,float z ,int state ){
		this.time = time ;
		this.x = x;
		this.y = y;
		this.z = z;
		this.state =state;
	}
	
	
}
