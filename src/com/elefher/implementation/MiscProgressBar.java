package com.elefher.implementation;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class MiscProgressBar extends ProgressBar{
	
	public int maxCpuProgress, currentCpuProgress;
	
	public MiscProgressBar(Context context, int Rdrawable, LinearLayout.LayoutParams paramsCircle) {
		super(context, null, android.R.attr.progressBarStyleHorizontal);
		
		setProgressDrawable(getResources().getDrawable(Rdrawable));
		setLayoutParams(paramsCircle);
		setIndeterminate(false);
		setVisibility(View.VISIBLE);
	}
	
	public void max(int max){
		setMax(max);
		maxCpuProgress = max;
	}
	
	public void setCurrentProgress(int percentage){
		setProgress(percentage);
		currentCpuProgress = percentage;
	}
	
	public void rotation(int rotate){
		setRotation(rotate);
	}
}