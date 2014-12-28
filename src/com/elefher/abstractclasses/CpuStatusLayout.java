package com.elefher.abstractclasses;

import android.widget.LinearLayout;
import android.widget.TextView;
import com.elefher.utils.MiscProgressBar;

/**
 * Created by elefher on 28/12/2014.
 */
public abstract class CpuStatusLayout {
    protected LinearLayout layout, statusLayout;
    protected TextView textView;
    protected MiscProgressBar progressBar;
    protected LinearLayout.LayoutParams progressBarParams;

    public void textViewAddView(){
        layout.addView(textView);
    }

    public void progressBarAddView(){
        layout.addView(progressBar);
    }

    public void display(){
        statusLayout.setOrientation(LinearLayout.VERTICAL);
        statusLayout.addView(layout);
    }

    public LinearLayout getLayout(){
        return layout;
    }

    public abstract void layoutSettings();
    public abstract void layoutParams();
    public abstract void textViewSettings();
    public abstract void textViewParams();
    public abstract void progressBarSettings();
    public abstract void progressBarParams();
    public abstract void update();
}