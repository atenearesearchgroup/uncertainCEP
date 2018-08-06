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
public class TempIncreaseEventSubscriber implements StatementSubscriber {
	
	//private final UReal HOME_PROB = new UReal(1);
	

    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(TempIncreaseEventSubscriber.class);

    /**
     * {@inheritDoc}
     */
    public String getStatement() {
    	
    		final double P_TempIncreaseRule= 0.98;
    	
	    	Configuration config = new Configuration();
	    	config.addImport("com.cor.cep.util.*");	// package import
	    	

	    	String crtiticalEventExpression = "insert into TempIncrease "
	    			+ "select h2.ts as ts, h1.id as id, h2.temp as temp, com.cor.cep.util.UReals.minus(h2.temp, h1.temp) as incr, "
	    			+ "h1.prob * h1.prob * "
	    			+ "com.cor.cep.util.UReals.ge(com.cor.cep.util.UReals.minus(h2.temp,h1.temp), 2.0).getC() * "
//	    			+ "com.cor.cep.util.UReals.lt(h1.ts,h2.ts).getC() * "
	    			+ P_TempIncreaseRule + " as prob\r\n"   
	    			+ "from pattern [(every (h1 = HomeEvent() -> h2=HomeEvent(com.cor.cep.util.UBooleans.toBoolean(com.cor.cep.util.UReals.ge(com.cor.cep.util.UReals.minus(h2.temp, h1.temp), 2)) and h2.id = h1.id)) )\r\n" + 
	    			"where timer:within(1 minutes)]";

        return crtiticalEventExpression;
    }
    
    /**
     * Listener method called when Esper has detected a pattern match.
     */
    public void update(Map<String, Object> eventMap) {
    	
    	int homeId = (Integer) eventMap.get("id");
        UReal temp1 = (UReal) eventMap.get("temp");
        UReal temp2 = (UReal) eventMap.get("incr");
        UReal ts = (UReal) eventMap.get("ts");
        double prob = (Double) eventMap.get("prob");
        
        
        StringBuilder sb = new StringBuilder();
        sb.append("* [ALERT] : Temperature Increase EVENT DETECTED: ");
        sb.append("Home "+ homeId + ": " + "temp=" + temp1 + ", incr=" + temp2 + ", ts=" + ts + ", prob=" + prob);

        LOG.debug(sb.toString());
    }

    
}
