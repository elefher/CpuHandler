package com.elefher.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

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
}
