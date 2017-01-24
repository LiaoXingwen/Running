package entity;

import com.lxw.baidumapdemo.MainHandleTool;

import tools.PublicSrc;
import tools.UploadData;
import SqlLiteDAO.DatabaseHelper;
import android.database.Cursor;

public class LocalRunData {

	private long phone =0;
	private double todayRun = 0;
	private double allRun = 0;
	private long uploadtime=0;
	private long uploadNumn = 0 ;
	private long date = 0 ;


	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}
	public long getPhone() {
		return phone;
	}
	public void setPhone(long phone) {
		this.phone = phone;
	}
	public double getTodayRun() {
		return todayRun;
	}
	public void setTodayRun(double todayRun) {
		this.todayRun = todayRun;
	}
	public double getAllRun() {
		return allRun;
	}
	public void setAllRun(double allRun) {
		this.allRun = allRun;
	}
	public long getUploadtime() {
		return uploadtime;
	}
	public void setUploadtime(long uploadtime) {
		this.uploadtime = uploadtime;
	}
	public long getUploadNumn() {
		return uploadNumn;
	}
	public void setUploadNumn(long uploadNumn) {
		this.uploadNumn = uploadNumn;
	}

	/**
	 * 通过这个静态方法得到这个实例
	 * 
	 * @param @return    
	 * @return LocalRunData   
	 * 
	 */
	public static  synchronized  LocalRunData SqlParseLocalRunData(long phone){
		LocalRunData localRunData = new LocalRunData();
		localRunData.setPhone(phone);
		localRunData.setDate(WalkData.parseLong());
		Cursor cursor  = DatabaseHelper.db.rawQuery("select upLoadTime,runNumber,upLoadRunNumber from RUNDATA where phone="+phone +" and date="+localRunData.getDate(), null);
		if (cursor.getCount()==1) {
			cursor.moveToLast();
			localRunData.setUploadNumn(cursor.getLong(0));
			localRunData.setTodayRun(cursor.getDouble(1));
			localRunData.setUploadNumn(cursor.getLong(2));
			Cursor cursor1  = DatabaseHelper.db.rawQuery("select sum(runNumber) from RUNDATA where phone="+phone, null);
			cursor1.moveToLast();
			localRunData.setAllRun(cursor1.getDouble(0));
		}else{
			DatabaseHelper.db.execSQL("insert into RUNDATA values ("+phone +" ,"+localRunData.getDate()+", "+0+", "+0+", "+0+") ");
		}
		return localRunData;
	}

	public synchronized void update(){
		//上传到服务器
		new UploadData().execute("uoloadRun","run",String.valueOf(getTodayRun()));
		DatabaseHelper.db.execSQL("update RUNDATA set  upLoadTime ="+getUploadtime()+", runNumber="+getTodayRun()+", upLoadRunNumber="+getUploadNumn()+
				" where phone = "+getPhone()+" and date = "+getDate());
	}
	/**
	 * 添加数据
	 * 
	 * @param @param l    
	 * @return void   
	 * 
	 */
	public synchronized  void addRun(double l) {
		try {
			//数据正确性判断
			if (!Double.isInfinite(l)&&!Double.isNaN(l)) {

				Cursor cursor  = DatabaseHelper.db.rawQuery("select upLoadTime,runNumber,upLoadRunNumber from RUNDATA where phone="+phone +" and date="+WalkData.parseLong(), null);
				if (cursor.getCount()==1) {
					PublicSrc.RUNDATA.setAllRun(l+getAllRun());
					PublicSrc.RUNDATA.setTodayRun(l+getTodayRun());
					if (getAllRun()<0) {
						PublicSrc.RUNDATA.setAllRun(0.00);
					}
					if (getTodayRun()<0) {
						PublicSrc.RUNDATA.setTodayRun(0.00);
					}
					PublicSrc.RUNDATA.update();
					MainHandleTool.sendMessage(MainHandleTool.UPDATE_RUN, null);
				}else{
					PublicSrc.RUNDATA = SqlParseLocalRunData(getPhone());
					PublicSrc.RUNDATA.setTodayRun(l);
					PublicSrc.RUNDATA.update();
					MainHandleTool.sendMessage(MainHandleTool.UPDATE_RUN, null);
				}
			}
		} catch (Exception e) {
			PublicSrc.RUNDATA = SqlParseLocalRunData(getPhone());
			addRun(l);
		}

	}
}
