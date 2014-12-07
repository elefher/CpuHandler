package com.elefher.tab;

import android.view.View;
import android.widget.LinearLayout;
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

		String gpuClock = gpu.findFilePath("max_gpuclk", this);
		if(gpuClock != null && !gpuClock.isEmpty()) {
			// Enable gpu frequency linear xml
			LinearLayout gpuFreqLayout = (LinearLayout) findViewById(R.id.gpufreqControl);
			gpuFreqLayout.setVisibility(View.VISIBLE);

			TextView curPS = (TextView) findViewById(R.id.currentGpuFrequency);
			// Get current frequency and return it to MHz
			curPS.setText(gpu.returnTo(gpu.getCurrentFrequency()));

			GpuFreqPicker gpuFreqPicker = new GpuFreqPicker(this);
		}
		/* End Gpu Frequency */

		/* Cpu Governor*/
		String gpuGov = gpu.findFilePath("gpu_governor", this);
		String gpuAvailGov = gpu.findFilePath("gpu_available_governors", this);
		if((gpuGov != null && !gpuGov.isEmpty()) &&
				(gpuAvailGov != null && !gpuAvailGov.isEmpty())) {
			// Enable gpu governor linear xml
			LinearLayout gpuGovLayout = (LinearLayout) findViewById(R.id.gpugovControl);
			gpuGovLayout.setVisibility(View.VISIBLE);

			TextView curGov = (TextView) findViewById(R.id.updatedCurrentGpuGov);
			// Get current frequency and return it to MHz
			curGov.setText(gpu.getCurrent("gpu_governor")[0]);

			GpuGovernorPicker gpuGovernorPicker = new GpuGovernorPicker(this);
		}
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