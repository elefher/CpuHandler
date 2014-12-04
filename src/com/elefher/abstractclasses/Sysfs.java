package com.elefher.abstractclasses;

import java.util.Arrays;

import org.json.JSONException;

import android.content.Context;

import com.elefher.utils.ArrayUtils;
import com.elefher.utils.CpuUtils;
import com.elefher.utils.ReadFile;

public abstract class Sysfs {
	
	public static String[] getAvailableFreequencies(Context cntx, String findPath) {
		String[] frequencies = CpuUtils.readStringArray(findFilePath(findPath, cntx));
		
		// In case does not exist frequencies return null
		if (frequencies == null) {
			return null;
		}

		// Convert String array to int array in order to sort it
		int[] minAvToInt = ArrayUtils.stringToIntArray(frequencies);
		// Sort the array as ascending
		Arrays.sort(minAvToInt);
		// Convert int array to string sorted
		frequencies = ArrayUtils.intToStringArray(minAvToInt);

		return frequencies;
	}
	
	public static String getCurrentFrequency(Context cntx, String searchFile) {
		return ReadFile.getStringOfFile(findFilePath(searchFile, cntx));
	}
	
	public static String findFilePath(String file, Context cntx){
		try {
			String path = ReadFile.existPath(ReadFile.getListOfFile("data/paths.json", "path", file, cntx));
			return path;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public abstract boolean setFrequency(String freq);
	public abstract boolean set(String Val, String To);
	public abstract boolean setFrequencies(String minFreq, String maxFreq);
	public abstract String[] getCurrent(String searchFile);
	public abstract String returnTo(String val);
}