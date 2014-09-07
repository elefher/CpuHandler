package com.elefher.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

	// Set player columns
	public static final String TABLE_PROFILES = "cpu_profiles";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_PROFILE = "profile";
	public static final String COLUMN_MINFREQ = "min";
	public static final String COLUMN_MAXFREQ = "max";

	private static final String DATABASE_NAME = "profiles.db";
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_PROFILES + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_PROFILE
			+ " text not null," + COLUMN_MINFREQ + " text not null, "
			+ COLUMN_MAXFREQ + " text not null);";

	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MySQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILES);
		onCreate(db);
	}

}