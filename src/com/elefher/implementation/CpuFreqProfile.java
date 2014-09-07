package com.elefher.implementation;

import java.util.List;

import com.elefher.cpuhandler.R;
import com.elefher.db.Profile;
import com.elefher.db.ProfilesDataSource;
import com.elefher.utils.CpuControl;
import com.elefher.utils.StringParser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class CpuFreqProfile implements DialogInterface.OnClickListener,
		OnClickListener {

	private Button setProfile;
	Activity activity;
	AlertDialog.Builder createProfiler, profiler, profileSelected;
	EditText profileName;
	Object checkedItem;

	public CpuFreqProfile(Activity activity) {
		this.activity = activity;
		setProfile = (Button) activity.findViewById(R.id.setCpuProfileButton);
		setProfile.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		profiler = new AlertDialog.Builder(activity);
		profiler.setSingleChoiceItems(convertAllProfilesToStringArray(), -1,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						ListView lw = ((AlertDialog)dialog).getListView();
						checkedItem = lw.getAdapter().getItem(lw.getCheckedItemPosition());
					}
				});
		profiler.setIcon(R.drawable.ic_launcher);
		profiler.setTitle("Profile: Create or set your cpu profile");
		profiler.setPositiveButton("Select", this);
		profiler.setNegativeButton("Cancel", this);
		profiler.setNeutralButton("Create", this).setCancelable(false);

		profiler.show();
	}

	@Override
	public void onClick(DialogInterface arg0, int arg1) {
		switch (arg1) {
		case DialogInterface.BUTTON_POSITIVE: // select
			chooseProfile();
			break;
		case DialogInterface.BUTTON_NEGATIVE: // cancel
			break;
		case DialogInterface.BUTTON_NEUTRAL: // create
			createCpuProfileDialog();
			break;
		default:
			// nothing
			break;
		}
	}

	private void createCpuProfileDialog() {
		/*
		 * Create a completed linearLayout for alert dialog.
		 */
		LinearLayout.LayoutParams profileDialogParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
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
					}
				});
		createProfiler.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub

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
		String[] availableFreqs = CpuControl.getAvailableFreequencies();
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
					}
				});
		profileSelected.setNeutralButton("DELETE",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						deleteProfile();
					}

				});
		profileSelected.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub

					}

				});

		profileSelected.show();
	}
	
	private void deleteProfile(){
		ProfilesDataSource datasource = new ProfilesDataSource(activity);
		datasource.open();

		datasource.deletePlayer(Integer.parseInt(StringParser.getIdOfString(checkedItem.toString())));
		datasource.close();
	}
	
	private void setProfileToCpu(){
		String newMinFreq = StringParser.getFrequenciesOfString(checkedItem.toString()).split(" ")[0];
		String newMaxFreq = StringParser.getFrequenciesOfString(checkedItem.toString()).split(" ")[1];
		
		Boolean isChanged = false;
		CpuControl cpuControl = new CpuControl(activity);
		isChanged = cpuControl.setCpuFrequencies(newMinFreq, newMaxFreq);
		if (isChanged) {
			// Initialize current frequencies
			float curMinFreq = Float.parseFloat(CpuControl.getCurrentMinCpuFreq());
			float curMaxFreq = Float.parseFloat(CpuControl.getCurrentMaxCpuFreq());
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