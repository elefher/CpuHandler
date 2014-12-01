package com.elefher.abstractclasses;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ToggleButton;

public abstract class ToggleButtonService {
	
	Activity activity;
	public final ToggleButton toggleBttn;
	int RId;
	
	public ToggleButtonService(Activity act , int RIdBttn){
		activity = act;
		RId = RIdBttn;
		toggleBttn = (ToggleButton) activity.findViewById(RId);
		addListenerOnButton();		
	}
	
	public void addListenerOnButton() {
		setBttnState(activity);

		toggleBttn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if(toggleBttn.isChecked())
					on();
				else
					off();
			}
		});
	}
	
	public abstract void on();
	public abstract void off();
	public abstract void setBttnState(Activity act);
}