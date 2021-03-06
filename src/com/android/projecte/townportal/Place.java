/* Place.java
 * Project E - Eric Daniels
 */

package com.android.projecte.townportal;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

/*
 * Place
 * Description: Data structure that holds Google Place data.
 * 
 * Notes:
 * Deprecated fields: id & reference - https://developers.google.com/places/documentation/search
 * 
 */
public class Place implements Serializable {

    public String place_id, icon, name, vicinity; // removed id, placeReference. Added place_id
    public Double latitude, longitude, rating;
    public Integer price;
    
    static Place jsonToPlace( JSONObject toPlace ) {

        Place result = null;
        
        try {
            
            JSONObject location = (JSONObject) ( (JSONObject) toPlace.get( "geometry" ) ).get( "location" );
            
            // Create and set place
            result = new Place();
            result.latitude = (Double) location.get( "lat" );
            result.longitude = (Double) location.get( "lng" );
            result.icon = toPlace.getString( "icon" );
            result.name = toPlace.getString( "name" );
            result.vicinity = toPlace.getString( "vicinity" );
            /*
             * result.id = toPlace.getString( "id" );
             * result.placeReference = toPlace.getString( "reference" );
             */
            result.place_id = toPlace.getString( "place_id" );
            result.rating = toPlace.has( "rating" ) ? toPlace.getDouble( "rating" ) : 0;
            result.price = toPlace.has( "price_level" ) ? toPlace.getInt( "price_level" ) : 0;

        } catch ( JSONException e ) {
            
            e.printStackTrace();
        }
        
        return result;
    }

    @Override
    public String toString() {
        
        return this.name;
    }
}