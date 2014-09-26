package com.elefher.customclasses;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;

import com.elefher.utils.CpuUtils;

public class OnBoot {

	Activity activity;
	ArrayList<String> commands;
	String fileName, shell;
	private final static String SYSTEM_INITD = "/system/etc/init.d/";	
	
	public OnBoot(Activity act) {
		activity = act;
		commands = new ArrayList<String>();
	}

	public void fileName(String file) {
		fileName = file;
	}

	public void setShell(String shellName) {
		shell = shellName;
	}
	
	public void addCommand(String command) {
		commands.add(command);
	}

	/*
	 * Write commands into file and set it executable
	 */
	public boolean setOnBoot() {
		String command = "";
		ArrayList<String> write = new ArrayList<String>();

		if (commands.isEmpty() || shell.isEmpty())
			return false;

		command += shell;

		for (String c : commands) {
			command += c + "\n";
		}

		write.add("touch " + SYSTEM_INITD + fileName + " \n");
		write.add("echo " + command + " > " + SYSTEM_INITD + fileName + " \n");		
		write.add("chmod 755 " + SYSTEM_INITD + fileName + " \n");
		write.add("exit\n");
		System.out.println("ss1 " + write.get(0));
		try {

			Process p = Runtime.getRuntime().exec(new String[] { "su", "-c", "echo '#' > /system/etc/init.d/99overclock"});
			DataOutputStream dos = new DataOutputStream(p.getOutputStream());
			
			for (String s : write) {
				dos.writeBytes(s);
				dos.flush();
			}
			dos.close();

			p.waitFor();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

}
