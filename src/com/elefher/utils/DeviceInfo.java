package com.elefher.utils;

import android.app.Activity;

public class DeviceInfo extends Activity{
	
	public static String codeName = "CodeName: " + android.os.Build.DEVICE;
	public static String device = "Device: " + android.os.Build.MODEL + " ("+ android.os.Build.PRODUCT + ")";
	public static String kernel = "Kernel: Linux version " + System.getProperty("os.version") + 
			"(" + android.os.Build.VERSION.INCREMENTAL + ")" + "\nos: " + android.os.Build.HOST +
			" " + android.os.Build.VERSION.RELEASE;
	public static String brand = "Brand: " + android.os.Build.BRAND;
	
	public DeviceInfo(){
	}
}