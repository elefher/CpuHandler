package com.elefher.implementation;

import android.app.Activity;
import android.widget.Toast;

import com.cpu.handler.R;
import com.elefher.customclasses.DisplayText;
import com.elefher.customclasses.IOSchedulers;
import com.elefher.extendedclasses.AlertIOReadAhead;

public class IOReadAheadPicker {
	
Activity activity;
	
	public IOReadAheadPicker(Activity act){
		activity = act;
		
		checkExistsReadAhead();
		
		/*
		 * Display i/o read ahead buffer in alert dialog
		 */
		AlertIOReadAhead alertIOReadAhead = new AlertIOReadAhead(activity);
		
		/*
		 * Display read ahead buffer size of sdcard
		 */
		DisplayText.updateText(activity, R.id.updatereadbuffersize, IOSchedulers.getReadAheadBufferSize() + " kb/s");
	}
	
	private void checkExistsReadAhead(){
		if(IOSchedulers.getReadAheadBufferSize() == null){
			Toast.makeText(activity, "Oops, it seems like system doesn't support init.d!!", Toast.LENGTH_LONG).show();
		}
	}
}