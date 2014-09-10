package com.elefher.utils;

import java.util.ArrayList;

import java.util.List;

import com.elefher.cpuhandler.R;

import android.app.Activity;
import android.app.ExpandableListActivity;
import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class CreateDropdownAndButton implements OnItemSelectedListener, OnClickListener {

	Activity activity;
	int lengthElements;
	Spinner spinner;

	public CreateDropdownAndButton(Activity act, int rIdSpinner, String firstElementAsTitle, String[] arrElements) {
		activity = act;
		lengthElements = arrElements.length;

		// Spinner element
		spinner = (Spinner) activity.findViewById(rIdSpinner);

		// Spinner click listener
		spinner.setOnItemSelectedListener(this);

		// Spinner Drop down elements
		List<String> categories = new ArrayList<String>();
		categories.add(firstElementAsTitle);
		for (int i = 0; i < lengthElements; i++) {
			categories.add(arrElements[i]);
		}

		// Creating adapter for spinner
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(activity,
				android.R.layout.simple_spinner_item, categories);

		// Drop down layout style - list view with radio button
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// attaching data adapter to spinner
		spinner.setAdapter(dataAdapter);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		Toast.makeText(activity, "Cpu governor " + arg0.getItemAtPosition(arg2).toString() + " selected!!",
				Toast.LENGTH_SHORT).show();		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

}