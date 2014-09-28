package com.elefher.extendedclasses;

import android.app.Activity;
import android.content.Context;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.elefher.abstractclasses.CustomCheckBoxes;
import com.elefher.cpuhandler.R;
import com.elefher.customclasses.CpuGovernors;
import com.elefher.customclasses.OnBoot;
import com.elefher.implementation.CpuFreqPicker;

public class SetOnBootTask extends CustomCheckBoxes {

	CheckBox freqBox, govBox/*, ioBox, bufferBox*/;
	Activity activity;

	public SetOnBootTask(Activity act) {
		super(act);
		activity = act;

		// Initialize checkboxes
		freqBox = (CheckBox) act.findViewById(R.id.checkBoxFreqs);
		freqBox.setChecked(OnBoot
				.checkExistsSetOnBootFile("/system/etc/init.d/99overclock"));

		govBox = (CheckBox) act.findViewById(R.id.checkBoxGovs);
		govBox.setChecked(OnBoot
				.checkExistsSetOnBootFile("/system/etc/init.d/99governor"));

		/*ioBox = (CheckBox) act.findViewById(R.id.checkBoxIO);
		ioBox.setChecked(OnBoot
				.checkExistsSetOnBootFile("/system/etc/init.d/99ioschedule"));*/

		/*bufferBox = (CheckBox) act.findViewById(R.id.checkBoxBufferSize);
		bufferBox.setChecked(OnBoot
				.checkExistsSetOnBootFile("/system/etc/init.d/99buffersize"));*/

		// Set checkboxes events
		freqBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				/*
				 * Create a new instance of SetBoxEvent which is declared in
				 * CustomCheckBoxes class. SetBoxEvent class has two functions,
				 * one when checkbox is checked and another one when checkbox in
				 * unchecked.
				 */
				SetBoxEvent boxEvent = new SetBoxEvent() {

					@Override
					public void unChecked() {
						freqBoxIsUnChecked();
					}

					@Override
					public void checked() {
						freqBoxIsChecked();
					}
				};

				if (arg0.isChecked()) {
					boxEvent.checked();
				} else {
					boxEvent.unChecked();
				}
			}
		});

		govBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				/*
				 * Create a new instance of SetBoxEvent which is declared in
				 * CustomCheckBoxes class. SetBoxEvent class has two functions,
				 * one when checkbox is checked and another one when checkbox in
				 * unchecked.
				 */
				SetBoxEvent boxEvent = new SetBoxEvent() {

					@Override
					public void unChecked() {
						govBoxIsUnChecked();
					}

					@Override
					public void checked() {
						govBoxIsChecked();
					}
				};

				if (arg0.isChecked()) {
					boxEvent.checked();
				} else {
					boxEvent.unChecked();
				}
			}
		});

		/*ioBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				
				  Create a new instance of SetBoxEvent which is declared in
				  CustomCheckBoxes class. SetBoxEvent class has two functions,
				  one when checkbox is checked and another one when checkbox in
				  unchecked.
				  
				SetBoxEvent boxEvent = new SetBoxEvent() {

					@Override
					public void unChecked() {
						ioBoxIsUnChecked();
					}

					@Override
					public void checked() {
						ioBoxIsChecked();
					}
				};

				if (arg0.isChecked()) {
					boxEvent.checked();
				} else {
					boxEvent.unChecked();
				}
			}
		});*/

		/*bufferBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				
				 Create a new instance of SetBoxEvent which is declared in
				 CustomCheckBoxes class. SetBoxEvent class has two functions,
				 one when checkbox is checked and another one when checkbox in
				 unchecked.
				 
				SetBoxEvent boxEvent = new SetBoxEvent() {

					@Override
					public void unChecked() {
						// TODO Auto-generated method stub

					}

					@Override
					public void checked() {
						// TODO Auto-generated method stub

					}
				};

				if (arg0.isChecked()) {
					boxEvent.checked();
				} else {
					boxEvent.unChecked();
				}
			}
		});*/
	}

	private void freqBoxIsChecked() {
		OnBoot onBoot = new OnBoot(activity);
		onBoot.fileName("99overclock");
		onBoot.setShell("#!/system/bin/sh");
		onBoot.addCommand("\necho " + (int) CpuFreqPicker.curMaxFreq
				+ " > /sys/devices/system/cpu/cpu0/cpufreq/scaling_max_freq");
		onBoot.addCommand("\necho " + (int) CpuFreqPicker.curMinFreq
				+ " > /sys/devices/system/cpu/cpu0/cpufreq/scaling_min_freq");
		if (onBoot.setOnBoot()) {
			Toast.makeText(activity, "set on boot is enabled!!",
					Toast.LENGTH_LONG).show();
		}
	}

	private void freqBoxIsUnChecked() {
		OnBoot.rmFile("/system", "/system/etc/init.d/99overclock");
		Toast.makeText(activity, "set on boot is disabled!!", Toast.LENGTH_LONG)
				.show();
	}

	private void govBoxIsChecked() {
		OnBoot onBoot = new OnBoot(activity);
		onBoot.fileName("99governor");
		onBoot.setShell("#!/system/bin/sh");
		onBoot.addCommand("\necho " + CpuGovernors.getCurrentGovernor()
				+ " > /sys/devices/system/cpu/cpu0/cpufreq/scaling_governor");
		if (onBoot.setOnBoot()) {
			Toast.makeText(activity, "set on boot is enabled!!",
					Toast.LENGTH_LONG).show();
		}
	}

	private void govBoxIsUnChecked() {
		OnBoot.rmFile("/system", "/system/etc/init.d/99governor");
		Toast.makeText(activity, "set on boot is disabled!!", Toast.LENGTH_LONG)
				.show();
	}

	private void ioBoxIsChecked() {
		// This feature is scheduled to complete in another version
	}

	private void ioBoxIsUnChecked() {
		// This feature is scheduled to complete in another version
	}
}