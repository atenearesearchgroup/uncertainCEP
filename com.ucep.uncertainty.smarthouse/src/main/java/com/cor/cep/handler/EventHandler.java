package com.cor.cep.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.cor.cep.event.PersonEvent;
import com.cor.cep.event.HomeEvent;
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
    private EPStatement tempIncreaseEventStatement;
    private EPStatement tempWarningEventStatement;
    private EPStatement highCoEventStatement;
    private EPStatement fireWarningEventStatement;
    private EPStatement nobodyHomeEventStatement;
    private EPStatement notifyFireDepartmentEventStatement;

    @Autowired
    @Qualifier("tempIncreaseEventSubscriber")
    private StatementSubscriber tempIncreaseEventSubscriber;

    @Autowired
    @Qualifier("highCoEventSubscriber")
    private StatementSubscriber highCoEventSubscriber;
    
    @Autowired
    @Qualifier("tempWarningEventSubscriber")
    private StatementSubscriber tempWarningEventSubscriber;

    @Autowired
    @Qualifier("fireWarningEventSubscriber")
    private StatementSubscriber fireWarningEventSubscriber;
    
    @Autowired
    @Qualifier("nobodyHomeEventSubscriber")
    private StatementSubscriber nobodyHomeEventSubscriber;
    
    @Autowired
    @Qualifier("notifyFireDepartmentEventSubscriber")
    private StatementSubscriber notifyFireDepartmentEventSubscriber;
    
    /**
     * Configure Esper Statement(s).
     */
    public void initService() {

        LOG.debug("Initializing Service ..");
        Configuration config = new Configuration();
        config.addEventTypeAutoName("com.cor.cep.event");
        epService = EPServiceProviderManager.getDefaultProvider(config);

        createTempIncreaseCheckExpression();
        createHighCoCheckExpression();
        createTempWarningCheckExpression();
        createFireWarningCheckExpression();
        createNobodyHomeCheckExpression();
        createNotifyFireDepartmentCheckExpression();
    }

    /**
     * EPL to check temperature
     */
    private void createTempIncreaseCheckExpression() {
        
        LOG.debug("create Temperature Increase Check Expression");
        tempIncreaseEventStatement = epService.getEPAdministrator().createEPL(tempIncreaseEventSubscriber.getStatement());
        tempIncreaseEventStatement.setSubscriber(tempIncreaseEventSubscriber);
    }
    
    private void createHighCoCheckExpression() {
        
        LOG.debug("create Temperature Increase Check Expression");
        highCoEventStatement = epService.getEPAdministrator().createEPL(highCoEventSubscriber.getStatement());
        highCoEventStatement.setSubscriber(highCoEventSubscriber);
    }
    
    private void createTempWarningCheckExpression() {
    	tempWarningEventStatement = epService.getEPAdministrator().createEPL(tempWarningEventSubscriber.getStatement());
    	tempWarningEventStatement.setSubscriber(tempWarningEventSubscriber);
    }

    private void createFireWarningCheckExpression() {
    	fireWarningEventStatement = epService.getEPAdministrator().createEPL(fireWarningEventSubscriber.getStatement());
    	fireWarningEventStatement.setSubscriber(fireWarningEventSubscriber);
    }
    
    private void createNobodyHomeCheckExpression() {
        nobodyHomeEventStatement = epService.getEPAdministrator().createEPL(nobodyHomeEventSubscriber.getStatement());
        nobodyHomeEventStatement.setSubscriber(nobodyHomeEventSubscriber);
    }
    
    private void createNotifyFireDepartmentCheckExpression() {
    	notifyFireDepartmentEventStatement = epService.getEPAdministrator().createEPL(notifyFireDepartmentEventSubscriber.getStatement());
    	notifyFireDepartmentEventStatement.setSubscriber(notifyFireDepartmentEventSubscriber);
    }
    

    /**
     * Handle the incoming HandlingEvent.
     */
    public void handle(HomeEvent event) {
//        LOG.debug(event.toString()); // To print the event into console when it is created and sent to Esper
        epService.getEPRuntime().sendEvent(event);
    }
    
    public void handle(PersonEvent event) {
//      LOG.debug(event.toString()); // To print the event into console when it is created and sent to Esper
      epService.getEPRuntime().sendEvent(event);

  }

    @Override
    public void afterPropertiesSet() {
        LOG.debug("Configuring...");
        initService();
    }
}
