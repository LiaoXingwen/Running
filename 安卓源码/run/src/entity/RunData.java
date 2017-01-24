package entity;

import java.util.ArrayList;
import java.util.List;

import com.baidu.mapapi.model.LatLng;

public class RunData {
	long spacetimeLong = 0 ; 
	public long getSpacetimeLong() {
		return spacetimeLong;
	}
	public void setSpacetimeLong(long spacetimeLong) {
		this.spacetimeLong = spacetimeLong;
	}
	String startTime = "0000-00-00 00:00:00"; //开始时间
	String endTime = "0000-00-00 00:00:00";//结束时间
	String spaceTime = "00:00:00";//花费时间
	String runNum = "0.00"; //跑步距离
	String runSpeed ="0.00"; //跑步速度
	List<LatLng> list = new ArrayList<LatLng>() ;
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getSpaceTime() {
		return spaceTime;
	}
	public void setSpaceTime(String spaceTime) {
		this.spaceTime = spaceTime;
	}
	public String getRunNum() {
		return runNum;
	}
	public void setRunNum(String runNum) {
		this.runNum = runNum;
	}
	public String getRunSpeed() {
		return runSpeed;
	}
	public void setRunSpeed(String runSpeed) {
		this.runSpeed = runSpeed;
	}
	public List<LatLng> getList() {
		return list;
	}
	public void setList(List<LatLng> list) {
		this.list = list;
	}
	public RunData( String startTime, String endTime,
			String spaceTime, String runNum, String runSpeed, List<LatLng> list) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.spaceTime = spaceTime;
		this.runNum = runNum;
		this.runSpeed = runSpeed;
		this.list = list;
	}
	public RunData() {
	}
	
	
}
