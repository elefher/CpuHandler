package com.elefher.tab;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cpu.handler.R;
import com.elefher.customclasses.CpuStat;
import com.elefher.customclasses.DeviceInfo;
import com.elefher.extendedclasses.*;
import com.elefher.utils.ReadFile;

import java.util.ArrayList;

/**
 * Created by elefher on 28/12/2014.
 */
public class InfoRemake extends Activity {

    private static int cores = CpuStat.getNumCores();
    private ArrayList<CircularCpuStatus> circularCpuStatuses;
    LinearLayout cpuStatusesLL, cpuStatusLL;
    TextView currentMinFreq, currentMaxFreq, scalingCurrentFreq;
    GovernorLinearLayout governorLayout;
    CpuTemperatureLinearLayout cpuTemperatureLayout;
    RamLinearLayout ramLayout;
    CpuUsageLinearLayout cpuUsageLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_remake);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize variables
        cpuStatusesLL = (LinearLayout) findViewById(R.id.dynamic_content);
        cpuStatusLL = new LinearLayout(this);
        circularCpuStatuses = new ArrayList<CircularCpuStatus>(cores);
        currentMinFreq = (TextView) findViewById(R.id.currentMin);
        currentMaxFreq = (TextView) findViewById(R.id.currentMax);
        scalingCurrentFreq = (TextView) findViewById(R.id.scalingCurrent);
        governorLayout = new GovernorLinearLayout(this);
        cpuTemperatureLayout = new CpuTemperatureLinearLayout(this);
        ramLayout = new RamLinearLayout(this);
        cpuUsageLayout = new CpuUsageLinearLayout(this);

        // Set parameters in LinearLayouts
        cpuStatusLL.setOrientation(LinearLayout.VERTICAL);

        // Display standard device info like kernel, os etc.
        displayDeviceGeneralInfo();

        // Display total cpu cores in device status
        displayTotalCpuCores();

        // Display device general status
        displayDeviceGeneralStatus();

        // Display Ram usage
        displayRam();

        // Display Cpu usage
        displayCpuUsage();

        // Create text views about cpu status and governor
        displayCpuStatuses();
        displayCpuTemperature();
        displayGovernor();

        // Display a separate Line
        displaySeparateLine();
    }

    private void displayDeviceGeneralInfo(){
        TextView textCodeName = (TextView) findViewById(R.id.codename);
        textCodeName.setText(DeviceInfo.codeName);
        TextView textDevice = (TextView) findViewById(R.id.device);
        textDevice.setText(DeviceInfo.device);
        TextView textKernel = (TextView) findViewById(R.id.kernel);
        textKernel.setText(DeviceInfo.kernel);
        TextView textBrand = (TextView) findViewById(R.id.brand);
        textBrand.setText(DeviceInfo.brand);
    }

    private void displayTotalCpuCores(){
        TextView totalCpuCores = (TextView) findViewById(R.id.cores);
        totalCpuCores.setText("Total Cores: " + cores);
    }

    private void displayCpuStatuses(){
        /*
         * Create an instance of CircuralCpuStatus for every core.
         * Display every core as a circle with percentage in main layout
         */
        for (int i = 0; i < cores; i++) {
            circularCpuStatuses.add(i, new CircularCpuStatus(this, i));
            cpuStatusLL.addView(circularCpuStatuses.get(i).getLayout());
        }
        // Add to the main layout
        cpuStatusesLL.addView(cpuStatusLL);
    }

    /*
     * This function also used for update
     */
    private void displayDeviceGeneralStatus(){
        currentMinFreq.setText("Current Min Freq: " + getTargetString("scaling_min_freq") + " KHz");
        currentMaxFreq.setText("Current Max Freq: " + getTargetString("scaling_max_freq") + " KHz");
        scalingCurrentFreq.setText("Current Min Freq: " + getTargetString("scaling_cur_freq") + " KHz");
    }

    private void displayCpuTemperature(){
        cpuStatusesLL.addView(cpuTemperatureLayout.getLayout());
    }

    private void displayRam(){
        cpuStatusesLL.addView(ramLayout.getLayout());
    }

    private void displayCpuUsage(){
        cpuStatusesLL.addView(cpuUsageLayout.getLayout());
    }

    private void displayGovernor(){
        cpuStatusesLL.addView(governorLayout.getLayout());
    }

    private void displaySeparateLine(){
        LinearLayout.LayoutParams separateLine = new LinearLayout.LayoutParams(
                android.view.ViewGroup.LayoutParams.FILL_PARENT, 1);
        separateLine.topMargin = 10;
        View separateL = new View(this);
        separateL.setLayoutParams(separateLine);
        separateL.setBackgroundColor(Color.rgb(237, 218, 116));
        cpuStatusesLL.addView(separateL);
    }

    private String getTargetString(String targetString){
        String str = null;
        try{
            str = ReadFile.getStringOfFile(ReadFile.findFilePath(targetString,this));
        } catch (NullPointerException e){
            e.printStackTrace();
            str = "Unknown";
        }
        return str;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}