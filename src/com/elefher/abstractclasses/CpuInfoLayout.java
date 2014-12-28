package com.elefher.abstractclasses;

import android.app.Activity;
import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by elefher on 28/12/2014.
 */
public abstract class CpuInfoLayout{
    LinearLayout layout;
    TextView textView;
    public abstract void LayoutSettings(Context cntx);
    public abstract void TextViewSettings(Context cntx);
}