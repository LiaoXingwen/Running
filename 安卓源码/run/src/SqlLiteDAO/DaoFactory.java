package SqlLiteDAO;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import entity.User;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

public final class DaoFactory {
	SQLiteDatabase db;
	/**
	 * 初始化daosiseesion
	 * @param context
	 */
	public DaoFactory(Context context){
		//必须初始化数据库
		if (db==null) {
			db = new DatabaseHelper(context).db;
		}
	}

	public static User queryUser(String id){
		return null;
	}



}  





