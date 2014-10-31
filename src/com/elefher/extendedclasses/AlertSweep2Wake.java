package com.elefher.extendedclasses;

import android.app.Activity;
import android.content.DialogInterface;
import android.widget.Toast;

import com.elefher.abstractclasses.AlertDialogUtils;
import com.elefher.cpuhandler.R;
import com.elefher.customclasses.DisplayText;
import com.elefher.customclasses.MiscServices;

public class AlertSweep2Wake extends AlertDialogUtils {

	Activity activity;
	
	public AlertSweep2Wake(Activity act) {
		super(act);
		activity = act;

		// Set id button
		createButton(R.id.sweep2wakeButton);
		String[] items = {"off", "sweep2wake+sweep2sleep", "sweep2sleep"};
		// Set available options to dialog
		setItems(items);

		// Set icon and tile for the dialog
		setIcon(R.drawable.ic_launcher);
		setTitle("Sweep2Wake");

		/*
		 *  Set positive and negative button
		 */
		setPositiveButton("Select", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				/*
				 * Display message if user haven't choose a value
				 */
				if (getStringItem.isEmpty()){
					Toast.makeText(activity, "You have to choose an option first!!", Toast.LENGTH_LONG).show();
					return;
				}
				
				/*
				 * Set the sweep2wake value and update info about the current sweep2wake value 
				 */
				boolean sweep2wakeChanged = MiscServices.setSweep2WakeState(getStringItem);
				if(!sweep2wakeChanged){
					Toast.makeText(activity, "Sorry, but the sweep2wake didn't change!!", Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(activity, "Apply Successfully !!", Toast.LENGTH_LONG).show();
				}
				
				String sweep2wakeState = MiscServices.getSweep2WakeState();
				if(sweep2wakeState.equals("0")){
					sweep2wakeState = "Sweep2Wake: off";
				}else if(sweep2wakeState.equals("1")){
					sweep2wakeState = "Sweep2Wake: sweep2wake+sweep2sleep";
				}else if(sweep2wakeState.equals("2")){
					sweep2wakeState = "Sweep2Wake: sweep2sleep";
				}
				// Update the current sweep2wake
				DisplayText.updateText(activity, R.id.sweep2wakeText, sweep2wakeState);
				
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