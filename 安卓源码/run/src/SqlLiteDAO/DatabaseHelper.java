package SqlLiteDAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {  

	//类没有实例化,是不能用作父类构造器的参数,必须声明为静态  

	private static final String name = "data"; //数据库名称  
	public static final String USER = "USER"; //用户表名名称
	public static final String WALKDATA = "WALKDATA"; //步行表名名称
	public static SQLiteDatabase db ;

	private static final int version = 1; //数据库版本  

	public DatabaseHelper(Context context) {  

		//第三个参数CursorFactory指定在执行查询时获得一个游标实例的工厂类,设置为null,代表使用系统默认的工厂类  

		super(context, name, null, version);  
		db = this.getWritableDatabase();

	}  

	//初始化，只会在创建数据库的时候调用
	@Override  
	public void onCreate(SQLiteDatabase db) {  


		//步行资料
		db.execSQL(" create table WALKDATA (" + 
				"        phone bigint not null," + 
				"        date bigint not null," + 
				"        upLoadTime bigint," + 
				"        walkNumber bigint," + 
				"        upLoadWalkNumber bigint," + 
				"        primary key (phone, date)" + 
				"    )");

		//步行资料
		db.execSQL(" create table RUNDATA (" + 
				"        phone bigint not null," + 
				"        date bigint not null," + 
				"        upLoadTime bigint," + 
				"        runNumber DOUBLE," + 
				"        upLoadRunNumber bigint," + 
				"        primary key (phone, date)" + 
				"    )");

	}  

	@Override   
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {  
	}  
}  