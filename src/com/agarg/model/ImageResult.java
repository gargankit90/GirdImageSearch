package com.agarg.model;

import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class ImageResult {
	private String fullUrl;
	private String tbUrl;
	private final String FULL_IMAGE_URL = "url";
	private final String THUMBNAIL_IMAGE_URL = "tbUrl";
	private final String TAG = "ImageResult";
	public ImageResult(String fullUrl, String tbUrl) {
		
		this.fullUrl = fullUrl;
		this.tbUrl = tbUrl;
	}
	
	public ImageResult(JSONObject json){
		try {
			this.setFullUrl(json.getString(FULL_IMAGE_URL));
			this.setTbUrl(json.getString(THUMBNAIL_IMAGE_URL));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.e(this.TAG, "Error in parsing JSON");
			e.printStackTrace();
		}

	}
	
	public String getFullUrl() {
		return fullUrl;
	}

	public String getTbUrl() {
		return tbUrl;
	}
	
	public void setFullUrl(String fullUrl) {
		this.fullUrl = fullUrl;
	}

	public void setTbUrl(String tbUrl) {
		this.tbUrl = tbUrl;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Full Url : "+this.fullUrl+" tbUrl : "+this.tbUrl;
	}

	public static Collection<ImageResult> fromJSONArray(
			JSONArray imageJSONResults) {
		ArrayList<ImageResult> results = new ArrayList<ImageResult>();
		for (int i = 0; i < imageJSONResults.length(); i++) {
			try {
				results.add(new ImageResult(imageJSONResults.getJSONObject(i)));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return results;
	}
}
