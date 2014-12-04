package com.elefher.extendedclasses;

import android.app.Activity;
import android.content.DialogInterface;
import android.widget.Toast;

import com.cpu.handler.R;
import com.elefher.abstractclasses.AlertDialogUtils;
import com.elefher.customclasses.DisplayText;
import com.elefher.customclasses.Gpu;

public class AlertGpuGovernors extends AlertDialogUtils {

	Activity activity;
	
	public AlertGpuGovernors(Activity act) {
		super(act);
		activity = act;

		// Set id button
		createButton(R.id.gpuGovernorButton);
		final Gpu gpu = new Gpu(activity);
		// Set available governors to dialog
		setItems(gpu.getCurrent("gpu_available_governors"));

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
				 * Set the Gpu Governor and update info about the current governor 
				 */
				boolean governorChanged = gpu.set(getStringItem, "gpu_governor");
				if(!governorChanged){
					Toast.makeText(activity, "Sorry, but the governor didn't change!!", Toast.LENGTH_LONG).show();
				} else if(governorChanged){
					Toast.makeText(activity, "The governor has changed to " + gpu.getCurrent("gpu_governor")[0] +
							"!!", Toast.LENGTH_LONG).show();
				}
				
				// Update the current governor
				DisplayText.updateText(activity, R.id.updatedCurrentGpuGov, gpu.getCurrent("gpu_governor")[0]);
				
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