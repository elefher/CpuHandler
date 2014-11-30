package com.elefher.extendedclasses;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.elefher.abstractclasses.AlertDialogUtils;
import com.cpu.tuner.R;
import com.elefher.customclasses.CpuControl;
import com.elefher.db.Profile;
import com.elefher.db.ProfilesDataSource;
import com.elefher.utils.StringParser;

public class AlertProfiles extends AlertDialogUtils {

	Activity activity;
	AlertDialog.Builder createProfiler, profiler, profileSelected;
	EditText profileName;
	
	public AlertProfiles(Activity act) {
		super(act);
		activity = act;

		// Set id button
		createButton(R.id.setCpuProfileButton);

		// Set available governors to dialog
		setItems(convertAllProfilesToStringArray());

		// Set icon and tile for the dialog
		setIcon(R.drawable.ic_launcher);
		setTitle("Profile: Create or set your cpu profile");

		/*
		 * Set positive, negative and neutral button
		 */
		setPositiveButton("Select", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {	
				chooseProfile();
			}
		});

		setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

			}
		});
		
		setNeutralButton("Create", new DialogInterface.OnClickListener(){
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				createCpuProfileDialog();
			}
		});
	}
	
	private void createCpuProfileDialog() {
		/*
		 * Create a completed linearLayout for alert dialog.
		 */
		LinearLayout.LayoutParams profileDialogParams = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
		profileDialogParams.weight = 1;

		LinearLayout profileNameLayout = new LinearLayout(activity);
		profileNameLayout.setOrientation(LinearLayout.HORIZONTAL);

		TextView profileNameTitle = new TextView(activity);
		profileNameTitle.setText("Set Profile Name:");
		profileNameTitle.setLayoutParams(profileDialogParams);

		profileName = new EditText(activity);
		profileName.setLayoutParams(profileDialogParams);

		profileNameLayout.addView(profileNameTitle);
		profileNameLayout.addView(profileName);

		/*
		 * Create the alert dialog
		 */
		createProfiler = new AlertDialog.Builder(activity);
		createProfiler.setView(profileNameLayout);
		createProfiler.setIcon(R.drawable.ic_launcher);
		createProfiler.setTitle("Welcome to create profiler");
		createProfiler.setPositiveButton("Save",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						String name = profileName.getText().toString();
						if (name.isEmpty()) {
							return;
						}
						saveProfile();
						
						// Update available governors to dialog
						setItems(convertAllProfilesToStringArray());
						
						// Delete getStringItem if needs
						getStringItem = "";
					}
				});
		createProfiler.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// Delete getStringItem if needs
						getStringItem = "";
					}

				});

		createProfiler.show();
	}

	private void saveProfile() {
		String minFreq = getFrequencyOfSeek()[0];
		String maxFreq = getFrequencyOfSeek()[1];

		ProfilesDataSource datasource = new ProfilesDataSource(activity);
		datasource.open();

		Profile profile = datasource.createProfile(profileName.getText()
				.toString(), minFreq, maxFreq);
		datasource.close();

		if (profile == null) {
			return;
		}
		Toast.makeText(
				activity,
				"The profile " + profile.getProfile() + " created with fmin: "
						+ profile.getMinFreq() + " MHz & fmax: "
						+ profile.getMaxFreq() + " MHz", Toast.LENGTH_LONG)
				.show();
	}

	private String[] getFrequencyOfSeek() {
		SeekBar minBar = (SeekBar) activity.findViewById(R.id.minFreqSeek);
		SeekBar maxBar = (SeekBar) activity.findViewById(R.id.maxFreqSeek);
		String[] availableFreqs = CpuControl.getAvailableFreequencies(activity);
		String[] frequencies = { availableFreqs[minBar.getProgress()],
				availableFreqs[maxBar.getProgress()] };
		return frequencies;
	}

	private List<Profile> getAllProfiles() {
		ProfilesDataSource datasource = new ProfilesDataSource(activity);
		datasource.open();

		List<Profile> profile = datasource.getAllProfiles();
		datasource.close();

		return profile;
	}

	private String[] convertAllProfilesToStringArray() {
		List<Profile> allProfiles = getAllProfiles();
		int profileSize = allProfiles.size();
		String[] convertedProfiles = new String[profileSize];

		if (allProfiles.isEmpty()) {
			return null;
		}

		for (int i = 0; i < profileSize; i++) {
			convertedProfiles[i] = allProfiles.get(i).getId() + " "
					+ allProfiles.get(i).getProfile() + " freqL: "
					+ allProfiles.get(i).getMinFreq() + " freqM: "
					+ allProfiles.get(i).getMaxFreq() + "";
		}

		return convertedProfiles;
	}

	private void chooseProfile() {
		profileSelected = new AlertDialog.Builder(activity);
		profileSelected
				.setMessage("Choose SET to active the profile or DELETE to delete it");
		profileSelected.setIcon(R.drawable.ic_launcher);
		profileSelected.setTitle("Profile");
		profileSelected.setPositiveButton("SET",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						setProfileToCpu();
						// Delete getStringItem if needs
						getStringItem = "";
					}
				});
		profileSelected.setNeutralButton("DELETE",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						deleteProfile();
						// Update available governors to dialog
						setItems(convertAllProfilesToStringArray());
						// Delete getStringItem if needs
						getStringItem = "";
					}
				});
		profileSelected.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// Delete getStringItem if needs
						getStringItem = "";
					}

				});

		profileSelected.show();
	}
	
	private void deleteProfile(){
		if(getStringItem == "")
			return;
		
		ProfilesDataSource datasource = new ProfilesDataSource(activity);
		datasource.open();

		datasource.deletePlayer(Integer.parseInt(StringParser.getIdOfString(getStringItem.toString())));
		datasource.close();
	}
	
	private void setProfileToCpu(){
		if(getStringItem == "")
			return;
		String newMinFreq = StringParser.getFrequenciesOfString(getStringItem.toString()).split(" ")[0];
		String newMaxFreq = StringParser.getFrequenciesOfString(getStringItem.toString()).split(" ")[1];
		
		Boolean isChanged = false;
		CpuControl cpuControl = new CpuControl(activity);
		isChanged = CpuControl.setCpuFrequencies(newMinFreq, newMaxFreq, activity);
		if (isChanged) {
			// Initialize current frequencies
			float curMinFreq = Float.parseFloat(CpuControl.getCurrentMinCpuFreq(activity));
			float curMaxFreq = Float.parseFloat(CpuControl.getCurrentMaxCpuFreq(activity));
			// Update the initial Freq values as text
			TextView minFreqText = (TextView) activity.findViewById(R.id.minFreq);
			TextView maxFreqText = (TextView) activity.findViewById(R.id.maxFreq);
			minFreqText.setText("Min Freq: " + curMinFreq / 1000 + " MHz");
			maxFreqText.setText("Max Freq: " + curMaxFreq / 1000 + " MHz");
			Toast.makeText(activity, "Cpu Profile Enabled!!!",
					Toast.LENGTH_LONG).show();
		}else{
			Toast.makeText(activity, "Sorry But Something Went Wrong!!!",
					Toast.LENGTH_LONG).show();
		}
	}
}
