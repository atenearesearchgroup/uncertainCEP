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
public class CriticalEventSubscriber implements StatementSubscriber {

    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(CriticalEventSubscriber.class);

    /**
     * {@inheritDoc}
     */
    public String getStatement() {
    	
	    	Configuration config = new Configuration();
	    	config.addImport("com.cor.cep.util.*");	// package import
	    	
	    	String crtiticalEventExpression = "insert into TempIncrease "
	    			+ "select h2.ts as ts, h1.id as id, h2.temp as temp, com.cor.cep.util.UReals.minus(h2.temp, h1.temp) as incr\r\n" + 
	    			"from pattern [(every (h1 = HomeEvent() -> h2=HomeEvent(com.cor.cep.util.UBooleans.toBoolean(com.cor.cep.util.UReals.ge(com.cor.cep.util.UReals.minus(h2.temp, h1.temp), 2)) and h2.id = h1.id)) )\r\n" + 
	    			"where timer:within(1 minutes)]";

        return crtiticalEventExpression;
    }
    
    /**
     * Listener method called when Esper has detected a pattern match.
     */
    public void update(Map<String, UReal> eventMap) {
    	
    	
//        UReal temp1 = (UReal) eventMap.get("temp");
//        UReal temp2 = (UReal) eventMap.get("incr");
//
//        StringBuilder sb = new StringBuilder();
//        sb.append("***************************************");
//        sb.append("\n* [ALERT] : CRITICAL EVENT DETECTED! ");
//        sb.append("\n* " + temp1 + " > " + temp2);
//        sb.append("\n***************************************");
//
//        LOG.debug(sb.toString());
    }

    
}
