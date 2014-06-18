package com.agarg.girdimagesearch;

import java.util.ArrayList;

import com.agarg.model.SettingsData;
import com.google.gson.Gson;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class SettingsActivity extends Activity implements OnItemSelectedListener {
	Spinner spSize;
	Spinner spColorFilter;
	Spinner spImgType;
	EditText etSiteFilter;
	Button btnSave;
	SettingsData settingsDataObj;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		spSize = (Spinner) findViewById(R.id.spSize);
		spColorFilter = (Spinner) findViewById(R.id.spColorFilter);
		spImgType = (Spinner) findViewById(R.id.spImgType);
		etSiteFilter = (EditText) findViewById(R.id.etSiteFilter);
		btnSave = (Button) findViewById(R.id.btnSave);
		spSize.setOnItemSelectedListener(this);
		spColorFilter.setOnItemSelectedListener(this);
		spImgType.setOnItemSelectedListener(this);
//		settingsDataObj = SettingsData.getInstance();
		SharedPreferences  mPrefs = getPreferences(MODE_PRIVATE);
		// Retrieve stored preferences.
	    Gson gson = new Gson();
	    String json = mPrefs.getString("SettingsData", "");
	    settingsDataObj = gson.fromJson(json, SettingsData.class);
	    if(settingsDataObj == null){
	    	settingsDataObj = SettingsData.getInstance();
	    }else{
//	    	ArrayList<String> arrSize = R.array.strArrImgSize;
//	    	spSize.setSelection(R.array.strArrImgSize);
	    }
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		if(parent.getId() == R.id.spSize){
			Log.d("Drop down selected", "size"+parent.getItemAtPosition(position));	
			settingsDataObj.setImgSize((String)parent.getItemAtPosition(position));
			SharedPreferences  mPrefs = getPreferences(MODE_PRIVATE);
			 Editor prefsEditor = mPrefs.edit();
		     Gson gson = new Gson();
		     String json = gson.toJson(settingsDataObj);
		     prefsEditor.putString("SettingsData", json);
		     prefsEditor.commit();
		}if(parent.getId() == R.id.spColorFilter){
			Log.d("Drop down selected", "color"+parent.getItemAtPosition(position));
			settingsDataObj.setColorFilter((String)parent.getItemAtPosition(position));
		}if(parent.getId() == R.id.spImgType){
			Log.d("Drop down selected", "img type"+parent.getItemAtPosition(position));
			settingsDataObj.setImageType((String)parent.getItemAtPosition(position));
		}
		
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
	
	
}
