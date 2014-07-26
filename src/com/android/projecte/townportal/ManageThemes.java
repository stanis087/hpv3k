package com.android.projecte.townportal;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ManageThemes extends Activity{
	ListView lvTheme;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manage_themes);
		
		lvTheme = (ListView)findViewById(R.id.lvThemes);
		lvTheme.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(getApplicationContext(), "Position: " + Integer.toString(position), 
						Toast.LENGTH_LONG).show();
				
			}
			
		});
	}
}
