package com.elefher.extendedclasses;

import android.app.Activity;
import android.content.DialogInterface;
import android.widget.Toast;

import com.elefher.abstractclasses.AlertDialogUtils;
import com.elefher.cpuhandler.R;
import com.elefher.customclasses.DisplayText;
import com.elefher.customclasses.IOSchedulers;

public class AlertIOReadAhead extends AlertDialogUtils {

	Activity activity;
	String[] readAheadValues = {"128", "256", "512", "1024", "2048", "3072", "4096"};
	
	public AlertIOReadAhead(Activity act) {
		super(act);
		activity = act;

		// Set id button
		createButton(R.id.setReadAheadButton);

		// Set available governors to dialog
		setItems(readAheadValues);

		// Set icon and tile for the dialog
		setIcon(R.drawable.ic_launcher);
		setTitle("Select Read Ahead Buffer Size In kb/s:");

		/*
		 *  Set positive and negative button
		 */
		setPositiveButton("Select", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				/*
				 * Display message if user haven't choose the buffer size or
				 * if system doesn't support init.d
				 */
				if (getStringItem.isEmpty()){
					Toast.makeText(activity, "You have to choose a read ahead buffer size first!!", Toast.LENGTH_LONG).show();
					return;
				}
				
				if (IOSchedulers.getReadAheadBufferSize() == null){
					Toast.makeText(activity, "Oops, it seems like system doesn't support init.d!!", Toast.LENGTH_LONG).show();
					return;
				}
				/*
				 * Set the readahead and update info about the readahead 
				 */
				
				boolean readAheadChanged = IOSchedulers.setReadAheadBufferSize(getStringItem);
				if(!readAheadChanged){
					Toast.makeText(activity, "Sorry, but read ahead buffer didn't change!!", Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(activity, "Read ahead buffer size has changed to " + IOSchedulers.getReadAheadBufferSize() +
							"!!", Toast.LENGTH_LONG).show();
				}
				
				// Update the current readahead size in kb/s
				DisplayText.updateText(activity, R.id.updatereadbuffersize, IOSchedulers.getReadAheadBufferSize() + " kb/s");
				
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