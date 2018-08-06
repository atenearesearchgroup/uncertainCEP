package com.cor.cep.subscriber;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.espertech.esper.client.Configuration;

@Component
public class Co_hazardousSubscriber implements StatementSubscriber{
	

    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(Co_hazardousSubscriber.class);

    /**
     * {@inheritDoc}
     */
    public String getStatement() {
    	
    		final double P_Co_hazardousRule= 0.98;
	    	Configuration config = new Configuration();
	    	config.addImport("com.cor.cep.util.*");	// package import
	    	String Co_hazardousPattern ="@Name('CO_Hazardous') "
	    			+ "@Priority(2) "
	    			+ "insert into PullutantLevel "
	    			+ "select a1.timestamp as timestamp, a1.stationId as stationId, 6 as levelNumber, 'CO_Hazardous' as levelName, "
	    			+ " a1.prob *  "
	    			+ "com.cor.cep.util.UBooleans.uAnd(com.cor.cep.util.UReals.ge(a1.value,30.5), com.cor.cep.util.UReals.le(a1.value,50.4)).getC() * "
	    			+ P_Co_hazardousRule + " as prob\r\n" 
	    			+ "from pattern [every a1 = CO_Avg(com.cor.cep.util.UBooleans.toBoolean(com.cor.cep.util.UBooleans.uAnd("
	    			+ "com.cor.cep.util.UReals.ge(a1.value,30.5),"
	    			+ "com.cor.cep.util.UReals.le(a1.value,50.4))))]";
	    			
        return Co_hazardousPattern;
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
        sb.append("\n* [ALERT] : CO_Hazardous EVENT DETECTED! ");
        sb.append("\n* " + timestamp + " , " + stationId + " , " + levelNumber+ " , " + levelName);
        sb.append("\n***************************************");

        //LOG.debug(sb.toString());
    }

}
