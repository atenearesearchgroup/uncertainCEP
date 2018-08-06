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
public class TempWarningEventSubscriber implements StatementSubscriber {

    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(TempWarningEventSubscriber.class);

    /**
     * {@inheritDoc}
     */
    public String getStatement() {
    	
	    	Configuration config = new Configuration();
	    	config.addImport("com.cor.cep.util.*");	// package import
	    	
	    	String crtiticalEventExpression = "insert into TempWarning\r\n" + 
	    			"select t4.ts as ts, t1.id as id, t4.temp as temp\r\n" + 
	    			"from pattern [(every (t1=TempIncrease(t1.temp>= 33.0))\r\n" + 
	    			"                  -> (t2=TempIncrease(t2.temp > t1.temp and t2.id = t1.id))\r\n" + 
	    			"                  -> (t3=TempIncrease(t3.temp > t2.temp and t3.id = t1.id))\r\n" + 
	    			"                  -> (t4=TempIncrease(t4.temp > t3.temp and t4.id = t1.id)))\r\n" + 
	    			"where timer:within(5 minutes)]";

        return crtiticalEventExpression;
    }
    
    /**
     * Listener method called when Esper has detected a pattern match.
     */
    public void update(Map<String, Object> eventMap) {
    	
//          int homeId = (Integer) eventMap.get("id");
//          double temp1 = (Double) eventMap.get("temp");
////        double temp2 = (Double) eventMap.get("incr");
//          double ts = (Double) eventMap.get("ts");
//        
//        StringBuilder sb = new StringBuilder();
//        sb.append("* [ALERT] : Temperature Warning EVENT DETECTED: ");
//        sb.append("Home "+ homeId + ": " + "temp=" + temp1 + ", ts=" + ts);
//
//        LOG.debug(sb.toString());
    }

    
}
