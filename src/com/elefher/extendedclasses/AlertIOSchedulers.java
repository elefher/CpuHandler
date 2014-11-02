package com.elefher.extendedclasses;

import android.app.Activity;
import android.content.DialogInterface;
import android.widget.Toast;

import com.elefher.abstractclasses.AlertDialogUtils;
import com.cpu.tuner.R;
import com.elefher.customclasses.DisplayText;
import com.elefher.customclasses.IOSchedulers;

public class AlertIOSchedulers extends AlertDialogUtils {

	Activity activity;
	
	public AlertIOSchedulers(Activity act) {
		super(act);
		activity = act;

		// Set id button
		createButton(R.id.setIOScheduleButton);

		// Set available governors to dialog
		setItems(IOSchedulers.getAvailableIOSchedules());

		// Set icon and tile for the dialog
		setIcon(R.drawable.ic_launcher);
		setTitle("I/O Schedules:");

		/*
		 *  Set positive and negative button
		 */
		setPositiveButton("Select", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				/*
				 * Display message if user haven't choose a governor
				 */
				if (getStringItem.isEmpty()){
					Toast.makeText(activity, "You have to choose an i/o schedule first!!", Toast.LENGTH_LONG).show();
					return;
				}
				
				/*
				 * Set the i/o schedule and update info about the i/o schedule 
				 */
				
				boolean ioSchedulerChanged = IOSchedulers.setIOSchedule(getStringItem);
				if(!ioSchedulerChanged){
					Toast.makeText(activity, "Sorry, but the I/O Scheduler didn't change!!", Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(activity, "The I/O Scheduler has changed to " + IOSchedulers.getCurrentIOSchedule() +
							"!!", Toast.LENGTH_LONG).show();
				}
				
				// Update the current IOSchedule
				DisplayText.updateText(activity, R.id.updateioschedule, IOSchedulers.getCurrentIOSchedule());
				
				/*
				 *  Initialize var getStringItem in order to delete the preview choose 
				 */
				getStringItem = "";
				
			}
		});

		setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

			}
		});
	}
}