package com.elefher.utils;

import java.io.File;
import java.util.ArrayList;

public class ArrayUtils {

	public ArrayUtils(){
		
	}
	
	public static String existPaths(ArrayList<String> files) {
		for(String file : files){
			File f = new File(file);
			if (f.exists())
				return file;
		}
		return null;	
	}
	
	public static int[] stringToIntArray(String[] array){
		int arrayLength = array.length;
		int[] intArray = new int[arrayLength];
		
		for(int i = 0; i < arrayLength; i++){
			intArray[i] = Integer.parseInt(array[i]);
		}
		
		return intArray;
	}
	
	public static String[] intToStringArray(int[] array){
		int arrayLength = array.length;
		String[] stringArray = new String[arrayLength];
		
		for(int i = 0; i < arrayLength; i++){
			stringArray[i] = String.valueOf(array[i]);
		}
		
		return stringArray;
	}
}