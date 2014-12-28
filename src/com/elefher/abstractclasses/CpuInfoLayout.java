package com.elefher.abstractclasses;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.elefher.utils.MiscProgressBar;

/**
 * Created by elefher on 28/12/2014.
 */
public abstract class CpuInfoLayout {
    LinearLayout layout;
    TextView textView;
    MiscProgressBar progressBar;

    public abstract void LayoutSettings(Context cntx);
    public abstract void TextViewSettings(Context cntx);
    public abstract void progressBarSettings(Context cntx, int Rdrawable, LinearLayout.LayoutParams paramsCircle);
}