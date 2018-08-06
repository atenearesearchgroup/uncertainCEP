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

    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(TempIncreaseEventSubscriber.class);

    /**
     * {@inheritDoc}
     */
    public String getStatement() {
    	
		Configuration config = new Configuration();
		config.addImport("com.cor.cep.util.*"); // package import

		String crtiticalEventExpression = "insert into TempIncrease "
				+ "select h2.ts as ts, h1.id as id, h2.temp as temp, (h2.temp - h1.temp) as incr\r\n"
				+ "from pattern [(every (h1 = HomeEvent() -> h2=HomeEvent(((h2.temp - h1.temp)>= 2) and (h2.id = h1.id))) )\r\n"
				// "from pattern [(every (h1 = HomeEvent() -> h2=HomeEvent(h2.id = h1.id)))\r\n"
				+ "where timer:within(1 minutes)]";

        return crtiticalEventExpression;
    }
    
    /**
     * Listener method called when Esper has detected a pattern match.
     */
    public void update(Map<String, Object> eventMap) {
    	
//    	int homeId = (Integer) eventMap.get("id");
//        double temp1 = (Double) eventMap.get("temp");
//        double temp2 = (Double) eventMap.get("incr");
//        double ts = (Double) eventMap.get("ts");
//        
//        StringBuilder sb = new StringBuilder();
//        sb.append("* [ALERT] : Temperature Increase EVENT DETECTED: ");
//        sb.append("Home "+ homeId + ": " + "temp=" + temp1 + ", incr=" + temp2 + ", ts=" + ts);
//
//        LOG.debug(sb.toString());
    }

    
}
