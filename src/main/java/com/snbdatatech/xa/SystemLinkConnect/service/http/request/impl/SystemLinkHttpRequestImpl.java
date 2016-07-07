package com.snbdatatech.xa.SystemLinkConnect.service.http.request.impl;

import com.snbdatatech.xa.SystemLinkConnect.service.http.request.SystemLinkHttpRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

/**
 * Created by sblin on 7/7/2016.
 */
@Component
public class SystemLinkHttpRequestImpl implements SystemLinkHttpRequest {

    @Value("${as400.systemName}")
    private String systemName;

    @Value("${as400.environmentID}")
    private String environmentID;

    @Value("${as400.userName}")
    private String userName;

    @Value("${as400.password}")
    private String password;

    @Value("${http.charset}")
    private String charset;

    private String encodedRequest;

    // Create logger
    private final Logger logger = LogManager.getLogger(SystemLinkHttpRequestImpl.class);

    /**
     * Initialize the HTTP request on bean creation
     */
    public void init() {

        try {

            // Encode the request
            this.encodedRequest = URLEncoder.encode("SystemLinkRequest", this.charset) + "=" +
                URLEncoder.encode(this.createSystemLinkRequest().toString(), this.charset);

        } catch (UnsupportedEncodingException e) {

            // Log the exception
            logger.error(e.getMessage());
        }
    }

    /**
     * Create the system link request string
     * @return the system link request
     */
    private StringBuffer createSystemLinkRequest() {

        // Create temp buffer to hold setup
        StringBuffer buffer = new StringBuffer();

        // Create buffer content
        buffer
            .append("<?xml version='1.0' encoding='" + this.charset + "'?>")
            .append("<!DOCTYPE System-Link SYSTEM 'SystemLinkRequest.dtd'>")
            .append("<System-Link>")
            .append("<Login userId='" + this.userName + "' ")
            .append("password='" + this.password + "' maxIdle='0' ")
            .append("properties='com.mapics.cas.domain.EnvironmentId=" + this.environmentID)
            .append(", com.mapics.cas.domain.SystemName=" + this.systemName)
            .append(", com.mapics.cas.user.LanguageId=en'/>")
            .append("<Request sessionHandle='*current' workHandle='*new' broker='EJB' maxIdle='1000'></Request>")
            .append("</System-Link>");

        // Return the string buffer
        return buffer;
    }

    /**
     * Send the System Link request
     * @param connection the HTTP connection
     * @return true if request was sent successfully
     */
    public boolean sendSystemLinkRequest(HttpURLConnection connection) {

        try {

            // Open up the connection's output stream
            OutputStream outputStream = connection.getOutputStream();

            // Write the request to the output stream
            outputStream.write(this.encodedRequest.getBytes(this.charset));

            // Flush and close the stream
            outputStream.flush();
            outputStream.close();

            // Verify response code == 200
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                // Request was successful, return true
                return true;
            }

            // Failed request
            return false;

        } catch (IOException e) {

            // Log the exception
            this.logger.error(e.getMessage());

            // Request failed, return false
            return false;
        }
    }
}
