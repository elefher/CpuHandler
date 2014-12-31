package com.elefher.extendedclasses;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cpu.handler.R;
import com.elefher.abstractclasses.CustomLinearLayoutOnTheFly;
import com.elefher.customclasses.CpuStat;
import com.elefher.utils.MiscProgressBar;

import java.util.ArrayList;

/**
 * Created by elefher on 28/12/2014.
 */
public class CircularCpuStatus extends CustomLinearLayoutOnTheFly{

    Context cntx;
    CpuStat cpuStats;
    int id;

    public CircularCpuStatus(Context cntx, int id){
        this.cntx = cntx;
        cpuStats = new CpuStat();
        this.id = id;

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
        layout.setOrientation(LinearLayout.HORIZONTAL);
    }

    @Override
    public void layoutParams() {
        layoutParams = new LinearLayout.LayoutParams(
                android.view.ViewGroup.LayoutParams.FILL_PARENT, 120);
        layoutParams.topMargin = 10;
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
        textViewParams = new LinearLayout.LayoutParams(300,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        textViewParams.setMargins(20, 40, 0, 0);
    }

    @Override
    public void setText() {
        textView.setText("Core " + id + ": ");
    }

    @Override
    public void progressBarSettings() {
        progressBarParams();
        progressBar = new MiscProgressBar(cntx, R.drawable.ringprogressbar, progressBarParams);
        progressBar.max(100);
        progressBar.rotation(110);
        progressBar.setCurrentProgress(0);
    }

    @Override
    public void progressBarParams() {
        progressBarParams = new LinearLayout.LayoutParams(
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        progressBarParams.width = 120;
        progressBarParams.height = 120;
    }

    @Override
    public void setCurrentProgressBar() {
        ArrayList<Integer> stats = new ArrayList<Integer>();
        try{
            stats = cpuStats.toArrayList();
            // After index 0 in stats.get(index) located the current cpu status
            progressBar.setCurrentProgress(stats.get(id+1));
            textView.setText("Core " + id + ": " + stats.get(id+1) + " %");
        } catch (NullPointerException e){
            e.printStackTrace();
            progressBar.setCurrentProgress(0);
            textView.setText("Core " + id + ": Unknown %");
        }
    }

    @Override
    public void update() { setCurrentProgressBar(); }
}