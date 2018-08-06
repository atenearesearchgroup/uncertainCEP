package com.cor.cep.subscriber;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.cor.cep.util.UReal;

import com.espertech.esper.client.Configuration;

@Component
public class BlowOutTireSubscriber implements StatementSubscriber {
	private int count = 0;

    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(BlowOutTireSubscriber.class);

    /**
     * {@inheritDoc}
     */
    public String getStatement() {
    	
    		final double P_BlowOutTireRule= 0.99;
    	
	    	Configuration config = new Configuration();
	    	config.addImport("com.cor.cep.util.*");	// package import
	    	String BlowOutTirePattern =			"@Name('BlowOutTire') "				
	    			+ "context SegmentedByMotorbikeId "				
	    			+ "insert into BlowOutTire "
	    			+ "select current_timestamp() as timestamp, a2.motorbikeId as motorbikeId, "
	    		    + "  a1.location as location_a1, "
	    		    + "  a1.tirePressure1 as tirePressure1_a1, "
	    		    + "  a1.tirePressure2 as tirePressure2_a1, "
	    		    + "  a2.location as location_a2, "			    
	    		    + "  a2.tirePressure1 as tirePressure1_a2, "
	    		    + "  a2.tirePressure2 as tirePressure2_a2, "
	    		    + "  a1.prob * a1.prob * a2.prob * a2.prob * "
	    		    + "  com.cor.cep.util.UBooleans.uor("
	    		    + "  com.cor.cep.util.UBooleans.uand( "
	    		    + "  com.cor.cep.util.UReals.ge(a1.tirePressure1,2.0), com.cor.cep.util.UReals.le(a2.tirePressure1,1.2)),"
	    		    + "  com.cor.cep.util.UBooleans.uand( "
	    		    + "  com.cor.cep.util.UReals.ge(a1.tirePressure2,2.0), com.cor.cep.util.UReals.le(a2.tirePressure2,1.2))).getC() * "
	    		    + "  com.cor.cep.util.UReals.lt(a1.timestamp, a2.timestamp).getC() * "
	    		    +    P_BlowOutTireRule + " as prob "
	    			+ "  from pattern [(every a1 = MotorbikeEvent(com.cor.cep.util.UBooleans.toBoolean(com.cor.cep.util.UReals.ge(a1.tirePressure1, 2.0))) -> "
	    			+ "  a2 = MotorbikeEvent(com.cor.cep.util.UBooleans.toBoolean(com.cor.cep.util.UReals.le(a2.tirePressure1,1.2))) where timer:within(5 milliseconds)) "
	    			+ "  or "
	    			+ "  (every a1 = MotorbikeEvent(com.cor.cep.util.UBooleans.toBoolean(com.cor.cep.util.UReals.ge(a1.tirePressure2, 2.0))) -> "
	    			+ "  a2 = MotorbikeEvent(com.cor.cep.util.UBooleans.toBoolean(com.cor.cep.util.UReals.le(a2.tirePressure2,1.2))) where timer:within(5 milliseconds))]";
	    	
        return BlowOutTirePattern;
    }
    
    /**
     * Listener method called when Esper has detected a pattern match.
     */
    public void update(Map<String, Object> eventMap) {
    	
    		
        Long timestamp = (Long) eventMap.get("timestamp");
        
        Integer motorbikeId = (Integer) eventMap.get("motorbikeId");

        String location1 = (String) eventMap.get("location_a1");
        
        UReal pressure1a1 = (UReal) eventMap.get("tirePressure1_a1");

        UReal pressure1a2 = (UReal) eventMap.get("tirePressure1_a2");
        
        String location2 = (String) eventMap.get("location_a2");
        
        UReal pressure2a1 = (UReal) eventMap.get("tirePressure2_a1");

        UReal pressure2a2 = (UReal) eventMap.get("tirePressure2_a2");
        
        double prob = (double) eventMap.get("prob");
        
        
        
        StringBuilder sb = new StringBuilder();
        sb.append("***************************************");
        sb.append("\n* [ALERT] : BlowOutTire EVENT DETECTED! ");
        sb.append("\n* " + timestamp + " , " + motorbikeId + " , " + location1 + " , " + pressure1a1 + " , " + pressure1a2 + " , "+ location2 + " , " + pressure2a1 + " , " + pressure2a2+ " , " + prob);
        sb.append("\n***************************************");
        count++;
        LOG.debug(sb.toString());
    }

}
