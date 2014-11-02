package com.elefher.implementation;

import com.cpu.tuner.R;
import com.elefher.customclasses.DisplayText;
import com.elefher.customclasses.IOSchedulers;
import com.elefher.extendedclasses.AlertIOSchedulers;

import android.app.Activity;

public class IOSchedulerPicker {

	Activity activity;
	
	public IOSchedulerPicker(Activity act){
		activity = act;
		
		/*
		 * Display i/o schedules in an alert dialog
		 */
		AlertIOSchedulers alertIOSchedules = new AlertIOSchedulers(activity);
		
		/*
		 * Display the current i/o schedule
		 */
		DisplayText.updateText(activity, R.id.updateioschedule, IOSchedulers.getCurrentIOSchedule());
	}
}
