/* MainActivity.java
 * Project E - Eric Daniels
 */

package com.android.projecte.townportal;

import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

/*
 * Main Activity
 * Description: Main area for user to select different activities in this
 *              application.
 */
public class MainActivity extends Activity {

    // Used for constructing types of places to views
    private Vector<PlaceType> vFood = new Vector<PlaceType>(),
                              vEnt = new Vector<PlaceType>(),
                              vShop = new Vector<PlaceType>(),
                              vSchool = new Vector<PlaceType>(),
                              vReligion = new Vector<PlaceType>(),
                              vMedical = new Vector<PlaceType>(),
                              vLodging = new Vector<PlaceType>();
    						
    
    private String foodTitle, entertainmentTitle, shoppingTitle, schoolsTitle, religionTitle,
    				medicalTitle, lodgingTitle;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {

        super.onCreate( savedInstanceState );

        // Use custom title bar
        requestWindowFeature( Window.FEATURE_CUSTOM_TITLE );
        setContentView( R.layout.activity_main );
        getWindow().setFeatureInt( Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title );
        
        // Get titles
        this.foodTitle = getString( R.string.food_text );
        this.entertainmentTitle = getString( R.string.entertainment_text );
        this.shoppingTitle = getString( R.string.shopping_text );
        this.schoolsTitle = getString( R.string.schools_text );
        this.religionTitle = getString( R.string.religious_text);
        this.medicalTitle = getString( R.string.medical_text);
        this.lodgingTitle = getString( R.string.lodge_text );

        // Setup food
        this.vFood.add( new PlaceType( "cafe", "Cafes" ) );
        this.vFood.add( new PlaceType( "restaurant", "Restaurants" ) );
        this.vFood.add( new PlaceType( "grocery_or_supermarket", "Markets" ) );

        // Setup Entertainment
        this.vEnt.add( new PlaceType( "movie_theater", "Movies" ) );
        this.vEnt.add( new PlaceType( "night_club", "Night Clubs" ) );
        this.vEnt.add( new PlaceType( "museum", "Museums" ) );

        // Setup Shopping
        this.vShop.add( new PlaceType( "shopping_mall", "Malls" ) );
        this.vShop.add( new PlaceType( "book_store", "Books" ) );
        this.vShop.add( new PlaceType( "electronics_store", "Electronics" ) );

        // Setup Schools
        this.vSchool.add( new PlaceType( "school", "Schools" ) );
        this.vSchool.add( new PlaceType( "university", "Universities" ) );
        
        // Setup Medical 
        this.vMedical.add(new PlaceType("hospital", "Hospitals"));
        this.vMedical.add(new PlaceType("pediatrician", "Pediatricians"));
        this.vMedical.add(new PlaceType("dentist", "Dental"));
        this.vMedical.add(new PlaceType("orthodontist", "Orthodontists"));
        
        // Setup Religious Places
        this.vReligion.add(new PlaceType("church", "Churches"));
        this.vReligion.add(new PlaceType("mosque", "Mosque"));
        this.vReligion.add(new PlaceType("synagogue", "Synagogue"));
        
        // Setup Lodging Places
        this.vLodging.add( new PlaceType("lodging", "Lodging"));
        
        
    }

    /*
     * Button On Click Listener
     * Description: Listens for any of the buttons being clicked
     *              and launches their respective activity
     */
    public void onClick( View v ) {

        switch ( v.getId() ) {
        
        case R.id.btnFood: {
            
            openPlaceList( this.foodTitle, this.vFood );
            
            break;
        }
            
        case R.id.btnEntertainment: {
            
            openPlaceList( this.entertainmentTitle, this.vEnt );
            
            break;
        }
        
        case R.id.btnShopping: {
            
            openPlaceList( this.shoppingTitle, this.vShop );
            
            break; 
        }

        case R.id.btnSchools: {
            
            openPlaceList( this.schoolsTitle, this.vSchool );
            
            break; 
        }
        
        case R.id.btnEmployment: {
            
            Intent employmentIntent = new Intent( this, EmploymentActivity.class );
            startActivity( employmentIntent );
            
            break; 
        }
        
        case R.id.btnNews:{
            
            Intent newsIntent = new Intent( this, NewsActivity.class );
            startActivity( newsIntent );
            
            break;
        }
        
        /*case R.id.btnLocations:{
            
            Intent locationsIntent = new Intent( this, ManageLocationsActivity.class );
            startActivity( locationsIntent );
            
            break;
        }*/
        
        case R.id.btnReligion:{
        	
        	openPlaceList( this.religionTitle, this.vReligion );
        	break;
        }
        
        case R.id.btnMedical:{
        	openPlaceList( this.medicalTitle, this.vMedical	);
        	break;
        }
        
        case R.id.btnLodging:{
        	openPlaceList( this.lodgingTitle, this.vLodging );
        	break;
        }
        default:
            break;
        }
    }

    /*
     * Open Place List
     * Description: Start a MapActivity based off certain place info.
     */
    private void openPlaceList( String title, Vector<PlaceType> places ) {

        Intent intent = new Intent( this, MapActivity.class );
        intent.putExtra( "title", title );
        
        for ( int i = 0; i < places.size(); i++ )
            intent.putExtra( "PlaceType" + Integer.toString( i + 1 ), places.get( i ) );
        
        startActivity( intent );
    }
    
    public boolean onCreateOptionsMenu(Menu menu) {  
        // Inflate the menu; this adds items to the action bar if it is present.  
        getMenuInflater().inflate(R.menu.menu_main, menu);//Menu Resource, Menu  
        return true;  
    }  
      
    @Override  
    public boolean onOptionsItemSelected(MenuItem item) {  
        switch (item.getItemId()) {  
            case R.id.menu_locations:  
              Toast.makeText(getApplicationContext(),"Managing Locations",Toast.LENGTH_LONG).show();
              Intent locationsIntent = new Intent( this, ManageLocationsActivity.class );
              startActivity( locationsIntent );
              return true;
              
            case R.id.menu_exit:  
            	MainActivity.this.finish();
                return true;     
            default:  
              return super.onOptionsItemSelected(item);  
        }  
    } 
}
