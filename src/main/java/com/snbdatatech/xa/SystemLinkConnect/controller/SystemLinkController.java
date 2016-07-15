package com.snbdatatech.xa.SystemLinkConnect.controller;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import com.snbdatatech.xa.SystemLinkConnect.service.http.connect.SystemLinkHttpConnection;
import com.snbdatatech.xa.SystemLinkConnect.service.http.request.SystemLinkHttpRequest;
import com.snbdatatech.xa.SystemLinkConnect.service.http.response.SystemLinkHttpResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

/**
 * Created by sblin on 7/7/2016.
 */
public class SystemLinkController {

    @Autowired
    SystemLinkHttpConnection connection;

    @Autowired
    SystemLinkHttpRequest request;

    @Autowired
    SystemLinkHttpResponse response;

    @Value("${http.charset}")
    private String charset;

    // Create logger
    private final Logger logger = LogManager.getLogger(SystemLinkController.class);

    /**
     * Process the system link request
     */
    public boolean isSystemLinkAvailable() {

        // Send the System Link request
        if (this.request.sendSystemLinkRequest(this.connection.getConnection())) {

            try {

                // Get the System Link response
                BufferedReader reader = new BufferedReader(new InputStreamReader(this.connection.getConnection().getInputStream(), this.charset));

                // Read the System Link response
                this.response.readSystemLinkResponse(reader);

                // Determine login status
                return ((!this.response.isErred()) && this.response.isLoggedIn()) ? true : false;

            } catch (IOException e) {

                // Log the exception
                this.logger.error(e.getMessage());

                // Exception occurred in response, return false
                return false;
            }
        }

        // System Link not available -- Failed to successfully send Request
        return false;
    }
}
