package com.snbdatatech.xa.SystemLinkConnect.service.http.response.impl;

import com.snbdatatech.xa.SystemLinkConnect.service.http.response.SystemLinkHttpResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;

/**
 * Created by sblin on 7/7/2016.
 */
@Component
public class SystemLinkHttpResponseImpl implements SystemLinkHttpResponse {

    private SAXReader saxReader;
    private Document document;
    private Element root;

    // Create logger
    private final Logger logger = LogManager.getLogger(SystemLinkHttpResponseImpl.class);

    /**
     * Initialize the HTTP response on bean creation
     */
    public void init() {
        this.saxReader = new SAXReader();
    }

    public void readSystemLinkResponse(BufferedReader reader) {

        try {

            // Read the given XML into a document
            this.document = this.saxReader.read(reader);

            // Get the root element
            this.root = this.document.getRootElement();

            Node login = this.root.element("LoginResponse");
            logger.error(login);

        } catch (DocumentException e) {

            // Log the exception
            this.logger.error(e.getMessage());
        }
    }
}
