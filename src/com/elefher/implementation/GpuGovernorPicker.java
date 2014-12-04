package com.elefher.implementation;

import android.app.Activity;

import com.cpu.handler.R;
import com.elefher.customclasses.CpuGovernors;
import com.elefher.customclasses.DisplayText;
import com.elefher.customclasses.Gpu;
import com.elefher.extendedclasses.AlertGpuGovernors;

public class GpuGovernorPicker {
	
	Activity activity;

	public GpuGovernorPicker(Activity act) {
		activity = act;
		
		/*
		 * Display governors in an alert dialog and user can 
		 * change a governor 
		 */
		AlertGpuGovernors alertGpuGovernors = new AlertGpuGovernors(activity);
		
		/*
		 * Display the current governor
		 */
		Gpu gpu = new Gpu(activity);
		DisplayText.updateText(activity, R.id.updatedCurrentGpuGov, gpu.getCurrent("gpu_governor")[0]);
	}
}