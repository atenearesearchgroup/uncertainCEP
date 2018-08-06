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
    	
    		final double P_HighCORule= 0.98;
    	
	    	Configuration config = new Configuration();
	    	config.addImport("com.cor.cep.util.*");	// package import
	    	
	    	String crtiticalEventExpression = "insert into HighCo "
	    			+ "select h1.co as co, h1.ts as ts, h1.id as id, h1.prob * h1.prob * "
	    			+ "com.cor.cep.util.UReals.ge(h1.co,5000.0).getC() * "
	    			+ P_HighCORule + " as prob\r\n" 
	    			+ "from pattern [(every (h1 = HomeEvent(com.cor.cep.util.UBooleans.toBoolean(com.cor.cep.util.UReals.ge(h1.co,5000)))))]";
	    
        return crtiticalEventExpression;
    }
    
    /**
     * Listener method called when Esper has detected a pattern match.
     */
    public void update(Map<String, Object> eventMap) {
    		
    		UReal co = (UReal) eventMap.get("co");
    		int homeId = (Integer) eventMap.get("id");
        UReal ts = (UReal) eventMap.get("ts");
        double prob = (Double) eventMap.get("prob");
        
        StringBuilder sb = new StringBuilder();
        sb.append("* [ALERT] : High CO EVENT DETECTED: ");
        sb.append("Home "+ homeId + ": " + ", ts=" + ts+ ", prob=" + prob + ", co=" + co);

        LOG.debug(sb.toString());
    }

    
}
