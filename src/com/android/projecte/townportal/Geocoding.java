package com.android.projecte.townportal;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

public class Geocoding {

	static public JSONObject getLocationInfo(String location) {

		try {
			return doFetch(new HttpGet(
					"http://maps.google.com/maps/api/geocode/json?address="
							+ URLEncoder.encode(location, "UTF-8")
							+ "&sensor=true"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new JSONObject();
	}

	static public JSONObject getLocationInfo(double lat, double lng) {

		return doFetch(new HttpGet(
				"http://maps.google.com/maps/api/geocode/json?latlng=" + lat
						+ "," + lng + "&sensor=true"));

	}

	static private JSONObject doFetch(HttpGet httpGet) {

		HttpClient client = new DefaultHttpClient();
		HttpResponse response;
		StringBuilder stringBuilder = new StringBuilder();

		try {
			response = client.execute(httpGet);
			HttpEntity entity = response.getEntity();
			InputStream stream = entity.getContent();
			int b;
			while ((b = stream.read()) != -1) {
				stringBuilder.append((char) b);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = new JSONObject(stringBuilder.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}

		// Log.v("Map object: ", jsonObject.toString() );
		return jsonObject;
	}
	
	static public void getMyLocation(Context context) {
		
		/*
		LocationManager locationManager;
		// Acquire a reference to the system Location Manager
        locationManager = (LocationManager) context.getSystemService( Context.LOCATION_SERVICE );
	
        if ( locationManager == null ) {
            
            Toast toast = Toast.makeText( context, "error: Failed to use the Location Service.", Toast.LENGTH_SHORT );
            toast.setGravity( Gravity.CENTER_HORIZONTAL, 0, 0 );
            toast.show();
            spinner.setSelection( 1 );
            currentSpinnerIndex = 1;
            
        } else {
                
            // Find best provider for searching locations
            bestProvider = locationManager.getBestProvider( new Criteria(), true );
            
            if ( bestProvider == null ) {

                Toast toast = Toast.makeText( context, "error: Please enable Location Services.", Toast.LENGTH_SHORT );
                toast.setGravity( Gravity.CENTER_HORIZONTAL, 0, 0 );
                toast.show();
                spinner.setSelection( 1 );
                currentSpinnerIndex = 1;
                
            } else {
                
                // Ask for updates every once in a while but we don't actually care when we get them
                locationManager.requestLocationUpdates( bestProvider, 6000, 20,  new LocationListener() {

                    @Override
                    public void onLocationChanged(Location location) {}

                    @Override
                    public void onProviderDisabled(String provider) {}

                    @Override
                    public void onProviderEnabled(String provider) {}

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {}
                        
                });
            }
        }*/
        
	}

}
