package com.snbdatatech.xa.SystemLinkConnect;

import com.snbdatatech.xa.SystemLinkConnect.service.router.AppStartupRouter;
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

        // Create startup router and start system link request
        AppStartupRouter router = (AppStartupRouter) context.getBean("appStartupRouter");
        router.startSystemLinkRequest();

        LOGGER.info("##### PROCESS COMPLETE #####");

        // Create Hook to destroy all active Beans
        ((AbstractApplicationContext) context).registerShutdownHook();

        // Close the context
        ((ClassPathXmlApplicationContext)context).close();
    }
}
