package com.android.projecte.townportal;

import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ManageLocationsActivity extends Activity {

	private ListView mainListView;
	private Locations locations;
	private LocationsAdapter listAdapter;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manage_locations);
		
		locations = Locations.loadPreferences(this);
		
		Log.v("LocationsActivity", "size: " + Integer.toString(locations.getSize()) );
		
		// Find the ListView resource.
		mainListView = (ListView) findViewById(R.id.mainListView);

		// Create ArrayAdapter
		listAdapter = new LocationsAdapter(this, R.layout.simplerow, locations.getLocations() );

		// Create OnItemClickListener
		mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				final int InternalPosition = position;
				// remove items here
				// http://www.mkyong.com/android/android-alert-dialog-example/
				
				new AlertDialog.Builder(view.getContext())

				.setTitle("Edit Location")
				.setMessage("Delete location or set as default.")
				.setPositiveButton("Delete",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								removeLocation(InternalPosition);																
							}
						})
				.setNeutralButton("Set Default",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								locations.setSelected(InternalPosition);																
							}
						})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								// do nothing
							}
						}).show();

			};
		});

		// Set the ArrayAdapter as the ListView's adapter.
		mainListView.setAdapter(listAdapter);

		// http://stackoverflow.com/questions/12422352/delete-item-by-clicking-any-item-in-listview
		Button button = (Button) findViewById(R.id.btnAdd);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Perform action on click
				
				listAdapter.notifyDataSetChanged();

				// http://twigstechtips.blogspot.com/2011/10/android-allow-user-to-editinput-text.html
				final EditText input = new EditText(v.getContext());

				input.setHint("Panama City, FL");

				new AlertDialog.Builder(v.getContext())

						.setTitle("New Location")
						.setMessage(
								"Enter your new location in the \"City, State\" format.")
						.setView(input)
						.setPositiveButton("Add",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int whichButton) {
										addLocation( input.getText().toString() );
									}
								})
						.setNegativeButton("Cancel",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int whichButton) {
									}
								}).show();

			}
		});
		
	}
	
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
				int zip = 00000;

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
					
					// extract state
					if (jotwo.getJSONArray("types").getString(0)
							.equals("postal_code")) {
						state = jotwo.getString("short_name");
						// Log.v("state:", state );
					}

				}

				// here is where I add the Location to Vector<Locations>
				locations.addLocation(new Location(location, lat, lng, city, state, zip));
				
				Toast.makeText(getApplicationContext(), location + " added.", Toast.LENGTH_LONG).show();
				listAdapter.notifyDataSetChanged();
				Log.v("Locations.addLocation: ", "Added: " + location + " - new size: " + Integer.toString(locations.getSize()) );

			} catch (JSONException e1) {
				e1.printStackTrace();

			}

		}

	}

	// invoke methods to store in Locations and to add to ListView
	private void addLocation(String location) {
		new RequestTask().execute(location);
	}
	
	private void removeLocation(int position){
		
		
		if(locations.removeLocation( position ))
			Toast.makeText(getApplicationContext(), "Location removed.", Toast.LENGTH_SHORT).show();
		else
			Toast.makeText(getApplicationContext(), "Cannot remove this location.", Toast.LENGTH_SHORT).show();
		
		
		
		listAdapter.notifyDataSetChanged();
	}
	
	@Override
	public void onStop() {
		super.onStop();
		Locations.savePreferences(this, locations);
	}

}
