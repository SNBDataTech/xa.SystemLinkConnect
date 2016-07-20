package com.snbdatatech.xa.SystemLinkConnect;

import com.snbdatatech.xa.SystemLinkConnect.controller.SystemLinkController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by sblin on 7/7/2016.
 */
public class App {

    // Create an application context
    private static ApplicationContext context;

    // Create logger
    private static final Logger LOGGER = LogManager.getLogger(App.class);

    public static void main(String[] args) {

        // Create the context to load Bean configuration file
        context = new ClassPathXmlApplicationContext("beans.xml");

        // Create controller to process system link request
        SystemLinkController controller = (SystemLinkController) context.getBean("appController");

        // Determine if System Link is available
        if(controller.isSystemLinkAvailable()) {
            LOGGER.info("##### SYSTEM LINK IS AVAILABLE #####");
        } else {
            LOGGER.info("##### SYSTEM LINK IS NOT AVAILABLE #####");
        }

        // Create Hook to destroy all active Beans
        ((AbstractApplicationContext) context).registerShutdownHook();

        // Close the context
        ((ClassPathXmlApplicationContext)context).close();
    }
}
