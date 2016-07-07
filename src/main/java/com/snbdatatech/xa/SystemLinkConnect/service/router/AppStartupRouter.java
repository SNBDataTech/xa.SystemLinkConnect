package com.snbdatatech.xa.SystemLinkConnect.service.router;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import com.snbdatatech.xa.SystemLinkConnect.service.http.connect.SystemLinkHttpConnection;
import com.snbdatatech.xa.SystemLinkConnect.service.http.request.SystemLinkHttpRequest;
import com.snbdatatech.xa.SystemLinkConnect.service.http.response.SystemLinkHttpResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * Created by sblin on 7/7/2016.
 */
public class AppStartupRouter {

    @Autowired
    SystemLinkHttpConnection connection;

    @Autowired
    SystemLinkHttpRequest request;

    @Autowired
    SystemLinkHttpResponse response;

    // Create logger
    private final Logger logger = LogManager.getLogger(AppStartupRouter.class);

    public void startSystemLinkRequest() {

        // Send the System Link request
        if (this.request.sendSystemLinkRequest(this.connection.getConnection())) {

            try {

                // Get the System Link response
                BufferedReader reader = new BufferedReader(new InputStreamReader(this.connection.getConnection().getInputStream(), "UTF-8"));

                // Read the System Link response
                this.response.readSystemLinkResponse(reader);

            } catch (IOException e) {

                // Log the exception
                this.logger.error(e.getMessage());
            }
        }

    }
}
