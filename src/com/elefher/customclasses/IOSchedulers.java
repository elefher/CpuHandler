package com.elefher.customclasses;

import java.io.DataOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.elefher.tab.Info;
import com.elefher.utils.CpuUtils;

public class IOSchedulers {

	private final static String ioscheduler = "/sys/block/mmcblk0/queue/scheduler";
	private final static String SEARCH_IN_FOLDERS = "(loop|zram|dm-)[0-9]+";

	public IOSchedulers() {

	}

	public static String[] getAvailableIOSchedules() {
		String[] ioSchedulers = CpuUtils.readStringArray(ioscheduler);
		int SchedulesLength = ioSchedulers.length;
		if (ioSchedulers != null) {
			for (int i = 0; i < SchedulesLength; i++) {
				if (ioSchedulers[i].charAt(0) == '[') {
					ioSchedulers[i] = ioSchedulers[i].substring(1,
							ioSchedulers[i].length() - 1);
					break;
				}
			}
		}

		return ioSchedulers;
	}

	public static String getCurrentIOSchedule() {
		String[] IOS = CpuUtils.readStringArray(ioscheduler);
		String currentIO = null;
		int IOLength = IOS.length;

		if (IOS != null) {
			for (int i = 0; i < IOLength; i++) {
				if (IOS[i].charAt(0) == '[') {
					currentIO = IOS[i].substring(1, IOS[i].length() - 1);
					break;
				}
			}
		}
		return currentIO;
	}

	public static boolean setIOSchedule(String newSchedule) {
		String finalyIOScheduleString = CreateStringToSetIOSchedule(newSchedule);
		if (finalyIOScheduleString.equals("")) {
			return false;
		}

		try {
			List<String> commands = new ArrayList<String>();

			File folders = new File("/sys/block");
			File[] dirs = folders.listFiles();
			for (File dir : dirs) {
				if (dir.isDirectory()
						&& !dir.getName().matches(SEARCH_IN_FOLDERS)) {
					File schedulerFile = new File(dir, "queue/scheduler");
					if (schedulerFile.exists()) {
						commands.add("chmod 0644 "
								+ schedulerFile.getAbsolutePath() + "\n");
						commands.add("echo " + newSchedule + " > "
								+ schedulerFile.getAbsolutePath() + "\n");
						commands.add("chmod 0444 "
								+ schedulerFile.getAbsolutePath() + "\n");
					}
				}
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

	public static String CreateStringToSetIOSchedule(String updateSchedule) {
		String formatedUpdateSchedule = "[" + updateSchedule + "]";
		String[] availableSchedules = getAvailableIOSchedules();
		String newUpdatedScheduler = "";
		int schedulesLength = availableSchedules.length;

		if (availableSchedules != null) {
			for (int i = 0; i < schedulesLength; i++) {
				/*
				 * When finds the enabled I/O Scheduler then remove the "[]" and
				 * I/OScheduler disabled
				 */
				if (availableSchedules[i].charAt(0) == '[') {
					if (i == 0) {
						newUpdatedScheduler += availableSchedules[i].substring(
								1, availableSchedules[i].length() - 1);
					} else {
						newUpdatedScheduler += " "
								+ availableSchedules[i].substring(1,
										availableSchedules[i].length() - 1);
					}
				}

				/*
				 * When scheduler of file system is equal with the schedule
				 * which chose of the user then add "[]" around of the
				 * scheduler. Also if the first element is equal with wishful
				 * value then don't add a whitespace.
				 */
				if (availableSchedules[i].equals(updateSchedule)) {
					if (i == 0) {
						newUpdatedScheduler += formatedUpdateSchedule;
					} else {
						newUpdatedScheduler += " " + formatedUpdateSchedule;
					}
				} else {
					if (i == 0) {
						newUpdatedScheduler += availableSchedules[i];
					} else {
						newUpdatedScheduler += " " + availableSchedules[i];
					}
				}
			}
		}

		return newUpdatedScheduler;
	}
}