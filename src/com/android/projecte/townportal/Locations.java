package com.android.projecte.townportal;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.widget.Toast;

public class Locations {

	// Collection of locations
	private Vector<Location> locations;
	private Context context;
		
	//http://stackoverflow.com/questions/7145606/how-android-sharedpreferences-save-store-object
	
	// constructor
	Locations (Context context) {
		locations.add( new Location("Panama City, FL", 30.205971, -85.858862, false) );
		this.context = context;
	}
	
	// add location method - standard
	public void addLocation(String name) {
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
	public void addLocation(String name, Boolean removable, Boolean selected) {
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
	
	public String toString(int i, Boolean dummy) {
		return locations.get(i).getName();
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
