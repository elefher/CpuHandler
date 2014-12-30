package com.elefher.extendedclasses;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.elefher.abstractclasses.CustomLinearLayoutOnTheFly;
import com.elefher.customclasses.CpuGovernors;

/**
 * Created by elefher on 30/12/2014.
 */
public class GovernorLinearLayout extends CustomLinearLayoutOnTheFly {

    Context cntx;

    public GovernorLinearLayout(Context cntx){
        this.cntx = cntx;

        // Initialize
        layoutSettings();
        textViewSettings();
        setText();
        textViewAddView();
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
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
    }

    @Override
    public void textViewSettings() {
        textView = new TextView(cntx);
        textViewParams();
        textView.setTypeface(Typeface.MONOSPACE);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(15);
    }

    @Override
    public void textViewParams() {

    }

    @Override
    public void setText() {
        textView.setText("Governor: " + CpuGovernors.getCurrentGovernor(cntx)
                .toUpperCase());
    }

    @Override
    public void progressBarSettings() {
    }

    @Override
    public void progressBarParams() {
    }

    @Override
    public void update() {

    }
}