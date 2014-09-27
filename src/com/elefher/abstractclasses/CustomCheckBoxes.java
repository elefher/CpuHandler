package com.elefher.abstractclasses;

import java.util.ArrayList;

import android.content.Context;
import android.widget.CheckBox;

public abstract class CustomCheckBoxes extends CheckBox {

	ArrayList<CheckBox> checkboxList = new ArrayList<CheckBox>();
	
	public CustomCheckBoxes(Context context) {
		super(context);
	}
	
	public void setACheckbox(CheckBox cBox){
		checkboxList.add(cBox);
	}
	
	public abstract class SetBoxEvent{
		public abstract void checked();
		public abstract void unChecked();
	}
}
