package com.elefher.implementation;

import java.util.Arrays;

import com.elefher.cpuhandler.R;
import com.elefher.customclasses.CpuControl;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class CpuFreqPicker implements OnSeekBarChangeListener, OnClickListener {

	TextView minFreqText, maxFreqText, minFreqSeekProgress,
			maxFreqSeekProgress;
	float curMinFreq, curMaxFreq;
	private SeekBar minBar, maxBar;
	private Button setCpu;
	String minAvailableFreq = CpuControl.getAvailableMinCpuFreq();
	String maxAvailableFreq = CpuControl.getAvailableMaxCpuFreq();
	public static String[] availableFreq = CpuControl.getAvailableFreequencies();
	int lengthAvailableFreq = availableFreq.length;
	int minAvailableFreqToInt = Integer.parseInt(minAvailableFreq);
	int maxAvailableFreqToInt = Integer.parseInt(maxAvailableFreq);
	
	static String currentMinFreq, currentMaxFreq;

	Activity activity;

	public CpuFreqPicker(Activity activity) {
		this.activity = activity;

		setCpu = (Button) activity.findViewById(R.id.setCpuButton);

		// Initialize current frequencies
		curMinFreq = Float.parseFloat(CpuControl.getCurrentMinCpuFreq());
		curMaxFreq = Float.parseFloat(CpuControl.getCurrentMaxCpuFreq());

		minFreqText = (TextView) activity.findViewById(R.id.minFreq);
		maxFreqText = (TextView) activity.findViewById(R.id.maxFreq);
		minFreqSeekProgress = (TextView) activity
				.findViewById(R.id.minFreqSeekProgress);
		maxFreqSeekProgress = (TextView) activity
				.findViewById(R.id.maxFreqSeekProgress);
		minBar = (SeekBar) activity.findViewById(R.id.minFreqSeek);
		maxBar = (SeekBar) activity.findViewById(R.id.maxFreqSeek);

		/*
		 * In this part of code initialize progress of seek bars The Idea is
		 * simple, seek bars take as max value the length of available
		 * frequencies. So, in order to initialize and set the progress well,
		 * just take the index of array which located the allowable frequency.
		 */
		minBar.setMax(lengthAvailableFreq - 1);
		maxBar.setMax(lengthAvailableFreq - 1);
		minBar.setProgress(Arrays.asList(availableFreq).indexOf(
				String.valueOf((int) curMinFreq)));
		maxBar.setProgress(Arrays.asList(availableFreq).indexOf(
				String.valueOf((int) curMaxFreq)));

		// Display initial Freq values as text
		minFreqText.setText("Min Freq: " + curMinFreq / 1000 + " MHz");
		maxFreqText.setText("Max Freq: " + curMaxFreq / 1000 + " MHz");

		minFreqSeekProgress.setText("Min: " + curMinFreq / 1000 + " MHz");
		maxFreqSeekProgress.setText("Max: " + curMaxFreq / 1000 + " MHz");

		minBar.setOnSeekBarChangeListener(this);
		maxBar.setOnSeekBarChangeListener(this);
		setCpu.setOnClickListener(this);
	}

	@Override
	public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
		/*
		 * The value of seek bar progress put as index in available frequencies
		 * array. So, in this way we can take the accurate frequency.
		 */
		float currentFreqToMHz = Float.parseFloat(availableFreq[arg1]) / 1000;
		// Update the text frequencies
		if (arg0 == minBar) {
			minFreqSeekProgress.setText("Min: " + currentFreqToMHz + " MHz");
		} else if (arg0 == maxBar) {
			maxFreqSeekProgress.setText("Max: " + currentFreqToMHz + " MHz");
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View arg0) {
		/*
		 * Set cpu frequencies
		 */
		if(minBar.getProgress() > maxBar.getProgress()){
			Toast.makeText(activity, "You cannot set min > max!!!",
					Toast.LENGTH_LONG).show();
			return;
		}
		boolean isChanged = false;
		String newMinFreq, newMaxFreq;
		newMinFreq = availableFreq[minBar.getProgress()];
		newMaxFreq = availableFreq[maxBar.getProgress()];
		CpuControl cpuControl = new CpuControl(activity);
		isChanged = cpuControl.setCpuFrequencies(newMinFreq, newMaxFreq);
		if (isChanged) {
			// Initialize current frequencies
			curMinFreq = Float.parseFloat(CpuControl.getCurrentMinCpuFreq());
			curMaxFreq = Float.parseFloat(CpuControl.getCurrentMaxCpuFreq());
			// Update the initial Freq values as text
			minFreqText.setText("Min Freq: " + curMinFreq / 1000 + " MHz");
			maxFreqText.setText("Max Freq: " + curMaxFreq / 1000 + " MHz");
			Toast.makeText(activity, "Cpu Frequencies Changed Successfully!!!",
					Toast.LENGTH_LONG).show();
		}else{
			Toast.makeText(activity, "Sorry But Something Went Wrong!!!",
					Toast.LENGTH_LONG).show();
		}
	}
}