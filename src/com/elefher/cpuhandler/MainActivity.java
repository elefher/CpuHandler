package com.elefher.cpuhandler;

import java.util.ArrayList;

import com.elefher.customclasses.CpuGpuFreqVoltages;
import com.elefher.tab.ControlCpu;
import com.elefher.tab.Info;
import com.elefher.tab.MiscTools;
import com.elefher.tab.Voltages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class MainActivity extends Activity {

	ArrayList<String> items;
	Activity that = this;
	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.activity_main);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);

		GridView gv = (GridView) findViewById(R.id.gridview);
		
		items = new ArrayList<String>();
		items.add("INFO");
		items.add("CONTROL CPU");
		if(CpuGpuFreqVoltages.hasCpuVoltages())
			items.add("VOLTAGES");
		items.add("MISC TOOLS");
		
		gv.setAdapter(new CustomAdapter(this, R.layout.gridlayout, items));
		
		gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {
				if("INFO".equals(items.get(position)))
					startActivity(new Intent().setClass(that, Info.class).addFlags(
							Intent.FLAG_ACTIVITY_CLEAR_TOP));
				else if("CONTROL CPU".equals(items.get(position)))
					startActivity(new Intent().setClass(that, ControlCpu.class)
							.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
				else if("VOLTAGES".equals(items.get(position)))
					startActivity(new Intent().setClass(that, Voltages.class)
							.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
				else if("MISC TOOLS".equals(items.get(position)))
					startActivity(new Intent().setClass(that, MiscTools.class)
							.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
				else if(position >= 4)
					System.out.println("bigger than 3");
			}
			
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}