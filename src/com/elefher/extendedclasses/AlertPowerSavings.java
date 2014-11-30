package com.elefher.extendedclasses;

import android.app.Activity;
import android.content.DialogInterface;
import android.widget.Toast;

import com.elefher.abstractclasses.AlertDialogUtils;
import com.cpu.tuner.R;
import com.elefher.customclasses.DisplayText;
import com.elefher.customclasses.MiscServices;

public class AlertPowerSavings extends AlertDialogUtils {

	Activity activity;
	
	public AlertPowerSavings(Activity act) {
		super(act);
		activity = act;

		// Set id button
		createButton(R.id.powerSavingsButton);
		String[] items = {"0", "1", "2"};
		// Set available governors to dialog
		setItems(items);

		// Set icon and tile for the dialog
		setIcon(R.drawable.ic_launcher);
		setTitle("Kernel: Power Savings");

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
					Toast.makeText(activity, "You have to choose a value first!!", Toast.LENGTH_LONG).show();
					return;
				}
				
				/*
				 * Set the power savings value and update info about the current power savings value 
				 */
				boolean powerSavingsChanged = MiscServices.setSchedMcPowerSavingsState(getStringItem, activity);
				if(!powerSavingsChanged){
					Toast.makeText(activity, "Sorry, but the power savings didn't change!!", Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(activity, "Apply Successfully !!", Toast.LENGTH_LONG).show();
				}
				
				// Update the current governor
				DisplayText.updateText(activity, R.id.currentPowerSavings, MiscServices.getSchedMcPowerSavingsState(activity));
				
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