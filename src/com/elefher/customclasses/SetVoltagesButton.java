package com.elefher.customclasses;

import com.elefher.cpuhandler.R;
import com.elefher.tab.Voltages;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
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
				
				/*
				 * Check if voltages changed
				 */
				if(CpuGpuFreqVoltages.setCpuVoltages(getEditTexts())){
					Toast.makeText(activity, "Greate the voltages has changed!!", Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(activity, "Somithing went wrong!!", Toast.LENGTH_LONG).show();
				}
			}
		});
	}
	
	private String getEditTexts(){
		String str = "";
		for (int i = 0; i < Voltages.lengthObj; i++){
			str += Voltages.setCpuVoltsList.get(i).strTitle + " " + Voltages.setCpuVoltsList.get(i).strValue + "\n";
		}
		return str;
	}
}