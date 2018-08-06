package com.cor.cep.handler;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.cor.cep.event.RunnerEvent;
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
public class RunnerEventHandler implements InitializingBean {

	
    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(RunnerEventHandler.class);

    /** Esper service */
    private EPServiceProvider epService;
    private EPStatement theWallEventStatement;
    private EPStatement negativeSplitEventStatement;
    private EPStatement optimalEventStatement;
    private EPStatement finisherEventStatement;
    private EPStatement cheatedEventStatement;
    private EPStatement recordEventStatement;
    private EPStatement scapedEventStatement;
    
    @Autowired
    @Qualifier("theWallSubscriber")
    private StatementSubscriber theWall;
    
    @Autowired
    @Qualifier("negativeSplitSubscriber")
    private StatementSubscriber negativeSplit;
    
    @Autowired
    @Qualifier("optimalSubscriber")
    private StatementSubscriber optimal;
    
    @Autowired
    @Qualifier("finisherSubscriber")
    private StatementSubscriber finisher;
    
    @Autowired
    @Qualifier("cheatedSubscriber")
    private StatementSubscriber cheated;
    
    @Autowired
    @Qualifier("recordSubscriber")
    private StatementSubscriber record;
    
    @Autowired
    @Qualifier("scapedSubscriber")
    private StatementSubscriber scaped;

    /**
     * Configure Esper Statement(s).
     */
    public void initService() {
    	
    		
    		
        LOG.debug("Initializing Servcie ..");
        Configuration config = new Configuration();
        config.addEventTypeAutoName("com.cor.cep.event");
        
        
        epService = EPServiceProviderManager.getDefaultProvider(config);
        
        //epService.getEPAdministrator().createEPL("create context from RunnerEvent");
        createTheWallCheckExpression();
        createNegativeSplitCheckExpression();
        createOptimalCheckExpression();
        createFinisherCheckExpression();
        createCheatedCheckExpression();
        createRecordCheckExpression();
        createScapedCheckExpression();

    }

    /**
     * EPL to check Runner
     */
    private void createTheWallCheckExpression() {
        
        LOG.debug("create The Wall Check Expression");
        theWallEventStatement = epService.getEPAdministrator().createEPL(theWall.getStatement());
        theWallEventStatement.setSubscriber(theWall);
    }
    
    
    /**
     * EPL to check Runner
     */
    private void createNegativeSplitCheckExpression() {
        
        LOG.debug("create Negative Split Check Expression");
        negativeSplitEventStatement = epService.getEPAdministrator().createEPL(negativeSplit.getStatement());
        negativeSplitEventStatement.setSubscriber(negativeSplit);
    }
    
    /**
     * EPL to check Runner
     */
    private void createOptimalCheckExpression() {
        
        LOG.debug("create Optimal Check Expression");
        optimalEventStatement = epService.getEPAdministrator().createEPL(optimal.getStatement());
        optimalEventStatement.setSubscriber(optimal);
    }
    
    /**
     * EPL to check Runner
     */
    private void createFinisherCheckExpression() {
        
        LOG.debug("create Finisher Check Expression");
        finisherEventStatement = epService.getEPAdministrator().createEPL(finisher.getStatement());
        finisherEventStatement.setSubscriber(finisher);
    }
    
    /**
     * EPL to check Runner
     */
    private void createCheatedCheckExpression() {
        
        LOG.debug("create Cheatead Check Expression");
        cheatedEventStatement = epService.getEPAdministrator().createEPL(cheated.getStatement());
        cheatedEventStatement.setSubscriber(cheated);
    }
    
    /**
     * EPL to check Runner
     */
    private void createRecordCheckExpression() {
        
        LOG.debug("create Record Check Expression");
        recordEventStatement = epService.getEPAdministrator().createEPL(record.getStatement());
        recordEventStatement.setSubscriber(record);
    }
    
    /**
     * EPL to check Runner
     */
    private void createScapedCheckExpression() {
        
        LOG.debug("create Scaped Check Expression");
        scapedEventStatement = epService.getEPAdministrator().createEPL(scaped.getStatement());
        scapedEventStatement.setSubscriber(scaped);
    }
    
    /**
     * Handle the incoming RunnerEvent.
     */
    public void handle(RunnerEvent event) {

        //LOG.debug(event.toString());
        epService.getEPRuntime().sendEvent(event);

    }

    @Override
    public void afterPropertiesSet() {
        
        LOG.debug("Configuring..");
        initService();
    }
}

