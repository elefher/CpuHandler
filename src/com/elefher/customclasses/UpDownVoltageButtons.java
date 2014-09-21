package com.elefher.customclasses;

import com.elefher.cpuhandler.R;
import com.elefher.extendedclasses.SetCpuVoltage;
import com.elefher.tab.Voltages;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class UpDownVoltageButtons {

	public UpDownVoltageButtons(Activity act){
		Button btnMinus = (Button) act.findViewById(R.id.downVolts);
		Button btnPlus = (Button) act.findViewById(R.id.upVolts);
		
		btnMinus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				int downVal = -250;
				changeEditValues(downVal);
				System.out.println("down");
				
			}
		});

		btnPlus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int upVal = 250;
				changeEditValues(upVal);
				System.out.println("up");
			}
		});
	}
	
	private void changeEditValues(int val){
		for (SetCpuVoltage sCV : Voltages.setCpuVoltsList){
			sCV.strValue = String.valueOf(Integer.parseInt(sCV.strValue)  + val);
			sCV.setText(sCV.strValue);
		}
	}
}