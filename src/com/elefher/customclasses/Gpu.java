package com.elefher.customclasses;

import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.elefher.abstractclasses.Sysfs;
import com.elefher.utils.CpuUtils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class Gpu extends Sysfs {

	Context context;

	public Gpu(Context cntx) {
		context = cntx;
	}

	@Override
	public boolean setFrequency(String freq) {
		if (freq.isEmpty() || freq == null)
			return false;

		String gpu_file = findFilePath("max_gpuclk", context);
		try {
			List<String> commands = new ArrayList<String>();
			/*
			 * Prepare permissions so that we can write
			 */
			commands.add("chmod 0664 " + gpu_file + "\n");

			commands.add("echo " + freq + " > " + gpu_file + "\n");

			/*
			 * Set permissions in initial state
			 */
			commands.add("chmod 0444 " + gpu_file + "\n");

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

	@Override
	public boolean setFrequencies(String minFreq, String maxFreq) {
		// TODO Auto-generated method stub
		return false;
	}

	public String getCurrentFrequency() {
		return getCurrentFrequency(context, "max_gpuclk");
	}

	@Override
	public String returnTo(String val) {
		if (val.isEmpty() || val == null)
			return null;
		int valToInt = new Integer(val);
		return String.valueOf(valToInt / 1000000) + " Mhz";
	}

	@Override
	public String[] getCurrent(String findPath) {
		String[] content = CpuUtils.readStringArray(findFilePath(findPath,
				context));

		// In case does not exist content return null
		if (content == null) {
			return null;
		}
		
		return content;
	}

	@Override
	public boolean set(String Val, String To) {
		if (Val.isEmpty() || Val == null)
			return false;

		String file = findFilePath(To, context);
		try {
			List<String> commands = new ArrayList<String>();
			/*
			 * Prepare permissions so that we can write
			 */
			commands.add("chmod 0664 " + file + "\n");

			commands.add("echo " + Val + " > " + file + "\n");

			/*
			 * Set permissions in initial state
			 */
			commands.add("chmod 0444 " + file + "\n");

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