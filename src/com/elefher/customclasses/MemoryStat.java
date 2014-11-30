package com.elefher.customclasses;

import com.elefher.utils.ReadFile;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;

public class MemoryStat {
	MemoryInfo mi;
	ActivityManager activityManager;
	
	public MemoryStat(Context context) {
		mi = new MemoryInfo(); 
		activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
	}
	
	public static long getTotalMemory(){
		String strMem = ReadFile.getStringOfFile("/proc/meminfo");
		String [] strM = strMem.split("\\s+");
		
		long totalMem = Long.valueOf(strM[1]).longValue() / 1024; 
		return totalMem;
	}
	
	public long getUsageMemory(){
		activityManager.getMemoryInfo(mi);
		long usageMemory = mi.availMem / 1048576L;
		return usageMemory;
	}

}
