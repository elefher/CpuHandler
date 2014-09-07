package com.elefher.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class ProfilesDataSource {

	// Database fields
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
			MySQLiteHelper.COLUMN_PROFILE, MySQLiteHelper.COLUMN_MINFREQ,
			MySQLiteHelper.COLUMN_MAXFREQ};

	private static Cursor cursorSearch;
	private static Profile updatedProfile;

	public ProfilesDataSource(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Profile createProfile(String profile, String minFreq, String maxFreq) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_PROFILE, profile);
		values.put(MySQLiteHelper.COLUMN_MINFREQ, minFreq);
		values.put(MySQLiteHelper.COLUMN_MAXFREQ, maxFreq);
		
		long insertId = database.insert(MySQLiteHelper.TABLE_PROFILES, null,
				values);
		Cursor cursor = database.query(MySQLiteHelper.TABLE_PROFILES,
				allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		Profile newProfile = cursorToProfile(cursor);
		cursor.close();
		return newProfile;
	}

	public void deletePlayer(int id) {
		database.delete(MySQLiteHelper.TABLE_PROFILES, MySQLiteHelper.COLUMN_ID
				+ " = '" + id + "'", null);
	}

	public List<Profile> getAllProfiles() {
		List<Profile> profiles = new ArrayList<Profile>();
		Profile profile = null;
		Cursor cursor = database.query(MySQLiteHelper.TABLE_PROFILES,
				allColumns, null, null, null, null, MySQLiteHelper.COLUMN_ID
						+ " ASC");

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			profile = cursorToProfile(cursor);
			profiles.add(profile);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return profiles;
	}

	private Profile cursorToProfile(Cursor cursor) {
		Profile profile = new Profile();
		profile.setId(cursor.getLong(0));
		profile.setProfile(cursor.getString(1));
		profile.setMinFreq(cursor.getString(2));
		profile.setMaxFreq(cursor.getString(3));
		return profile;
	}

	public Profile saveProfileData(Profile dataProfile) {
		Profile np = null;
		cursorSearch = selectCursorWithColumn(MySQLiteHelper.COLUMN_PROFILE);

		cursorSearch.moveToFirst();
		np = cursorToProfile(cursorSearch);
		cursorSearch.moveToNext();
		// make sure to close the cursor
		cursorSearch.close();
		
		if (np.getProfile().equals(dataProfile.getProfile())) {
			ContentValues values = new ContentValues();
			values.put(MySQLiteHelper.COLUMN_MINFREQ,
					np.getMinFreq() + dataProfile.getMinFreq());
			values.put(MySQLiteHelper.COLUMN_MAXFREQ,
					dataProfile.getMaxFreq());
			
			int check = database.update(
					MySQLiteHelper.TABLE_PROFILES,
					values,
					MySQLiteHelper.COLUMN_PROFILE + "='"
							+ dataProfile.getProfile() + "'", null);

			if (check == 1) {
				cursorSearch = selectCursorWithColumn(MySQLiteHelper.COLUMN_PROFILE);

				cursorSearch.moveToFirst();
				this.updatedProfile = cursorToProfile(cursorSearch);
				cursorSearch.moveToNext();

				// make sure to close the cursor
				cursorSearch.close();
			}
		}
		return this.updatedProfile;
	}

	public Cursor selectCursorWithColumn(String columnDep) {
		Cursor cursor = database.query(MySQLiteHelper.TABLE_PROFILES,
				allColumns, "profile" + "='"+columnDep+"'", null, null, null, null);
		return cursor;
	}
}