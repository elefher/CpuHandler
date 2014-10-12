package com.elefher.customclasses;

import java.io.DataOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import android.widget.Toast;

import com.elefher.tab.Info;
import com.elefher.utils.CpuUtils;
import com.elefher.utils.ReadFile;

public class MiscServices {

	public final static String FORCE_FAST_CHARGE = "/sys/kernel/fast_charge/force_fast_charge";

	public MiscServices() {
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
}