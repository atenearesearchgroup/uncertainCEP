package com.cor.cep.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.cor.cep.event.TempEvent;
import com.cor.cep.event.OxigenEvent;
import com.cor.cep.event.TrafficJamEvent;
import com.cor.cep.subscriber.StatementSubscriber;
import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

/**
 * This class handles incoming Temperature Events. It processes them through the EPService, to which
 * it has attached the 3 queries.
 */
@Component
@Scope(value = "singleton")
public class EventHandler implements InitializingBean {

    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(EventHandler.class);

    /** Esper service */
    private EPServiceProvider epService;
    private EPStatement tvsMalfun1EventStatement;
    private EPStatement tvsMalfun2EventStatement;
    private EPStatement tvsMalfun3EventStatement;
   

    @Autowired
    @Qualifier("tvsMalfun1EventSubscriber")
    private StatementSubscriber tvsMalfun1EventSubscriber;

    @Autowired
    @Qualifier("tvsMalfun2EventSubscriber")
    private StatementSubscriber tvsMalfun2EventSubscriber;
    
    @Autowired
    @Qualifier("tvsMalfun3EventSubscriber")
    private StatementSubscriber tvsMalfun3EventSubscriber;

    
    /**
     * Configure Esper Statement(s).
     */
    public void initService() {

        LOG.debug("Initializing Service ..");
        Configuration config = new Configuration();
        config.addEventTypeAutoName("com.cor.cep.event");
        epService = EPServiceProviderManager.getDefaultProvider(config);

        createtvsMalfun1CheckExpression();
        createtvsMalfun2CheckExpression();
        createtvsMalfun3CheckExpression();
       
    }

    /**
     * EPL to check temperature
     */
    private void createtvsMalfun1CheckExpression() {
        
        LOG.debug("create tvsMalfun1 Check Expression");
        tvsMalfun1EventStatement = epService.getEPAdministrator().createEPL(tvsMalfun1EventSubscriber.getStatement());
        tvsMalfun1EventStatement.setSubscriber(tvsMalfun1EventSubscriber);
    }
    
    private void createtvsMalfun2CheckExpression() {
        
        LOG.debug("create tvsMalfun2 Check Expression");
        tvsMalfun2EventStatement = epService.getEPAdministrator().createEPL(tvsMalfun2EventSubscriber.getStatement());
        tvsMalfun2EventStatement.setSubscriber(tvsMalfun2EventSubscriber);
    }
    
    private void createtvsMalfun3CheckExpression() {
    	
    		LOG.debug("create tvsMalfun3 Check Expression");
    		tvsMalfun3EventStatement = epService.getEPAdministrator().createEPL(tvsMalfun3EventSubscriber.getStatement());
    		tvsMalfun3EventStatement.setSubscriber(tvsMalfun3EventSubscriber);
    }

    
    /**
     * Handle the incoming HandlingEvent.
     */
    public void handle(OxigenEvent event) {
//        LOG.debug(event.toString()); // To print the event into console when it is created and sent to Esper
        epService.getEPRuntime().sendEvent(event);
    }
    
    public void handle(TempEvent event) {
//      LOG.debug(event.toString()); // To print the event into console when it is created and sent to Esper
      epService.getEPRuntime().sendEvent(event);

    }
    
    public void handle(TrafficJamEvent event) {
//      LOG.debug(event.toString()); // To print the event into console when it is created and sent to Esper
      epService.getEPRuntime().sendEvent(event);
  }

    @Override
    public void afterPropertiesSet() {
        
        LOG.debug("Configuring...");
        initService();
    }
}

