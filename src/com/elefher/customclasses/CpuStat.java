package com.elefher.customclasses;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.regex.Pattern;

import android.util.Log;

public class CpuStat {
	private static final String TAG = "CpuUsage";
	private RandomAccessFile statFile;
	private CpuInfo mCpuInfoTotal;
	private ArrayList<CpuInfo> mCpuInfoList;

	public CpuStat() {
	}

	public void update() {
		try {
			createFile();
			parseFile();
			closeFile();
		} catch (FileNotFoundException e) {
			statFile = null;
			Log.e(TAG, "cannot open /proc/stat: " + e);
		} catch (IOException e) {
			Log.e(TAG, "cannot close /proc/stat: " + e);
		}
	}

	private void createFile() throws FileNotFoundException {
		statFile = new RandomAccessFile("/proc/stat", "r");
	}

	public void closeFile() throws IOException {
		if (statFile != null)
			statFile.close();
	}

	private void parseFile() {
		if (statFile != null) {
			try {
				statFile.seek(0);
				String cpuLine = "";
				int cpuId = -1;
				do {
					cpuLine = statFile.readLine();
					parseCpuLine(cpuId, cpuLine);
					cpuId++;
				} while (cpuLine != null);
			} catch (IOException e) {
				Log.e(TAG, "Ops: " + e);
			}
		}
	}

	private void parseCpuLine(int cpuId, String cpuLine) {
		if (cpuLine != null && cpuLine.length() > 0) {
			String[] parts = cpuLine.split("[ ]+");
			String cpuLabel = "cpu";
			if (parts[0].indexOf(cpuLabel) != -1) {
				createCpuInfo(cpuId, parts);
			}
		} else {
			//Log.e(TAG, "unable to get cpu line");
		}
	}

	private void createCpuInfo(int cpuId, String[] parts) {
		if (cpuId == -1) {
			if (mCpuInfoTotal == null)
				mCpuInfoTotal = new CpuInfo();
			mCpuInfoTotal.update(parts);
		} else {
			if (mCpuInfoList == null)
				mCpuInfoList = new ArrayList<CpuInfo>();
			if (cpuId < mCpuInfoList.size())
				mCpuInfoList.get(cpuId).update(parts);
			else {
				CpuInfo info = new CpuInfo();
				info.update(parts);
				mCpuInfoList.add(info);
			}
		}
	}

	public int getCpuUsage(int cpuId) {
		update();
		int usage = 0;
		if (mCpuInfoList != null) {
			int cpuCount = mCpuInfoList.size();
			if (cpuCount > 0) {
				cpuCount--;
				if (cpuId == cpuCount) { // -1 total cpu usage
					usage = mCpuInfoList.get(0).getUsage();
				} else {
					if (cpuId <= cpuCount)
						usage = mCpuInfoList.get(cpuId).getUsage();
					else
						usage = -1;
				}
			}
		}
		return usage;
	}

	public int getTotalCpuUsage() {
		update();
		int usage = 0;
		if (mCpuInfoTotal != null)
			usage = mCpuInfoTotal.getUsage();
		return usage;
	}

	@Override
	public String toString() {
		update();
		StringBuffer buf = new StringBuffer();
		if (mCpuInfoTotal != null) {
			buf.append("Cpu Total : ");
			buf.append(mCpuInfoTotal.getUsage());
			buf.append("% \n");
		}
		if (mCpuInfoList != null) {
			for (int i = 0; i < mCpuInfoList.size(); i++) {
				CpuInfo info = mCpuInfoList.get(i);
				buf.append(" Cpu Core(" + i + ") : ");
				buf.append(info.getUsage());
				buf.append("% \n");
				info.getUsage();
			}
		}
		return buf.toString();
	}
	
	public ArrayList<Integer> toArrayList() {
		update();
		ArrayList<Integer> buf = new ArrayList<Integer>();
		if (mCpuInfoTotal != null) {
			buf.add(0, mCpuInfoTotal.getUsage());
		}
		if (mCpuInfoList != null) {
			for (int i = 0; i < mCpuInfoList.size(); i++) {
				CpuInfo info = mCpuInfoList.get(i);
				buf.add(i+1, info.getUsage());
				info.getUsage();
			}
		}
		return buf;
	}

	public class CpuInfo {
		private int mUsage;
		private long mLastTotal;
		private long mLastIdle;

		public CpuInfo() {
			mUsage = 0;
			mLastTotal = 0;
			mLastIdle = 0;
		}

		private int getUsage() {
			return mUsage;
		}

		public void update(String[] parts) {
			// the columns are:
			//
			// 0 "cpu": the string "cpu" that identifies the line
			// 1 user: normal processes executing in user mode
			// 2 nice: niced processes executing in user mode
			// 3 system: processes executing in kernel mode
			// 4 idle: twiddling thumbs
			// 5 iowait: waiting for I/O to complete
			// 6 irq: servicing interrupts
			// 7 softirq: servicing softirqs
			//
			long idle = Long.parseLong(parts[4], 10);
			long total = 0;
			boolean head = true;
			for (String part : parts) {
				if (head) {
					head = false;
					continue;
				}
				total += Long.parseLong(part, 10);
			}
			long diffIdle = idle - mLastIdle;
			long diffTotal = total - mLastTotal;
			mUsage = (int) ((float) (diffTotal - diffIdle) / diffTotal * 100);
			mLastTotal = total;
			mLastIdle = idle;
			//Log.i(TAG, "CPU total=" + total + "; idle=" + idle + "; usage=" + mUsage);
		}
	}	

	/**
	 * Gets the number of cores available in this device, across all processors.
	 * Requires: Ability to peruse the filesystem at "/sys/devices/system/cpu"
	 * 
	 * @return The number of cores, or 1 if failed to get result
	 */
	public static int getNumCores() {
		// Private Class to display only CPU devices in the directory listing
		class CpuFilter implements FileFilter {
			@Override
			public boolean accept(File pathname) {
				// Check if filename is "cpu", followed by a single digit number
				if (Pattern.matches("cpu[0-9]", pathname.getName())) {
					return true;
				}
				return false;
			}
		}

		try {
			// Get directory containing CPU info
			File dir = new File("/sys/devices/system/cpu/");
			// Filter to only list the devices we care about
			File[] files = dir.listFiles(new CpuFilter());
			// Log.d(TAG, "CPU Count: "+files.length);
			// Return the number of cores (virtual CPU devices)
			return files.length;
		} catch (Exception e) {
			// Print exception
			// Log.d(TAG, "CPU Count: Failed.");
			e.printStackTrace();
			// Default to return 1 core
			return 1;
		}
	}
}