package com.elefher.extendedclasses;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cpu.handler.R;
import com.elefher.abstractclasses.CustomLinearLayoutOnTheFly;
import com.elefher.customclasses.CpuStat;
import com.elefher.customclasses.MemoryStat;
import com.elefher.utils.MiscProgressBar;

import java.util.ArrayList;

/**
 * Created by elefher on 31/12/2014.
 */
public class CpuUsageLinearLayout extends CustomLinearLayoutOnTheFly {

    Context cntx;
    CpuStat cpuStats;

    public CpuUsageLinearLayout(Context cntx){
        this.cntx = cntx;
        cpuStats = new CpuStat();

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
                android.view.ViewGroup.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
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
                android.view.ViewGroup.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void setText() {
        textView.setText("Cpu Usage: Unknown %");
    }

    @Override
    public void progressBarSettings() {
        progressBarParams();
        progressBar = new MiscProgressBar(cntx, R.drawable.lineprogressbar, progressBarParams);
        progressBar.max(100);
        progressBar.setCurrentProgress(0);
    }

    @Override
    public void progressBarParams() {
        progressBarParams = new LinearLayout.LayoutParams(
                android.view.ViewGroup.LayoutParams.FILL_PARENT, 10);
    }

    @Override
    public void setCurrentProgressBar() {
        ArrayList<Integer> stats = new ArrayList<Integer>();
        try{
            stats = cpuStats.toArrayList();
            // In index stats.get(0) located the total cpu usage
            progressBar.setCurrentProgress(stats.get(0));
            textView.setText("Cpu Usage: " + stats.get(0) + " %");
        } catch (NullPointerException e){
            e.printStackTrace();
            progressBar.setCurrentProgress(0);
            textView.setText("Cpu Usage: Unknown %");
        }
    }

    @Override
    public void update() {
        setCurrentProgressBar();
    }
}