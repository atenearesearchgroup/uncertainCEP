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
public class NobodyHomeEventSubscriber implements StatementSubscriber {

    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(NobodyHomeEventSubscriber.class);

    /**
     * {@inheritDoc}
     */
    public String getStatement() {
    	
	    	Configuration config = new Configuration();
	    	config.addImport("com.cor.cep.util.*");	// package import

	    	
	    	String crtiticalEventExpression = "insert into NobodyHome\r\n" + 
	    	"select h.ts as ts, h.x as x, h.y as y, h.id as id, h.id as pid \r\n" +
	    	"from pattern [(every h=HomeEvent( not h.dopen) "
	    	+ "-> (p=PersonEvent(" 
	    + "(p.x <= (h.x - Math.sqrt(h.sqre)/4)) or \r\n"
	    	+ "(p.x >= (h.x + Math.sqrt(h.sqre)/4)) or\r\n"  
	    	+ "(p.y <= (h.y - Math.sqrt(h.sqre)/4)) or \r\n"
	    	+ "(p.y >= (h.y + Math.sqrt(h.sqre)/4)))))\r\n" + 	    	
		"where timer:within(10 seconds)]"; 
	    	

	    	

        return crtiticalEventExpression;
    }
    
    /**
     * Listener method called when Esper has detected a pattern match.
     */
    public void update(Map<String, Object> eventMap) {
    	
    		//int id = (Integer) eventMap.get("pid");
//        double x = (Double) eventMap.get("x");
//        double y = (Double) eventMap.get("y");
//        double ts = (Double) eventMap.get("ts");
//        
//        StringBuilder sb = new StringBuilder();
//        sb.append("* [ALERT] : Nobody home EVENT DETECTED: ");
//        //sb.append("Person "+ id + ": " + "x=" + x + ", y=" + y + ", ts=" + ts);
//
//        LOG.debug(sb.toString());
    }

    
}
