package com.elefher.extendedclasses;

import android.app.Activity;
import android.content.Context;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.elefher.abstractclasses.CustomCheckBoxes;
import com.elefher.cpuhandler.R;
import com.elefher.customclasses.OnBoot;

public class SetOnBootTask extends CustomCheckBoxes {

	CheckBox freqBox, govBox, ioBox, bufferBox;

	public SetOnBootTask(Activity act) {
		super(act);

		// Initialize checkboxes
		freqBox = (CheckBox) act.findViewById(R.id.checkBoxFreqs);
		freqBox.setChecked(OnBoot
				.checkExistsSetOnBootFile("/system/etc/init.d/99overclock"));

		govBox = (CheckBox) act.findViewById(R.id.checkBoxGovs);
		govBox.setChecked(OnBoot
				.checkExistsSetOnBootFile("/system/etc/init.d/99governor"));

		ioBox = (CheckBox) act.findViewById(R.id.checkBoxIO);
		ioBox.setChecked(OnBoot
				.checkExistsSetOnBootFile("/system/etc/init.d/99ioschedule"));

		bufferBox = (CheckBox) act.findViewById(R.id.checkBoxBufferSize);
		bufferBox.setChecked(OnBoot
				.checkExistsSetOnBootFile("/system/etc/init.d/99buffersize"));

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
		});

		ioBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

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
		});

		bufferBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

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
		});
	}

	private void freqBoxIsChecked() {
		System.out.println("is checked");
	}

	private void freqBoxIsUnChecked() {
		System.out.println("is un checked");
	}
}