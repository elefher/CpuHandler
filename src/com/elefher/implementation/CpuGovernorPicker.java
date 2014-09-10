package com.elefher.implementation;

import java.util.ArrayList;
import java.util.List;

import com.elefher.cpuhandler.R;
import com.elefher.customclasses.CpuGovernors;
import com.elefher.utils.CreateDropdownAndButton;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Spinner;

public class CpuGovernorPicker {
	
	Activity activity;
	
	public CpuGovernorPicker(Activity act){
		activity = act;
		String[] elements = {"eeeee", "rrrrrrr"};
		CreateDropdownAndButton createDropDown = new CreateDropdownAndButton(activity, R.id.governorSpinner, "Governors", elements);
	}

}