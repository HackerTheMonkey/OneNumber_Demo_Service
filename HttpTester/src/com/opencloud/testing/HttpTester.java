/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.opencloud.testing;

import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author Hasanein.Khafaji
 */
public class HttpTester
{
    public static void main (String args[])
    {
        /**
         * Sending a POST message and displaying the received response back.
         */
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost();
        ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
            public String handleResponse(HttpResponse hr) throws ClientProtocolException, IOException
            {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        }

    }
}
