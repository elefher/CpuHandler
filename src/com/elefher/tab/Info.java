package com.elefher.tab;

import java.util.ArrayList;

import com.elefher.cpuhandler.R;
import com.elefher.customclasses.BatteryStat;
import com.elefher.customclasses.CpuGovernors;
import com.elefher.customclasses.CpuStat;
import com.elefher.customclasses.DeviceInfo;
import com.elefher.customclasses.MemoryStat;
import com.elefher.utils.MiscProgressBar;
import com.elefher.utils.ReadFile;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Info extends Activity {

	/** Called when the activity is first created. */
	public static int cores = CpuStat.getNumCores();
	long totalMemory = MemoryStat.getTotalMemory();
	MemoryStat memoryStat;
	CpuStat cpuStats = new CpuStat();
	BatteryStat batteryStat;
	ArrayList<TextView> textViewList;
	ArrayList<MiscProgressBar> circleProgressBars;
	MiscProgressBar totalCpuLineProgressBar, memoryUsageProgressBar,
			batteryTempProgressBar, cpuTempProgressBar;
	TextView totalCpu, totalCpuCores, memoryUsage, batteryStats,
			batteryMiscStats, batteryTempStat, cpuTemp, currentGovernor;
	private boolean started = false;
	private Handler handler = new Handler();

	LinearLayout.LayoutParams params1, paramsMem, paramsCircle, paramsLine,
			paramsLineMem, paramWith2Lines, separateLine, marginLeft, statusGovernorParams,
			displayGovernorParams, titles;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info);

		memoryStat = new MemoryStat(this);

		// Cpu status
		LinearLayout lcpuInfo = (LinearLayout) findViewById(R.id.cpuInfo);

		// linear layout params for circle cpu
		marginLeft = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		marginLeft.leftMargin = 10;
		
		titles = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);

		separateLine = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
				1);
		separateLine.topMargin = 10;

		paramsCircle = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		paramsCircle.width = 120;
		paramsCircle.height = 120;

		paramsLine = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		paramsLine.width = 350;
		paramsLine.height = 10;
		paramsLine.setMargins(0, 60, 0, 0);

		paramsLineMem = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
				10);
		

		paramWith2Lines = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, 50);

		params1 = new LinearLayout.LayoutParams(300, LayoutParams.WRAP_CONTENT);
		params1.setMargins(20, 40, 0, 0);

		paramsMem = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 60);
		paramsMem.setMargins(20, 40, 20, 0);

		// Linear layout params for layout
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, 120);
		layoutParams.topMargin = 10;
		
		statusGovernorParams = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f);
		
		displayGovernorParams = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f);

		// display standard device info like kernel, os etc.
		TextView textCodeName = (TextView) findViewById(R.id.codename);
		textCodeName.setText(DeviceInfo.codeName);
		TextView textDevice = (TextView) findViewById(R.id.device);
		textDevice.setText(DeviceInfo.device);
		TextView textKernel = (TextView) findViewById(R.id.kernel);
		textKernel.setText(DeviceInfo.kernel);
		TextView textBrand = (TextView) findViewById(R.id.brand);
		textBrand.setText(DeviceInfo.brand);
		// *************************************************

		// Total cpu cores
		totalCpuCores = (TextView) findViewById(R.id.cores);
		totalCpuCores.setText("Total Cores: " + cores);

		// Initialize progress bars
		textViewList = new ArrayList<TextView>(cores);
		circleProgressBars = new ArrayList<MiscProgressBar>(cores);
		totalCpuLineProgressBar = new MiscProgressBar(this,
				R.drawable.lineprogressbar, paramsLineMem);
		memoryUsageProgressBar = new MiscProgressBar(this,
				R.drawable.lineprogressbar, paramsLineMem);
		batteryTempProgressBar = new MiscProgressBar(this,
				R.drawable.lineprogressbar, paramsLineMem);
		cpuTempProgressBar = new MiscProgressBar(this,
				R.drawable.lineprogressbar, paramsLineMem);

		// Memory stats
		LinearLayout memoryLayout = new LinearLayout(this);
		memoryLayout.setOrientation(LinearLayout.VERTICAL);
		memoryLayout.setLayoutParams(paramsMem);
		memoryUsage = new TextView(this);
		memoryUsage.setTextColor(Color.WHITE);
		memoryUsage.setText("Mem Usage: " + ("0 / " + totalMemory + "MB"));
		memoryUsage.setLayoutParams(paramWith2Lines);
		memoryUsageProgressBar.max((int) totalMemory);
		memoryUsageProgressBar.setCurrentProgress(0);
		memoryLayout.addView(memoryUsage);
		memoryLayout.addView(memoryUsageProgressBar);
		lcpuInfo.addView(memoryLayout);
		// *************************************************************

		// Total cpu usage
		LinearLayout totalCpuLayout = new LinearLayout(this);
		totalCpuLayout.setOrientation(LinearLayout.VERTICAL);
		totalCpuLayout.setLayoutParams(paramsMem);
		totalCpu = new TextView(this);
		totalCpu.setTypeface(Typeface.MONOSPACE);
		totalCpu.setLayoutParams(paramWith2Lines);
		totalCpu.setTextColor(Color.WHITE);
		totalCpu.setTextSize(15);
		totalCpu.setText("Cpu Usage: ");
		totalCpuLineProgressBar.max(100);
		totalCpuLineProgressBar.setCurrentProgress(0);
		totalCpuLayout.addView(totalCpu);
		totalCpuLayout.addView(totalCpuLineProgressBar);
		lcpuInfo.addView(totalCpuLayout);
		// ****************************************************
		
		/*
		 *  Create Linearlayout view about cpu stats & governor
		 *  Status and governor layouts are vertical, put them together
		 *  in horizontal layout. 
		 */
		LinearLayout statusLayout = new LinearLayout(this);
		statusLayout.setOrientation(LinearLayout.VERTICAL);

		// Create text views about cpu status
		for (int i = 0; i < cores; i++) {
			// Create every time a new linear layout in order to have a
			// new line.
			LinearLayout layout = new LinearLayout(this);
			layout.setOrientation(LinearLayout.HORIZONTAL);
			layout.setLayoutParams(layoutParams);

			textViewList.add(i, new TextView(this));
			textViewList.get(i).setTypeface(Typeface.MONOSPACE);
			textViewList.get(i).setLayoutParams(params1);
			textViewList.get(i).setTextColor(Color.WHITE);
			textViewList.get(i).setTextSize(15);
			textViewList.get(i).setText("Core " + i + ": ");
			layout.addView(textViewList.get(i));

			circleProgressBars.add(i, new MiscProgressBar(this,
					R.drawable.ringprogressbar, paramsCircle));
			circleProgressBars.get(i).max(100);
			circleProgressBars.get(i).rotation(110);
			circleProgressBars.get(i).setCurrentProgress(0);
			layout.addView(circleProgressBars.get(i));
			
			statusLayout.addView(layout);
		}
		
		LinearLayout govLayout = new LinearLayout(this);
		govLayout.setOrientation(LinearLayout.HORIZONTAL);
		govLayout.setLayoutParams(displayGovernorParams);
		
		currentGovernor = new TextView(this);
		currentGovernor.setTextSize(20);
		currentGovernor.setX(225);
		currentGovernor.setTextColor(Color.rgb(188, 198, 204));
		currentGovernor.setText(CpuGovernors.getCurrentGovernor().toUpperCase());
		
		RotateAnimation rotate= (RotateAnimation)AnimationUtils.loadAnimation(this,R.drawable.rotateanimation);
		currentGovernor.setAnimation(rotate);
		
		govLayout.addView(currentGovernor);
		
		LinearLayout statusGovLayout = new LinearLayout(this);
		statusGovLayout.setOrientation(LinearLayout.HORIZONTAL);
		statusGovLayout.setLayoutParams(statusGovernorParams);
		statusGovLayout.addView(statusLayout);
		statusGovLayout.addView(govLayout);
		
		lcpuInfo.addView(statusGovLayout);
		
		// Display cpu temperature
		LinearLayout cpuTempLayout = new LinearLayout(this);
		cpuTempLayout.setOrientation(LinearLayout.VERTICAL);
		cpuTempLayout.setLayoutParams(paramsMem);
		cpuTemp = new TextView(this);
		cpuTemp.setTextColor(Color.WHITE);
		cpuTemp.setTypeface(Typeface.MONOSPACE);
		cpuTemp.setTextSize(15);
		cpuTemp.setLayoutParams(paramWith2Lines);
		cpuTempProgressBar.max(100);
		cpuTempProgressBar.setCurrentProgress(0);
		cpuTempLayout.addView(cpuTemp);
		cpuTempLayout.addView(cpuTempProgressBar);
		displayCpuTemp();
		lcpuInfo.addView(cpuTempLayout);

		// Display a separate line
		View separateL = new View(this);
		separateL.setLayoutParams(separateLine);
		separateL.setBackgroundColor(Color.rgb(237, 218, 116));
		lcpuInfo.addView(separateL);

		// Display Battery Status Title
		LinearLayout setCenter = new LinearLayout(this);
		setCenter.setOrientation(1); // 1 means vertical
		setCenter.setLayoutParams(titles);
		
		batteryStats = new TextView(this);
		batteryStats.setTextColor(Color.rgb(188, 198, 204));
		batteryStats.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
		batteryStats.setGravity(Gravity.CENTER);
		batteryStats.setTextSize(25);
		batteryStats.setLayoutParams(titles);
		batteryStats.setText(R.string.BatteryStatus);
		setCenter.addView(batteryStats);
		lcpuInfo.addView(setCenter);

		// Battery misc stats
		batteryStat = new BatteryStat(this);
		LinearLayout setCenterMisc = new LinearLayout(this);
		setCenter.setOrientation(1); // 1 means vertical
		setCenter.setLayoutParams(titles);

		batteryMiscStats = new TextView(this);
		batteryMiscStats.setTextColor(Color.rgb(86, 165, 236));
		batteryMiscStats.setTypeface(Typeface.SANS_SERIF);
		batteryMiscStats.setGravity(Gravity.CENTER);
		batteryMiscStats.setTextSize(15);
		batteryMiscStats.setLayoutParams(titles);
		setCenterMisc.addView(batteryMiscStats);
		displayBatteryStats();
		lcpuInfo.addView(setCenterMisc);

		// Battery temperature stat
		LinearLayout batteryTempLayout = new LinearLayout(this);
		batteryTempLayout.setOrientation(LinearLayout.VERTICAL);
		batteryTempLayout.setLayoutParams(paramsMem);
		batteryTempStat = new TextView(this);
		batteryTempStat.setTextColor(Color.WHITE);
		batteryTempStat.setTypeface(Typeface.MONOSPACE);
		batteryTempStat.setTextSize(15);
		batteryTempStat.setLayoutParams(paramWith2Lines);
		batteryTempProgressBar.max(100);
		batteryTempProgressBar.setCurrentProgress(0);
		batteryTempLayout.addView(batteryTempStat);
		batteryTempLayout.addView(batteryTempProgressBar);
		displayBatteryTemp();
		lcpuInfo.addView(batteryTempLayout);

		startCpuStatus();
		// *******************************************************************
	}

	private Runnable runnable = new Runnable() {
		@Override
		public void run() {
			ArrayList<Integer> stats = new ArrayList<Integer>();
			stats = cpuStats.toArrayList();

			// update memory usage
			long usageMem = memoryStat.getUsageMemory();
			memoryUsage.setText("Mem Usage: "
					+ (usageMem + " / " + totalMemory + "MB"));
			memoryUsageProgressBar.setCurrentProgress((int) usageMem);

			// update total cpu usage
			int totalCpuUsage = stats.get(0);
			totalCpu.setText("Cpu Usage: " + totalCpuUsage + "%");
			totalCpuLineProgressBar.setCurrentProgress(totalCpuUsage);

			// Update cpu's cores percentages and circle bar
			int corePer;
			for (int i = 0; i < cores; i++) {
				corePer = stats.get(i + 1);
				textViewList.get(i).setText("Core " + i + ": " + corePer + "%");
				circleProgressBars.get(i).setCurrentProgress(corePer);
			}

			TextView currentMin = (TextView) findViewById(R.id.currentMin);
			currentMin
					.setText("Current Min Freq: "
							+ ReadFile
									.getStringOfFile("/sys/devices/system/cpu/cpu0/cpufreq/scaling_min_freq") + " KHz");

			TextView currentMax = (TextView) findViewById(R.id.currentMax);
			currentMax
					.setText("Current Max Freq: "
							+ ReadFile
									.getStringOfFile("/sys/devices/system/cpu/cpu0/cpufreq/scaling_max_freq") + " KHz");

			TextView scalingCurrent = (TextView) findViewById(R.id.scalingCurrent);
			scalingCurrent
					.setText("Scaling Current Freq: "
							+ ReadFile
									.getStringOfFile("/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq") + " KHz");

			// Update cpu governor
			currentGovernor.setText(CpuGovernors.getCurrentGovernor().toUpperCase());
			
			// Update cpu temperature
			displayCpuTemp();

			// Update Battery stats
			displayBatteryStats();
			displayBatteryTemp();

			if (started) {
				startCpuStatus();
			}
		}
	};

	public void stopCpuStatus() {
		started = false;
		handler.removeCallbacks(runnable);
	}

	public void startCpuStatus() {
		started = true;
		handler.postDelayed(runnable, 2000);
	}

	private void displayCpuTemp() {
		int cpuT = Integer.parseInt(ReadFile
				.getStringOfFile("/sys/class/thermal/thermal_zone0/temp"));
		cpuTemp.setText("Cpu Temp: " + String.valueOf(cpuT) + " \u00b0C");
		cpuTempProgressBar.setCurrentProgress(cpuT);
	}

	private void displayBatteryStats() {
		String batMiscStats = "";
		if (batteryStat.isPresent) {
			batMiscStats += "Level: " + batteryStat.level + "% \n";
			batMiscStats += "Battery Plugged in : "
					+ batteryStat.getPlugTypeString(batteryStat.plugged) + "\n";
			batMiscStats += "Voltage = " + batteryStat.voltage + " mV";
			batMiscStats += " Health: "
					+ batteryStat.getHealthString(batteryStat.health) + "\n";
			batMiscStats += "Status: "
					+ batteryStat.getStatusString(batteryStat.status);
		} else {
			batMiscStats = "Battery not present!!!";
		}
		batteryMiscStats.setText(batMiscStats);
	}

	private void displayBatteryTemp() {
		String batteryTemp = "";
		float bTemp = (float) batteryStat.temperature / 10;
		if (batteryStat.isPresent) {
			batteryTemp = "Battery Temperature: " + bTemp + " \u00b0C \n";
			batteryTempProgressBar.setCurrentProgress(Math.round(bTemp));
		} else {
			batteryTemp = "Battery not present!!!";
		}
		batteryTempStat.setText(batteryTemp);
	}
}