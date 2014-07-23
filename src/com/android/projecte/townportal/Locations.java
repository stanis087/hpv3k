package com.android.projecte.townportal;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.widget.Toast;

public class Locations {

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

	
	// Collection of locations
	private Vector<Location> locations;
		
	//http://stackoverflow.com/questions/7145606/how-android-sharedpreferences-save-store-object
	
	// constructor
	Locations () {
		locations.add( new Location("Panama City, FL", 30.205971, -85.858862, false) );
	}
	
	// add location method - standard
	public void addLocation(String name, Context context) {
		Geocoder geocoder = new Geocoder(context);  
		List<Address> addresses;
		try {
			addresses = geocoder.getFromLocationName(name, 1);
			if(addresses.size() > 0) {
			    
				locations.add( new Location(
						name, 
						addresses.get(0).getLatitude(), 
						addresses.get(0).getLongitude() 
					)
				);
			    
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(context.getApplicationContext(), 
					"Could not determine coordinates.", 
					Toast.LENGTH_LONG).show();
		}

	}

	// add location method - unremovable: Panama City
	public void addLocation(String name, Boolean removable, Boolean selected, Context context) {
		Geocoder geocoder = new Geocoder(context);  
		List<Address> addresses;
		try {
			addresses = geocoder.getFromLocationName(name, 1);
			if(addresses.size() > 0) {
			    
				locations.add( new Location(
						name, 
						addresses.get(0).getLatitude(), 
						addresses.get(0).getLongitude(),
						removable
					)
				);
				
			    
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(context.getApplicationContext(), 
					"Could not determine coordinates.", 
					Toast.LENGTH_LONG).show();
			
		}

	}
	
	// create new location, then save to list/prefs
	
	// remove location
	// if location is removable, then remove
	public void removeLocation(String name) {
		
		int position = findLocation(name);
		
		if( position != -1 )
			locations.remove(position);
		
	}
	
	public int findLocation(String name) {
		int position = -1; 
		
		for(int i = 0; i < locations.size(); i++) {
			if( locations.get(i).getName().equals(name) ){
				break;
			}
		}
		
		return position;
	}
	
	
	public Location getLocation(String name) {
		
		for(int i = 0; i < locations.size(); i++) {
			if( locations.get(i).getName().equals(name) ){
				return locations.get(i);
			}
		}
		
		return null;
	}
	
	
	// method to update selected location
	public void setSelected(String name) {
		for(int i = 0; i < locations.size(); i++) {
			if( locations.get(i).getName().equals(name) ){
				locations.get(i).setSelected();
			}
			else
				locations.get(i).setUnSelected();
		}
	}
	
}
