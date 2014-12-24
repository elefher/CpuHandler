package com.cpu.handler;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.*;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import com.elefher.customclasses.CpuGpuFreqVoltages;
import com.elefher.customclasses.Gpu;
import com.elefher.tab.*;

import java.util.ArrayList;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

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

	/**
	 * A placeholder fragment containing a simple view. This fragment
	 * would include your content.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
								 Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_my, container, false);
			return rootView;
		}
	}

	/**
	 * This class makes the ad request and loads the ad.
	 */
	public static class AdFragment extends Fragment {

		private AdView mAdView;

		public AdFragment() {
		}

		@Override
		public void onActivityCreated(Bundle bundle) {
			super.onActivityCreated(bundle);

			// Gets the ad view defined in layout/ad_fragment.xml with ad unit ID set in
			// values/strings.xml.
			mAdView = (AdView) getView().findViewById(R.id.adView);

			// Create an ad request. Check logcat output for the hashed device ID to
			// get test ads on a physical device. e.g.
			// "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
			AdRequest adRequest = new AdRequest.Builder()
					.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
					.build();

			// Start loading the ad in the background.
			mAdView.loadAd(adRequest);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
								 Bundle savedInstanceState) {
			return inflater.inflate(R.layout.fragment_ad, container, false);
		}

		/** Called when leaving the activity */
		@Override
		public void onPause() {
			if (mAdView != null) {
				mAdView.pause();
			}
			super.onPause();
		}

		/** Called when returning to the activity */
		@Override
		public void onResume() {
			super.onResume();
			if (mAdView != null) {
				mAdView.resume();
			}
		}

		/** Called before the activity is destroyed */
		@Override
		public void onDestroy() {
			if (mAdView != null) {
				mAdView.destroy();
			}
			super.onDestroy();
		}

	}
}