package com.android.projecte.townportal;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ManageLocationsActivity extends Activity {

	private ListView mainListView ;
	  private ArrayAdapter<String> listAdapter ;
	  
	  /** Called when the activity is first created. */
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_manage_locations);
	    
	    // Find the ListView resource. 
	    mainListView = (ListView) findViewById( R.id.mainListView );

	    // Create and populate a List of planet names.
	    String[] planets = new String[] { "Mercury", "Venus", "Earth", "Mars",
	                                      "Jupiter", "Saturn", "Uranus", "Neptune"};  
	    ArrayList<String> planetList = new ArrayList<String>();
	    planetList.addAll( Arrays.asList(planets) );
	    
	    // Create ArrayAdapter using the planet list.
	    listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, planetList);
	    mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(getApplication().getBaseContext(), listAdapter.getItem(position), Toast.LENGTH_LONG).show();
				
			}
		});
	    // Add more planets. If you passed a String[] instead of a List<String> 
	    // into the ArrayAdapter constructor, you must not add more items. 
	    // Otherwise an exception will occur.
	    listAdapter.add( "Ceres" );
	    listAdapter.add( "Pluto" );
	    listAdapter.add( "Haumea" );
	    listAdapter.add( "Makemake" );
	    listAdapter.add( "Eris" );
	    
	    // Set the ArrayAdapter as the ListView's adapter.
	    mainListView.setAdapter( listAdapter );      
	  }
	 
}

