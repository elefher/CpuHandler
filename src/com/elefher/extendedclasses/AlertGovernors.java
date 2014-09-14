package com.elefher.extendedclasses;

import android.app.Activity;
import android.content.DialogInterface;
import android.widget.Toast;

import com.elefher.abstractclasses.*;
import com.elefher.cpuhandler.R;
import com.elefher.customclasses.CpuGovernors;
import com.elefher.customclasses.DisplayCurrentGovernor;

public class AlertGovernors extends AlertDialogUtils {

	Activity activity;
	
	public AlertGovernors(Activity act) {
		super(act);
		activity = act;

		// Set id button
		createButton(R.id.governorButton);

		// Set available governors to dialog
		setItems(CpuGovernors.getAvailableGovernors());

		// Set icon and tile for the dialog
		setIcon(R.drawable.ic_launcher);
		setTitle("Governor: Choose your Governor");

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
					Toast.makeText(activity, "You have to choose a governor first!!", Toast.LENGTH_LONG).show();
					return;
				}
				
				/*
				 * Set the cpu Governor and update info about the current governor 
				 */
				boolean governorChanged = CpuGovernors.setGovernor(getStringItem);
				if(!governorChanged){
					Toast.makeText(activity, "Sorry, but the governor didn't change!!", Toast.LENGTH_LONG).show();
				} else if(governorChanged){
					Toast.makeText(activity, "The governor has changed to " + CpuGovernors.getCurrentGovernor() +
							"!!", Toast.LENGTH_LONG).show();
				}
				
				// Update the current governor
				DisplayCurrentGovernor.updateCurrentGovernor(activity, R.id.updatedCurrentGov, "");
				
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