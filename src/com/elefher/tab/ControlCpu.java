package com.elefher.tab;

import com.cpu.tuner.R;
import com.elefher.extendedclasses.SetOnBootTask;
import com.elefher.implementation.CpuFreqPicker;
import com.elefher.implementation.CpuFreqProfile;
import com.elefher.implementation.CpuGovernorPicker;
import com.elefher.implementation.IOReadAheadPicker;
import com.elefher.implementation.IOSchedulerPicker;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.app.Activity;

public class ControlCpu extends Activity {

	/** Called when the activity is first created. */
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.controlcpu);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		CpuFreqPicker cpuFreqPicker = new CpuFreqPicker(this);
		CpuFreqProfile cpuFreqProfile = new CpuFreqProfile(this);
		CpuGovernorPicker cpuGovernorPicker = new CpuGovernorPicker(this);
		IOSchedulerPicker ioScheduler = new IOSchedulerPicker(this);
		IOReadAheadPicker ioReadAhead = new IOReadAheadPicker(this);
		SetOnBootTask setOnBootTask = new SetOnBootTask(this);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    // Respond to the action bar's Up/Home button
	    case android.R.id.home:
	        NavUtils.navigateUpFromSameTask(this);
	        return true;
	    }
	    return super.onOptionsItemSelected(item);
	}
}