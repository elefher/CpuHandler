package com.elefher.tab;

import com.elefher.cpuhandler.R;
import com.elefher.customclasses.SetVoltagesButton;
import com.elefher.extendedclasses.SetCpuVoltage;

import android.os.Bundle;
import android.app.Activity;
 
public class Voltages extends Activity {
 
    /** Called when the activity is first created. */
       
      @Override
      public void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.voltages);
          
          SetCpuVoltage alertChangeCpuVoltages = new SetCpuVoltage(this);
          alertChangeCpuVoltages.setValue("33333");
          alertChangeCpuVoltages.createReachEditText(R.id.voltagesEdit, "freq KHz :");
          
          SetCpuVoltage alertChangeCpuVoltages2 = new SetCpuVoltage(this);
          alertChangeCpuVoltages2.setValue("33333");
          alertChangeCpuVoltages2.createReachEditText(R.id.voltagesEdit, "freq");
          
          SetVoltagesButton setVoltBtn = new SetVoltagesButton(this, "Set Volts");
          setVoltBtn.positionButton(R.id.voltagesEdit);
          setVoltBtn.enableButton();
        }
}