package com.elefher.implementation;

import com.elefher.abstractclasses.ToggleButtonService;
import com.elefher.cpuhandler.R;
import com.elefher.customclasses.MiscServices;

import android.app.Activity;
import android.widget.Toast;

public class ForceFastCharge extends ToggleButtonService{

	Activity activity;

	public ForceFastCharge(Activity act) {
		super(act, R.id.fastchargebutton);
		activity = act;
	}

	@Override
	public void on() {
		if(MiscServices.setFastChargeState("1"))
			Toast.makeText(activity, "Apply Successfully !!", Toast.LENGTH_LONG).show();
		else
			Toast.makeText(activity, "Didn't Change !!", Toast.LENGTH_LONG).show();
		setBttnState();
	}

	@Override
	public void off() {
		if(MiscServices.setFastChargeState("0"))
			Toast.makeText(activity, "Apply Successfully !!", Toast.LENGTH_LONG).show();
		else
			Toast.makeText(activity, "Didn't Change !!", Toast.LENGTH_LONG).show();
		setBttnState();
	}

	@Override
	public void setBttnState() {
		if(MiscServices.getFastChargeState().equals("1"))
			toggleBttn.setChecked(true);
		else
			toggleBttn.setChecked(false);
	}
}