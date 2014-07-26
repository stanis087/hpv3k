package com.android.projecte.townportal;

import java.util.Vector;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.util.Log;

public class Locations {

	// Collection of locations
	private Vector<Location> locations = new Vector<Location>();

	// private List<RequestTask> requestTasks = new Vector<RequestTask>();

	// private Context context;

	// http://stackoverflow.com/questions/7145606/how-android-sharedpreferences-save-store-object

	// constructor
	Locations() {
		
		Log.v("Locations.java", "Constructor invoked.");
		
		// need to add my location
		locations.add(new Location("My Location", 0, 0,
				"city", "st", 1, false));
		locations.add(new Location("Panama City, FL", 30.205971, -85.858862,
				"Panama City", "FL", 1, false));
		/*addLocation("Ocala, FL");
		addLocation("Sebring, FL");
		addLocation("Miami, FL");
		addLocation("Lexington, KY");
		addLocation("San Fransisco, CA");*/

		// still need to add "my location"

	}

	// add location method - standard
	public void addLocation(Location l) {
		
		this.locations.add(l);

	}

	public Vector<Location> getLocations() {
		return this.locations;
	}
	
	public int getSize() {
		return locations.size();
	}

	@Override
	public String toString() {
		String str = "";

		for (int i = 0; i < locations.size(); i++) {
			str = str + locations.get(i).getName();
		}

		return str;
	}

	public String toString(int i, Boolean dummy) {
		return locations.get(i).getName();
	}

	// remove location
	// if location is removable, then remove
	public Boolean removeLocation(String name) {

		int position = findLocation(name);

		if (position != -1 && locations.get(position).isRemovable()  ) {
			locations.remove(position);
			return true;
		}

		return false;
	}

	public Boolean removeLocation(int position) {

		if ( locations.get(position).isRemovable() ) {
			locations.remove(position);
			return true;
		}
		
		return false;
	}

	public int findLocation(String name) {
		int position = -1;

		for (int i = 0; i < locations.size(); i++) {
			if (locations.get(i).getName().equals(name)) {
				break;
			}
		}

		return position;
	}

	public Location getLocation(String name) {

		for (int i = 0; i < locations.size(); i++) {
			if (locations.get(i).getName().equals(name)) {
				return locations.get(i);
			}
		}

		return null;
	}

	public Location getLocation(int i) {

		if (i < locations.size()) {
			return locations.get(i);
		}

		return null;
	}

	// method to update selected location
	public void setSelected(String name) {
		for (int i = 0; i < locations.size(); i++) {
			if (locations.get(i).getName().equals(name)) {
				locations.get(i).setSelected();
			} else
				locations.get(i).setUnSelected();
		}
	}
	
	public void setSelected(int j) {
		for (int i = 0; i < locations.size(); i++)
			locations.get(i).setUnSelected();
		
		locations.get(j).setSelected();
	}
	
	public int getSelected() {
		for (int i = 0; i < locations.size(); i++) {
			if (locations.get(i).isSelected()) {
				Log.v("Locations.getSelected()", "Selected: " + Integer.toString(i));
				return i;
			}
		}
		return 0; // default to first
	}
	
	public static Location returnSelected(Context con){
		Locations list = Locations.loadPreferences(con);
		return list.getLocation(list.getSelected());
	}

	public void updateMyLocation(double lat, double lng) {
		
		//Log.v("Locations.updateMyLocation()", "Updating my location :" + Double.toString(lat) + "/." + Double.toString(lng)); 
		
		if( locations.get(0).getName().equals("My Location") )
			locations.get(0).updateCoordinates(lat, lng);
	}
	
	// http://stackoverflow.com/questions/5418160/store-and-retrieve-a-class-object-in-shared-preference
	public static void savePreferences(Context context, Locations locations) {
		SharedPreferences mPrefs = context.getSharedPreferences(
				"com.android.projecte.townportal", Context.MODE_PRIVATE);
		Gson gson = new Gson();
		Editor prefsEditor = mPrefs.edit();

		String json = gson.toJson(locations);
		prefsEditor.putString("userLocationsList", json);
		prefsEditor.commit();
	}

	public static Locations loadPreferences(Context context) {
		SharedPreferences mPrefs = context.getSharedPreferences(
				"com.android.projecte.townportal", Context.MODE_PRIVATE);
		Gson gson = new Gson();

		// Log.v("Locations loadPref", "Loading");

		// fetch pref
		String json = mPrefs.getString("userLocationsList", "notFound");

		// Log.v("Locations loadPref", "Fetched");

		Locations locations;
		
		// Create pref if not exists
		if (json.equals("notFound")) {
			Log.v("Locations loadPref", "was null");
			locations = new Locations();
			savePreferences(context, locations);
		} else
			Log.v("Locations loadPref", "not null");

		//Log.v("Locations loadPref dump1: ", json);
		json = mPrefs.getString("userLocationsList", "notFound");
		//Log.v("Locations loadPref dump2: ", json);

		return gson.fromJson(json, Locations.class);
	}

	/*
	 * // need to cancel request tasks if activity is closed before tasks finish
	 * protected void onDestroy() {
	 * 
	 * for ( PhotoTask task : this.RequestTasks ) task.cancel( true ); }
	 */

}
