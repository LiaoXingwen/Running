package entity;

import android.database.sqlite.SQLiteDatabase;
import SqlLiteDAO.DatabaseHelper;

public abstract class Entity {
	public abstract String query(SQLiteDatabase db);
	public abstract String save(SQLiteDatabase db);
	public abstract String delete(SQLiteDatabase db);
}
