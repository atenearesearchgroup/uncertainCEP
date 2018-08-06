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
public class FireWarningEventSubscriber implements StatementSubscriber {

    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(FireWarningEventSubscriber.class);

    /**
     * {@inheritDoc}
     */
    public String getStatement() {
    	
	    	Configuration config = new Configuration();
	    	config.addImport("com.cor.cep.util.*");	// package import
	    	
	    	String crtiticalEventExpression = "insert into FireWarning "
	    			+ "select tw.id as id, coh.ts as ts\r\n" + 
	    			"from pattern [(every (every (coh = HighCo()) -> every (tw = TempWarning(tw.id = coh.id))))\r\n" + 
	    			"where timer:within(5 seconds)]";

        return crtiticalEventExpression;
    }
    
    /**
     * Listener method called when Esper has detected a pattern match.
     */
    public void update(Map<String, UReal> eventMap) {
    	
    	
        //UReal temp1 = (UReal) eventMap.get("temp");
        //UReal temp2 = (UReal) eventMap.get("incr");

//        StringBuilder sb = new StringBuilder();
        //sb.append("***************************************");
//        sb.append("\n* [ALERT] : Fire Warning EVENT DETECTED! ");
        //sb.append("\n* " + temp1 + " > " + temp2);
        //sb.append("\n***************************************");

//        LOG.debug(sb.toString());
    }

    
}
