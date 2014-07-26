package com.android.projecte.townportal;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

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
				
				switch(position)
				{
				case 0: ThemeUtilities.setTheme(ThemeUtilities.THEME_BEACH);
					break;
				case 1: ThemeUtilities.setTheme(ThemeUtilities.THEME_FSU);
					Log.i("ManageThemes", "Attempting to set FSU_THEME");
					break;
				case 2: ThemeUtilities.setTheme(ThemeUtilities.THEME_ANDROID);
					break;
				}
			}
			
		});
	}
}
