package com.opencloud.demo;

import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity 
{
	/**
	 * This is a log prefix used to identify specific
	 * application log messages.
	 */
	public static final String LOG_PREFIX = "RHINO COMPANION LOGGING: ";
	private static final String LOCATION_UPDATED_STATUS = "location_update_status";
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        Log.i(this.getClass().getName(), MainActivity.LOG_PREFIX + "starting the MainActivity...");
    	super.onCreate(savedInstanceState);
        /**
         * Get a reference to the string array that represents the main menu items to be 
         * displayed on the screen.
         */
        final String[] mainMenuListViewElements = getResources().getStringArray(R.array.main_menu_items);
		/**
		 * Populate the main_menu_layout ListView with the menu items to be displayed
		 */
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mainMenuListViewElements));
		/**
		 * Get an object representing the listView
		 */
		ListView listView = getListView();
		/**
		 * Register a ClickEventListener to listen for the click events that
		 * are generated when the user clicks/taps over the main menu items.
		 */
		TapEventDispatcher tapEventDispatcher = new TapEventDispatcher(this);
		listView.setOnItemClickListener(tapEventDispatcher);
		/**
		 * Before exiting the onCreate() method, the activity will register for
		 * receiving location updates from the LocationManager system service. This
		 * need to be done only once upon the first launch of the application, later
		 * on, the registration for location updates
		 */		
		if (!isLocationUpdateInitiated()) 
		{
			initiateLocationUpdate();
		}
    }
    
    private void initiateLocationUpdate()
    {
    	Log.i(this.getClass().getName(), MainActivity.LOG_PREFIX + "Initiating location update...");
    	LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    	locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new GpsLocationFixListener());
    	Log.i(this.getClass().getName(), MainActivity.LOG_PREFIX + "GPS location updates has been registered...");
    	/**
    	 * Set the LOCATION_UPDATED_STATUS boolean flag to true to aid in that
    	 * the registration for location update requests is done only once during
    	 * the first time the application is run.
    	 */
    	setLocationUpdateStatus(true);
    }
    
    private boolean isLocationUpdateInitiated()
    {
    	SharedPreferences settings = getSharedPreferences(LOCATION_UPDATED_STATUS, 0);
    	return settings.getBoolean(LOCATION_UPDATED_STATUS, false);    	
    }
    
    private void setLocationUpdateStatus(boolean status)
    {
    	SharedPreferences settings = getSharedPreferences(LOCATION_UPDATED_STATUS, 0);
    	SharedPreferences.Editor editor = settings.edit().putBoolean(LOCATION_UPDATED_STATUS, status);
    	editor.commit();
    }
};