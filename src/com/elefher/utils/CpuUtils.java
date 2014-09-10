package com.elefher.utils;

import java.io.File;

public class CpuUtils {
	public CpuUtils(){
		
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
}
