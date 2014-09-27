package com.elefher.customclasses;

import java.io.DataOutputStream;
import java.io.File;
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

		if (commands.isEmpty() || shell.isEmpty())
			return false;

		command += shell;

		for (String c : commands) {
			command += c;
		}

		File f = new File(SYSTEM_INITD + fileName);
		if (!f.exists()) {
			changeFolderPermsToRW(SYSTEM_INITD, SYSTEM_INITD + fileName);
			createScript(command, SYSTEM_INITD , SYSTEM_INITD + fileName);
		} else {
			createScript(command, SYSTEM_INITD, SYSTEM_INITD + fileName);
		}
		return true;
	}

	private void changeFolderPermsToRW(String folder, String pathFileToCreate) {
		try {

			// Get Root
			Process p = Runtime.getRuntime().exec(CpuUtils.getSUbinaryPath());
			DataOutputStream dos = new DataOutputStream(p.getOutputStream());

			// Remount system folder as writable within the root process
			dos.writeBytes("mount -w -o remount,rw " + folder + "\n");
			dos.flush();

			// Create file
			dos.writeBytes("touch " + pathFileToCreate + "\n");
			dos.flush();

			// Set perms file
			dos.writeBytes("chmod 777 " + pathFileToCreate + "\n");
			dos.flush();

			// Set perms file
			dos.writeBytes("chown root:shell " + pathFileToCreate + "\n");
			dos.flush();

			// Remount system folder as Read-Only
			dos.writeBytes("mount -r -o remount,ro " + folder + "\n");
			dos.flush();

			// End process
			dos.writeBytes("exit\n");
			dos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void rmFile(String folder, String pathFileToCreate) {
		try {

			// Get Root
			Process p = Runtime.getRuntime().exec(CpuUtils.getSUbinaryPath());
			DataOutputStream dos = new DataOutputStream(p.getOutputStream());

			// Remount system folder as writable within the root process
			dos.writeBytes("mount -w -o remount,rw " + folder + "\n");
			dos.flush();

			// Create file
			dos.writeBytes("rm " + pathFileToCreate + "\n");
			dos.flush();

			// End process
			dos.writeBytes("exit\n");
			dos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createScript(String code, String folder, String filePath) {
		try {

			// Get Root
			Process p = Runtime.getRuntime().exec(CpuUtils.getSUbinaryPath());
			DataOutputStream dos = new DataOutputStream(p.getOutputStream());

			// Remount system folder as writable within the root process
			dos.writeBytes("mount -w -o remount,rw " + folder + "\n");
			dos.flush();

			// write code to the script file
			dos.writeBytes("echo \"" + code + "\" > " + filePath + "\n");
			dos.flush();
			
			// Remount system folder as writable within the root process
			dos.writeBytes("mount -w -o remount,rw " + folder + "\n");
			dos.flush();

			// End process
			dos.writeBytes("exit\n");
			dos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean checkExistsSetOnBootFile(String setOnBootFile){
		return true;
	}
}
