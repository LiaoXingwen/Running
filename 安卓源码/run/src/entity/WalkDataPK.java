package entity;

import java.io.Serializable;

/**   
*    
* ��Ŀ���ƣ�Running   
* �����ƣ�WalkDataPK   
* ��������   �ܲ����ϵ���������
* �����ˣ�������
* ����ʱ�䣺2016��11��22�� ����7:54:35   
* �޸��ˣ�lxw   
* �޸�ʱ�䣺2016��11��22�� ����7:54:35   
* �޸ı�ע��   
* @version 1.0.0   
*    
*/
public class WalkDataPK implements Serializable {
	private long uid = 0 ;//�û�id
	private long Date = 0; //�����ʱ��

	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	public long getDate() {
		return Date;
	}
	public void setDate(long date) {
		Date = date;
	}
	public WalkDataPK(int uid, long date) {
		this.uid = uid;
		Date = date;
	}
	public WalkDataPK() {
	}
}
