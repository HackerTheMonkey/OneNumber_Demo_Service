package com.opencloud.demo;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

public class GpsLocationFixListener implements LocationListener 
{

	@Override
	public void onLocationChanged(Location location) 
	{
		Log.i(this.getClass().getName(), MainActivity.LOG_PREFIX + "onLocationChanged(): Device location change has been reported: " + location.getLatitude() + ":" + location.getLongitude());
		sendLocationUpdates(getLocationServerAddress(), Double.toString(location.getLongitude()), Double.toString(location.getLatitude()), getAddressOfRecord());
	}

	@Override
	public void onProviderDisabled(String provider) 
	{
		Log.i(this.getClass().getName(), MainActivity.LOG_PREFIX + "onProviderDisabled() has been called");
	}

	@Override
	public void onProviderEnabled(String provider) 
	{
		Log.i(this.getClass().getName(), MainActivity.LOG_PREFIX + "onProviderEnabled() has been called");
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) 
	{
		Log.i(this.getClass().getName(), MainActivity.LOG_PREFIX + "onStatusChnaged() has been called");
	}
	
	private String getLocationServerAddress()
	{
		return "http://192.168.140.143:8000";
	}
	
	private String getAddressOfRecord()
	{
		return "sip:test03@opencloud.com:5060";
	}
	
	private void sendLocationUpdates(String connectionString, String longitude, String latitude, String addressOfRecord)
	{
		Log.i(this.getClass().getName(), MainActivity.LOG_PREFIX + "sending location updates via HTTP");
		/**
		 * Construct the message object to be sent. This message object will be embedded. 
		 */
		Message message = new Message();
		message.setMessageType(Message.MSG_TYPE_LOCATION_UPDATE);
		message.setAddressOfRecord(addressOfRecord);
		message.setLongitude(longitude);
		message.setLatitude(latitude);
		message.setUpdateTimeStamp((new Date()).toString());
		/**
		 * Create the HTTP Client
		 */
		HttpClient httpClient = new DefaultHttpClient();
		try
		{
			/**
             * Creating an empty ByteArrayOutputStream
             */
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            /**
             * Creating a new ObjectOutputStream
             */
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            /**
             * Writing the Message object into the the objectOutputStream
             */
            objectOutputStream.writeObject(message);
            objectOutputStream.flush();
            /**
             * Convert the Message object into a ByteArray
             */
            byte[] messageByteArray = byteArrayOutputStream.toByteArray();
            /**
             * Construct a new HttpEntity and set its contents to the byteArray
             */
            ByteArrayEntity byteArrayEntity =  new ByteArrayEntity(messageByteArray);
            /**
             * Create a new HttpPost that contains the created ByteArray
             */                  
            HttpPost httpPost = new HttpPost(connectionString);        
            httpPost.setEntity(byteArrayEntity);
            /**
             * Turn off the Expect-Continue handshake header inclusion in the request
             * which cause the OC-HTTP-RA to respond back with a 417 Expectation Failed response.
             */
            httpClient.getParams().setBooleanParameter("http.protocol.expect-continue", false);
            /**
             * Send the created HttpPost
             */                                               
            httpClient.execute(httpPost);
            Log.i(this.getClass().getName(), MainActivity.LOG_PREFIX + "HTTP POST location update message has been sent to " + connectionString);
            /**
             * Terminate the established HTTP Session
             */
            httpClient.getConnectionManager().shutdown();
		}
		catch(Exception ex)
		{
			Log.e(this.getClass().getName(), MainActivity.LOG_PREFIX + ex.getMessage());
		}
	}
}