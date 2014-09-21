package com.elefher.tab;

import java.util.ArrayList;

import com.elefher.cpuhandler.R;
import com.elefher.customclasses.CpuGpuFreqVoltages;
import com.elefher.customclasses.SetVoltagesButton;
import com.elefher.customclasses.UpDownVoltageButtons;
import com.elefher.extendedclasses.SetCpuVoltage;
import com.elefher.utils.CpuUtils;

import android.os.Bundle;
import android.app.Activity;

public class Voltages extends Activity {

	/** Called when the activity is first created. */

	String[] freqs, volts;
	public static ArrayList<SetCpuVoltage> setCpuVoltsList;
	public static int lengthObj;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.voltages);

		setCpuVoltages();
		
		setCpuVoltsList = new ArrayList<SetCpuVoltage>();
		
		// Length of cpuVolts objects
		lengthObj = volts.length;
		
		for(int i = 0; i < lengthObj; i++){
			setCpuVoltsList.add(new SetCpuVoltage(this));
			setCpuVoltsList.get(i).setValue(volts[i]);
			setCpuVoltsList.get(i).setTitle(freqs[i]);
			setCpuVoltsList.get(i).createReachEditText(R.id.voltagesEdit);
		}

		SetVoltagesButton setVoltBtn = new SetVoltagesButton(this, "Set Volts");
		setVoltBtn.positionButton(R.id.voltagesEdit);
		setVoltBtn.enableButton();
		
		UpDownVoltageButtons upDownOffsetBtns = new UpDownVoltageButtons(this);
	}

	private void setCpuVoltages() {
		ArrayList<String> cpuFreqsVolts = CpuGpuFreqVoltages.getCpuVoltages();
		int size = cpuFreqsVolts.size();
		freqs = new String[size];
		volts = new String[size];
		
		/*
		 *  Trim spaces at the end and start of string and after that
		 *  split string. Separate to freqs and volts
		 */		
		for(int i = 0; i < size; i++){
			String[] str = cpuFreqsVolts.get(i).trim().split("\\s+");
			freqs[i] = str[0];
			volts[i] = str[1];
		}
	}
}