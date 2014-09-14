package com.elefher.customclasses;

import android.app.Activity;
import android.widget.TextView;

public class DisplayText {
	
	Activity activity;
	
	public DisplayText(Activity act){
		activity = act;
	}
	
	public static void updateText(Activity act, int rIdGovText, String descriptionString){
		TextView currentCpuString = (TextView) act.findViewById(rIdGovText);
		currentCpuString.setText(descriptionString);
	}
}