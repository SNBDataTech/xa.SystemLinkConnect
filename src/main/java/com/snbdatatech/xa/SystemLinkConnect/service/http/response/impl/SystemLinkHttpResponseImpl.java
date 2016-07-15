package com.snbdatatech.xa.SystemLinkConnect.service.http.response.impl;

import com.snbdatatech.xa.SystemLinkConnect.service.http.response.SystemLinkHttpResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;

/**
 * Created by sblin on 7/7/2016.
 */
@Component
public class SystemLinkHttpResponseImpl implements SystemLinkHttpResponse {

    @Value("${systemLink.response.slTagName}")
    private String slTagName;

    @Value("${systemLink.response.loginTagName}")
    private String loginTagName;

    @Value("${systemLink.response.exceptionTagName}")
    private String exceptionTagName;

    private SAXReader saxReader;
    private boolean erred;
    private boolean loggedIn;

    // Create logger
    private final Logger logger = LogManager.getLogger(SystemLinkHttpResponseImpl.class);

    /**
     * Initialize the HTTP response on bean creation
     */
    public void init() {

        // Initialize reader
        this.saxReader = new SAXReader();

        // Set default status variables
        this.erred = true;
        this.loggedIn = false;
    }

    /**
     * Read the response from System Link and set status variables
     * @param reader the buffered reader holding the response
     */
    public void readSystemLinkResponse(BufferedReader reader) {

        try {

            // Read the given XML into a document
            Document document = this.saxReader.read(reader);

            // Get the XML document's root element
            Element root = document.getRootElement();

            // Verify root element has System Link element name
            if (root.getName().equals(this.slTagName)) {

                // Determine if exception occurred
                this.erred = this.didExceptionOccur(root.element(this.exceptionTagName));

                // Determine if login was successful
                this.loggedIn = this.wasLoginSuccessful(root.element(this.loginTagName));
            }

        } catch (DocumentException e) {

            // Log the exception
            this.logger.error(e.getMessage());
        }
    }

    private boolean didExceptionOccur(Element element) {

        return false;
    }

    private boolean wasLoginSuccessful(Element element) {
        return true;
    }

    public boolean isErred() {
        return erred;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }
}
