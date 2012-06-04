package com.opencloud.demo.onenumber.location;

import com.opencloud.demo.Message;
import com.opencloud.slee.resources.http.HttpRequest;
import com.opencloud.slee.services.location.LocationDbProfileLocalInterface;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.slee.ActivityContextInterface;
import javax.slee.CreateException;
import javax.slee.RolledBackContext;
import javax.slee.Sbb;
import javax.slee.SbbContext;
import javax.slee.facilities.ActivityContextNamingFacility;
import javax.slee.facilities.AlarmFacility;
import javax.slee.facilities.TimerFacility;
import javax.slee.facilities.Tracer;
import javax.slee.nullactivity.NullActivityContextInterfaceFactory;
import javax.slee.nullactivity.NullActivityFactory;
import javax.slee.profile.ProfileFacility;
import javax.slee.profile.ProfileLocalObject;
import javax.slee.profile.ProfileTable;
import javax.slee.serviceactivity.ServiceActivityContextInterfaceFactory;
import javax.slee.serviceactivity.ServiceActivityFactory;

/**
 *
 * @author Hasanein.Khafaji
 */
public abstract class LocationServerSbb implements Sbb
{

    /**
     * Private variables definition
     */
    private SbbContext sbbContext = null;
    private AlarmFacility alarmFacility = null;
    private ProfileFacility profileFacility = null;
    private TimerFacility timerFacility = null;
    private ActivityContextNamingFacility acNamingFacility = null;
    private NullActivityContextInterfaceFactory nullActivityContextInterfaceFactory = null;
    private NullActivityFactory nullActivityFactory = null;
    private ServiceActivityFactory serviceActivityFactory = null;
    private ServiceActivityContextInterfaceFactory serviceActivityContextInterfaceFactory = null;
    private Tracer tracer = null;
    private boolean debugEnabled = false;
    private final String LOCATION_DATABASE_PROFILE_TABLE_NAME = "LocationProfileTable";
    /**
     * Set the SbbContext and get the tracer and the facilities
     * provided by the JSLEE Container
     * @param sc
     */
    public void setSbbContext(SbbContext sbbContext)
    {
        this.sbbContext = sbbContext;
        /**
         * get the tracer
         */
        tracer = sbbContext.getTracer(sbbContext.getSbb().getName());
        tracer.info("The SbbContext has been set...");
        /**
         * Query the JNDI for the JSLEE-provided facilities
         */
        try
        {
            tracer.info("Looking up the JNDI for the JSLEE-provided facilities...");
            /**
             * Set a JNDI initial context to start the lookup
             * operation from
             */
            InitialContext initialContext = new InitialContext();
            /**
             * Starting from the initial context, query for the
             * desired JSLEE-injected facilities
             */
            alarmFacility = (AlarmFacility) initialContext.lookup(alarmFacility.JNDI_NAME);
            profileFacility = (ProfileFacility) initialContext.lookup(profileFacility.JNDI_NAME);
            timerFacility = (TimerFacility) initialContext.lookup(timerFacility.JNDI_NAME);
            acNamingFacility = (ActivityContextNamingFacility) initialContext.lookup(acNamingFacility.JNDI_NAME);
            nullActivityContextInterfaceFactory = (NullActivityContextInterfaceFactory) initialContext.lookup(nullActivityContextInterfaceFactory.JNDI_NAME);
            nullActivityFactory = (NullActivityFactory) initialContext.lookup(nullActivityFactory.JNDI_NAME);
            serviceActivityFactory = (ServiceActivityFactory) initialContext.lookup(serviceActivityFactory.JNDI_NAME);
            serviceActivityContextInterfaceFactory = (ServiceActivityContextInterfaceFactory) initialContext.lookup(serviceActivityContextInterfaceFactory.JNDI_NAME);

            tracer.info("The facilities have been successfully obtained from the JNDI...");
        }
        catch (NamingException ex)
        {
            tracer.severe(ex.getMessage());
        }
    }

    public void sbbActivate()
    {
        //tracer.info("SbbActivate method has been called...");
    }

    public void sbbCreate() throws CreateException
    {
        if (debugEnabled)
        {
            tracer.info("SbbCreate method has been called...");
        }
    }

    public void sbbExceptionThrown(Exception excptn, Object o, ActivityContextInterface aci)
    {
        if (debugEnabled)
        {
            tracer.info("SbbExceptionThrown method has been called...");
        }
    }

    public void sbbLoad()
    {
        if (debugEnabled)
        {
            tracer.info("SbbLoad method has been called...");
        }
    }

    public void sbbPassivate()
    {
        if (debugEnabled)
        {
            tracer.info("SbbPassivate method has been called...");
        }
    }

    public void sbbPostCreate() throws CreateException
    {
        if (debugEnabled)
        {
            tracer.info("SbbPostCreate method has been called...");
        }
    }

    public void sbbRemove()
    {
        if (debugEnabled)
        {
            tracer.info("SbbRemove method has been called...");
        }
    }

    public void sbbRolledBack(RolledBackContext rbc)
    {
        if (debugEnabled)
        {
            tracer.info("SbbRolledBack method has been called...");
        }
    }

    public void sbbStore()
    {
        if (debugEnabled)
        {
            tracer.info("SbbStore method has been called...");
        }
    }

    public void unsetSbbContext()
    {
        if (debugEnabled)
        {
            tracer.info("Clearing the SbbContext...");
        }
        sbbContext = null;
        if (debugEnabled)
        {
            tracer.info("The SbbContext has been cleared out...");
        }

        if (debugEnabled)
        {
            tracer.info("Clearing the JSLEE-provided facilities...");
        }
        clearFacilities();
        if (debugEnabled)
        {
            tracer.info("The JSLEE-provided facilities have been cleared out.");
        }
    }

    private void clearFacilities()
    {
        alarmFacility = null;
        profileFacility = null;
        timerFacility = null;
        acNamingFacility = null;
        nullActivityContextInterfaceFactory = null;
        nullActivityFactory = null;
        serviceActivityFactory = null;
        serviceActivityContextInterfaceFactory = null;
    }

    public void onPostRequest(HttpRequest httpRequest, ActivityContextInterface aci)
    {
        try
        {
            tracer.info("HTTP POST event has been received....");
            /**
             * Construct a ByteArrayInputStream using the contents of the received
             * HttpPost request (the request entity).
             */
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(httpRequest.getContent());
            /**
             * Create a new ObjectInputStream using the previously created ByteArrayInputStream
             */
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            /**
             * Read the sent message via the objectInputStream
             */
            Message message = (Message) objectInputStream.readObject();
            /**
             * Here we need to look to directly access the LocationDatabase
             * profile table and search for a profile with the same name as the
             * AOR contained in the received message. In the case where there is
             * no profile with such a name, then we simply need to quit the process
             * of location update/query with the appropriate response to the
             * requestor, if needed.
             */
            tracer.info("Looking up profile for the address of record received in the HTTP location update message...");
            LocationDbProfileLocalInterface profile = lookupProfile(message.getAddressOfRecord());
            if (profile != null)
            {
                /**
                 * Update the location information into the location database profile
                 */
                tracer.info("Profile has been found, updating location information...");
                storeLocationInformation(profile, message.getLongitude(), message.getLatitude(), message.getUpdateTimeStamp());
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void onGetRequest(HttpRequest httpRequest, ActivityContextInterface aci)
    {
        tracer.info("HTTP GET event has been received....");
    }

    public void onDeleteRequest(HttpRequest httpRequest, ActivityContextInterface aci)
    {
        tracer.info("HTTP DELETE event has been received....");
    }

    public void onPutRequest(HttpRequest httpRequest, ActivityContextInterface aci)
    {
        tracer.info("HTTP PUT event has been received....");
    }

    private void storeLocationInformation(LocationDbProfileLocalInterface profile,String longitude, String latitude, String updateTimeStamp)
    {
        tracer.info("Setting the location information for the device with AOR: " + profile.getProfileName() + ", Location Information: Longitude=" + longitude + ", Latitude=" + latitude + ", UpdateTimeStamp=" + updateTimeStamp);
        profile.setLatitude(latitude);
        profile.setLongitude(longitude);
        profile.setTimestamp(updateTimeStamp);
    }

    /**
     * This method will search for the given profile table for the existence
     * of a profile that match the passed in address of record (AOR), if it does
     * not find that specific profile, then it would simply return null.
     * @param addressOfRecord
     * @return
     */
    private LocationDbProfileLocalInterface lookupProfile(String addressOfRecord)
    {
        tracer.info("Looking up profile for the address of record: " + addressOfRecord);
        LocationDbProfileLocalInterface aorProfile = null;
        try
        {
            ProfileTable locationProfileTable = profileFacility.getProfileTable(LOCATION_DATABASE_PROFILE_TABLE_NAME);
            if (locationProfileTable != null) // If the ProfileTable exists
            {
                ProfileLocalObject profileLocalObject = locationProfileTable.find(addressOfRecord);
                if(profileLocalObject != null)
                {
                    tracer.info("Profile has been found...");
                    aorProfile = (LocationDbProfileLocalInterface) profileLocalObject;
                }
            }
            else
            {
                tracer.severe("ProfileTable with the name: LocationProfileTable does not exist");
                tracer.severe("Please contact the JSLEE container administrator to resolve the issue...");
                /**
                 * We need to design an exception to be thrown at this point.
                 */
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return aorProfile;
    }

}