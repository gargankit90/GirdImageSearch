package com.agarg.model;

import android.net.Uri;

public class SettingsData {
	
	private final String BASE_URL = "https://ajax.googleapis.com/ajax/services/search/images";
	private final String VERSION = "1.0"; // Has to be URL encoded as its a query parameter.
	private final int RESULTSIZE = 8;
	private int start = 0;
	private String imgSize = "";
	private String colorFilter = "";
	private String imageType = "";
	private String siteRestrict = "";
	private String query = "";
	
	public SettingsData() {
		
	}
	private static SettingsData settingsDataObj = new SettingsData();
	
	public static SettingsData getInstance( ) {
		return settingsDataObj;
	}
	
	public String getQuery() {
		return query;
	}

	public int getStart() {
		return start;
	}
	public String getImgSize() {
		//some logic to convert values to actual values
		return imgSize;
	}
	public String getColorFilter() {
		return colorFilter;
	}
	public String getImageType() {
		return imageType;
	}
	public String getSiteRestrict() {
		return siteRestrict;
	}
	
	public String getBASE_URL() {
		return BASE_URL;
	}
	public String getVERSION() {
		return VERSION;
	}
	public int getRESULTSIZE() {
		return RESULTSIZE;
	}
	
	public String getReqStart() {
		return Uri.encode("start")+"="+Uri.encode(this.getStart()+"");
	}
	public String getReqImgSize() {
		return Uri.encode("imgsz")+"="+Uri.encode(this.getImgSize());
	}
	public String getReqColorFilter() {
		return Uri.encode("imgcolor")+"="+Uri.encode(this.getColorFilter());
	}
	public String getReqImageType() {
		return Uri.encode("imgtype")+"="+Uri.encode(this.getImageType());
	}
	public String getReqSiteRestrict() {
		return Uri.encode("as_sitesearch")+"="+Uri.encode(this.getSiteRestrict());
	}

	public String getReqVERSION() {
		return Uri.encode("v")+"="+Uri.encode(this.getVERSION());
	}
	public String getReqRESULTSIZE() {
		return Uri.encode("rsz")+"="+Uri.encode(this.getRESULTSIZE()+"");
	}

	public String getReqQuery() {
		return Uri.encode("q")+"="+Uri.encode(this.getQuery());
	}
	
	public void setStart(int start) {
		this.start = start;
	}
	public void setImgSize(String imgSize) {
		if(imgSize.equalsIgnoreCase("large")){
			this.imgSize = "xxlarge";
		}else{
			this.imgSize = imgSize;
		}
	}
	public void setColorFilter(String colorFilter) {
		this.colorFilter = colorFilter;
	}
	public void setImageType(String imageType) {
		this.imageType = imageType;
	}
	public void setSiteRestrict(String siteRestrict) {
		this.siteRestrict = siteRestrict;
	}
	
	public void setQuery(String query) {
		this.query = query;
	}
	
	public String getRequestUrl(){
		String reqString = "";
		reqString+=this.BASE_URL+"?";
		reqString+="&"+this.getReqVERSION();
		reqString+= "&"+this.getReqRESULTSIZE();
		reqString+="&"+this.getReqStart();
		
		if(!this.getQuery().isEmpty()){
			reqString+= "&"+this.getReqQuery();
		}	
		if(!this.getImgSize().isEmpty()){
			reqString+="&"+this.getReqImgSize();
		}
		if(!this.getColorFilter().isEmpty()){
			reqString+= "&"+this.getReqColorFilter();
		}
		if(!this.getImageType().isEmpty()){
			reqString+= "&"+this.getReqImageType();
		}
		if(!this.getSiteRestrict().isEmpty()){
			reqString+= "&"+this.getReqSiteRestrict();
		}
		return reqString;		
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Base Url : "+this.BASE_URL+" Version: "+this.getVERSION()+" ResultSize: "+this.getRESULTSIZE()+" Start: "+this.getStart()+" Query :"+this.getQuery()+" Image SIze : "+this.getImgSize()+" color filter :"+this.getColorFilter()+" Image TYpe :"+this.getImageType()+ " ssite :"+this.getSiteRestrict();           
	}
}
