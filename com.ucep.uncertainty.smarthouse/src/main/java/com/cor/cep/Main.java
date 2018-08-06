package com.cor.cep;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//import com.cor.cep.util.EventGenerator;
import com.cor.cep.util.EventGenerator;
//import com.cor.cep.util.PersonEventGenerator;

/**
 * Entry point for the Demo. Run this from your IDE, or from the command line using 'mvn exec:java'.
 */
public class Main {

    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(Main.class);

    
    /**
     * Main method - start the Demo!
     */
    public static void main(String[] args) throws Exception {

        LOG.debug("Starting...");

        // Load spring config
        ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(new String[] { "application-context.xml" });
        BeanFactory factory = (BeanFactory) appContext;

        // Start Demo
//        EventGenerator homeGenerator = (EventGenerator) factory.getBean("homeEventGenerator");
//        homeGenerator.startSendingTemperatureReadings();
//        homeGenerator.startSendingPersonReadings();

        EventGenerator eventGenerator = (EventGenerator) factory.getBean("eventGenerator");
        eventGenerator.startSendingEvents();
        
    }

}
