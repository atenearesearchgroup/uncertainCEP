package com.cor.cep.subscriber;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.cor.cep.util.UReal;


import com.espertech.esper.client.Configuration;

@Component
public class CrashSubscriber implements StatementSubscriber{
	
	private int count = 0;
    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(CrashSubscriber.class);

    /**
     * {@inheritDoc}
     */
    public String getStatement() {
    	
    		final double P_CrashRule= 0.99;
	    	Configuration config = new Configuration();
	    	config.addImport("com.cor.cep.util.*");	// package import
	    	String CrashPattern ="@Name('Crash') "				
	    			+ "context SegmentedByMotorbikeId "					
	    			+ "insert into Crash "
	    			+ "select a2.timestamp as timestamp, a1.motorbikeId as motorbikeId, "
	    			+ "  a2.location as location, a1.speed as speed_a1, a2.speed as speed_a2,  "
	    			+ "  a1.prob * a2.prob * com.cor.cep.util.UReals.lt(a1.timestamp,a2.timestamp).getC() * "
	    			+ "  com.cor.cep.util.UBooleans.uand(com.cor.cep.util.UReals.ge(a1.speed,50.0), com.cor.cep.util.UReals.uEqualsZero(a2.speed)).getC() * "  
	    			+  P_CrashRule + " as prob "
	    			+ "from pattern [every a1 = MotorbikeEvent(com.cor.cep.util.UBooleans.toBoolean(com.cor.cep.util.UReals.ge(a1.speed,50))) -> a2 = MotorbikeEvent(com.cor.cep.util.UReals.equalsZero(a2.speed))   "
	    			+ "where timer:within(3 milliseconds)]";
	    	    
        return CrashPattern;
    }
    
    /**
     * Listener method called when Esper has detected a pattern match.
     */
    public void update(Map<String, Object> eventMap) {
    		
    		
        UReal timestamp = (UReal) eventMap.get("timestamp");
        Integer motorbikeId = (Integer) eventMap.get("motorbikeId");
        String location = (String) eventMap.get("location");
        UReal speeda1 = (UReal) eventMap.get("speed_a1");
        UReal speeda2 = (UReal) eventMap.get("speed_a2");
        double prob = (double) eventMap.get("prob");
        StringBuilder sb = new StringBuilder();
        
        sb.append("***************************************");
        sb.append("\n* [ALERT] : Crash EVENT DETECTED! ");
        sb.append("\n* " + timestamp + " , " + motorbikeId + " , " + location + " , " + speeda1 + " , " + speeda2+ " , " + prob);
        sb.append("\n***************************************");
        //LOG.debug(sb.toString());
        
    }


}
