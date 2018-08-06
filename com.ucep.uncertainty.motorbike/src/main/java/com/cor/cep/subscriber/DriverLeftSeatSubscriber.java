package com.cor.cep.subscriber;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.cor.cep.util.UReal;
import com.cor.cep.util.UBoolean;

import com.espertech.esper.client.Configuration;

/**
 * Wraps Esper Statement and Listener. No dependency on Esper libraries.
 */
@Component
public class DriverLeftSeatSubscriber implements StatementSubscriber {

	
	private int count = 0;
    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(DriverLeftSeatSubscriber.class);

    /**
     * {@inheritDoc}
     */
    public String getStatement() {
    	
    		final double P_DriverLeftSeatRule= 0.99;
    		
	    	Configuration config = new Configuration();
	    	config.addImport("com.cor.cep.util.*");	// package import
	    	String DriverLeftSeatPattern ="@Name('DriverLeftSeat') "
	    			+ "context SegmentedByMotorbikeId "					
	    			+ "insert into DriverLeftSeat "
	    			+ "select current_timestamp() as timestamp, a2.motorbikeId as motorbikeId, a2.location as location, a1.seat as seat_a1, a2.seat as seat_a2, "
	    			+ "a1.prob * a2.prob * "
	    			+ "com.cor.cep.util.UBooleans.uand(a1.seat, com.cor.cep.util.UBooleans.unot(a2.seat)).getC()  * "
	    			+ "com.cor.cep.util.UReals.lt(a1.timestamp, a2.timestamp).getC() * "
	    			+ P_DriverLeftSeatRule + " as prob "
	    			+ "from pattern [every a1 = MotorbikeEvent(com.cor.cep.util.UBooleans.toBoolean(a1.seat))->"
	    			+ "a2 = MotorbikeEvent(com.cor.cep.util.UBooleans.toBoolean(com.cor.cep.util.UBooleans.unot(a2.seat)))]";
        return DriverLeftSeatPattern;
    }
    
    /**
     * Listener method called when Esper has detected a pattern match.
     */
    public void update(Map<String, Object> eventMap) {
    	
        Long timestamp = (Long) eventMap.get("timestamp");
        
        Integer motorbikeId = (Integer) eventMap.get("motorbikeId");

        String location = (String) eventMap.get("location");
        
        UBoolean seat_a1 = (UBoolean) eventMap.get("seat_a1");

        UBoolean seat_a2 = (UBoolean) eventMap.get("seat_a2");
        
        double prob = (double) eventMap.get("prob");

        StringBuilder sb = new StringBuilder();
        sb.append("***************************************");
        sb.append("\n* [ALERT] : DRIVERLEFTSEAT EVENT DETECTED! ");
        sb.append("\n* " + timestamp + " , " + motorbikeId+ " , " + location + " , " + seat_a1 + " , " + seat_a2+ " , " + prob);
        sb.append("\n***************************************");
        count++;
        //LOG.debug(sb.toString() );
    }

    
}
