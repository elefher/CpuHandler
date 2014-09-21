package com.elefher.abstractclasses;

import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public abstract class EditTextButtons extends EditText {

	Activity activity;
	public TextView titleText;
	public Button btnMinus, btnPlus, button;
	public String strValue;

	public EditTextButtons(Activity act) {
		super(act);
		activity = act;
		titleText = new TextView(activity);
	}
	
	public abstract void createReachEditText(int rIdLinear, String title);
	public abstract void setTextViewStyles();
	public abstract void setButtonStyles();
	public abstract void setEditTextStyles();
}
