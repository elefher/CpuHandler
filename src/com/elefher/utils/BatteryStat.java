package com.elefher.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;

public class BatteryStat {
	
	public int plugged, scale, health, status,
	rawlevel, voltage, temperature, level;
	public boolean isPresent;
	public String technology;
	
	public BatteryStat(Context context){
		IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		context.registerReceiver(battery_receiver, filter);
	}

	private BroadcastReceiver battery_receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			isPresent = intent.getBooleanExtra("present", false);
			technology = intent.getStringExtra("technology");
			plugged = intent.getIntExtra("plugged", -1);
			scale = intent.getIntExtra("scale", -1);
			health = intent.getIntExtra("health", 0);
			status = intent.getIntExtra("status", 0);
			rawlevel = intent.getIntExtra("level", -1);
			voltage = intent.getIntExtra("voltage", 0);
			temperature = intent.getIntExtra("temperature", 0);
			level = 0;

			Bundle bundle = intent.getExtras();

			Log.i("BatteryLevel", bundle.toString());

			if (isPresent) {
				if (rawlevel >= 0 && scale > 0) {
					level = (rawlevel * 100) / scale;
				}

				String info = "Battery Level: " + level + "%\n";
				info += ("Technology: " + technology + "\n");
				info += ("Plugged: " + getPlugTypeString(plugged) + "\n");
				info += ("Health: " + getHealthString(health) + "\n");
				info += ("Status: " + getStatusString(status) + "\n");
				info += ("Voltage: " + voltage + "\n");
				info += ("Temperature: " + temperature + "\n");

				//setBatteryLevelText(info + "\n\n" + bundle.toString());
				//System.out.println(info + "\n\n" + bundle.toString());
			} else {
				//setBatteryLevelText("Battery not present!!!");
				//System.out.println("Battery not present!!!");
			}
		}
	};
	
	public String getPlugTypeString(int plugged) {
        String plugType = "Unknown";
 
        switch (plugged) {
        case BatteryManager.BATTERY_PLUGGED_AC:
            plugType = "AC";
            break;
        case BatteryManager.BATTERY_PLUGGED_USB:
            plugType = "USB";
            break;
        }
 
        return plugType;
    }
	
	public String getHealthString(int health) {
		String healthString = "Unknown";

		switch (health) {
		case BatteryManager.BATTERY_HEALTH_DEAD:
			healthString = "Dead";
			break;
		case BatteryManager.BATTERY_HEALTH_GOOD:
			healthString = "Good";
			break;
		case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
			healthString = "Over Voltage";
			break;
		case BatteryManager.BATTERY_HEALTH_OVERHEAT:
			healthString = "Over Heat";
			break;
		case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
			healthString = "Failure";
			break;
		}

		return healthString;
	}
	
	public String getStatusString(int status) {
		String statusString = "Unknown";

		switch (status) {
		case BatteryManager.BATTERY_STATUS_CHARGING:
			statusString = "Charging";
			break;
		case BatteryManager.BATTERY_STATUS_DISCHARGING:
			statusString = "Discharging";
			break;
		case BatteryManager.BATTERY_STATUS_FULL:
			statusString = "Full";
			break;
		case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
			statusString = "Not Charging";
			break;
		}

		return statusString;
	}
}