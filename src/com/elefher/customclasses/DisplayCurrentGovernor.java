package com.elefher.customclasses;

import android.app.Activity;
import android.widget.TextView;

public class DisplayCurrentGovernor {
	
	Activity activity;
	
	public DisplayCurrentGovernor(Activity act){
		activity = act;
	}
	
	public static void updateCurrentGovernor(Activity act, int rIdGovText, String descriptionString){
		TextView currentCpuString = (TextView) act.findViewById(rIdGovText);
		currentCpuString.setText(descriptionString + CpuGovernors.getCurrentGovernor());
	}
}