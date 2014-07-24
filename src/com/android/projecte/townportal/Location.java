package com.android.projecte.townportal;

public class Location {
	private String name;
	private double lat, lng;
	private Boolean removable, selected;
	
	Location(String name, double lat, double lng) {
		this.name = name;
		this.lat = lat;
		this.lng = lng;
		this.removable = true;
		this.selected = false;
	}
	
	Location(String name, double lat, double lng, Boolean removable) {
		this.name = name;
		this.lat = lat;
		this.lng = lng;
		this.removable = removable;
		this.selected = false;
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
	
}