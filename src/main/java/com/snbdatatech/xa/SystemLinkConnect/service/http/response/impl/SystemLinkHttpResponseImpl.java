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

    @Value("${systemLink.response.exceptionTagName}")
    private String exceptionTagName;

    @Value("${systemLink.response.loginTagName}")
    private String loginTagName;

    @Value("${systemLink.response.loginActionAttribute}")
    private String loginActionAttribute;

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

        // Set default status variables -- opposite of positive SL connection
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
                this.erred = this.didExceptionOccur(root);

                // Determine if login was successful
                this.loggedIn = this.wasLoginSuccessful(root);
            }

        } catch (DocumentException e) {

            // Log the exception
            this.logger.error(e.getMessage());
        }
    }

    /**
     * Determine if the System Link response contains an exception tag
     * @param root Document Root
     * @return true if there was an exception
     */
    private boolean didExceptionOccur(Element root) {

        // Get the exception tag
        Element exception = root.element(this.exceptionTagName);

        // Determine if exception was found
        if (exception != null) {

            // Log the SL exception
            this.logger.error("There was an exception in the response from SystemLink.  Exception Name: [" +
                    exception.attributeValue("name") + "]");

            // Exception was found
            return true;
        }

        // No exception was found
        return false;
    }

    /**
     * Determine if login attempt to SL was successful
     * @param root Document root
     * @return true if login attempt was successfull
     */
    private boolean wasLoginSuccessful(Element root) {

        // Get the login Node from the response
        Node login = root.element(this.loginTagName);

        // Determine if Login node was found and is an element
        if ((login != null) && (login.getNodeType() == Node.ELEMENT_NODE)) {

            // Get the login status from the action attribute
            boolean loginStatus = Boolean.parseBoolean(((Element) login).attributeValue(this.loginActionAttribute));

            // Determine if login was successful
            if (loginStatus) {
                return true;
            }
        }

        // Login attempt was unsuccessful
        return false;
    }

    public boolean isErred() {
        return erred;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }
}
