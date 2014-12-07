package com.elefher.customclasses;

import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.elefher.utils.CpuUtils;
import com.elefher.utils.ReadFile;

/*
 * TODO this class needs optimization
 */

public class MiscServices {

	public MiscServices() {
	}

	public static String getFastChargeState(Activity act) {
		return ReadFile.getStringOfFile(findFilePath("force_fast_charge", act));
	}

	public static boolean setFastChargeState(String state, Context cntx) {
		String FORCE_FAST_CHARGE = findFilePath("force_fast_charge", cntx);
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
	
	public static String getSchedMcPowerSavingsState(Context cntx) {
		return ReadFile.getStringOfFile(findFilePath("sched_mc_power_savings", cntx));
	}
	
	public static boolean setSchedMcPowerSavingsState(String state, Context cntx) {
		String SCHED_MC_POWER_SAVINGS = findFilePath("sched_mc_power_savings", cntx);
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
	
	public static boolean setSweep2WakeState(String state, Context cntx) {
		if(state.equals("off")){
			state = "0";
		}else if(state.equals("sweep2wake+sweep2sleep")){
			state = "1";
		}else if(state.equals("sweep2sleep")){
			state = "2";
		}
		String SWEEP2WAKE = findFilePath("sweep2wake", cntx);
		try {
			List<String> commands = new ArrayList<String>();

			commands.add("chmod 0664 " + SWEEP2WAKE + "\n");
			commands.add("echo " + state + " > " + SWEEP2WAKE + "\n");			
			commands.add("chmod 0444 " + SWEEP2WAKE + "\n");
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
	
	public static String getSweep2WakeState(Context cntx) {
		return ReadFile.getStringOfFile(findFilePath("sweep2wake", cntx));
	}
	
	public static boolean setDoubleTap2Wake(String state, Context cntx) {
		String DOUBLETAP2WAKE = findFilePath("doubletap2wake", cntx);
		try {
			List<String> commands = new ArrayList<String>();

			commands.add("chmod 0664 " + DOUBLETAP2WAKE + "\n");
			commands.add("echo " + state + " > " + DOUBLETAP2WAKE + "\n");			
			commands.add("chmod 0444 " + DOUBLETAP2WAKE + "\n");
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
	
	public static String getDoubleTap2Wake(Context cntx) {
		return ReadFile.getStringOfFile(findFilePath("doubletap2wake", cntx));
	}

	public static String getSweep2DimState(Activity act) {
		return ReadFile.getStringOfFile(findFilePath("sweep2dim", act));
	}

	public static boolean setSweep2Dim(String state, Context cntx) {
		String SWEEP2DIM = findFilePath("sweep2dim", cntx);
		try {
			List<String> commands = new ArrayList<String>();

			commands.add("chmod 0664 " + SWEEP2DIM + "\n");
			commands.add("echo " + state + " > " + SWEEP2DIM + "\n");
			commands.add("chmod 0444 " + SWEEP2DIM + "\n");
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
	
	public static String findFilePath(String file, Context cntx){
		try {
			String path = ReadFile.existPath(ReadFile.getListOfFile("data/paths.json", "path", file, cntx));
			return path;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}