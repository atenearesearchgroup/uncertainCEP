package com.cor.cep.subscriber;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.espertech.esper.client.Configuration;

/**
 * Wraps Esper Statement and Listener. No dependency on Esper libraries.
 */
@Component
public class GhostRiderSubscriber implements StatementSubscriber {

	
	private int count = 0;
    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(DriverLeftSeatSubscriber.class);

    /**
     * {@inheritDoc}
     */
    public String getStatement() {
    	
	    	Configuration config = new Configuration();
	    	config.addImport("com.cor.cep.util.*");	// package import
	    	String GhostRiderPattern ="@Name('GhostRider') "				
	    			+ "insert into GhostRider "
	    			+ "select e.timestamp as timestamp, e.motorbikeId as motorbikeId "
	    			+ "from pattern [every e = MotorbikeEvent(e.seat = false and e.speed>0)]"; 
	    	    
        return GhostRiderPattern;
    }
    
    /**
     * Listener method called when Esper has detected a pattern match.
     */
    public void update(Map<String, Object> eventMap) {
    	
        Long timestamp = (Long) eventMap.get("timestamp");
        
        Integer motorbikeId = (Integer) eventMap.get("motorbikeId");

        
        StringBuilder sb = new StringBuilder();
        sb.append("***************************************");
        sb.append("\n* [ALERT] : GHOSTRIDER EVENT DETECTED! ");
        sb.append("\n* " + timestamp + " , " + motorbikeId);
        sb.append("\n***************************************");
        //count++;
        LOG.debug(sb.toString());
    }

    
}