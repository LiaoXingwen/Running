package entity;

import java.io.Serializable;

/**   
*    
* 项目名称：Running   
* 类名称：WalkDataPK   
* 类描述：   跑步资料的联合主键
* 创建人：廖兴文
* 创建时间：2016年11月22日 下午7:54:35   
* 修改人：lxw   
* 修改时间：2016年11月22日 下午7:54:35   
* 修改备注：   
* @version 1.0.0   
*    
*/
public class WalkDataPK implements Serializable {
	private long uid = 0 ;//用户id
	private long Date = 0; //当天的时间

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
