package com.snbdatatech.xa.SystemLinkConnect;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by sblin on 7/7/2016.
 */
public class App {

    // Create an application context
    private static ApplicationContext context;

    public static void main(String[] args) {

        // Create the context to load Bean configuration file
        context = new ClassPathXmlApplicationContext("beans.xml");

        System.out.println("HERE");

        // Create Hook to destroy all active Beans
        ((AbstractApplicationContext) context).registerShutdownHook();

        // Close the context
        ((ClassPathXmlApplicationContext)context).close();
    }
}
