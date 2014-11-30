package com.elefher.tab;

import com.cpu.tuner.R;
import com.elefher.customclasses.MiscServices;
import com.elefher.extendedclasses.AlertPowerSavings;
import com.elefher.implementation.ForceFastCharge;
import com.elefher.implementation.IntelliPlug;
import com.elefher.implementation.MpDecision;
import com.elefher.utils.ArrayUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MiscTools extends Activity {
	
/** Called when the activity is first created. */
	Activity that = this;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.misctools);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		/*
		 * Check if kernel supports below features 
		 */
		String forceStr = MiscServices.findFilePath("force_fast_charge", that);
		if(forceStr != null && !forceStr.isEmpty()){
			LinearLayout fastChargeLayout = (LinearLayout) findViewById(R.id.fastCharge);
			fastChargeLayout.setVisibility(View.VISIBLE);
			ForceFastCharge forceFastCharge = new ForceFastCharge(this);
		}
		
		String schedStr = MiscServices.findFilePath("sched_mc_power_savings", that);
		if(schedStr != null && !schedStr.isEmpty()){
			LinearLayout powerSavingsLayout = (LinearLayout) findViewById(R.id.powerSavings);
			powerSavingsLayout.setVisibility(View.VISIBLE);
			TextView curPS = (TextView) findViewById(R.id.currentPowerSavings);
			curPS.setText(MiscServices.getSchedMcPowerSavingsState(that));
			AlertPowerSavings alertPowerSavings = new AlertPowerSavings(this);
		}
		
		/*
		 * Check if mpdecision exists. It is a kernel feature for Qualcomm cpu.
		 * Mpdecision is a proprietary code and doesn't recommended. 
		 * If feature exists then give to the user the right to disable or enable it.
		 */
		//MiscServices miscServicesMPDecision = new MiscServices();
		String decisionStr = MiscServices.findFilePath("mp_decision", that);
		if(decisionStr != null && !decisionStr.isEmpty()){
		//String mpdecisionFile = ArrayUtils.existPaths(MiscServices.MPDECISION_PATHS);
		//if(mpdecisionFile != null){
			LinearLayout mpdecisionLayout = (LinearLayout) findViewById(R.id.mpdecision);
			mpdecisionLayout.setVisibility(View.VISIBLE);
			// Firstly you have to set path for mpdecision
			MpDecision.path = decisionStr;
			MpDecision mpDecision = new MpDecision(this);
		}
		
		//MiscServices miscServicesIntelliPlug = new MiscServices();
		String intelliStr = MiscServices.findFilePath("intelli_plug", that);
		if(intelliStr != null && !intelliStr.isEmpty()){
		//String intelliPlugFile = ArrayUtils.existPaths(MiscServices.INTELLIPLUG_PATHS);
		//if(intelliPlugFile != null){
			LinearLayout mpdecisionLayout = (LinearLayout) findViewById(R.id.intelliplug);
			mpdecisionLayout.setVisibility(View.VISIBLE);
			// Firstly you have to set path for intelliplug
			IntelliPlug.path = intelliStr;
			IntelliPlug intelliplug = new IntelliPlug(this);
		}
		
		/*
		 * Check if screen wake control supported by kernel
		 */
		String sweep2wakeStr = MiscServices.findFilePath("sweep2wake", that);
		String doubletap2wakeStr = MiscServices.findFilePath("doubletap2wake", that);
		if((sweep2wakeStr != null && !sweep2wakeStr.isEmpty()) || (doubletap2wakeStr != null && !doubletap2wakeStr.isEmpty())){
		//if(MiscServices.exists(MiscServices.SWEEP2WAKE) || MiscServices.exists(MiscServices.DOUBLETAP2WAKE)){
			LinearLayout screenWakeControlLayout = (LinearLayout) findViewById(R.id.screenwakecontrol);
			screenWakeControlLayout.setVisibility(View.VISIBLE);
			/*
			 * If button pressed start a new activity for screen wake control
			 */
			Button screenWakeControl = (Button) findViewById(R.id.screenWakeControlButton);
			screenWakeControl.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					startActivity(new Intent().setClass(that, ScreenWakeControl.class).addFlags(
							Intent.FLAG_ACTIVITY_CLEAR_TOP));
				}
			});
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