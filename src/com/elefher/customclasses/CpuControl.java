package com.elefher.customclasses;

import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONException;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.elefher.tab.Info;
import com.elefher.utils.ArrayUtils;
import com.elefher.utils.CpuUtils;
import com.elefher.utils.ReadFile;

public class CpuControl {

	/*private final static String cpufreq_sys_dir = "/sys/devices/system/cpu/cpu0/cpufreq/";
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
	private final static String screen_off_max_freq = cpufreq_sys_dir	+ "screen_off_max_freq";*/

	static Context context;

	public CpuControl(Context cntx) {
		context = cntx;
	}

	public static String[] getAvailableFreequencies(Context cntx) {
		String[] frequencies = CpuUtils.readStringArray(findFilePath("scaling_available_frequencies", cntx));

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

	public static String getCurrentMinCpuFreq(Context cntx) {
		return ReadFile.getStringOfFile(findFilePath("scaling_min_freq", cntx));
	}

	public static String getCurrentMaxCpuFreq(Context cntx) {
		return ReadFile.getStringOfFile(findFilePath("scaling_max_freq", cntx));
	}

	public static String getAvailableMinCpuFreq(Context cntx) {
		String[] availableF = getAvailableFreequencies(cntx);
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

	public static String getAvailableMaxCpuFreq(Context cntx) {
		String[] availableF = getAvailableFreequencies(cntx);
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

	public static boolean setCpuFrequencies(String min_freq, String max_freq, Context cntx) {
		int min = Integer.parseInt(min_freq);
		int max = Integer.parseInt(max_freq);
		if (min > max)
			return false;
		
		String scaling_min_freq = findFilePath("scaling_min_freq", cntx);
		String scaling_max_freq = findFilePath("scaling_max_freq", cntx);
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

	public static String getScreenOffMaxFreq(Context cntx) {
		return ReadFile.getStringOfFile(findFilePath("screen_off_max_freq", cntx));
	}

	public static boolean isScreenOffMaxFreqSupported(Context cntx) {
		String str = findFilePath("screen_off_max_freq", cntx);
		if(str != null && !str.isEmpty())
			return true;
		return false;
	}

	public static boolean setScreenOffMaxFreq(String maxSreenOffFreq, Context cntx) {

		String screen_off_max_freq = findFilePath("screen_off_max_freq", cntx);
		try {
			List<String> commands = new ArrayList<String>();

			/*
			 * Prepare permissions so that we can write
			 */
			commands.add("chmod 0664 " + screen_off_max_freq + "\n");

			commands.add("echo " + maxSreenOffFreq + " > "
					+ screen_off_max_freq + "\n");

			/*
			 * Set permissions in initial state
			 */
			commands.add("chmod 0444 " + screen_off_max_freq + "\n");

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