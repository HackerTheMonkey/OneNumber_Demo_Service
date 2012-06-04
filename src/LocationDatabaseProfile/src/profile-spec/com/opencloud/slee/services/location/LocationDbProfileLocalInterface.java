/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.opencloud.slee.services.location;

import javax.sip.header.ContactHeader;
import javax.slee.profile.ProfileLocalObject;

/**
 *
 * @author Hasanein.Khafaji
 */
public interface LocationDbProfileLocalInterface extends ProfileLocalObject, LocationDbProfileSpecCMP
{
    public void addBinding(ContactHeader[] contactHeaders);

    public void removeBinding(String[] contactHeaders);

    public String[] queryBinding();
    
}