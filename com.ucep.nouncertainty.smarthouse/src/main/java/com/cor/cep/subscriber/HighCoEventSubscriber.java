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
public class HighCoEventSubscriber implements StatementSubscriber {

    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(HighCoEventSubscriber.class);

    /**
     * {@inheritDoc}
     */
    public String getStatement() {
    	
	    	Configuration config = new Configuration();
	    	config.addImport("com.cor.cep.util.*");	// package import
	    	
	    	String crtiticalEventExpression = "insert into HighCo "
	    			+ "select h1.ts as ts, h1.id as id\r\n" + 
	    			"from pattern [(every (h1 = HomeEvent(h1.co >= 5000)))]";

        return crtiticalEventExpression;
    }
    
    /**
     * Listener method called when Esper has detected a pattern match.
     */
    public void update(Map<String, Object> eventMap) {
    	
//    	int homeId = (Integer) eventMap.get("id");
//        double ts = (Double) eventMap.get("ts");
//        
//        StringBuilder sb = new StringBuilder();
//        sb.append("* [ALERT] : High CO EVENT DETECTED: ");
//        sb.append("Home "+ homeId + ": " + ", ts=" + ts);
//
//        LOG.debug(sb.toString());
    }

    
}
