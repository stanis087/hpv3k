package com.android.projecte.townportal;

import java.util.Vector;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

				.setTitle("Remove Location")
				.setMessage("Are you sure.")
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								removeLocation(InternalPosition);
																
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

	// invoke methods to store in Locations and to add to ListView
	private void addLocation(String location) {
		locations.addLocation(location);
		Toast.makeText(getApplicationContext(), location + " added.", Toast.LENGTH_LONG).show();
		listAdapter.notifyDataSetChanged();
		//Log.v("Locations.addLocation: ", "Added: " + location + " - new size: " + Integer.toString(locations.getSize()) );
	}
	
	private void removeLocation(int position){
		
		locations.removeLocation( position );
		listAdapter.notifyDataSetChanged();
	}
	
	@Override
	public void onStop() {
		super.onStop();
		Locations.savePreferences(this, locations);
	}

}
