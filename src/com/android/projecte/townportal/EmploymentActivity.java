/* EmploymentActivity.java
 * Project E - Eric Daniels
 */

package com.android.projecte.townportal;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

/*
 * Employment Activity
 * Description: A Feed Activity that gets job listings from Monster.
 */
final public class EmploymentActivity extends FeedActivity {
    
    private String jobsSource;
    private ListView lvFeed;
    
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate( Bundle savedInstanceState ) {

        super.onCreate( savedInstanceState );
        lvFeed = (ListView)findViewById(R.id.feedList);
        // JavaScript makes the Monster mobile site function better
        this.webView.getSettings().setJavaScriptEnabled( true );
        

        // Get strings
        this.jobsSource= findJobRss(getString(R.string.jobsRss));
        this.title = getString( R.string.empl_text );
        this.seeMoreUrl = getString( R.string.jobsViewMore );
        
        // Set title and courtesy
        ((TextView) findViewById( R.id.title ) ).setText( this.title );

        this.courtesyText.setText( getString( R.string.emplCourtesy ) );
        
        buildToTheme();
        new FeedTask( this.context ).execute();
    }
    
    /*
     * Get Web Contents
     * Descriptions: Retrieves a page from a given URL.
     */
    private InputStream getWebContents( String url ) {
        
        DefaultHttpClient client = new DefaultHttpClient();
        
        try {
            
            // Get HTML from URL
            HttpResponse response = client.execute( new HttpGet( url ) );
            HttpEntity message = response.getEntity();
            return message.getContent();
            
        } catch ( ClientProtocolException e ) {
            
            e.printStackTrace();
            
        } catch ( IOException e ) {
            
            e.printStackTrace();
        }
       
        return null;
    }

    @Override
    /*
     * Get Items
     * Description: Gets a list of items for display in the ListView.
     */
    protected List<Item> getItems() {
        
        List<Item> result = new Vector<Item>();

        // Create Document from RSS content
        try {
            
            // Used http://jsoup.org/cookbook/ to figure out how to parse
            Document rssDoc = Jsoup.parse( getWebContents( this.jobsSource ), "UTF-8", "", Parser.xmlParser() );
            
            Elements jobItems = rssDoc.select("item");
            
            for ( Element element : jobItems ) {
                
                String title = element.select( "title" ).get( 0 ).text();
                String description = element.select( "description" ).get( 0 ).text();
                String link = element.select( "link" ).get( 0 ).text();

                if ( title != null && link != null && description != null )
                    result.add( new Item( title, description, link ) );
            }
            
        } catch ( IOException e ) {
            
            e.printStackTrace(); 
        }
        
        return result;
    }
    
    @Override
    protected String modifyUrl( String url ) {
        
        // Transform link to mobile version for visibility purposes
        url = url.replace( "jobview", "m" );
        
        Matcher matcher = Pattern.compile(".com/.*-([0-9]+)\\.aspx").matcher( url );
        
        // Should always return true
        if ( matcher.find() )
            url = matcher.replaceFirst( ".com/" + matcher.group( 1 ) );
        
        return url;
    }
     
    protected String findJobRss(String intial) {
        // Find proper job feed, needs location data
        Location currentLocation = Locations.returnSelected(this);
        String[] parts = currentLocation.getCity().split(" ");
        
        // Loop through string split to join with '+'
        for(int i = 0; i < parts.length; ++i)
        {
        	intial += parts[i];
        	if(i+1 < parts.length)
        		intial += "+";
        	else
        		intial += "%2C+" + currentLocation.getState();
        }
        //Log.i("Job Feed", intial);
        return intial;
    }
    
    private void buildToTheme(){
    	lvFeed.setBackgroundColor(getResources().getColor(ThemeUtilities.getBgColor()));
    	this.courtesyText.setTextColor(getResources().getColor(ThemeUtilities.getTextColor()));
    	View view = this.getWindow().getDecorView();
    	view.setBackgroundColor(getResources().getColor(ThemeUtilities.getBgColor()));
    }
}
