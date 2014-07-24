package com.android.projecte.townportal;

import java.util.Vector;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ManageLocationsActivity extends Activity {

	private ListView mainListView;
	private Vector<Location> locations = new Vector<Location>();
	private LocationsAdapter listAdapter;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manage_locations);

		Log.v("ManageLocationsActivity", "Before adding locations.");

		locations.add(new Location("Panama City, FL", 30.205971, -85.858862));
		locations.add(new Location("Ocala, FL", 29.173885, -82.156807));

		Log.v("ManageLocationsActivity", "After adding locations.");

		// Find the ListView resource.
		mainListView = (ListView) findViewById(R.id.mainListView);

		// Create ArrayAdapter
		listAdapter = new LocationsAdapter(this,
				R.layout.simplerow, locations);

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

		Log.v("ManageLocationsActivity", "After setOnItemClickListener.");

		// listAdapter.add( new Location("Gainesville, FL", 29.686270,
		// -82.31974) );

		// Set the ArrayAdapter as the ListView's adapter.
		mainListView.setAdapter(listAdapter);

		// http://stackoverflow.com/questions/12422352/delete-item-by-clicking-any-item-in-listview

		Button button = (Button) findViewById(R.id.btnAdd);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Perform action on click

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
		locations.add( new Location(location, 0, 0) );
		Toast.makeText(getApplicationContext(), location + " added.", Toast.LENGTH_LONG).show();
		listAdapter.notifyDataSetChanged();
	}
	
	private void removeLocation(int position){
		
		locations.remove( position );
		listAdapter.notifyDataSetChanged();
	}

}
