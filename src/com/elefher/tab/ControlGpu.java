package com.elefher.tab;

import com.cpu.handler.R;
import com.elefher.customclasses.Gpu;
import com.elefher.implementation.GpuFreqPicker;
import com.elefher.implementation.GpuGovernorPicker;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.TextView;

public class ControlGpu extends Activity {

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.controlgpu);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		/* Gpu Frequency */
		// Set current Gpu Frequency
		Gpu gpu = new Gpu(this);
		TextView curPS = (TextView) findViewById(R.id.currentGpuFrequency);
		// Get current frequency and return it to MHz
		curPS.setText(gpu.returnTo(gpu.getCurrentFrequency()));
		
		GpuFreqPicker gpuFreqPicker = new GpuFreqPicker(this);
		/* End Gpu Frequency */
		/* Cpu Governor*/
		TextView curGov = (TextView) findViewById(R.id.updatedCurrentGpuGov);
		// Get current frequency and return it to MHz
		curGov.setText(gpu.getCurrent("gpu_governor")[0]);
		
		GpuGovernorPicker gpuGovernorPicker = new GpuGovernorPicker(this);
		/* End Gpu Governor*/
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