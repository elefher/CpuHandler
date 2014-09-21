package com.elefher.customclasses;

import com.elefher.cpuhandler.R;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class SetVoltagesButton extends Button {

	Activity activity;
	LinearLayout mainLayout;
	
	public SetVoltagesButton(Activity act, String buttonTitle) {
		super(act);
		activity = act;
		
		setText(buttonTitle);
		setBackgroundResource(R.drawable.button1); 
		setWidth(500);
		setHeight(50);
		setTextColor(Color.rgb(80, 74, 75));
		setTypeface(Typeface.SANS_SERIF, 1);
	}
	
	public void positionButton(int rIdLayout){
		mainLayout = (LinearLayout) activity.findViewById(rIdLayout);
		
		LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		param.weight = 1f;
		param.topMargin = 10;
		param.gravity = Gravity.CENTER;
		
		LinearLayout buttonappend = new LinearLayout(activity);
		buttonappend.setLayoutParams(param);
		buttonappend.addView(this);
		
		mainLayout.addView(buttonappend);
	}
	
	public void enableButton(){
		setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getEditTexts();
				System.out.println("ttttttttt");
			}
		});
	}
	
	private void getEditTexts(){
		/*
		 * Count all items in main layout. There is "-1" because we don't want to 
		 * get the "Set Volts" button 
		 */				
		int mainLength = mainLayout.getChildCount() - 1;
		
		for(int i = 0; i < mainLength; i++){
			if(mainLayout.getChildAt(i) instanceof EditText){
				System.out.println("get val " + mainLayout.getChildAt(i));
			}
		}
		
	}
}