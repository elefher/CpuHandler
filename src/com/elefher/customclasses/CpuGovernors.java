package com.elefher.customclasses;

import java.util.Arrays;

import com.elefher.utils.CpuUtils;

public class CpuGovernors {

	private final static String cpufreq_sys_dir = "/sys/devices/system/cpu/cpu0/cpufreq/";
	private final static String scaling_governor = cpufreq_sys_dir
			+ "scaling_governor";
	private final static String scaling_available_governors = cpufreq_sys_dir
			+ "scaling_available_governors";

	public CpuGovernors() {

	}

	public static String[] getAvailableGovernors() {
		String[] governors = CpuUtils.readStringArray(scaling_available_governors);

		// In case does not exist governors return null
		if (governors == null) {
			return null;
		}

		return governors;
	}

}
