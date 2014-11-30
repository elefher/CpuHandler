package com.elefher.customclasses;

import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.content.Context;
import android.util.Log;

import com.elefher.utils.CpuUtils;
import com.elefher.utils.ReadFile;

public class CpuGpuFreqVoltages {

	/*private final static String cpufreq_sys_volts = "/sys/devices/system/cpu/cpufreq/vdd_table/vdd_levels";
	private final static String gpufreq_sys_volts = "/sys/devices/system/cpu/cpufreq/vdd_table/vdd_levels_GPU";
	* gpufreq_sys_volts is an unsued feature!!!
	*/

	public CpuGpuFreqVoltages() {
	}

	public static ArrayList<String> getCpuVoltages(Context cntx) {

		ArrayList<String> arrayStringList = CpuUtils
				.readStringArray2Cells(findFilePath("vdd_levels", cntx));

		return arrayStringList;
	}
	
	public static boolean hasCpuVoltages(Context cntx) {
		String str = findFilePath("vdd_levels", cntx);
		if(str != null && !str.isEmpty())
			return true;
		return false;
	}

	public static boolean setCpuVoltages(ArrayList<String> addSubVal, Context cntx) {
		if (addSubVal.isEmpty())
			return false;
		
		List<String> commands = new ArrayList<String>();
		String cpufreq_sys_volts = findFilePath("vdd_levels", cntx);
		try {
			commands.add("chmod 0644 " + cpufreq_sys_volts + "\n");
			for (String s : addSubVal) {
				commands.add("echo " + s + " > " + cpufreq_sys_volts + "\n");
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