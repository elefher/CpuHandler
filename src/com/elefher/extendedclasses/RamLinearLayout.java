package com.elefher.extendedclasses;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cpu.handler.R;
import com.elefher.abstractclasses.CustomLinearLayoutOnTheFly;
import com.elefher.customclasses.MemoryStat;
import com.elefher.utils.MiscProgressBar;

/**
 * Created by elefher on 31/12/2014.
 */
public class RamLinearLayout extends CustomLinearLayoutOnTheFly {

    Context cntx;
    MemoryStat memoryStat;

    public RamLinearLayout(Context cntx){
        this.cntx = cntx;
        memoryStat = new MemoryStat(cntx);

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
        textView.setText("Ram Usage: Unknown MB");
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
        try{
            progressBar.setCurrentProgress((int)memoryStat.getUsageMemory());
            textView.setText("Ram Usage: " + memoryStat.getUsageMemory() + " MB");
        } catch (NullPointerException e){
            e.printStackTrace();
            progressBar.setCurrentProgress(0);
            textView.setText("Ram Usage: Unknown MB");
        }
    }

    @Override
    public void update() {
        setCurrentProgressBar();
    }
}