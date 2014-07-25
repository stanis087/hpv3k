package com.android.projecte.townportal;

public class Location {
	private String name, city, state;
	private double lat, lng;
	private Boolean removable = true, selected = false;
	int zip;

	Location(String name, double lat, double lng) {
		this.name = name;
		this.lat = lat;
		this.lng = lng;
	}

	Location(String name, double lat, double lng, String city, String state,
			int zip) {
		this.name = name;
		this.lat = lat;
		this.lng = lng;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}

	Location(String name, double lat, double lng, String city, String state,
			int zip, Boolean removable) {
		this.name = name;
		this.lat = lat;
		this.lng = lng;
		this.city = city;
		this.state = state;
		this.zip = zip;
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
	
	public String getCity() {
		return this.city;
	}
	
	public String getState() {
		return this.state;
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