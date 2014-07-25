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
		locations.add(new Location("Panama City, FL", 30.205971, -85.858862,
				"Panama City", "FL", 1, false));
		addLocation("Ocala, FL");
		addLocation("Sebring, FL");
		addLocation("Miami, FL");
		addLocation("Lexington, KY");
		addLocation("San Fransisco, CA");

		// still need to add "my location"

	}

	// add location method - standard
	public void addLocation(String name) {

		new RequestTask().execute(name);

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

		Log.v("Locations.toString()", "begin dump");
		Log.v("Locations.getSize()", String.valueOf(getSize()));

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
	public void removeLocation(String name) {

		int position = findLocation(name);

		if (position != -1)
			locations.remove(position);

	}

	public void removeLocation(int position) {

		locations.remove(position);
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

		Log.v("Locations loadPref dump1: ", json);
		json = mPrefs.getString("userLocationsList", "notFound");
		Log.v("Locations loadPref dump2: ", json);

		return gson.fromJson(json, Locations.class);
	}

	/*
	 * // need to cancel request tasks if activity is closed before tasks finish
	 * protected void onDestroy() {
	 * 
	 * for ( PhotoTask task : this.RequestTasks ) task.cancel( true ); }
	 */

	class RequestTask extends AsyncTask<String, Void, JSONObject> {

		String location;

		@Override
		// do fetch in background
		protected JSONObject doInBackground(String... params) {

			location = params[0];

			return Geocoding.getLocationInfo(params[0]);

		}

		// do stuff with obtained data
		protected void onPostExecute(JSONObject ret) {

			try {
				// parse lat/lng
				double lat = ret.getJSONArray("results").getJSONObject(0)
						.getJSONObject("geometry").getJSONObject("location")
						.getDouble("lat");
				double lng = ret.getJSONArray("results").getJSONObject(0)
						.getJSONObject("geometry").getJSONObject("location")
						.getDouble("lng");

				// parse city/state now
				JSONArray address_components = ret.getJSONArray("results")
						.getJSONObject(0).getJSONArray("address_components");

				String city = "", state = "";

				for (int j = 0; j < address_components.length(); j++) {

					JSONObject jotwo = address_components.getJSONObject(j);

					// extract city
					if (jotwo.getJSONArray("types").getString(0)
							.equals("locality")) {
						city = jotwo.getString("long_name");
						// Log.v("city:", city );
					}

					// extract state
					if (jotwo.getJSONArray("types").getString(0)
							.equals("administrative_area_level_1")) {
						state = jotwo.getString("short_name");
						// Log.v("state:", state );
					}

				}

				// here is where I add the Location to Vector<Locations>
				locations.add(new Location(location, lat, lng, city, state, 1));

			} catch (JSONException e1) {
				e1.printStackTrace();

			}

		}

	}

}
