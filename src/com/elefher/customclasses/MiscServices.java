package com.elefher.customclasses;

import java.io.DataOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.elefher.utils.CpuUtils;
import com.elefher.utils.ReadFile;

public class MiscServices {

	public final static String FORCE_FAST_CHARGE = "/sys/kernel/fast_charge/force_fast_charge";
	public final static String SCHED_MC_POWER_SAVINGS = "/sys/devices/system/cpu/sched_mc_power_savings";
	public final static String MPDECISION = "/sys/module/msm_mpdecision/parameters/enabled";
	public final static String MPDECISION_KERNEL_BASED = "/sys/kernel/msm_mpdecision/conf/enabled";
	public final static String INTELLIPLUG = "/sys/module/intelli_plug/parameters/intelli_plug_active";
	public final static ArrayList<String> MPDECISION_PATHS = new ArrayList<String>();
	public final static ArrayList<String> INTELLIPLUG_PATHS = new ArrayList<String>();
	
	public MiscServices() {
		MPDECISION_PATHS.add(MPDECISION);
		MPDECISION_PATHS.add(MPDECISION_KERNEL_BASED);
		INTELLIPLUG_PATHS.add(INTELLIPLUG);
	}

	public static boolean exists(String file) {
		File f = new File(file);
		if (f.exists())
			return true;
		else
			return false;
	}

	public static String getFastChargeState() {
		return ReadFile.getStringOfFile(FORCE_FAST_CHARGE);
	}

	public static boolean setFastChargeState(String state) {
		try {
			List<String> commands = new ArrayList<String>();

			commands.add("chmod 0664 " + FORCE_FAST_CHARGE + "\n");
			commands.add("echo " + state + " > " + FORCE_FAST_CHARGE + "\n");			
			commands.add("chmod 0444 " + FORCE_FAST_CHARGE + "\n");
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
			Log.e("error: ", ex.toString());
			return false;
		}
	}
	
	public static boolean setMpDecisionIntelliPlugState(String path, String state) {
		try {
			List<String> commands = new ArrayList<String>();

			commands.add("chmod 0664 " + path + "\n");
			commands.add("echo " + state + " > " + path + "\n");			
			commands.add("chmod 0444 " + path + "\n");
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
			Log.e("error: ", ex.toString());
			return false;
		}
	}
	
	public static String getMpDecisionIntelliPlugState(String path) {
		return ReadFile.getStringOfFile(path);
	}
	
	public static String getSchedMcPowerSavingsState() {
		return ReadFile.getStringOfFile(SCHED_MC_POWER_SAVINGS);
	}
	
	public static boolean setSchedMcPowerSavingsState(String state) {
		try {
			List<String> commands = new ArrayList<String>();

			commands.add("chmod 0664 " + SCHED_MC_POWER_SAVINGS + "\n");
			commands.add("echo " + state + " > " + SCHED_MC_POWER_SAVINGS + "\n");			
			commands.add("chmod 0444 " + SCHED_MC_POWER_SAVINGS + "\n");
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
			Log.e("error: ", ex.toString());
			return false;
		}
	}
}