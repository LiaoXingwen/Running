package entity;

import java.text.DecimalFormat;

/**   
 * �û���������
*    
* ��Ŀ���ƣ�Running   
* �����ƣ�OpenUserData   
* ��������   
* �����ˣ�������
* ����ʱ�䣺2016��12��25�� ����11:34:25   
* �޸��ˣ�lxw   
* �޸�ʱ�䣺2016��12��25�� ����11:34:25   
* �޸ı�ע��   
* @version 1.0.0   
*    
*/
public class OpenUserData {
	
	long phone;
	String name;
	String distance;//��ĳ�˵ľ���
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
