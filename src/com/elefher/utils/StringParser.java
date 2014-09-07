package com.elefher.utils;

public class StringParser {
	public StringParser(){
		
	}
	
	public static String getIdOfString(String str){
		/*
		 * Attention these functions work only for string with the below format
		 * 1 str str: frequency MHz str: frequency MHz.
		 * It was made for profiles. 
		 */
		return str.split(" ")[0].toString();
	}
	
	public static String getFrequenciesOfString(String str){
		/*
		 * Attention these functions work only for string with the below format
		 * 1 str str: frequency MHz str: frequency MHz.
		 * It was made for profiles. 
		 */
		return str.split(" ")[3].toString() + " " + str.split(" ")[5].toString();
	}
}
