package com.elefher.extendedclasses;

import android.app.Activity;

import com.elefher.customclasses.OnBoot;

public class SetOnBoot extends OnBoot{

	public SetOnBoot(Activity act){
		super(act);
		fileName("99overclock");
		setShell("#!/system/bin/sh");
		addCommand("\nthis is a test commandssssssssss");
		addCommand("\necho 1536000 > /sys/devices/system/cpu/cpu0/cpufreq/scaling_max_freq");
		setOnBoot();
	}
}