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

		/*
		 * // Create and populate a List of planet names. String[] planets = new
		 * String[] { "Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn",
		 * "Uranus", "Neptune" }; ArrayList<String> planetList = new
		 * ArrayList<String>(); planetList.addAll(Arrays.asList(planets));
		 */

		// Create ArrayAdapter using the planet list.
		LocationsAdapter listAdapter = new LocationsAdapter(this,
				R.layout.simplerow, locations);

		// Create OnItemClickListener
		mainListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Toast.makeText(getApplication().getBaseContext(),
								"Clicked" /*
										 * listAdapter.getItem(position).toString
										 * ()
										 */, Toast.LENGTH_LONG).show();

					}
				});

		Log.v("ManageLocationsActivity", "After setOnItemClickListener.");

		// listAdapter.add( new Location("Gainesville, FL", 29.686270,
		// -82.31974) );

		// Set the ArrayAdapter as the ListView's adapter.
		mainListView.setAdapter(listAdapter);

		Button button = (Button) findViewById(R.id.btnAdd);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Perform action on click

				final EditText txtUrl = new EditText(v.getContext());

				// Set the default text to a link of the Queen

				txtUrl.setHint("http://www.librarising.com/astrology/celebs/images2/QR/queenelizabethii.jpg");

				new AlertDialog.Builder(v.getContext())

						.setTitle("New Location")
						.setMessage("Enter your new location in the \"City, State\" format.")
						.setView(txtUrl)
						.setPositiveButton("Add",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int whichButton) {
										String url = txtUrl.getText().toString();
										// do add location thing here
									}
								})
						.setNegativeButton("Cancel",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
									}
								}).show();

			}
		});

	}

}
