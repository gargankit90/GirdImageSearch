package com.agarg.girdimagesearch;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.agarg.adapter.ImageResultArrayAdapter;
import com.agarg.model.ImageResult;
import com.agarg.model.SettingsData;
import com.agarg.utils.EndlessScrollListener;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

public class SearchActivity extends Activity {
	EditText etQuery;
	Button btnSearch;
	GridView gvResults;
	ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
	ImageResultArrayAdapter imageAdapter;
	SettingsData settings;
	private final String TAG= "SearchActivity"; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		setupViews();
		imageAdapter = new ImageResultArrayAdapter(this, imageResults);
		gvResults.setAdapter(imageAdapter);
		
		SharedPreferences  mPrefs = getPreferences(MODE_PRIVATE);
		// Retrieve stored preferences.
	    Gson gson = new Gson();
	    String json = mPrefs.getString("SettingsData", "");
	    settings = gson.fromJson(json, SettingsData.class);
	    if(settings == null){
	    	settings = SettingsData.getInstance();
	    }
		Log.d("########## Settings object values inside oncreate for search", settings.toString());
		gvResults.setOnScrollListener(new EndlessScrollListener() {
			
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				// TODO Auto-generated method stub
				customLoadMoreDataFromApi(page); 
			}
		} );
	}
  
	// Append more data into the adapter
    public void customLoadMoreDataFromApi(int offset) {
      // This method probably sends out a network request and appends new data items to your adapter. 
      // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
      // Deserialize API response and then construct new objects to append to the adapter
    	if(settings.getStart() == -1){
    		return;
    	}
		String reqUrl = settings.getRequestUrl();
		Log.d("Search Application","hitting google with"+reqUrl);
		AsyncHttpClient httpClient = new AsyncHttpClient();
		httpClient.addHeader("Referer", "GridImageSearch");
		httpClient.get(reqUrl, new JsonHttpResponseHandler(){

			@Override
			public void onSuccess(JSONObject response) {
				JSONArray imageJSONResults = null;
				JSONArray responsePages = null;
				int currentPageIndex;
				try {
					imageJSONResults = response.getJSONObject("responseData").getJSONArray("results");
					imageAdapter.addAll(ImageResult.fromJSONArray(imageJSONResults));
					Log.d(TAG, imageResults.toString());
					responsePages = response.getJSONObject("responseData").getJSONObject("cursor").getJSONArray("pages");
					currentPageIndex = response.getJSONObject("responseData").getJSONObject("cursor").getInt("currentPageIndex");
					currentPageIndex++;
					if(currentPageIndex < responsePages.length()){
						settings.setStart(responsePages.getJSONObject(currentPageIndex).getInt("start"));
						Log.d("updated start",settings.getStart()+"");
					}else{
						settings.setStart(-1);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
    }
	
    public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	};
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.miSetting:
//	            composeMessage();
	        	Log.d(TAG,"MenuItem clicked");
	        	Intent i = new Intent(getApplicationContext(),SettingsActivity.class);
	        	startActivity(i);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	protected void setupViews() {
		etQuery = (EditText) findViewById(R.id.etQuery);
		btnSearch = (Button) findViewById(R.id.btnSearch);
		btnSearch.setOnClickListener(searchClickListener);
		gvResults = (GridView) findViewById(R.id.gvResults);
//		gvResults.setOnClickListener(searchClickListener);
		gvResults.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.d(TAG, "ImageItem clicked");
				Intent i = new Intent(getApplicationContext(), ImageDisplayActivity.class);
				ImageResult imgInfo = imageResults.get(position);
				i.putExtra("url", imgInfo.getFullUrl());
				startActivity(i);
			}
		
		});
		
	}
	
	OnClickListener searchClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// If user clicked on search button
			if(v.getId() == R.id.btnSearch){
				String searchQuery =etQuery.getText().toString(); 
				Log.d("Search Application", "searching for"+searchQuery);
				//settings = SettingsData.getInstance();
				settings.setQuery(searchQuery);
				settings.setStart(0);
				String reqUrl = settings.getRequestUrl();
				Log.d("Search Application","hitting google with"+reqUrl);
				AsyncHttpClient httpClient = new AsyncHttpClient();
				httpClient.addHeader("Referer", "GridImageSearch");
				httpClient.get(reqUrl, new JsonHttpResponseHandler(){

					@Override
					public void onSuccess(JSONObject response) {
						JSONArray imageJSONResults = null;
						JSONArray responsePages = null;
						int currentPageIndex;
						try {
							imageJSONResults = response.getJSONObject("responseData").getJSONArray("results");
							imageResults.clear();
							imageAdapter.addAll(ImageResult.fromJSONArray(imageJSONResults));
							Log.d(TAG, imageResults.toString());
							responsePages = response.getJSONObject("responseData").getJSONObject("cursor").getJSONArray("pages");
							currentPageIndex = response.getJSONObject("responseData").getJSONObject("cursor").getInt("currentPageIndex");
							Log.d("#################current page index",currentPageIndex+"" );
							currentPageIndex++;
							Log.d("#################updated current page index",currentPageIndex+"" );
							if(currentPageIndex < responsePages.length()){
					
								settings.setStart(responsePages.getJSONObject(currentPageIndex).getInt("start"));
								Log.d("#####################updated start",settings.getStart()+"");
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				});
			}
		}
	};
	
}
