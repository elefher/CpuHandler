package com.elefher.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ServicesDataSource {
	// Database fields
	private SQLiteDatabase database;
	private MySQLiteServicesHelper dbHelper;
	private String[] allColumns = { MySQLiteServicesHelper.COLUMN_ID,
			MySQLiteServicesHelper.COLUMN_MINFREQ,
			MySQLiteServicesHelper.COLUMN_MAXFREQ,
			MySQLiteServicesHelper.COLUMN_GOVERNOR,
			MySQLiteServicesHelper.COLUMN_ENABLE };

	private static Cursor cursorSearch;
	private static Service updatedService;

	public ServicesDataSource(Context context) {
		dbHelper = new MySQLiteServicesHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Service createFreqService(String minFreq, String maxFreq) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_MINFREQ, minFreq);
		values.put(MySQLiteHelper.COLUMN_MAXFREQ, maxFreq);

		long insertId = database.insert(MySQLiteHelper.TABLE_PROFILES, null,
				values);
		Cursor cursor = database.query(MySQLiteHelper.TABLE_PROFILES,
				allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		Service newService = cursorToService(cursor);
		cursor.close();
		return newService;
	}

	public void deletePlayer(int id) {
		database.delete(MySQLiteServicesHelper.TABLE_PROFILES,
				MySQLiteServicesHelper.COLUMN_ID + " = '" + id + "'", null);
	}

	public List<Service> getService() {
		List<Service> services = new ArrayList<Service>();
		Service service = null;
		Cursor cursor = database.query(MySQLiteServicesHelper.TABLE_PROFILES,
				allColumns, null, null, null, null,
				MySQLiteServicesHelper.COLUMN_ID + " ASC");

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			service = cursorToService(cursor);
			services.add(service);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return services;
	}

	private Service cursorToService(Cursor cursor) {
		Service service = new Service();
		service.setId(cursor.getLong(0));
		service.setMinFreq(cursor.getString(1));
		service.setMaxFreq(cursor.getString(2));
		service.setGovernor(cursor.getString(3));
		service.setEnable(cursor.getString(4));
		return service;
	}

	public Service saveProfileData(Service dataService) {
		Service np = null;
		cursorSearch = selectCursorWithColumn(MySQLiteServicesHelper.COLUMN_ID);

		cursorSearch.moveToFirst();
		np = cursorToService(cursorSearch);
		cursorSearch.moveToNext();
		// make sure to close the cursor
		cursorSearch.close();

		if (np.getId() == dataService.getId()) {
			ContentValues values = new ContentValues();
			values.put(MySQLiteServicesHelper.COLUMN_MINFREQ, np.getMinFreq()
					+ dataService.getMinFreq());
			values.put(MySQLiteServicesHelper.COLUMN_MAXFREQ,
					dataService.getMaxFreq());
			values.put(MySQLiteServicesHelper.COLUMN_GOVERNOR,
					dataService.getGovernor());
			values.put(MySQLiteServicesHelper.COLUMN_ENABLE,
					dataService.getEnable());

			int check = database.update(MySQLiteServicesHelper.TABLE_PROFILES,
					values, MySQLiteServicesHelper.COLUMN_ID + "='"
							+ dataService.getId() + "'", null);

			if (check == 1) {
				cursorSearch = selectCursorWithColumn(MySQLiteServicesHelper.COLUMN_ID);

				cursorSearch.moveToFirst();
				this.updatedService = cursorToService(cursorSearch);
				cursorSearch.moveToNext();

				// make sure to close the cursor
				cursorSearch.close();
			}
		}
		return this.updatedService;
	}

	public Cursor selectCursorWithColumn(String columnDep) {
		Cursor cursor = database.query(MySQLiteServicesHelper.TABLE_PROFILES,
				allColumns, "id" + "='" + columnDep + "'", null, null, null,
				null);
		return cursor;
	}
}