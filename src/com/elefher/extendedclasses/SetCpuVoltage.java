package com.elefher.extendedclasses;

import com.elefher.abstractclasses.EditTextButtons;
import com.elefher.cpuhandler.R;

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

	@Override
	public void createReachEditText(int rIdLinear, String title) {
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
		titleText.setText(title);
		horLayout.addView(titleText);
		// Set text tile styles
		setTextViewStyles();

		btnMinus = new Button(activity);
		btnMinus.setText("-");
		btnMinus.setWidth(30);
		btnMinus.setHeight(30);

		btnPlus = new Button(activity);
		btnPlus.setText("+");
		btnPlus.setWidth(30);
		btnPlus.setHeight(30);
		// Set styles for buttons
		setButtonStyles();

		// EditText part
		setWidth(200);
		setInputType(InputType.TYPE_CLASS_NUMBER);
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
				int step = -100;
				String text = getText().toString();
				setText(String.valueOf(Integer.parseInt(text) + step));
			}
		});

		btnPlus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int step = 100;
				String text = getText().toString();
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
		btnMinus.setTextColor(Color.rgb(80, 74, 75));
		btnMinus.setTypeface(Typeface.SANS_SERIF, 1);

		btnPlus.setBackgroundResource(R.drawable.button1);
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