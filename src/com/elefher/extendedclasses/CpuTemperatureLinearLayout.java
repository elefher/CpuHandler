package com.elefher.extendedclasses;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cpu.handler.R;
import com.elefher.abstractclasses.CustomLinearLayoutOnTheFly;
import com.elefher.utils.MiscProgressBar;
import com.elefher.utils.ReadFile;

/**
 * Created by elefher on 30/12/2014.
 */
public class CpuTemperatureLinearLayout extends CustomLinearLayoutOnTheFly {

    Context cntx;
    public CpuTemperatureLinearLayout(Context cntx){
        this.cntx = cntx;

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
        layoutParams.setMargins(20, 80, 20, 0);
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
        textView.setText("Cpu Temp: Unknown \u00b0C");
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
        String str = null;
        try{
            str = ReadFile.getStringOfFile(ReadFile.findFilePath("temp", cntx));
            float tempVal = Integer.parseInt(str) / 1000;
            progressBar.setCurrentProgress((int)tempVal);
            textView.setText("Cpu Temp: " + tempVal + " \u00b0C");
        } catch (NullPointerException e){
            e.printStackTrace();
            progressBar.setCurrentProgress(0);
            textView.setText("Cpu Temp: Unknown \u00b0C");
        }
    }

    @Override
    public void update() {
        setCurrentProgressBar();
    }
}