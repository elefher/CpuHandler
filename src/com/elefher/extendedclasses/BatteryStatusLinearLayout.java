package com.elefher.extendedclasses;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cpu.handler.R;
import com.elefher.abstractclasses.CustomLinearLayoutOnTheFly;
import com.elefher.customclasses.BatteryStat;
import com.elefher.utils.MiscProgressBar;

/**
 * Created by elefher on 31/12/2014.
 */
public class BatteryStatusLinearLayout extends CustomLinearLayoutOnTheFly {

    Context cntx;
    TextView textViewMiscStats;
    public BatteryStat batteryStat;

    public BatteryStatusLinearLayout(Context cntx){
        this.cntx = cntx;
        textViewMiscStats = (TextView) ((Activity)cntx).getWindow().getDecorView().findViewById(R.id.batteryMiscStats);
        batteryStat = new BatteryStat(cntx);

        // Initialize
        layoutSettings();
        textViewSettings();
        setText();
        progressBarSettings();
        textViewAddView();
        progressBarAddView();
    }

    @Override
    public void layoutSettings() {
        layout = new LinearLayout(cntx);
        layoutParams();
        layout.setLayoutParams(layoutParams);
        layout.setOrientation(LinearLayout.VERTICAL);
    }

    @Override
    public void layoutParams() {
        layoutParams = new LinearLayout.LayoutParams(
                android.view.ViewGroup.LayoutParams.FILL_PARENT, 60);
        layoutParams.setMargins(20, 40, 20, 0);
    }

    @Override
    public void textViewSettings() {
        textView = new TextView(cntx);
        textViewParams();
        textView.setLayoutParams(textViewParams);
        textView.setTypeface(Typeface.MONOSPACE);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(15);
    }

    @Override
    public void textViewParams() {
        textViewParams = new LinearLayout.LayoutParams(
                android.view.ViewGroup.LayoutParams.FILL_PARENT, 50);
    }

    @Override
    public void setText() {
        textView.setText("Battery Temperature: Unknown");
        textViewMiscStats.setText(displayBatteryMiscStats());
    }

    @Override
    public void progressBarSettings() {
        progressBarParams();
        progressBar = new MiscProgressBar(cntx, R.drawable.lineprogressbar, progressBarParams);
        progressBar.max(45);
        progressBar.setCurrentProgress(0);
    }

    @Override
    public void progressBarParams() {
        progressBarParams = new LinearLayout.LayoutParams(
                android.view.ViewGroup.LayoutParams.FILL_PARENT, 10);
    }

    @Override
    public void setCurrentProgressBar() {
        String batteryTemp = "";
        float bTemp = (float) batteryStat.temperature / 10;
        if (batteryStat.isPresent) {
            batteryTemp = "Battery Temperature: " + bTemp + " \u00b0C \n";
            progressBar.setCurrentProgress(Math.round(bTemp));
        } else {
            batteryTemp = "Battery not present!";
        }
        textView.setText(batteryTemp);
    }

    @Override
    public void update() {
        setCurrentProgressBar();
        textViewMiscStats.setText(displayBatteryMiscStats());
    }

    private String displayBatteryMiscStats() {
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
            batMiscStats = "Battery not present!";
        }
        return batMiscStats;
    }
}