package com.cor.cep.subscriber;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.espertech.esper.client.Configuration;

@Component
public class So2_moderateSubscriber implements StatementSubscriber{
	

    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(So2_moderateSubscriber.class);

    /**
     * {@inheritDoc}
     */
    public String getStatement() {
    	
    		final double P_So2_moderateRule= 0.98;
    	
	    	Configuration config = new Configuration();
	    	config.addImport("com.cor.cep.util.*");	// package import
	    	String So2_moderatePattern ="@Name('SO2_Moderate') "
	    			+ "@Priority(2) "
	    			+ "insert into PullutantLevel "
	    			+ "select a1.timestamp as timestamp, a1.stationId as stationId, 2 as levelNumber, 'SO2_Moderate' as levelName, "
	    			+ " a1.prob *  "
	    			+ "com.cor.cep.util.UBooleans.uAnd(com.cor.cep.util.UReals.ge(a1.value,36.0), com.cor.cep.util.UReals.lt(a1.value,76.0)).getC() * "
	    			+ P_So2_moderateRule + " as prob\r\n" 
	    			+ "from pattern [every a1 = SO2_Avg(com.cor.cep.util.UBooleans.toBoolean(com.cor.cep.util.UBooleans.uAnd("
	    			+ "com.cor.cep.util.UReals.ge(a1.value,36.0),"
	    			+ "com.cor.cep.util.UReals.lt(a1.value,76.0))))]";
	    			
        return So2_moderatePattern;
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
        sb.append("\n* [ALERT] : SO2_Moderate EVENT DETECTED! ");
        sb.append("\n* " + timestamp + " , " + stationId + " , " + levelNumber+ " , " + levelName);
        sb.append("\n***************************************");

       //LOG.debug(sb.toString());
    }

}


