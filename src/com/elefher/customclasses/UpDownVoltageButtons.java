package com.elefher.customclasses;

import com.cpu.handler.R;
import com.elefher.extendedclasses.SetCpuVoltage;
import com.elefher.tab.Voltages;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class UpDownVoltageButtons {

	public static int offestCount = 0;
	public static int offset = 250;
	
	public UpDownVoltageButtons(Activity act){
		Button btnMinus = (Button) act.findViewById(R.id.downVolts);
		Button btnPlus = (Button) act.findViewById(R.id.upVolts);
		
		btnMinus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				int downVal = -offset;
				offestCount--;
				changeEditValuesOffset(downVal);								
			}
		});

		btnPlus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int upVal = offset;
				offestCount++;
				changeEditValuesOffset(upVal);				
			}
		});
	}
	
	private void changeEditValuesOffset(int val){
		for (SetCpuVoltage sCV : Voltages.setCpuVoltsList){
			sCV.strValue = String.valueOf(Integer.parseInt(sCV.strValue)  + val);
			sCV.setText(sCV.strValue);
		}
	}
}