package entity;

import java.text.DecimalFormat;

/**   
 * 用户公开资料
*    
* 项目名称：Running   
* 类名称：OpenUserData   
* 类描述：   
* 创建人：廖兴文
* 创建时间：2016年12月25日 上午11:34:25   
* 修改人：lxw   
* 修改时间：2016年12月25日 上午11:34:25   
* 修改备注：   
* @version 1.0.0   
*    
*/
public class OpenUserData {
	
	long phone;
	String name;
	String distance;//跟某人的距离
	String runNum ;
	String WalkNum ;
	public long getPhone() {
		return phone;
	}
	public void setPhone(long phone) {
		this.phone = phone;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getRunNum() {
		return runNum;
	}
	public void setRunNum(String runNum) {
		this.runNum = runNum;
	}
	public String getWalkNum() {
		return WalkNum;
	}
	public void setWalkNum(String walkNum) {
		WalkNum = walkNum;
	} 
	
}
