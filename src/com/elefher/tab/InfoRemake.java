package com.elefher.tab;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cpu.handler.R;
import com.elefher.customclasses.CpuStat;
import com.elefher.customclasses.DeviceInfo;
import com.elefher.extendedclasses.CircularCpuStatus;

import java.util.ArrayList;

/**
 * Created by elefher on 28/12/2014.
 */
public class InfoRemake extends Activity {

    private static int cores = CpuStat.getNumCores();
    private ArrayList<CircularCpuStatus> circularCpuStatuses;
    LinearLayout cpuStatusesLL, cpuStatusLL;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_remake);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize variables
        cpuStatusesLL = (LinearLayout) findViewById(R.id.dynamic_content);
        cpuStatusLL = new LinearLayout(this);
        circularCpuStatuses = new ArrayList<CircularCpuStatus>(cores);

        // Set parameters in LinearLayouts
        cpuStatusLL.setOrientation(LinearLayout.VERTICAL);

        // display standard device info like kernel, os etc.
        displayDeviceGeneralInfo();

        // Create text views about cpu status
        displayCpuStatuses();
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