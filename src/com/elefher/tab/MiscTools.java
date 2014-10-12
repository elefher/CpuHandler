package com.elefher.tab;

import com.elefher.cpuhandler.R;
import com.elefher.customclasses.DisplayText;
import com.elefher.customclasses.MiscServices;
import com.elefher.extendedclasses.AlertPowerSavings;
import com.elefher.extendedclasses.AlertProfiles;
import com.elefher.implementation.ForceFastCharge;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MiscTools extends Activity {
	
/** Called when the activity is first created. */
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.misctools);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		/*
		 * Check if kernel supports below features 
		 */
		if(MiscServices.exists(MiscServices.FORCE_FAST_CHARGE)){
			LinearLayout fastChargeLayout = (LinearLayout) findViewById(R.id.fastCharge);
			fastChargeLayout.setVisibility(View.VISIBLE);
			ForceFastCharge forceFastCharge = new ForceFastCharge(this);
		}
		
		if(MiscServices.exists(MiscServices.SCHED_MC_POWER_SAVINGS)){
			LinearLayout powerSavingsLayout = (LinearLayout) findViewById(R.id.powerSavings);
			powerSavingsLayout.setVisibility(View.VISIBLE);
			TextView curPS = (TextView) findViewById(R.id.currentPowerSavings);
			curPS.setText(MiscServices.getSchedMcPowerSavingsState());
			AlertPowerSavings alertPowerSavings = new AlertPowerSavings(this);
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