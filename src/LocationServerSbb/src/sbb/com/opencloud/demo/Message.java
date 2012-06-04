package com.opencloud.demo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Message implements Serializable
{

    /**
     * Set the various message types in here
     */
    public static final String MSG_TYPE_LOCATION_UPDATE = "location_update_message";
    public static final String MSG_TYPE_LOCATION_QUERY = "location_query_message";
    /**
     * Set the private variables here, for each private variable we need a setter
     * and a getter method to enable external entities from accessing it.
     */
    private String addressOfRecord = null;
    private String longitude = null;
    private String latitude = null;
    private String updateTimeStamp = null;    
    private String messageType = null;
    /**
     * Getter and Setter methods definitions that enable external entities from
     * accessing the information communicated inside the this Message object.
     */
    /**
     * Get the addressOfRecord
     */
    public String getAddressOfRecord()
    {
        return addressOfRecord;
    }

    /**
     * Set the addressOfRecord
     */
    public void setAddressOfRecord(String aor)
    {
        this.addressOfRecord = aor;
    }

    /**
     * Get the UpdateTimeStamp
     */
    public String getUpdateTimeStamp()
    {
        return this.updateTimeStamp;
    }

    /**
     * Set the updateTimeStamp
     */
    public void setUpdateTimeStamp(String updateTimeStamp)
    {
        this.updateTimeStamp = updateTimeStamp;
    }

    /**
     * Set the Longitude
     */
    public void setLongitude(String longitude)
    {
        this.longitude = longitude;
    }

    /**
     * Get the Longitude
     */
    public String getLongitude()
    {
        return this.longitude;
    }

    /**
     * Set the latitude
     */
    public void setLatitude(String latitude)
    {
        this.latitude = latitude;
    }

    /**
     * Get the latitude
     */
    public String getLatitude()
    {
        return this.latitude;
    }

    /**
     * Set the messageType
     */
    public void setMessageType(String messageType)
    {
        this.messageType = messageType;
    }

    /**
     * Get the messageType
     */
    public String getMessageType()
    {
        return messageType;
    }

}
