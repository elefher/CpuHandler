package com.elefher.extendedclasses;

import android.app.Activity;

import com.elefher.customclasses.OnBoot;

public class SetOnBoot {

	public SetOnBoot(Activity act){
		OnBoot onBoot = new OnBoot(act);
		onBoot.fileName("99overclock");
		onBoot.setShell("#!/system/bin/sh");
		onBoot.addCommand("this is a test command");
		onBoot.addCommand("echo 1536000 > /sys/devices/system/cpu/cpu0/cpufreq/scaling_max_freq");
		onBoot.setOnBoot();
	}
}