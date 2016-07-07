package com.snbdatatech.xa.SystemLinkConnect.service.http.connect.impl;

import com.snbdatatech.xa.SystemLinkConnect.service.http.connect.SystemLinkHttpConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.net.HttpURLConnection;

/**
 * Created by sblin on 7/7/2016.
 */
@Component
public class SystemLinkHttpConnectionImpl implements SystemLinkHttpConnection {

    @Value("${as400.systemURL}")
    private String systemURL;

    private URL url;
    private HttpURLConnection connection;

    @Value("${http.connectTimeout}")
    private int connectTimeout;

    @Value("${http.doOutput}")
    private boolean doOutput;

    @Value("${http.contentType}")
    private String contentType;

    @Value("${http.readTimeout}")
    private int readTimeout;

    @Value("${http.requestMethod}")
    private String requestMethod;

    // Create logger
    private final Logger logger = LogManager.getLogger(SystemLinkHttpConnectionImpl.class);

    /**
     * Initialize the HTTP connection on bean creation
     */
    public void init() {

        try {

            // Create URL to AS400
            this.url = new URL(this.systemURL);

            // Connect to the AS400
            this.connect();

        } catch (IOException e) {

            // Log the exception
            logger.error(e.getMessage());
        }
    }

    /**
     * Create the HTTP URL connection
     * @throws IOException
     */
    private void connect() throws IOException {

        // Open the URL connection
        this.connection = (HttpURLConnection) this.url.openConnection();

        // Set the request method
        this.connection.setRequestMethod(this.requestMethod);

        // Set connection parameters
        this.connection.setConnectTimeout(this.connectTimeout);
        this.connection.setDoOutput(this.doOutput);
        this.connection.setRequestProperty("Content-Type", this.contentType);
        this.connection.setReadTimeout(this.readTimeout);
    }

    /**
     * Get the HTTP Connection
     * @return the connection
     */
    public HttpURLConnection getConnection() {
        return connection;
    }
}
