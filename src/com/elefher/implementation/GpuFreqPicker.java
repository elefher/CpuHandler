package com.elefher.implementation;

import java.util.ArrayList;

import android.app.Activity;
import android.content.DialogInterface;
import android.widget.Toast;

import com.cpu.handler.R;
import com.elefher.abstractclasses.AlertDialogUtils;
import com.elefher.customclasses.DisplayText;
import com.elefher.customclasses.GpuFreq;

public class GpuFreqPicker extends AlertDialogUtils {

	Activity activity;
	
	public GpuFreqPicker(Activity act) {
		super(act);
		activity = act;

		// Set id button
		createButton(R.id.gpuFreqButton);
		// Fill items array with values
		ArrayList<String> items = new ArrayList<String>();
		final GpuFreq gpuFreq = new GpuFreq(activity);
		String[] freqs = gpuFreq.getAvailableFreequencies(activity, "gpu_available_frequencies");
		int freqsLength = freqs.length;
		for(int i = 0; i < freqsLength; i++){
			items.add(freqs[i]); 
		}
		// Set available frequencies to dialog
		setItems(items.toArray(new String[items.size()]));

		// Set icon and tile for the dialog
		setIcon(R.drawable.ic_launcher);
		setTitle("Gpu Frequencies");

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
				 * Set the Gpu frequency and update info about the current Gpu frequency 
				 */
				boolean gpuFrequencyChanged = gpuFreq.setFrequency(getStringItem);
				if(!gpuFrequencyChanged){
					Toast.makeText(activity, "Sorry, but frequency didn't change!!", Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(activity, "Apply Successfully !!", Toast.LENGTH_LONG).show();
				}
				
				// Update the current Gpu frequncy
				DisplayText.updateText(activity, R.id.currentGpuFrequency, gpuFreq.returnTo(gpuFreq.getCurrentFrequency()));
				
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