package com.elefher.implementation;

import com.elefher.extendedclasses.AlertGovernors;

import android.app.Activity;

public class CpuGovernorPicker {

	Activity activity;

	public CpuGovernorPicker(Activity act) {
		activity = act;
		AlertGovernors governorAlert = new AlertGovernors(activity);
	}
}