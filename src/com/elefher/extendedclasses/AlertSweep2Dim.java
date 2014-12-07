package com.elefher.extendedclasses;

import android.app.Activity;
import android.content.DialogInterface;
import android.widget.Toast;
import com.cpu.handler.R;
import com.elefher.abstractclasses.AlertDialogUtils;
import com.elefher.customclasses.DisplayText;
import com.elefher.customclasses.MiscServices;

/**
 * Created by elefher on 6/12/2014.
 */
public class AlertSweep2Dim extends AlertDialogUtils {

    Activity activity;

    public AlertSweep2Dim(Activity act) {
        super(act);
        activity = act;

        // Set id button
        createButton(R.id.sweep2dimButton);
        String[] items = {"0", "1"};
        // Set available options to dialog
        setItems(items);

        // Set icon and tile for the dialog
        setIcon(R.drawable.ic_launcher);
        setTitle("Sweep2Dim");

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
				 * Set the sweep2dim value and update info about the current sweep2dim value
				 */
                boolean sweep2dimChanged = MiscServices.setSweep2Dim(getStringItem, activity);
                if(!sweep2dimChanged){
                    Toast.makeText(activity, "Sorry, but the sweep2dim didn't change!!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(activity, "Apply Successfully !!", Toast.LENGTH_LONG).show();
                }

                String sweep2dimState = MiscServices.getSweep2DimState(activity);
                if(sweep2dimState.equals("0")){
                    sweep2dimState = "DoubleTap2Wake: Disabled";
                }else if(sweep2dimState.equals("1")){
                    sweep2dimState = "DoubleTap2Wake: Enabled";
                }
                // Update the current sweep2wake
                DisplayText.updateText(activity, R.id.sweep2dimText, sweep2dimState);

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