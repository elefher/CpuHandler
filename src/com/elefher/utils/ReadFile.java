package com.elefher.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class ReadFile {

	public ReadFile() {
		// TODO Auto-generated constructor stub
	}

	public static String getStringOfFile(String file) {
		String cpuFreq = "";
		RandomAccessFile reader;
		try {
			reader = new RandomAccessFile(file, "r");
			cpuFreq = reader.readLine();
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return cpuFreq;
	}

	public static ArrayList<String> getListOfFile(String fileToRead, String oBj, String searchPath, Context cntx) throws JSONException {
		JSONObject jsonObj = new JSONObject(loadJSONFromAssets(fileToRead, cntx));
		JSONArray jsonPaths = jsonObj.getJSONArray(oBj);

		int jsonPathsLength = jsonPaths.length();
		ArrayList<String> pathList = new ArrayList<String>();
		
		for (int i = 0; i < jsonPathsLength; i++) {
			// check if searchPath is equal with object  
			if(jsonPaths.getJSONObject(i).names().toString().toLowerCase().indexOf(searchPath) != -1){
				// Store every value from array to an ArrayList
				JSONArray jArr = jsonPaths.getJSONObject(i).getJSONArray(searchPath);
				int jsonArrLength = jArr.length();
				for (int l = 0; l < jsonArrLength; l++){
					pathList.add(jArr.getString(l));
				}
				break;
			}
		}
		//Log.d("out-->", pathList.toString());
		return pathList;
	}

	public static String loadJSONFromAssets(String file, Context cntx) {
		String json = null;
		try {
			InputStream is = cntx.getAssets().open(file);
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			json = new String(buffer, "UTF-8");
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
		return json;
	}
	
	public static String existPath(ArrayList<String> paths){
		int pathsSize = paths.size();
		for(int i = 0; i < pathsSize; i++){
			File file = new File(paths.get(i));
			if (file.exists()){
				return paths.get(i);
			}
		}
		return null;
	}

	public static String findFilePath(String file, Context cntx){
		String path = null;
		try {
			path = existPath(getListOfFile("data/paths.json", "path", file, cntx));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return path;
	}
}