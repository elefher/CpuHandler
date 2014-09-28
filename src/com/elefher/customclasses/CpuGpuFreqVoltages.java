package com.elefher.customclasses;

import java.io.DataOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.util.Log;

import com.elefher.utils.CpuUtils;
import com.elefher.utils.ReadFile;

public class CpuGpuFreqVoltages {

	private final static String cpufreq_sys_volts = "/sys/devices/system/cpu/cpufreq/vdd_table/vdd_levels";
	private final static String gpufreq_sys_volts = "/sys/devices/system/cpu/cpufreq/vdd_table/vdd_levels_GPU";

	public CpuGpuFreqVoltages() {
	}

	public static ArrayList<String> getCpuVoltages() {

		ArrayList<String> arrayStringList = CpuUtils
				.readStringArray2Cells(cpufreq_sys_volts);

		return arrayStringList;
	}
	
	public static boolean hasCpuVoltages() {
		File f = new File(cpufreq_sys_volts);
		if(f.exists())
			return true;
		else
			return false;
	}

	public static boolean setCpuVoltages(ArrayList<String> addSubVal) {
		if (addSubVal.isEmpty())
			return false;

		List<String> commands = new ArrayList<String>();
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
}
