/* PlaceDetailActivity.java
 * Project E - Eric Daniels
 */

package com.android.projecte.townportal;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/*
 * Place Detail Activity
 * Description: Used with Place Detail Activity page to display detailed place 
 *              information when user clicks on a place in Map Activity.
 */
public class PlaceDetailActivity extends Activity { 

    private TextView nameTextView, ratingTextView, priceTextView, 
                addressTextView, phoneNumberTextView, websiteTextView,
                loadingText, reviewsText;
    private ImageView photoImageView;
    
    private GooglePlacesSearch gpSearch;
    private AtomicInteger loadingCounter;
    private List<PhotoTask> photoTasks = new Vector<PhotoTask>();
    private PlaceDetail detail; // needed for scope resolution

    @Override
    protected void onCreate( Bundle savedInstanceState ) {

        super.onCreate( savedInstanceState );

        this.gpSearch = new GooglePlacesSearch( getIntent().getExtras().getString( "gpSearchType" ), 
                getIntent().getExtras().getString( "gpSearchGeoLocation" ), 1 );
        
        // Use custom title bar
        requestWindowFeature( Window.FEATURE_CUSTOM_TITLE );
        setContentView( R.layout.activity_place_detail );
        getWindow().setFeatureInt( Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title );
        ((TextView) findViewById( R.id.title ) ).setText( R.string.returnText );

        // Set Place detail TextViews
        this.nameTextView = (TextView) findViewById( R.id.nameText );
        this.ratingTextView = (TextView) findViewById( R.id.ratingText );
        this.priceTextView = (TextView) findViewById( R.id.priceText );
        this.addressTextView = (TextView) findViewById( R.id.addressText );
        this.phoneNumberTextView = (TextView) findViewById( R.id.phoneNumberText );
        this.websiteTextView = (TextView) findViewById( R.id.websiteText );
        this.photoImageView = (ImageView) findViewById( R.id.photoImage );
        this.loadingText = (TextView) findViewById( R.id.loading );
        this.reviewsText = (TextView) findViewById(R.id.reviews);
        
        Place place = (Place) getIntent().getExtras().getSerializable("place");
        detail = (PlaceDetail) getIntent().getExtras().getSerializable("placeDetail");
        
        // Set TextViews
        this.nameTextView.setText(detail.siteName);
        this.ratingTextView.setText( ratingToStar( place.rating.intValue() ) );
        this.addressTextView.setText(detail.address);
        this.phoneNumberTextView.setText(detail.phoneNumber);
        this.websiteTextView.setClickable( true );
        this.websiteTextView.setMovementMethod( LinkMovementMethod.getInstance() );
        this.loadingCounter = (AtomicInteger) getIntent().getExtras().getSerializable( "loadingCounter" );
        
        String dollars = priceToDollar( place.price );
        
        // Don't display dollar if it isn't there so we save space
        if ( dollars.isEmpty() )
            this.priceTextView.setVisibility( View.GONE );
        else
            this.priceTextView.setText( dollars );

        String website = detail.website;
        if ( website != null )
            this.websiteTextView.setText( Html.fromHtml( "<a href=" + website + ">" + website + "</a>"));
        
        if (!detail.reviews.isEmpty())
        {
        	StringBuilder rbuf = new StringBuilder("\nReviews:\n\n");
	        for (PlaceDetail.Review r : detail.reviews)
	        {
	        	rbuf.append(r.author + "\n");
	        	rbuf.append(r.text + "\n\n");
	        }
	        
	        this.reviewsText.setText(rbuf);
        }
        
        PhotoTask t = new PhotoTask(detail.photoRef);
        t.execute();
        photoTasks.add(t);
        
        // need to show action button if type is movie_theater
        if( getIntent().getExtras().getString( "gpSearchType" ).equals("movie_theater") ) {       	
        	View b = findViewById(R.id.btnAction1);
        	b.setVisibility(View.VISIBLE);
        	
        	// click listener goes here
        	b.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Uri uri = Uri.parse("http://www.fandango.com/dragon+ball+z3a+battle+of+gods_174747/movietimes?location=33870&wssaffid=11836&wssac=123");
	                startActivity(new Intent(Intent.ACTION_VIEW, uri));
					
				}
			});
        }
        
        // Get Directions button here
        Button btnPlayAgain = (Button) findViewById(R.id.btnGetDirections);
		btnPlayAgain.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    	// launch Google Maps app
		    	// http://stackoverflow.com/questions/2662531/launching-google-maps-directions-via-an-intent-on-android
		    	Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
		    		Uri.parse("http://maps.google.com/maps?q=" + PlaceDetailActivity.this.detail.address));
		    	startActivity(intent);
		    }
		});
        
        
    }
    
    @Override
    protected void onDestroy() {
    	
    	for ( PhotoTask task : this.photoTasks )
    		task.cancel( true );
    	
    	super.onDestroy();
    }
    
    // Async Task to get Google Places Photo
    class PhotoTask extends AsyncTask<Void, Void, PlacePhoto> {

        private String photoReference;

        public PhotoTask( String photoRef ) {

            this.photoReference = photoRef;
        }
        
        @Override
        protected void onPreExecute() {
        	
        	loadingCounter.addAndGet( 1 );
        	
        	if ( loadingCounter.get() == 1 )
        		loadingText.setVisibility( View.VISIBLE );
        }

        @Override
        protected PlacePhoto doInBackground( Void... unused ) {

            return gpSearch.findPlacePhoto( photoReference );
        }

        @Override
        protected void onPostExecute( PlacePhoto placePhoto ) {
            
        	if ( isCancelled() )
        		return;
        	
        	loadingCounter.addAndGet( -1 );
        	
        	if ( loadingCounter.get() == 0 )
        		loadingText.setVisibility( View.GONE );
        	
            // Set Photo ImageView
            if ( placePhoto.photo != null )
                photoImageView.setImageBitmap( placePhoto.photo );
            else
                photoImageView.setVisibility( View.GONE );
        }

    }
    
    /*
     * Rating to Star
     * Description: Creates a star rating based off
     *              some integer out of 5.
     */
    private String ratingToStar( int rating ) {
        
        String result = "";
        
        for ( int i = 0; i < rating; i++ )
            result += "\u2605";
        
        if ( !result.isEmpty() )
            for ( int i = 0; i < 5-rating; i++ )
                result += "\u2606";
        else
            result = "No Rating";
        
        return result;
    }
    
    /*
     * Price to Dollar
     * Description: Creates a dollar representation
     *              of a price level.
     */
    private String priceToDollar( int price ) {
        
        String result = "";
        
        for ( int i = 0; i < price; i++ )
            result += "\u0024";
            
        return result;
    }

}
