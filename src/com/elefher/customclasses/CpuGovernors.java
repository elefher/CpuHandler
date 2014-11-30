package com.elefher.customclasses;

import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.content.Context;
import android.util.Log;

import com.elefher.tab.Info;
import com.elefher.utils.CpuUtils;
import com.elefher.utils.ReadFile;

public class CpuGovernors {

	/*private final static String cpufreq_sys_dir = "/sys/devices/system/cpu/cpu0/cpufreq/";
	private final static String scaling_governor = cpufreq_sys_dir
			+ "scaling_governor";
	private final static String scaling_available_governors = cpufreq_sys_dir
			+ "scaling_available_governors";*/

	public CpuGovernors() {

	}

	public static String[] getAvailableGovernors(Context cntx) {
		String[] governors = CpuUtils.readStringArray(findFilePath("scaling_available_governors", cntx));

		// In case does not exist governors return null
		if (governors == null) {
			return null;
		}

		return governors;
	}
	
	public static String getCurrentGovernor(Context cntx){
		String currentGov = "";
		currentGov = ReadFile.getStringOfFile(findFilePath("scaling_governor", cntx));
		
		return currentGov;
	}
	
	public static boolean setGovernor(String governor, Context cntx){
		if(governor.isEmpty())
			return false;
		
		String scaling_governor = findFilePath("scaling_governor", cntx);
		try {
			List<String> commands = new ArrayList<String>();

			for (int i = 0; i < Info.cores; i++) {
				/*
				 * Prepare permissions so that we can write
				 */
				commands.add("chmod 0664 "
						+ scaling_governor.replace("cpu0", "cpu" + i) + "\n");

				commands.add("echo " + governor + " > "
						+ scaling_governor.replace("cpu0", "cpu" + i) + "\n");

				/*
				 * Set permissions in initial state
				 */
				commands.add("chmod 0444 "
						+ scaling_governor.replace("cpu0", "cpu" + i) + "\n");
			}

			commands.add("exit\n");

			Process p = Runtime.getRuntime().exec(CpuUtils.getSUbinaryPath());
			DataOutputStream dos = new DataOutputStream(p.getOutputStream());
			for (String command : commands) {
				dos.writeBytes(command);
				dos.flush();
			}
			dos.close();			
			
			p.waitFor();
			return true;
		} catch (Exception ex) {
			Log.e("", ex.toString() + " Error: " + ex.getMessage());
			return false;
		}
	}

	private static String findFilePath(String file, Context cntx){
		try {
			String path = ReadFile.existPath(ReadFile.getListOfFile("data/paths.json", "path", file, cntx));
			return path;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}