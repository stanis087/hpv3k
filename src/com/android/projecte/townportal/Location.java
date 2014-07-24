package com.android.projecte.townportal;

import android.util.Log;

public class Location {
	private String name;
	private double lat, lng;
	private Boolean removable = true, selected = false;
	
	Location(String name, double lat, double lng) {
		Log.v("Location.java", "Primary Constructor.");
		this.name = name;
		this.lat = lat;
		this.lng = lng;
	}
	
	Location(String name, double lat, double lng, Boolean removable) {
		Log.v("Location.java", "Secondary Constructor.");
		this.name = name;
		this.lat = lat;
		this.lng = lng;
		this.removable = removable;
	}	
	
	public String getName() {
		return this.name;
	}
	
	public double getLat() {
		return this.lat;
	}
	
	public double getLng() {
		return this.lng;
	}
	
	public Boolean isRemovable() {
		return removable;
	}
	
	public Boolean isSelected() {
		return selected;
	}
	
	public void setSelected() {
		this.selected = true;
	}
	
	public void setUnSelected() {
		this.selected = false;
	}
	
	public String toString() {
		return this.name;
	}
	
}