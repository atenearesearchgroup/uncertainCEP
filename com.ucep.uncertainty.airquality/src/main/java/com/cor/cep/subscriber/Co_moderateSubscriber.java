package com.cor.cep.subscriber;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.espertech.esper.client.Configuration;

@Component
public class Co_moderateSubscriber implements StatementSubscriber{
	

    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(Co_moderateSubscriber.class);

    /**
     * {@inheritDoc}
     */
    public String getStatement() {
    	
    		final double P_Co_moderateRule= 0.98;
	    	Configuration config = new Configuration();
	    	config.addImport("com.cor.cep.util.*");	// package import
	    	String Co_moderatePattern ="@Name('CO_Moderate') "
	    			+ "@Priority(2) "
	    			+ "insert into PullutantLevel "
	    			+ "select a1.timestamp as timestamp, a1.stationId as stationId, 2 as levelNumber, 'CO_Moderate' as levelName, "
	    			+ " a1.prob *  "
	    			+ "com.cor.cep.util.UBooleans.uAnd(com.cor.cep.util.UReals.ge(a1.value,4.5), com.cor.cep.util.UReals.lt(a1.value,9.5)).getC() * "
	    			+ P_Co_moderateRule + " as prob\r\n" 
	    			+ "from pattern [every a1 = CO_Avg(com.cor.cep.util.UBooleans.toBoolean(com.cor.cep.util.UBooleans.uAnd("
	    			+ "com.cor.cep.util.UReals.ge(a1.value,4.5),"
	    			+ "com.cor.cep.util.UReals.lt(a1.value,9.5))))]";
	    			
        return Co_moderatePattern;
    }
    
    /**
     * Listener method called when Esper has detected a pattern match.
     */
    public void update(Map<String, Object> eventMap) {
    		
    	
        Long timestamp = (Long) eventMap.get("timestamp");
        
        Integer stationId = (Integer) eventMap.get("stationId");

        Integer levelNumber = (Integer) eventMap.get("levelNumber");
        
        String levelName = (String) eventMap.get("levelName");

        StringBuilder sb = new StringBuilder();
        sb.append("***************************************");
        sb.append("\n* [ALERT] : CO_Moderate EVENT DETECTED! ");
        sb.append("\n* " + timestamp + " , " + stationId + " , " + levelNumber+ " , " + levelName);
        sb.append("\n***************************************");

        LOG.debug(sb.toString());
    }

}




