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
public class NotifyFireDepartmentEventSubscriber implements StatementSubscriber {

    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(NotifyFireDepartmentEventSubscriber.class);

    /**
     * {@inheritDoc}
     */
    public String getStatement() {
    	
    	final double P_CallFireDepartmentRule= 0.98;
    	
    	Configuration config = new Configuration();
    	config.addImport("com.cor.cep.util.*");	// package import
	    	
	    	String crtiticalEventExpression = "insert into NotifyFireDepartment "+
	    			"select fw.id as id, fw.ts as ts, "
	    			+ "nh.prob * fw.prob *"
//	    			+ "com.cor.cep.util.UReals.lt(nh.ts,fw.ts).getC() * "
	    			+ P_CallFireDepartmentRule + "as prob\r\n "
	    			//+ "com.cor.cep.util.UReals.mult(com.cor.cep.util.UReals.mult(nh.prob,fw.prob),com.cor.cep.util.UReals.mult(nh.ts,com.cor.cep.util.UReals.lt(nh.ts,fw.ts).getC())) as prob \r\n" + 
	    			+ "from pattern [(every (nh = NobodyHome())-> (fw = FireWarning(fw.id = nh.id)))\r\n" + 
	    			"where timer:within(5 seconds)]";

        return crtiticalEventExpression;
    }
    
    /**
     * Listener method called when Esper has detected a pattern match.
     */
    public void update(Map<String, Object> eventMap) {
    	
    	int homeId = (Integer) eventMap.get("id");
        UReal ts = (UReal) eventMap.get("ts");
        double prob = (Double) eventMap.get("prob");
//        
        StringBuilder sb = new StringBuilder();
        sb.append("* [ALERT] : Notify Fire Department EVENT DETECTED: ");
        sb.append("Home "+ homeId + ": " + ", ts=" + ts + ", prob=" + prob);
//
        LOG.debug(sb.toString());
    }

    
}
