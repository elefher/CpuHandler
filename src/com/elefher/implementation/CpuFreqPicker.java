package com.elefher.implementation;

import java.util.Arrays;

import com.cpu.tuner.R;
import com.elefher.customclasses.CpuControl;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class CpuFreqPicker implements OnSeekBarChangeListener, OnClickListener {

	TextView minFreqText, maxFreqText, minFreqSeekProgress,
			maxFreqSeekProgress, maxFreqScreenOffSeekProgress;
	public static float curMinFreq, curMaxFreq, curMaxScreenOffFreq;
	private SeekBar minBar, maxBar, maxScreenOffBar;
	private Button setCpu;
	String minAvailableFreq;
	String maxAvailableFreq;
	public static String[] availableFreq;
	int lengthAvailableFreq;
	int minAvailableFreqToInt;
	int maxAvailableFreqToInt;

	Activity activity;

	public CpuFreqPicker(Activity activity) {
		this.activity = activity;
		/*Initializes*/
		minAvailableFreq = CpuControl.getAvailableMinCpuFreq(activity);
		maxAvailableFreq = CpuControl.getAvailableMaxCpuFreq(activity);
		availableFreq = CpuControl.getAvailableFreequencies(activity);
		lengthAvailableFreq = availableFreq.length;
		minAvailableFreqToInt = Integer.parseInt(minAvailableFreq);
		maxAvailableFreqToInt = Integer.parseInt(maxAvailableFreq);
		
		setCpu = (Button) activity.findViewById(R.id.setCpuButton);
		// Initialize current frequencies
		curMinFreq = Float.parseFloat(CpuControl.getCurrentMinCpuFreq(activity));
		curMaxFreq = Float.parseFloat(CpuControl.getCurrentMaxCpuFreq(activity));
		
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
		/*
		 * Add max screen off frequency if supported by kernel
		 */
		if (CpuControl.isScreenOffMaxFreqSupported(activity)) {
			LinearLayout maxScreenOffFreqLayout = (LinearLayout) activity.findViewById(R.id.maxfreqscreenofflayout);
			maxScreenOffFreqLayout.setVisibility(View.VISIBLE);
			
			curMaxScreenOffFreq = Float.parseFloat(CpuControl.getScreenOffMaxFreq(activity));
			
			maxFreqScreenOffSeekProgress = (TextView) activity
					.findViewById(R.id.currentmaxscreenofffreq);

			maxScreenOffBar = (SeekBar) activity
					.findViewById(R.id.maxFreqScreenOffSeek);
			maxScreenOffBar.setMax(lengthAvailableFreq - 1);
			maxScreenOffBar.setProgress(Arrays.asList(availableFreq).indexOf(
					String.valueOf(Integer.parseInt(CpuControl
							.getScreenOffMaxFreq(activity)))));

			maxFreqScreenOffSeekProgress.setText(" "
					+ Float.parseFloat(CpuControl.getScreenOffMaxFreq(activity)) / 1000
					+ " MHz");

			maxScreenOffBar.setOnSeekBarChangeListener(this);
		}
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
		// Max screen off used only if supported by kernel
		else if (arg0 == maxScreenOffBar) {
			maxFreqScreenOffSeekProgress.setText(" " + currentFreqToMHz
					+ " MHz");
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
		if (minBar.getProgress() > maxBar.getProgress()) {
			Toast.makeText(activity, "You cannot set min > max!!!",
					Toast.LENGTH_LONG).show();
			return;
		}
		boolean isChanged = false;
		String newMinFreq, newMaxFreq, newMaxScreenOffFreq;
		newMinFreq = availableFreq[minBar.getProgress()];
		newMaxFreq = availableFreq[maxBar.getProgress()];
		isChanged = CpuControl.setCpuFrequencies(newMinFreq, newMaxFreq, activity);
		if (isChanged) {
			// Initialize current frequencies
			curMinFreq = Float.parseFloat(CpuControl.getCurrentMinCpuFreq(activity));
			curMaxFreq = Float.parseFloat(CpuControl.getCurrentMaxCpuFreq(activity));
			// Update the initial Freq values as text
			minFreqText.setText("Min Freq: " + curMinFreq / 1000 + " MHz");
			maxFreqText.setText("Max Freq: " + curMaxFreq / 1000 + " MHz");
			Toast.makeText(activity, "Cpu Frequencies Changed Successfully!!!",
					Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(activity, "Sorry But Something Went Wrong!!!",
					Toast.LENGTH_LONG).show();
		}

		if (CpuControl.isScreenOffMaxFreqSupported(activity)) {
			newMaxScreenOffFreq = availableFreq[maxScreenOffBar.getProgress()];
			if (CpuControl.setScreenOffMaxFreq(newMaxScreenOffFreq, activity)) {
				// Initialize current max screen off frequency
				curMaxScreenOffFreq = Float.parseFloat(CpuControl.getScreenOffMaxFreq(activity));
				// Update the initial max screen off frequency
				maxFreqScreenOffSeekProgress.setText(" " + curMaxScreenOffFreq / 1000 + " MHz");
				Toast.makeText(activity,
						"Cpu Max Screen Off Frequency Changed Successfully!!!",
						Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(activity,
						"Max Screen Off Frequency didn't Change!!!",
						Toast.LENGTH_LONG).show();
			}
		}
	}
}