package com.elefher.implementation;

import com.elefher.extendedclasses.AlertProfiles;

import android.app.Activity;

public class CpuFreqProfile {
	
	Activity activity;

	public CpuFreqProfile(Activity activity) {
		this.activity = activity;
		AlertProfiles alertProfiles = new AlertProfiles(activity);
	}
}