package com.elefher.tab;

import com.elefher.cpuhandler.R;
import com.elefher.extendedclasses.SetOnBoot;
import com.elefher.implementation.CpuFreqPicker;
import com.elefher.implementation.CpuFreqProfile;
import com.elefher.implementation.CpuGovernorPicker;
import com.elefher.implementation.IOReadAheadPicker;
import com.elefher.implementation.IOSchedulerPicker;

import android.os.Bundle;
import android.app.Activity;

public class ControlCpu extends Activity {

	/** Called when the activity is first created. */
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.controlcpu);
		
		CpuFreqPicker cpuFreqPicker = new CpuFreqPicker(this);
		CpuFreqProfile cpuFreqProfile = new CpuFreqProfile(this);
		CpuGovernorPicker cpuGovernorPicker = new CpuGovernorPicker(this);
		IOSchedulerPicker ioScheduler = new IOSchedulerPicker(this);
		IOReadAheadPicker ioReadAhead = new IOReadAheadPicker(this);
		SetOnBoot setOnBoot = new SetOnBoot(this);
	}
}