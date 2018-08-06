package com.cor.cep.subscriber;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

//import com.espertech.esper.client.Configuration;

/**
 * Wraps Esper Statement and Listener. No dependency on Esper libraries.
 */
@Component
public class TvsMalfun2EventSubscriber implements StatementSubscriber {
	private int count = 0;

    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(TvsMalfun2EventSubscriber.class);
    
    /**
     * {@inheritDoc}
     */
    public String getStatement() {
        
    		final double PFN_JAM = 0.1;
    		final double P_TvsMalfun2Rule=0.99;
    		final double PFN_TrafficJamEvent=0.0001;
    		
        // Example using 'Match Recognise' syntax.
        String crtiticalEventExpression = "insert into TvsMalfun2 "
        			+ "select t1.km as km, t1.value as temp, "
        		    + "t1.prob * "+ (1-PFN_JAM) + " * " 
        		    	+ PFN_TrafficJamEvent + " * "
        		    + "com.cor.cep.util.UReals.gt(t1.value,30.0).getC() * "
        		    + P_TvsMalfun2Rule+ " as prob "
        			+ "from pattern [(every (t1 = TempEvent(com.cor.cep.util.UBooleans.toBoolean(com.cor.cep.util.UReals.gt(t1.value,30.0)))) -> "
        			+ "(not tj = TrafficJamEvent("
        			+ "com.cor.cep.util.UBooleans.toBoolean("
        			+ "com.cor.cep.util.UBooleans.uand("
        			+ "com.cor.cep.util.UReals.lt(com.cor.cep.util.UReals.minus(t1.km,10.0),tj.km), "
        			+ "com.cor.cep.util.UReals.lt(tj.km, com.cor.cep.util.UReals.add(t1.km,10.0))))))) "
        			+ "where timer:within(5 minutes)]";
        
        return crtiticalEventExpression;
    }
    
    /**
     * Listener method called when Esper has detected a pattern match.
     */
    public void update(Map<String, Object> eventMap) {
    	
    		
    		
    		double prob = (Double) eventMap.get("prob");
    		
    		
        StringBuilder sb = new StringBuilder();
        sb.append("***************************************");
        sb.append("\n* [ALERT] : TVS2 MALFUNCTIONING EVENT DETECTED! ");
        sb.append("Prob = " + prob );
        sb.append("\n***************************************");
        count++;
        //LOG.debug(sb.toString() + Integer.toString(count));
    }

    
}
