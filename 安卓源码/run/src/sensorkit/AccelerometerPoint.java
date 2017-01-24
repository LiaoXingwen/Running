package sensorkit;

/**
 * 方便记录数值
 * @作者 廖兴文
 *
 * @时间 2016-11-12
 */
public class AccelerometerPoint {
	//需要记录的数值
	public long time = 0;
	public float x = 0;
	public float y = 0;
	public float z = 0;
	public int state = 0;
	/**
	 * 直接初始化所有数据
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
