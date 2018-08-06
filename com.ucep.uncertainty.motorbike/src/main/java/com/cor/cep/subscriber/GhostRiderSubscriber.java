package com.cor.cep.subscriber;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.cor.cep.util.UReal;

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
    	
    		final double P_GhostRiderRule= 0.99;
    		
	    	Configuration config = new Configuration();
	    	config.addImport("com.cor.cep.util.*");	// package import
	    	String GhostRiderPattern ="@Name('GhostRider') "			
	    			+ "insert into GhostRider "
	    			+ "select e.timestamp as timestamp, e.motorbikeId as motorbikeId, "
	    			+ "e.prob * "
	    			+ "com.cor.cep.util.UBooleans.uand(com.cor.cep.util.UBooleans.unot(e.seat),com.cor.cep.util.UReals.gt(e.speed,0.0)).getC() * "
	    			+ P_GhostRiderRule + " as prob  "
	    			+ "from pattern [every e = MotorbikeEvent(com.cor.cep.util.UBooleans.toBoolean("
	    			+ "com.cor.cep.util.UBooleans.uand("
	    			+ "com.cor.cep.util.UBooleans.unot(e.seat),"
	    			+ "com.cor.cep.util.UReals.gt(e.speed,0))))]"; 
	    	    
        return GhostRiderPattern;
    }
    
    /**
     * Listener method called when Esper has detected a pattern match.
     */
    public void update(Map<String, Object> eventMap) {
    	
        UReal timestamp = (UReal) eventMap.get("timestamp");
        
        Integer motorbikeId = (Integer) eventMap.get("motorbikeId");
        
        double prob = (double) eventMap.get("prob");


        StringBuilder sb = new StringBuilder();
        sb.append("***************************************");
        sb.append("\n* [ALERT] : GHOSTRIDER EVENT DETECTED! ");
        sb.append("\n* " + timestamp + " , " + motorbikeId+ " , " + prob);
        sb.append("\n***************************************");
        count++;
        //LOG.debug(sb.toString());
    }

    
}

