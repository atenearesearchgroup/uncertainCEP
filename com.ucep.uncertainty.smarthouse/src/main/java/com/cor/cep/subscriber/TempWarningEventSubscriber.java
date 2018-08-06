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
	    	
	    	final double P_TempWarningRule= 0.99;
	    	
	    	String crtiticalEventExpression = "insert into TempWarning\r\n" + 
	    			"select t4.ts as ts, t1.id as id, t4.temp as temp, "
	    			+ "t1.prob * t2.prob * t3.prob * t4.prob * "
	    			+ "com.cor.cep.util.UBooleans.uand(com.cor.cep.util.UReals.ge(t1.temp, 33.0), "  
	    			+"   com.cor.cep.util.UBooleans.uand( "  
	    			+"     com.cor.cep.util.UBooleans.uand( "
	    			+"       com.cor.cep.util.UReals.gt(t2.temp,t1.temp), "  
	    			+"       com.cor.cep.util.UReals.gt(t3.temp,t2.temp)"
	    			+ "                                   )," 
	    			+ "      com.cor.cep.util.UReals.gt(t4.temp,t3.temp)"
	    			+ "                                 )"
	    			+ "                              ).getC() * "
//	    			+ "com.cor.cep.util.UBooleans.uand( " 
//	    			+ "com.cor.cep.util.UReals.lt(t1.ts,t2.ts)," 
//	    			+ "com.cor.cep.util.UBooleans.uand( com.cor.cep.util.UReals.lt(t2.ts,t3.ts)," 
//	    			+ "com.cor.cep.util.UReals.lt(t3.ts,t4.ts))).getC() * "
	    			+ P_TempWarningRule + "as prob\r\n" + 
	    			"from pattern [(every (t1=TempIncrease(com.cor.cep.util.UBooleans.toBoolean(com.cor.cep.util.UReals.ge(t1.temp, 33.0))))\r\n" + 
	    			"                  -> (t2=TempIncrease(com.cor.cep.util.UBooleans.toBoolean(com.cor.cep.util.UReals.gt(t2.temp,t1.temp)) and t2.id = t1.id))\r\n" + 
	    			"                  -> (t3=TempIncrease(com.cor.cep.util.UBooleans.toBoolean(com.cor.cep.util.UReals.gt(t3.temp,t2.temp)) and t3.id = t1.id))\r\n" + 
	    			"                  -> (t4=TempIncrease(com.cor.cep.util.UBooleans.toBoolean(com.cor.cep.util.UReals.gt(t4.temp,t3.temp)) and t4.id = t1.id)))\r\n" + 
	    			"where timer:within(5 minutes)]";

        return crtiticalEventExpression;
    }
    
    /**
     * Listener method called when Esper has detected a pattern match.
     */
    public void update(Map<String, Object> eventMap) {
    	
          int homeId = (Integer) eventMap.get("id");
          UReal temp1 = (UReal) eventMap.get("temp");
//        UReal temp2 = (UReal) eventMap.get("incr");
          UReal ts = (UReal) eventMap.get("ts");
          double prob = (Double) eventMap.get("prob");
        
        StringBuilder sb = new StringBuilder();
        sb.append("* [ALERT] : Temperature Warning EVENT DETECTED: ");
        sb.append("Home "+ homeId + ": " + "temp=" + temp1 + ", ts=" + ts + "prob=" + prob);

        LOG.debug(sb.toString());
    }

    
}
