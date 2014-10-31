package com.elefher.extendedclasses;

import android.app.Activity;
import android.content.DialogInterface;
import android.widget.Toast;

import com.elefher.abstractclasses.AlertDialogUtils;
import com.elefher.cpuhandler.R;
import com.elefher.customclasses.DisplayText;
import com.elefher.customclasses.MiscServices;

public class AlertDoubleTap2Wake extends AlertDialogUtils {

	Activity activity;
	
	public AlertDoubleTap2Wake(Activity act) {
		super(act);
		activity = act;

		// Set id button
		createButton(R.id.doubleTap2wakeButton);
		String[] items = {"0", "1"};
		// Set available options to dialog
		setItems(items);

		// Set icon and tile for the dialog
		setIcon(R.drawable.ic_launcher);
		setTitle("DoubleTap2Wake");

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
				 * Set the doubletap2wake value and update info about the current doubletap2wake value 
				 */
				boolean doubleTap2WakeChanged = MiscServices.setDoubleTap2Wake(getStringItem);
				if(!doubleTap2WakeChanged){
					Toast.makeText(activity, "Sorry, but the doubletap2wake didn't change!!", Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(activity, "Apply Successfully !!", Toast.LENGTH_LONG).show();
				}
				
				String doubleTap2WakeState = MiscServices.getDoubleTap2Wake();
				if(doubleTap2WakeState.equals("0")){
					doubleTap2WakeState = "DoubleTap2Wake: Disabled";
				}else if(doubleTap2WakeState.equals("1")){
					doubleTap2WakeState = "DoubleTap2Wake: Enabled";
				}
				// Update the current sweep2wake
				DisplayText.updateText(activity, R.id.doubleTap2WakeText, doubleTap2WakeState);
				
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