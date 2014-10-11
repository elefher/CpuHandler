package com.elefher.cpuhandler;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

import com.elefher.customclasses.CpuControl;
import com.elefher.customclasses.CpuGovernors;
import com.elefher.customclasses.OnBoot;
import com.elefher.utils.CpuUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ServicesAfterBooting extends BroadcastReceiver {

	private final String overclock = OnBoot.SYSTEM_INITD + "99overclock.sh";
	private final String governor = OnBoot.SYSTEM_INITD + "99governor.sh";

	public ServicesAfterBooting() {
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		File freqFile = new File(overclock);
		File govFile = new File(governor);

		if (freqFile.exists()) {
			setOnBootFreqs(context);
		}

		if (govFile.exists()) {
			setOnBootGov(context);
		}
	}

	private void setOnBootFreqs(Context context) {
		if (executeFile(overclock)) {
			Toast.makeText(context,
					"Trickle Cpu Handler: frequencies changed !!!",
					Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(context,
					"Trickle Cpu Handler: frequencies cannot changed !!!",
					Toast.LENGTH_LONG).show();
		}
	}

	private void setOnBootGov(Context context) {
		if (executeFile(governor)) {
			Toast.makeText(context,
					"Trickle Cpu Handler: governor changed !!! ",
					Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(context,
					"Trickle Cpu Handler: governor cannot changed !!! ",
					Toast.LENGTH_LONG).show();
		}
	}

	private boolean executeFile(String file) {
		Process su = null;
		try {
			su = Runtime.getRuntime().exec("su");
		    DataOutputStream outputStream = new DataOutputStream(su.getOutputStream());

		    outputStream.writeBytes("sh ./"+file+"\n");
		    outputStream.flush();

		    outputStream.writeBytes("exit\n");
		    outputStream.flush();
		    su.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (su != null) {
				su.destroy();
				return true;
			}
			return false;
		}
	}
}