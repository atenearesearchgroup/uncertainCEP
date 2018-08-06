package com.cor.cep.handler;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.cor.cep.event.QualityAirEvent;
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
public class  QualityAirEventHandler implements InitializingBean{

    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(QualityAirEventHandler.class);

    /** Esper service */
    private EPServiceProvider epService;
    private EPStatement no2_avgEventStatement;
    private EPStatement no2_goodEventStatement;
    private EPStatement no2_moderateEventStatement;
    private EPStatement no2_unhealthyforsensitivegroupsEventStatement;
    private EPStatement no2_unhealthyEventStatement;
    private EPStatement no2_veryunhealthyEventStatement;
    private EPStatement no2_hazardousEventStatement;
    
    private EPStatement so2_avgEventStatement;
    private EPStatement so2_goodEventStatement;
    private EPStatement so2_moderateEventStatement;
    private EPStatement so2_unhealthyforsensitivegroupsEventStatement;
    private EPStatement so2_unhealthyEventStatement;
    private EPStatement so2_veryunhealthyEventStatement;
    private EPStatement so2_hazardousEventStatement;
    
    private EPStatement co_avgEventStatement;
    private EPStatement co_goodEventStatement;
    private EPStatement co_moderateEventStatement;
    private EPStatement co_unhealthyforsensitivegroupsEventStatement;
    private EPStatement co_unhealthyEventStatement;
    private EPStatement co_veryunhealthyEventStatement;
    private EPStatement co_hazardousEventStatement;
    
    private EPStatement o3_avgEventStatement;
    private EPStatement o3_goodEventStatement;
    private EPStatement o3_moderateEventStatement;
    private EPStatement o3_unhealthyforsensitivegroupsEventStatement;
    private EPStatement o3_unhealthyEventStatement;
    private EPStatement o3_veryunhealthyEventStatement;
    private EPStatement o3_hazardousEventStatement;
    
    private EPStatement pm2_5_avgEventStatement;
    private EPStatement pm2_5_goodEventStatement;
    private EPStatement pm2_5_moderateEventStatement;
    private EPStatement pm2_5_unhealthyforsensitivegroupsEventStatement;
    private EPStatement pm2_5_unhealthyEventStatement;
    private EPStatement pm2_5_veryunhealthyEventStatement;
    private EPStatement pm2_5_hazardousEventStatement;
    
    
    private EPStatement pm10_avgEventStatement;
    private EPStatement pm10_goodEventStatement;
    private EPStatement pm10_moderateEventStatement;
    private EPStatement pm10_unhealthyforsensitivegroupsEventStatement;
    private EPStatement pm10_unhealthyEventStatement;
    private EPStatement pm10_veryunhealthyEventStatement;
    private EPStatement pm10_hazardousEventStatement;
    
    private EPStatement airqualitylevelEventStatement;
    
    
    @Autowired
    @Qualifier("no2_avgSubscriber")
    private StatementSubscriber no2_avg;
    @Autowired
    @Qualifier("no2_goodSubscriber")
    private StatementSubscriber no2_good;
    @Autowired
    @Qualifier("no2_moderateSubscriber")
    private StatementSubscriber no2_moderate;
    @Autowired
    @Qualifier("no2_unhealthyforsensitivegroupsSubscriber")
    private StatementSubscriber no2_unhealthyforsensitivegroups;
    @Autowired
    @Qualifier("no2_unhealthySubscriber")
    private StatementSubscriber no2_unhealthy;
    @Autowired
    @Qualifier("no2_veryunhealthySubscriber")
    private StatementSubscriber no2_veryunhealthy;
    @Autowired
    @Qualifier("no2_hazardousSubscriber")
    private StatementSubscriber no2_hazardous;
    
    @Autowired
    @Qualifier("so2_avgSubscriber")
    private StatementSubscriber so2_avg;
    @Autowired
    @Qualifier("so2_goodSubscriber")
    private StatementSubscriber so2_good;
    @Autowired
    @Qualifier("so2_moderateSubscriber")
    private StatementSubscriber so2_moderate;
    @Autowired
    @Qualifier("so2_unhealthyforsensitivegroupsSubscriber")
    private StatementSubscriber so2_unhealthyforsensitivegroups;
    @Autowired
    @Qualifier("so2_unhealthySubscriber")
    private StatementSubscriber so2_unhealthy;
    @Autowired
    @Qualifier("so2_veryunhealthySubscriber")
    private StatementSubscriber so2_veryunhealthy;
    @Autowired
    @Qualifier("so2_hazardousSubscriber")
    private StatementSubscriber so2_hazardous;
    
    @Autowired
    @Qualifier("co_avgSubscriber")
    private StatementSubscriber co_avg;@Autowired
    @Qualifier("co_goodSubscriber")
    private StatementSubscriber co_good;
    @Autowired
    @Qualifier("co_moderateSubscriber")
    private StatementSubscriber co_moderate;
    @Autowired
    @Qualifier("co_unhealthyforsensitivegroupsSubscriber")
    private StatementSubscriber co_unhealthyforsensitivegroups;
    @Autowired
    @Qualifier("co_unhealthySubscriber")
    private StatementSubscriber co_unhealthy;
    @Autowired
    @Qualifier("co_veryunhealthySubscriber")
    private StatementSubscriber co_veryunhealthy;
    @Autowired
    @Qualifier("co_hazardousSubscriber")
    private StatementSubscriber co_hazardous;
    
    
    @Autowired
    @Qualifier("o3_avgSubscriber")
    private StatementSubscriber o3_avg;
    @Autowired
    @Qualifier("o3_goodSubscriber")
    private StatementSubscriber o3_good;
    @Autowired
    @Qualifier("o3_moderateSubscriber")
    private StatementSubscriber o3_moderate;
    @Autowired
    @Qualifier("o3_unhealthyforsensitivegroupsSubscriber")
    private StatementSubscriber o3_unhealthyforsensitivegroups;
    @Autowired
    @Qualifier("o3_unhealthySubscriber")
    private StatementSubscriber o3_unhealthy;
    @Autowired
    @Qualifier("o3_veryunhealthySubscriber")
    private StatementSubscriber o3_veryunhealthy;
    @Autowired
    @Qualifier("o3_hazardousSubscriber")
    private StatementSubscriber o3_hazardous;
    
    
    @Autowired
    @Qualifier("pm2_5_avgSubscriber")
    private StatementSubscriber pm2_5_avg;
    @Autowired
    @Qualifier("pm2_5_goodSubscriber")
    private StatementSubscriber pm2_5_good;
    @Autowired
    @Qualifier("pm2_5_moderateSubscriber")
    private StatementSubscriber pm2_5_moderate;
    @Autowired
    @Qualifier("pm2_5_unhealthyforsensitivegroupsSubscriber")
    private StatementSubscriber pm2_5_unhealthyforsensitivegroups;
    @Autowired
    @Qualifier("pm2_5_unhealthySubscriber")
    private StatementSubscriber pm2_5_unhealthy;
    @Autowired
    @Qualifier("pm2_5_veryunhealthySubscriber")
    private StatementSubscriber pm2_5_veryunhealthy;
    @Autowired
    @Qualifier("pm2_5_hazardousSubscriber")
    private StatementSubscriber pm2_5_hazardous;
    
    
    @Autowired
    @Qualifier("pm10_avgSubscriber")
    private StatementSubscriber pm10_avg;
    @Autowired
    @Qualifier("pm10_goodSubscriber")
    private StatementSubscriber pm10_good;
    @Autowired
    @Qualifier("pm10_moderateSubscriber")
    private StatementSubscriber pm10_moderate;
    @Autowired
    @Qualifier("pm10_unhealthyforsensitivegroupsSubscriber")
    private StatementSubscriber pm10_unhealthyforsensitivegroups;
    @Autowired
    @Qualifier("pm10_unhealthySubscriber")
    private StatementSubscriber pm10_unhealthy;
    @Autowired
    @Qualifier("pm10_veryunhealthySubscriber")
    private StatementSubscriber pm10_veryunhealthy;
    @Autowired
    @Qualifier("pm10_hazardousSubscriber")
    private StatementSubscriber pm10_hazardous;
    
    
    @Autowired
    @Qualifier("airqualitylevelSubscriber")
    private StatementSubscriber airqualitylevel;
    
    /**
     * Configure Esper Statement(s).
     */
    public void initService() {

        LOG.debug("Initializing Servcie ..");
        Configuration config = new Configuration();
        config.addEventTypeAutoName("com.cor.cep.event");
        epService = EPServiceProviderManager.getDefaultProvider(config);
        //epService.getEPAdministrator().createEPL("create context SegmentedByMotorbikeId partition by motorbikeId from MotorbikeEvent");
        createNo2_avgCheckExpression();
        createNo2_goodCheckExpression();
        createNo2_moderateCheckExpression();
        createNo2_unhealthyforsensitivegroupsCheckExpression();
        createNo2_unhealthyCheckExpression();
        createNo2_veryunhealthyCheckExpression();
        createNo2_hazardousCheckExpression();
        
        createSo2_avgCheckExpression();
        createSo2_goodCheckExpression();
        createSo2_moderateCheckExpression();
        createSo2_unhealthyforsensitivegroupsCheckExpression();
        createSo2_unhealthyCheckExpression();
        createSo2_veryunhealthyCheckExpression();
        createSo2_hazardousCheckExpression();
        
        
        createCo_avgCheckExpression();
        createCo_goodCheckExpression();
        createCo_moderateCheckExpression();
        createCo_unhealthyforsensitivegroupsCheckExpression();
        createCo_unhealthyCheckExpression();
        createCo_veryunhealthyCheckExpression();
        createCo_hazardousCheckExpression();
        
        
        createO3_avgCheckExpression();
        createO3_goodCheckExpression();
        createO3_moderateCheckExpression();
        createO3_unhealthyforsensitivegroupsCheckExpression();
        createO3_unhealthyCheckExpression();
        createO3_veryunhealthyCheckExpression();
        createO3_hazardousCheckExpression();
        
        createPm2_5_avgCheckExpression();
        createPm2_5_goodCheckExpression();
        createPm2_5_moderateCheckExpression();
        createPm2_5_unhealthyforsensitivegroupsCheckExpression();
        createPm2_5_unhealthyCheckExpression();
        createPm2_5_veryunhealthyCheckExpression();
        createPm2_5_hazardousCheckExpression();
        
        
        createPm10_avgCheckExpression();
        createPm10_goodCheckExpression();
        createPm10_moderateCheckExpression();
        createPm10_unhealthyforsensitivegroupsCheckExpression();
        createPm10_unhealthyCheckExpression();
        createPm10_veryunhealthyCheckExpression();
        createPm10_hazardousCheckExpression();
        
        createAirqualitylevelCheckExpression();

    }
    
    private void createNo2_avgCheckExpression() {
        
        LOG.debug("create No2_avg Check Expression");
        no2_avgEventStatement = epService.getEPAdministrator().createEPL(no2_avg.getStatement());
        no2_avgEventStatement.setSubscriber(no2_avg);
    }
    
    private void createNo2_goodCheckExpression() {
        
        LOG.debug("create No2_good Check Expression");
        no2_goodEventStatement = epService.getEPAdministrator().createEPL(no2_good.getStatement());
        no2_goodEventStatement.setSubscriber(no2_good);
    }
    
    private void createNo2_moderateCheckExpression() {
        
        LOG.debug("create No2_moderate Check Expression");
        no2_moderateEventStatement = epService.getEPAdministrator().createEPL(no2_moderate.getStatement());
        no2_moderateEventStatement.setSubscriber(no2_moderate);
    }
    private void createNo2_unhealthyforsensitivegroupsCheckExpression() {
        
        LOG.debug("create No2_unhealthyforsensitivegroups Check Expression");
        no2_unhealthyforsensitivegroupsEventStatement = epService.getEPAdministrator().createEPL(no2_unhealthyforsensitivegroups.getStatement());
        no2_unhealthyforsensitivegroupsEventStatement.setSubscriber(no2_unhealthyforsensitivegroups);
    }
    private void createNo2_unhealthyCheckExpression() {
        
        LOG.debug("create No2_unhealthy Check Expression");
        no2_unhealthyEventStatement = epService.getEPAdministrator().createEPL(no2_unhealthy.getStatement());
        no2_unhealthyEventStatement.setSubscriber(no2_unhealthy);
    }
    private void createNo2_veryunhealthyCheckExpression() {
        
        LOG.debug("create No2_veryunhealthy Check Expression");
        no2_veryunhealthyEventStatement = epService.getEPAdministrator().createEPL(no2_veryunhealthy.getStatement());
        no2_veryunhealthyEventStatement.setSubscriber(no2_veryunhealthy);
    }
    private void createNo2_hazardousCheckExpression() {
        
        LOG.debug("create No2_hazardous Check Expression");
        no2_hazardousEventStatement = epService.getEPAdministrator().createEPL(no2_hazardous.getStatement());
        no2_hazardousEventStatement.setSubscriber(no2_hazardous);
    }
    
    
    
    private void createSo2_avgCheckExpression() {
        
        LOG.debug("create So2_avg Check Expression");
        so2_avgEventStatement = epService.getEPAdministrator().createEPL(so2_avg.getStatement());
        so2_avgEventStatement.setSubscriber(so2_avg);
    }
    
    private void createSo2_goodCheckExpression() {
        
        LOG.debug("create So2_good Check Expression");
        so2_goodEventStatement = epService.getEPAdministrator().createEPL(so2_good.getStatement());
        so2_goodEventStatement.setSubscriber(so2_good);
    }
    
    private void createSo2_moderateCheckExpression() {
        
        LOG.debug("create So2_moderate Check Expression");
        so2_moderateEventStatement = epService.getEPAdministrator().createEPL(so2_moderate.getStatement());
        so2_moderateEventStatement.setSubscriber(so2_moderate);
    }
    
    private void createSo2_unhealthyforsensitivegroupsCheckExpression() {
        
        LOG.debug("create So2_unhealthyforsensitivegroups Check Expression");
        so2_unhealthyforsensitivegroupsEventStatement = epService.getEPAdministrator().createEPL(so2_unhealthyforsensitivegroups.getStatement());
        so2_unhealthyforsensitivegroupsEventStatement.setSubscriber(so2_unhealthyforsensitivegroups);
    }
    private void createSo2_unhealthyCheckExpression() {
        
        LOG.debug("create So2_unhealthy Check Expression");
        so2_unhealthyEventStatement = epService.getEPAdministrator().createEPL(so2_unhealthy.getStatement());
        so2_unhealthyEventStatement.setSubscriber(so2_unhealthy);
    }
    private void createSo2_veryunhealthyCheckExpression() {
        
        LOG.debug("create So2_veryunhealthy Check Expression");
        so2_veryunhealthyEventStatement = epService.getEPAdministrator().createEPL(so2_veryunhealthy.getStatement());
        so2_veryunhealthyEventStatement.setSubscriber(so2_veryunhealthy);
    }
    private void createSo2_hazardousCheckExpression() {
        
        LOG.debug("create So2_hazardous Check Expression");
        so2_hazardousEventStatement = epService.getEPAdministrator().createEPL(so2_hazardous.getStatement());
        so2_hazardousEventStatement.setSubscriber(so2_hazardous);
    }
    
    
    private void createCo_avgCheckExpression() {
        
        LOG.debug("create Co_avg Check Expression");
        co_avgEventStatement = epService.getEPAdministrator().createEPL(co_avg.getStatement());
        co_avgEventStatement.setSubscriber(co_avg);
    }
    private void createCo_goodCheckExpression() {
        
        LOG.debug("create Co_good Check Expression");
        co_goodEventStatement = epService.getEPAdministrator().createEPL(co_good.getStatement());
        co_goodEventStatement.setSubscriber(co_good);
    }
    
    private void createCo_moderateCheckExpression() {
        
        LOG.debug("create Co_moderate Check Expression");
        co_moderateEventStatement = epService.getEPAdministrator().createEPL(co_moderate.getStatement());
        co_moderateEventStatement.setSubscriber(co_moderate);
    }
    private void createCo_unhealthyforsensitivegroupsCheckExpression() {
        
        LOG.debug("create Co_unhealthyforsensitivegroups Check Expression");
        co_unhealthyforsensitivegroupsEventStatement = epService.getEPAdministrator().createEPL(co_unhealthyforsensitivegroups.getStatement());
        co_unhealthyforsensitivegroupsEventStatement.setSubscriber(co_unhealthyforsensitivegroups);
    }
    
    private void createCo_unhealthyCheckExpression() {
        
        LOG.debug("create Co_unhealthy Check Expression");
        co_unhealthyEventStatement = epService.getEPAdministrator().createEPL(co_unhealthy.getStatement());
        co_unhealthyEventStatement.setSubscriber(co_unhealthy);
    }
    
    private void createCo_veryunhealthyCheckExpression() {
        
        LOG.debug("create Co_veryunhealthy Check Expression");
        co_veryunhealthyEventStatement = epService.getEPAdministrator().createEPL(co_veryunhealthy.getStatement());
        co_veryunhealthyEventStatement.setSubscriber(co_veryunhealthy);
    }
    private void createCo_hazardousCheckExpression() {
        
        LOG.debug("create Co_hazardous Check Expression");
        co_hazardousEventStatement = epService.getEPAdministrator().createEPL(co_hazardous.getStatement());
        co_hazardousEventStatement.setSubscriber(co_hazardous);
    }
    
    private void createO3_avgCheckExpression() {
        
        LOG.debug("create O3_avg Check Expression");
        o3_avgEventStatement = epService.getEPAdministrator().createEPL(o3_avg.getStatement());
        o3_avgEventStatement.setSubscriber(o3_avg);
    }
    private void createO3_goodCheckExpression() {
        
        LOG.debug("create O3_good Check Expression");
        o3_goodEventStatement = epService.getEPAdministrator().createEPL(o3_good.getStatement());
        o3_goodEventStatement.setSubscriber(o3_good);
    }
    private void createO3_moderateCheckExpression() {
        
        LOG.debug("create O3_moderate Check Expression");
        o3_moderateEventStatement = epService.getEPAdministrator().createEPL(o3_moderate.getStatement());
        o3_moderateEventStatement.setSubscriber(o3_moderate);
    }
    private void createO3_unhealthyforsensitivegroupsCheckExpression() {
        
        LOG.debug("create O3_unhealthyforsensitivegroups Check Expression");
        o3_unhealthyforsensitivegroupsEventStatement = epService.getEPAdministrator().createEPL(o3_unhealthyforsensitivegroups.getStatement());
        o3_unhealthyforsensitivegroupsEventStatement.setSubscriber(o3_unhealthyforsensitivegroups);
    }
    private void createO3_unhealthyCheckExpression() {
        
        LOG.debug("create O3_unhealthy Check Expression");
        o3_unhealthyEventStatement = epService.getEPAdministrator().createEPL(o3_unhealthy.getStatement());
        o3_unhealthyEventStatement.setSubscriber(o3_unhealthy);
    }
    private void createO3_veryunhealthyCheckExpression() {
        
        LOG.debug("create O3_veryunhealthy Check Expression");
        o3_veryunhealthyEventStatement = epService.getEPAdministrator().createEPL(o3_veryunhealthy.getStatement());
        o3_veryunhealthyEventStatement.setSubscriber(o3_veryunhealthy);
    }
    private void createO3_hazardousCheckExpression() {
        
        LOG.debug("create O3_hazardous Check Expression");
        o3_hazardousEventStatement = epService.getEPAdministrator().createEPL(o3_hazardous.getStatement());
        o3_hazardousEventStatement.setSubscriber(o3_hazardous);
    }
    
    
    
    private void createPm2_5_avgCheckExpression() {
        
        LOG.debug("create Pm2_5_avg Check Expression");
        pm2_5_avgEventStatement = epService.getEPAdministrator().createEPL(pm2_5_avg.getStatement());
        pm2_5_avgEventStatement.setSubscriber(pm2_5_avg);
    }
    
    private void createPm2_5_goodCheckExpression() {
        
        LOG.debug("create Pm2_5_good Check Expression");
        pm2_5_goodEventStatement = epService.getEPAdministrator().createEPL(pm2_5_good.getStatement());
        pm2_5_goodEventStatement.setSubscriber(pm2_5_good);
    }
    
    private void createPm2_5_moderateCheckExpression() {
        
        LOG.debug("create Pm2_5_moderate Check Expression");
        pm2_5_moderateEventStatement = epService.getEPAdministrator().createEPL(pm2_5_moderate.getStatement());
        pm2_5_moderateEventStatement.setSubscriber(pm2_5_moderate);
    }
    private void createPm2_5_unhealthyforsensitivegroupsCheckExpression() {
        
        LOG.debug("create Pm2_5_unhealthyforsensitivegroups Check Expression");
        pm2_5_unhealthyforsensitivegroupsEventStatement = epService.getEPAdministrator().createEPL(pm2_5_unhealthyforsensitivegroups.getStatement());
        pm2_5_unhealthyforsensitivegroupsEventStatement.setSubscriber(pm2_5_unhealthyforsensitivegroups);
    }
    private void createPm2_5_unhealthyCheckExpression() {
        
        LOG.debug("create Pm2_5_unhealthy Check Expression");
        pm2_5_unhealthyEventStatement = epService.getEPAdministrator().createEPL(pm2_5_unhealthy.getStatement());
        pm2_5_unhealthyEventStatement.setSubscriber(pm2_5_unhealthy);
    }
    private void createPm2_5_veryunhealthyCheckExpression() {
        
        LOG.debug("create Pm2_5_veryunhealthy Check Expression");
        pm2_5_veryunhealthyEventStatement = epService.getEPAdministrator().createEPL(pm2_5_veryunhealthy.getStatement());
        pm2_5_veryunhealthyEventStatement.setSubscriber(pm2_5_veryunhealthy);
    }
    private void createPm2_5_hazardousCheckExpression() {
        
        LOG.debug("create Pm2_5_hazardous Check Expression");
        pm2_5_hazardousEventStatement = epService.getEPAdministrator().createEPL(pm2_5_hazardous.getStatement());
        pm2_5_hazardousEventStatement.setSubscriber(pm2_5_hazardous);
    }
    
    
    private void createPm10_avgCheckExpression() {
        
        LOG.debug("create Pm10_avg Check Expression");
        pm10_avgEventStatement = epService.getEPAdministrator().createEPL(pm10_avg.getStatement());
        pm10_avgEventStatement.setSubscriber(pm10_avg);
    }
    
    private void createPm10_goodCheckExpression() {
        
        LOG.debug("create Pm10_good Check Expression");
        pm10_goodEventStatement = epService.getEPAdministrator().createEPL(pm10_good.getStatement());
        pm10_goodEventStatement.setSubscriber(pm10_good);
    }
    
    private void createPm10_moderateCheckExpression() {
        
        LOG.debug("create Pm10_moderate Check Expression");
        pm10_moderateEventStatement = epService.getEPAdministrator().createEPL(pm10_moderate.getStatement());
        pm10_moderateEventStatement.setSubscriber(pm10_moderate);
    }
    private void createPm10_unhealthyforsensitivegroupsCheckExpression() {
        
        LOG.debug("create Pm10_unhealthyforsensitivegroups Check Expression");
        pm10_unhealthyforsensitivegroupsEventStatement = epService.getEPAdministrator().createEPL(pm10_unhealthyforsensitivegroups.getStatement());
        pm10_unhealthyforsensitivegroupsEventStatement.setSubscriber(pm10_unhealthyforsensitivegroups);
    }
    private void createPm10_unhealthyCheckExpression() {
        
        LOG.debug("create Pm10_unhealthy Check Expression");
        pm10_unhealthyEventStatement = epService.getEPAdministrator().createEPL(pm10_unhealthy.getStatement());
        pm10_unhealthyEventStatement.setSubscriber(pm10_unhealthy);
    }
    private void createPm10_veryunhealthyCheckExpression() {
        
        LOG.debug("create Pm10_veryunhealthy Check Expression");
        pm10_veryunhealthyEventStatement = epService.getEPAdministrator().createEPL(pm10_veryunhealthy.getStatement());
        pm10_veryunhealthyEventStatement.setSubscriber(pm10_veryunhealthy);
    }
    private void createPm10_hazardousCheckExpression() {
        
        LOG.debug("create Pm10_hazardous Check Expression");
        pm10_hazardousEventStatement = epService.getEPAdministrator().createEPL(pm10_hazardous.getStatement());
        pm10_hazardousEventStatement.setSubscriber(pm10_hazardous);
    }
    
    
    private void createAirqualitylevelCheckExpression() {
        
        LOG.debug("create Airqualitylevel Check Expression");
        airqualitylevelEventStatement = epService.getEPAdministrator().createEPL(airqualitylevel.getStatement());
        airqualitylevelEventStatement.setSubscriber(airqualitylevel);
    }
    
    /**
     * Handle the incoming MotorbikeEvent.
     */
    public void handle(QualityAirEvent event) {

        //LOG.debug(event.toString());
        epService.getEPRuntime().sendEvent(event);

    }

    @Override
    public void afterPropertiesSet() {
        
        LOG.debug("Configuring..");
        initService();
    }
}