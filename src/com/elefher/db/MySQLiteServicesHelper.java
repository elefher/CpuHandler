package com.elefher.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteServicesHelper extends SQLiteOpenHelper {

	// Set services columns
	public static final String TABLE_PROFILES = "services";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_GOVERNOR = "governor";
	public static final String COLUMN_MINFREQ = "min";
	public static final String COLUMN_MAXFREQ = "max";
	public static final String COLUMN_ENABLE = "enable";

	private static final String DATABASE_NAME = "services.db";
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_PROFILES + "(" + COLUMN_ID	+ " integer primary key autoincrement, " + 
			" text not null," + COLUMN_MINFREQ + " text not null, "
			+ COLUMN_MAXFREQ + " text not null," + COLUMN_GOVERNOR + " text, " + COLUMN_ENABLE +" INTEGER);";

	public MySQLiteServicesHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MySQLiteServicesHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILES);
		onCreate(db);
	}
}