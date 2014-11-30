package com.elefher.implementation;

import com.cpu.tuner.R;
import com.elefher.customclasses.CpuGovernors;
import com.elefher.customclasses.DisplayText;
import com.elefher.extendedclasses.AlertGovernors;

import android.app.Activity;

public class CpuGovernorPicker {

	Activity activity;

	public CpuGovernorPicker(Activity act) {
		activity = act;
		
		/*
		 * Display governors in an alert dialog and user can 
		 * change a governor 
		 */
		AlertGovernors alertGovernors = new AlertGovernors(activity);
		
		/*
		 * Display the current governor
		 */
		DisplayText.updateText(activity, R.id.updatedCurrentGov, CpuGovernors.getCurrentGovernor(activity));
	}
}