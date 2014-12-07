package com.cpu.handler;

import java.util.ArrayList;

import com.elefher.customclasses.CpuGpuFreqVoltages;
import com.elefher.customclasses.Gpu;
import com.elefher.tab.ControlCpu;
import com.elefher.tab.ControlGpu;
import com.elefher.tab.Info;
import com.elefher.tab.MiscTools;
import com.elefher.tab.Voltages;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

public class MainActivity extends Activity {

	ArrayList<String> items;
	Activity that = this;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_activity_actions, menu);

		Log.d("MainMenu", ",menu title0: " + menu.getItem(0).getTitle());
		return true;
	}

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.activity_main);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		GridView gv = (GridView) findViewById(R.id.gridview);

		items = new ArrayList<String>();
		items.add("INFO");
		items.add("CONTROL CPU");
		/*
		 * Check if Gpu features exists in kernel
		 * then enable Gpu tab or not
		 */
		Gpu gpuFeatures = new Gpu(this);
		String gpuClock = gpuFeatures.findFilePath("max_gpuclk", this);
		String gpuGov = gpuFeatures.findFilePath("gpu_governor", this);

		if((gpuClock != null && !gpuClock.isEmpty()) ||
				(gpuGov != null && !gpuGov.isEmpty())) {
			items.add("CONTROL GPU");
		}

		if (CpuGpuFreqVoltages.hasCpuVoltages(this))
			items.add("VOLTAGES");
		items.add("MISC TOOLS");

		gv.setAdapter(new CustomAdapter(this, R.layout.gridlayout, items));

		gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				if ("INFO".equals(items.get(position)))
					startActivity(new Intent().setClass(that, Info.class)
							.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
				else if ("CONTROL CPU".equals(items.get(position)))
					startActivity(new Intent().setClass(that, ControlCpu.class)
							.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
				else if ("CONTROL GPU".equals(items.get(position)))
					startActivity(new Intent().setClass(that, ControlGpu.class)
							.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
				else if ("VOLTAGES".equals(items.get(position)))
					startActivity(new Intent().setClass(that, Voltages.class)
							.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
				else if ("MISC TOOLS".equals(items.get(position)))
					startActivity(new Intent().setClass(that, MiscTools.class)
							.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
				else if (position >= 4)
					System.out.println("bigger than 4");
			}

		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
			return true;
		case R.id.action_licenses:
			licenses();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void licenses() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		final TextView message = new TextView(this);
		final SpannableString s = new SpannableString(getText(R.string.licenses));
		Linkify.addLinks(s, Linkify.WEB_URLS);
		message.setText(s);
		message.setMovementMethod(LinkMovementMethod.getInstance());

		alert.setTitle("Licenses:");
		alert.setView(message);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
			}
		});

		alert.show();
	}
}