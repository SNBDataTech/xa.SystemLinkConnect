package com.snbdatatech.xa.SystemLinkConnect.service.router;

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

        try {

            // Send the System Link request
            this.request.sendSystemLinkRequest(this.connection.getConnection());

            System.out.println(this.connection.getConnection().getResponseCode());

        } catch (IOException e) {

            // Log the exception
            logger.error(e.getMessage());
        }
    }
}
