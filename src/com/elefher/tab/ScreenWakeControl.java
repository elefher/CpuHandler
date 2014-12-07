package com.elefher.tab;

import com.cpu.handler.R;
import com.elefher.customclasses.DisplayText;
import com.elefher.customclasses.MiscServices;
import com.elefher.extendedclasses.AlertDoubleTap2Wake;
import com.elefher.extendedclasses.AlertSweep2Dim;
import com.elefher.extendedclasses.AlertSweep2Wake;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

public class ScreenWakeControl extends Activity {

	public ScreenWakeControl() {
	}

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.screen_wake_control);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		/*
		 * Sweep2Wake
		 */
		String sweep2wakeStr = MiscServices.findFilePath("sweep2wake", this);
		if(sweep2wakeStr != null && !sweep2wakeStr.isEmpty()){
		//if (MiscServices.exists(MiscServices.SWEEP2WAKE)) {
			LinearLayout sweep2wakeLayout = (LinearLayout) findViewById(R.id.sweep2wake);
			sweep2wakeLayout.setVisibility(View.VISIBLE);
			
			String sweep2wakeState = MiscServices.getSweep2WakeState(this);
			if (sweep2wakeState.equals("0")) {
				sweep2wakeState = "Sweep2Wake: off";
			} else if (sweep2wakeState.equals("1")) {
				sweep2wakeState = "Sweep2Wake: sweep2wake+sweep2sleep";
			} else if (sweep2wakeState.equals("2")) {
				sweep2wakeState = "Sweep2Wake: sweep2sleep";
			}
			// Update the current sweep2wake
			DisplayText.updateText(this, R.id.sweep2wakeText, sweep2wakeState);
			
			AlertSweep2Wake alertSweep2Wake = new AlertSweep2Wake(this);
		}
		
		/*
		 * DoubleTap2Wake
		 */
		String doubletap2wakeStr = MiscServices.findFilePath("doubletap2wake", this);
		if(doubletap2wakeStr != null && !doubletap2wakeStr.isEmpty()){
		//if (MiscServices.exists(MiscServices.DOUBLETAP2WAKE)) {
			LinearLayout sweep2wakeLayout = (LinearLayout) findViewById(R.id.doubleTap2wake);
			sweep2wakeLayout.setVisibility(View.VISIBLE);
			
			String doubleTap2WakeState = MiscServices.getDoubleTap2Wake(this);
			if(doubleTap2WakeState.equals("0")){
				doubleTap2WakeState = "DoubleTap2Wake: Disabled";
			}else if(doubleTap2WakeState.equals("1")){
				doubleTap2WakeState = "DoubleTap2Wake: Enabled";
			}
			// Update the current doubletap2wake
			DisplayText.updateText(this, R.id.doubleTap2WakeText, doubleTap2WakeState);
			
			AlertDoubleTap2Wake alertDoubleTap2Wake = new AlertDoubleTap2Wake(this);
		}

		/*
		 * Sweep2Dim
		 */
		String sweep2dimStr = MiscServices.findFilePath("sweep2dim", this);
		if(sweep2dimStr != null && !sweep2dimStr.isEmpty()){
			LinearLayout sweep2dimLayout = (LinearLayout) findViewById(R.id.sweep2dim);
			sweep2dimLayout.setVisibility(View.VISIBLE);

			String sweep2dimState = MiscServices.getSweep2DimState(this);
			if(sweep2dimState.equals("0")){
				sweep2dimState = "Sweep2Dim: Disabled";
			}else if(sweep2dimState.equals("1")){
				sweep2dimState = "Sweep2Dim: Enabled";
			}
			// Update the current doubletap2wake
			DisplayText.updateText(this, R.id.sweep2dimText, sweep2dimState);

			AlertSweep2Dim alertSweep2Dim = new AlertSweep2Dim(this);
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    // Respond to the action bar's Up/Home button
	    case android.R.id.home:
	        NavUtils.navigateUpFromSameTask(this);
	        return true;
	    }
	    return super.onOptionsItemSelected(item);
	}
}