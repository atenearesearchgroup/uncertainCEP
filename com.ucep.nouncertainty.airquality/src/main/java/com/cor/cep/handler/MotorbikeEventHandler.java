package com.cor.cep.handler;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.cor.cep.event.MotorbikeEvent;
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
public class MotorbikeEventHandler implements InitializingBean{

    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(MotorbikeEventHandler.class);

    /** Esper service */
    private EPServiceProvider epService;
    private EPStatement driverLeftSeatEventStatement;
    private EPStatement blowOutTireEventStatement;
    private EPStatement crashEventStatement;
    private EPStatement occupantThrownAccidentEventStatement;
    private EPStatement accidentReportEventStatement;
    private EPStatement dangerousLocationEventStatement;
    private EPStatement ghostRiderEventStatement;
    
    @Autowired
    @Qualifier("driverLeftSeatSubscriber")
    private StatementSubscriber driverLeftSeat;
    @Autowired
    @Qualifier("blowOutTireSubscriber")
    private StatementSubscriber blowOutTire;
    @Autowired
    @Qualifier("crashSubscriber")
    private StatementSubscriber crash;
    @Autowired
    @Qualifier("occupantThrownAccidentSubscriber")
    private StatementSubscriber occupantThrownAccident;
    @Autowired
    @Qualifier("accidentsReportSubscriber")
    private StatementSubscriber accidentReport;
    @Autowired
    @Qualifier("dangerousLocationSubscriber")
    private StatementSubscriber dangerousLocation;
    @Autowired
    @Qualifier("ghostRiderSubscriber")
    private StatementSubscriber ghostRider;
    

    /**
     * Configure Esper Statement(s).
     */
    public void initService() {

        LOG.debug("Initializing Servcie ..");
        Configuration config = new Configuration();
        config.addEventTypeAutoName("com.cor.cep.event");
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.getEPAdministrator().createEPL("create context SegmentedByMotorbikeId partition by motorbikeId from MotorbikeEvent");
        createDriverLeftSeatCheckExpression();
        createBlowOutTireCheckExpression();
        createCrashCheckExpression();
        createOccupantThrownAccidentCheckExpression();
        createAccidentReportCheckExpression();
        createDangerousLocationCheckExpression();
        createGhostRiderCheckExpression();

    }

    /**
     * EPL to check Motorbike
     */
    private void createDriverLeftSeatCheckExpression() {
        
        LOG.debug("create Driver Left Seat Check Expression");
        driverLeftSeatEventStatement = epService.getEPAdministrator().createEPL(driverLeftSeat.getStatement());
        driverLeftSeatEventStatement.setSubscriber(driverLeftSeat);
    }
    
    /**
     * EPL to check Motorbike
     */
    private void createBlowOutTireCheckExpression() {
        
        LOG.debug("create Blow Out Tire Check Expression");
        blowOutTireEventStatement = epService.getEPAdministrator().createEPL(blowOutTire.getStatement());
        blowOutTireEventStatement.setSubscriber(blowOutTire);
    }
    /**
     * EPL to check Motorbike
     */
    private void createCrashCheckExpression() {
        
        LOG.debug("create Crash Check Expression");
        crashEventStatement = epService.getEPAdministrator().createEPL(crash.getStatement());
        crashEventStatement.setSubscriber(crash);
    }
    
    /**
     * EPL to check Motorbike
     */
    private void createOccupantThrownAccidentCheckExpression() {
        
        LOG.debug("create Occupant Thrown Accident Check Expression");
        occupantThrownAccidentEventStatement = epService.getEPAdministrator().createEPL(occupantThrownAccident.getStatement());
        occupantThrownAccidentEventStatement.setSubscriber(occupantThrownAccident);
    }
    
    private void createAccidentReportCheckExpression() {
        
        LOG.debug("create Accident Report Check Expression");
        accidentReportEventStatement = epService.getEPAdministrator().createEPL(accidentReport.getStatement());
        accidentReportEventStatement.setSubscriber(accidentReport);
    }
    
    private void createDangerousLocationCheckExpression() {
        
        LOG.debug("create Dangerous Location Check Expression");
        dangerousLocationEventStatement = epService.getEPAdministrator().createEPL(dangerousLocation.getStatement());
        dangerousLocationEventStatement.setSubscriber(dangerousLocation);
    }
    
    private void createGhostRiderCheckExpression() {
        
        LOG.debug("create Ghost Rider Check Expression");
        ghostRiderEventStatement = epService.getEPAdministrator().createEPL(ghostRider.getStatement());
        ghostRiderEventStatement.setSubscriber(ghostRider);
    }

    /**
     * Handle the incoming MotorbikeEvent.
     */
    public void handle(MotorbikeEvent event) {

        //LOG.debug(event.toString());
        epService.getEPRuntime().sendEvent(event);

    }

    @Override
    public void afterPropertiesSet() {
        
        LOG.debug("Configuring..");
        initService();
    }
}
