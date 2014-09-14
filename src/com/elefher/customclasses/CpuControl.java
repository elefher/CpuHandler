package com.elefher.customclasses;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.elefher.tab.Info;
import com.elefher.utils.ArrayUtils;
import com.elefher.utils.CpuUtils;
import com.elefher.utils.ReadFile;

public class CpuControl {

	private final static String cpufreq_sys_dir = "/sys/devices/system/cpu/cpu0/cpufreq/";
	private final static String scaling_min_freq = cpufreq_sys_dir
			+ "scaling_min_freq";
	private final static String cpuinfo_min_freq = cpufreq_sys_dir
			+ "cpuinfo_min_freq";
	private final static String scaling_max_freq = cpufreq_sys_dir
			+ "scaling_max_freq";
	private final static String cpuinfo_max_freq = cpufreq_sys_dir
			+ "cpuinfo_max_freq";
	private final static String scaling_cur_freq = cpufreq_sys_dir
			+ "scaling_cur_freq";
	private final static String cpuinfo_cur_freq = cpufreq_sys_dir
			+ "cpuinfo_cur_freq";
	private final static String scaling_available_freq = cpufreq_sys_dir
			+ "scaling_available_frequencies";
	private final static String scaling_stats_time_in_state = cpufreq_sys_dir
			+ "stats/time_in_state";

	private final static String ioscheduler = "/sys/block/mmcblk0/queue/scheduler";

	static Context context;

	public CpuControl(Context cntx) {
		context = cntx;
		// setCpuFrequencies("594000", "1026000");
	}

	public static String[] getAvailableFreequencies() {
		String[] frequencies = CpuUtils.readStringArray(scaling_available_freq);
		
		// In case does not exist frequencies return null
		if (frequencies == null) {
			return null;
		}

		// Convert String array to int array in order to sort it
		int[] minAvToInt = ArrayUtils.stringToIntArray(frequencies);
		// Sort the array as ascending
		Arrays.sort(minAvToInt);
		// Convert int array to string sorted
		frequencies = ArrayUtils.intToStringArray(minAvToInt);

		return frequencies;
	}

	public static String getCurrentMinCpuFreq() {
		return ReadFile.getStringOfFile(scaling_min_freq);
	}

	public static String getCurrentMaxCpuFreq() {
		return ReadFile.getStringOfFile(scaling_max_freq);
	}

	public static String getAvailableMinCpuFreq() {
		String[] availableF = getAvailableFreequencies();
		String minAvFreq = "";

		// Convert String array to int array in order to sort it
		int[] minAvToInt = ArrayUtils.stringToIntArray(availableF);
		// Sort the array as ascending
		Arrays.sort(minAvToInt);
		// Convert int array to string sorted
		availableF = ArrayUtils.intToStringArray(minAvToInt);

		minAvFreq = availableF[0];
		return minAvFreq;
	}

	public static String getAvailableMaxCpuFreq() {
		String[] availableF = getAvailableFreequencies();
		String maxAvFreq = "";

		// Convert String array to int array in order to sort it
		int[] maxAvToInt = ArrayUtils.stringToIntArray(availableF);
		// Sort the array as ascending
		Arrays.sort(maxAvToInt);
		// Convert int array to string sorted
		availableF = ArrayUtils.intToStringArray(maxAvToInt);

		maxAvFreq = availableF[availableF.length - 1];
		return maxAvFreq;
	}

	public static boolean setCpuFrequencies(String min_freq, String max_freq) {
		int min = Integer.parseInt(min_freq);
		int max = Integer.parseInt(max_freq);
		if(min > max)
			return false;

		try {
			List<String> commands = new ArrayList<String>();

			for (int i = 0; i < Info.cores; i++) {
				/*
				 * Prepare permissions so that we can write
				 */
				commands.add("chmod 0664 "
						+ scaling_min_freq.replace("cpu0", "cpu" + i) + "\n");
				commands.add("chmod 0664 "
						+ scaling_max_freq.replace("cpu0", "cpu" + i) + "\n");

				commands.add("echo " + min_freq + " > "
						+ scaling_min_freq.replace("cpu0", "cpu" + i) + "\n");
				commands.add("echo " + max_freq + " > "
						+ scaling_max_freq.replace("cpu0", "cpu" + i) + "\n");

				/*
				 * Set permissions in initial state
				 */
				commands.add("chmod 0444 "
						+ scaling_min_freq.replace("cpu0", "cpu" + i) + "\n");
				commands.add("chmod 0444 "
						+ scaling_max_freq.replace("cpu0", "cpu" + i) + "\n");
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
			Log.e("", ex.toString());
			Toast.makeText(context, "Error: " + ex.getMessage(),
					Toast.LENGTH_LONG).show();
			return false;
		}
	}
}
