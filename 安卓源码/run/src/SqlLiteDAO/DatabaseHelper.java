package SqlLiteDAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {  

	//��û��ʵ����,�ǲ����������๹�����Ĳ���,��������Ϊ��̬  

	private static final String name = "data"; //���ݿ�����  
	public static final String USER = "USER"; //�û���������
	public static final String WALKDATA = "WALKDATA"; //���б�������
	public static SQLiteDatabase db ;

	private static final int version = 1; //���ݿ�汾  

	public DatabaseHelper(Context context) {  

		//����������CursorFactoryָ����ִ�в�ѯʱ���һ���α�ʵ���Ĺ�����,����Ϊnull,����ʹ��ϵͳĬ�ϵĹ�����  

		super(context, name, null, version);  
		db = this.getWritableDatabase();

	}  

	//��ʼ����ֻ���ڴ������ݿ��ʱ�����
	@Override  
	public void onCreate(SQLiteDatabase db) {  


		//��������
		db.execSQL(" create table WALKDATA (" + 
				"        phone bigint not null," + 
				"        date bigint not null," + 
				"        upLoadTime bigint," + 
				"        walkNumber bigint," + 
				"        upLoadWalkNumber bigint," + 
				"        primary key (phone, date)" + 
				"    )");

		//��������
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