package com.elefher.abstractclasses;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public abstract class AlertDialogUtils extends AlertDialog.Builder implements
		OnClickListener {

	Activity activity;
	private Button button;
	public String[] strItems;
	public String getStringItem = "";

	public AlertDialogUtils(Activity act) {
		super(act);
		activity = act;
	}

	public void createButton(int rIdButton) {
		button = (Button) activity.findViewById(rIdButton);
		button.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		singleItemChoices();
	}

	public void singleItemChoices() {
		if(strItems == null)
			return;
		
		setSingleChoiceItems(strItems, -1,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						ListView lw = ((AlertDialog) dialog).getListView();
						getStringItem = lw.getAdapter().getItem(lw.getCheckedItemPosition()).toString();
					}
				});
		show();
	}
	
	public void setItems(String[] strArray){
		if(strArray == null){
			strItems = new String[1];
			strItems[0] = "";
			return;
		}
		
		int strLength = strArray.length;
		strItems = new String[strLength];
		strItems = strArray;
	}
}