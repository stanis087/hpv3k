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

import android.util.Log;


public class Geocoding {

	static public JSONObject getLocationInfo(String location)  {

		try {
			return doFetch( new HttpGet("http://maps.google.com/maps/api/geocode/json?address="+ URLEncoder.encode(location, "UTF-8") +"&sensor=true") );
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new JSONObject();
    }
	
	static public JSONObject getLocationInfo(double lat, double lng) {

		return doFetch( new HttpGet("http://maps.google.com/maps/api/geocode/json?latlng="+lat+","+lng+"&sensor=true") );
    
	}

	static private JSONObject doFetch(HttpGet httpGet){
		
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
        }
        catch (ClientProtocolException e) { e.printStackTrace(); } 
        catch (IOException e) { e.printStackTrace(); }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringBuilder.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        //Log.v("Map object: ", jsonObject.toString() );
        return jsonObject;
	}
	
}
