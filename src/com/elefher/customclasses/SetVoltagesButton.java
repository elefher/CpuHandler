package com.elefher.customclasses;

import java.util.ArrayList;

import com.cpu.handler.R;
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
		
		LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
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
				if(!"0".equals(getEditOffset())){
					offsetDoChanges();
				} else {
					editBtnsDoChanges();
				}
			}
		});
	}
	
	/*
	 * Create an offset string which is going to add or sub the voltages
	 * in all frequencies
	 */
	private String getEditOffset(){
		String offset = "";
		
		if(UpDownVoltageButtons.offestCount < 0){
			offset = String.valueOf(UpDownVoltageButtons.offset * UpDownVoltageButtons.offestCount);
		} else if(UpDownVoltageButtons.offestCount > 0){
			offset = "+" + String.valueOf(UpDownVoltageButtons.offset * UpDownVoltageButtons.offestCount);
		} else {
			offset = "0";
		}
		
		return offset;
	}
	
	private void offsetDoChanges(){
		/*
		 * Check if voltages changed. Also prepare getEditOffset to arraylist
		 * in order to fit in setCpuVoltages function.
		 */
		ArrayList<String> offset = new ArrayList<String>();
		offset.add(getEditOffset());
		if(CpuGpuFreqVoltages.setCpuVoltages(offset, activity)){
			Toast.makeText(activity, "Greate the voltages has changed!!", Toast.LENGTH_LONG).show();
			UpDownVoltageButtons.offestCount = 0;
		}else{
			if("0".equals(getEditOffset())){
				Toast.makeText(activity, "You didn't change voltages!!", Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(activity, "Somithing went wrong!!",
						Toast.LENGTH_LONG).show();
			}
		}
	}
	
	/*
	 * Get all freqs and volts from edit texts
	 */
	private ArrayList<String> getChanges(){
		ArrayList<String> storeNewFreqsVolts = new ArrayList<String>();
		for(int i = 0; i < Voltages.lengthObj; i++){
			String str = Voltages.setCpuVoltsList.get(i).strTitle + " " +Voltages.setCpuVoltsList.get(i).strValue;
			storeNewFreqsVolts.add(str);
		}
		return storeNewFreqsVolts;
	}
	
	/*
	 * Compare new freqs-volts with current freqs-volts and 
	 * set a new ArrayList. The new ArrayList is going to update the current
	 * volts with new values.
	 */
	private ArrayList<String> compareNewCurrentVoltsGetDif(ArrayList<String> newVolts){
		ArrayList<String> finalVolts = new ArrayList<String>();
		ArrayList<String> currentVolts = CpuGpuFreqVoltages.getCpuVoltages(activity);
				
		for(int i = 0; i < Voltages.lengthObj; i++){
			// replaceAll used in order to ignore all spaces
			if(!newVolts.get(i).replaceAll("\\s+","").equals(currentVolts.get(i).replaceAll("\\s+",""))){
				// Create string without ":"
				finalVolts.add(newVolts.get(i).replace(":", ""));
			}
		}
		
		return finalVolts;
	}
	
	/*
	 * This function called when edit button texts
	 * must do changes in volts
	 */
	private void editBtnsDoChanges(){
		ArrayList<String> finalVolts = compareNewCurrentVoltsGetDif(getChanges());
		if(finalVolts.isEmpty()){
			Toast.makeText(activity, "You didn't change voltages!!", Toast.LENGTH_LONG).show();
		} else {
			if(CpuGpuFreqVoltages.setCpuVoltages(finalVolts, activity)){
				Toast.makeText(activity, "Greate the voltages has changed!!", Toast.LENGTH_LONG).show();
				UpDownVoltageButtons.offestCount = 0;
			} else {
				Toast.makeText(activity, "Somithing went wrong!!", Toast.LENGTH_LONG).show();
			}
		}
	}
}