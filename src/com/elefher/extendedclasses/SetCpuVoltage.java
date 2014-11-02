package com.elefher.extendedclasses;

import com.elefher.abstractclasses.EditTextButtons;
import com.cpu.tuner.R;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class SetCpuVoltage extends EditTextButtons {

	Activity activity;

	public SetCpuVoltage(Activity act) {
		super(act);
		activity = act;
	}

	public void setValue(String value) {
		strValue = value;
	}
	
	public void setTitle(String title){
		strTitle = title;
	}

	@Override
	public void createReachEditText(int rIdLinear) {
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.topMargin = 5;

		LinearLayout.LayoutParams paramsCenter = new LinearLayout.LayoutParams(
				0, LayoutParams.WRAP_CONTENT, 1);

		LinearLayout mainLayout = (LinearLayout) activity
				.findViewById(rIdLinear);

		LinearLayout horLayout = new LinearLayout(activity);
		horLayout.setLayoutParams(params);
		horLayout.setOrientation(0);

		titleText.setLayoutParams(paramsCenter);
		titleText.setText(strTitle);
		horLayout.addView(titleText);
		// Set text tile styles
		setTextViewStyles();

		btnMinus = new Button(activity);
		btnMinus.setText("-");

		btnPlus = new Button(activity);
		btnPlus.setText("+");
		
		// Set styles for buttons
		setButtonStyles();

		// EditText part
		setWidth(200);
		setInputType(InputType.TYPE_CLASS_NUMBER);
		setFocusableInTouchMode(false);
		setText(strValue);
		// Set styles for edit text
		setEditTextStyles();

		horLayout.addView(btnMinus);
		horLayout.addView(this);
		horLayout.addView(btnPlus);

		mainLayout.addView(horLayout);

		/*
		 * Handle minus and plus buttons
		 */
		btnMinus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int step = -250;
				String text = getText().toString();
				strValue = String.valueOf(Integer.parseInt(text) + step);
				setText(String.valueOf(Integer.parseInt(text) + step));
			}
		});

		btnPlus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int step = 250;
				String text = getText().toString();
				strValue = String.valueOf(Integer.parseInt(text) + step);
				setText(String.valueOf(Integer.parseInt(text) + step));
			}
		});
	}

	@Override
	public void setTextViewStyles() {
		titleText.setTypeface(Typeface.SANS_SERIF, 1);
		titleText.setTextSize(20);
		titleText.setTextColor(Color.rgb(86, 165, 236));
	}

	@Override
	public void setButtonStyles() {
		btnMinus.setBackgroundResource(R.drawable.button1);
		btnMinus.setLayoutParams(new LinearLayout.LayoutParams(80, 80));
		btnMinus.setTextColor(Color.rgb(80, 74, 75));
		btnMinus.setTypeface(Typeface.SANS_SERIF, 1);

		btnPlus.setBackgroundResource(R.drawable.button1);
		btnPlus.setLayoutParams(new LinearLayout.LayoutParams(80, 80));
		btnPlus.setTextColor(Color.rgb(80, 74, 75));
		btnPlus.setTypeface(Typeface.SANS_SERIF, 1);
	}

	@Override
	public void setEditTextStyles() {
		setTextColor(Color.rgb(237, 218, 116));
		setTextSize(15);
		setTypeface(Typeface.SANS_SERIF, 1);
	}
}