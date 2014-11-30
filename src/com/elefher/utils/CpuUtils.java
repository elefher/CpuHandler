package com.elefher.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CpuUtils {
	public CpuUtils() {

	}

	public static String getSUbinaryPath() {
		String s = "/system/bin/su";
		File f = new File(s);
		if (f.exists()) {
			return s;
		}
		s = "/system/xbin/su";
		f = new File(s);
		if (f.exists()) {
			return s;
		}
		return null;
	}

	public static String[] readStringArray(String filename) {
		String line = ReadFile.getStringOfFile(filename);
		if (line != null) {
			return line.split(" ");
		}
		return null;
	}

	public static ArrayList<String> readStringArray2Cells(String filename) {
		BufferedReader buffered_reader = null;
		ArrayList<String> strLines = new ArrayList<String>();
		try {
			buffered_reader = new BufferedReader(new FileReader(filename));
			String line;

			while ((line = buffered_reader.readLine()) != null) {
				strLines.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (buffered_reader != null){
					buffered_reader.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		return strLines;
	}
}
