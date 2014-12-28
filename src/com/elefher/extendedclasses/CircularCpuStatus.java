package com.elefher.extendedclasses;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cpu.handler.R;
import com.elefher.abstractclasses.CpuStatusLayout;
import com.elefher.utils.MiscProgressBar;

/**
 * Created by elefher on 28/12/2014.
 */
public class CircularCpuStatus extends CpuStatusLayout{

    Context cntx;
    int id;
    public CircularCpuStatus(Context cntx, int id){
        this.cntx = cntx;
        this.id = id;

        // Initialize
        layoutSettings();
        textViewSettings();
        progressBarSettings();
        textViewAddView();
        progressBarAddView();
    }

    @Override
    public void layoutSettings() {
        layout = new LinearLayout(cntx);
        layoutParams();
        layout.setOrientation(LinearLayout.HORIZONTAL);
    }

    @Override
    public void layoutParams() {

    }

    @Override
    public void textViewSettings() {
        textView = new TextView(cntx);
        textViewParams();
        textView.setTypeface(Typeface.MONOSPACE);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(15);
        textView.setText("Core " + id + ": ");
    }

    @Override
    public void textViewParams() {

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
    public void update() {

    }
}