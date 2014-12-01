package com.elefher.implementation;

import android.app.Activity;
import android.widget.Toast;

import com.elefher.abstractclasses.ToggleButtonService;
import com.cpu.tuner.R;
import com.elefher.customclasses.MiscServices;

public class IntelliPlug extends ToggleButtonService{

	Activity activity;
	public static String path;

	public IntelliPlug(Activity act) {
		super(act, R.id.intelliplugbutton);
		activity = act;
	}

	@Override
	public void on() {
		if(MiscServices.setMpDecisionIntelliPlugState(path, "1"))
			Toast.makeText(activity, "Apply Successfully !!", Toast.LENGTH_LONG).show();
		else
			Toast.makeText(activity, "Didn't Change !!", Toast.LENGTH_LONG).show();
		setBttnState(activity);
	}

	@Override
	public void off() {
		if(MiscServices.setMpDecisionIntelliPlugState(path, "0"))
			Toast.makeText(activity, "Apply Successfully !!", Toast.LENGTH_LONG).show();
		else
			Toast.makeText(activity, "Didn't Change !!", Toast.LENGTH_LONG).show();
		setBttnState(activity);
	}

	@Override
	public void setBttnState(Activity act) {
		if(MiscServices.getMpDecisionIntelliPlugState(path).equals("1"))
			toggleBttn.setChecked(true);
		else
			toggleBttn.setChecked(false);
	}
}