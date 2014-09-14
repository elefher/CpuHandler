package com.elefher.implementation;

import com.elefher.cpuhandler.R;
import com.elefher.customclasses.DisplayCurrentGovernor;
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
		DisplayCurrentGovernor.updateCurrentGovernor(activity, R.id.updatedCurrentGov, "");
	}
}