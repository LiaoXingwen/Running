package entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.baidu.a.a.a.c;
import com.lxw.baidumapdemo.MainHandleTool;

import servicekit.ManageService;
import tools.PublicSrc;
import tools.UploadData;

import android.R.string;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import SqlLiteDAO.DatabaseHelper;

/**   
 *    
 * ��Ŀ���ƣ�Running   
 * �����ƣ��ܲ�����   
 * ��������   
 * �����ˣ�������
 * ����ʱ�䣺2016��11��21�� ����5:01:25   
 * �޸��ˣ�lxw   
 * �޸�ʱ�䣺2016��11��21�� ����5:01:25   
 * �޸ı�ע��   
 * @version 1.0.0   
 *    
 */
public class WalkData {
	private WalkDataPK walkDataPK ;//�û�id
	private long upLoadTime = 0; 
	private long walkNumber = 0;
	private long allNumber = 0;
	public long getAllNumber() {
		return allNumber;
	}
	public void setAllNumber(long allNumber) {
		this.allNumber = allNumber;
	}

	private long upLoadWalkNumber = 0;
	public WalkDataPK getWalkDataPK() {
		return walkDataPK;
	}
	public void setWalkDataPK(WalkDataPK walkDataPK) {
		this.walkDataPK = walkDataPK;
	}
	public long getUpLoadTime() {
		return upLoadTime;
	}
	public void setUpLoadTime(long upLoadTime) {
		this.upLoadTime = upLoadTime;
	}
	public synchronized long getWalkNumber() {
		return walkNumber;
	}
	public void setWalkNumber(long walkNumber) {
		this.walkNumber = walkNumber;
	}
	public long getUpLoadWalkNumber() {
		return upLoadWalkNumber;
	}
	public void setUpLoadWalkNumber(long upLoadWalkNumber) {
		this.upLoadWalkNumber = upLoadWalkNumber;
	}
	public WalkData(WalkDataPK walkDataPK, long upLoadTime, long walkNumber,
			long upLoadWalkNumber) {
		this.walkDataPK = walkDataPK;
		this.upLoadTime = upLoadTime;
		this.walkNumber = walkNumber;
		this.upLoadWalkNumber = upLoadWalkNumber;
	}
	public WalkData() {
		if (DatabaseHelper.db == null) {
			new DatabaseHelper(PublicSrc.context);
		}
	}
	public String querySQL() {
		return "select upLoadTime,walkNumber,upLoadWalkNumber from "+DatabaseHelper.WALKDATA +" where phone="+getWalkDataPK().getUid() +" and date="+setDate();
	}
	public String insert() {
		return "insert into "+DatabaseHelper.WALKDATA+" values ("+walkDataPK.getUid()+" ,"+walkDataPK.getDate()+", "+getUpLoadTime()+", "+getWalkNumber()+", "+getUpLoadWalkNumber()+") ";
	}

	public String update() {
		//		UPDATE ������ SET ������ = ��ֵ WHERE ������ = ĳֵ
		return "update "+DatabaseHelper.WALKDATA+" set  upLoadTime ="+getUpLoadTime()+", walkNumber="+getWalkNumber()+", upLoadWalkNumber="+getUpLoadWalkNumber()+
				" where phone = "+getWalkDataPK().getUid() +" and date = "+getWalkDataPK().getDate();
	}
	public String delete() {
		//		DELETE FROM ������ WHERE ������ = ֵ
		return "delete from "+DatabaseHelper.WALKDATA +" where phone = "+getWalkDataPK().getUid() +" and date = "+getWalkDataPK().getDate();
	}




	public long addWalk(long num) {
		walkNumber = walkNumber+num;
		allNumber = allNumber+num;
		save();
		walkUpdate();
		return walkNumber ; 
	}


	public  synchronized void save() {
		Cursor cursor  = DatabaseHelper.db.rawQuery(querySQL(), null);

		if (cursor.getCount()==1) {
			DatabaseHelper.db.execSQL(update());
		}else{
			setWalkNumber(0);
			DatabaseHelper.db.execSQL(insert());
		}
	}

	public String delete(SQLiteDatabase db) {
		return null;
	}



	public  synchronized  void query() {
		
		Cursor cursor  = DatabaseHelper.db.rawQuery(querySQL(), null);
		if (cursor.getCount()==1) {
			cursor.moveToLast();
			setUpLoadTime(cursor.getLong(0));
			setWalkNumber(cursor.getLong(1));
			setUpLoadWalkNumber(cursor.getLong(2));
			Cursor cursor1  = DatabaseHelper.db.rawQuery("select sum(walkNumber) from WALKDATA where phone="+getWalkDataPK().getUid(), null);
			cursor1.moveToLast();
			setAllNumber(cursor1.getLong(0));
		}else{
			DatabaseHelper.db.execSQL(insert());
		}
		walkUpdate();
	}


	public long setDate() {
		long ptime = parseLong();
		getWalkDataPK().setDate(ptime);
		return ptime;
	}

	/**
	 * ��ʱ��ת��0���long
	 * 
	 * @param @param time
	 * @param @return
	 * @param @throws ParseException    
	 * @return long   
	 * 
	 */
	public static long parseLong()  {

		try {
			Calendar calendar = Calendar.getInstance();
			int year = calendar.get(calendar.YEAR);
			int m = calendar.get(calendar.MONTH) ;
			int day = calendar.get(calendar.DAY_OF_MONTH);
			String format = "yyyy-MM-dd HH:mm:ss";
			String timesString = year +"-"+m +"-"+day +" 00:00:00";
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			return dateFormat.parse(timesString).getTime();
		} catch (ParseException e) {
		}
		return 0;
	} 

	private  synchronized void walkUpdate() {
		
		if (Math.random()*10>9) {
			new UploadData().execute("uoloadWalk","walk",String.valueOf(getWalkNumber()));
		}
		
		MainHandleTool.sendMessage(MainHandleTool.UPDATE_ALLWALK, new String[]{String.valueOf(getAllNumber())});
		MainHandleTool.sendMessage(MainHandleTool.UPDATE_TODAYWALK, new String[]{String.valueOf(getWalkNumber())});
		ManageService.updatdNotification("���첽��"+walkNumber+"��");
	}


}
