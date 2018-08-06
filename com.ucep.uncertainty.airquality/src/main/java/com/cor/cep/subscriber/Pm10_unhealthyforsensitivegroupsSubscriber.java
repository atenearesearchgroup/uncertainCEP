package com.cor.cep.subscriber;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.espertech.esper.client.Configuration;

@Component
public class Pm10_unhealthyforsensitivegroupsSubscriber implements StatementSubscriber{
	

    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(Pm10_unhealthyforsensitivegroupsSubscriber.class);

    /**
     * {@inheritDoc}
     */
    public String getStatement() {
    	
    		final double P_Pm10_unhealthyforsensitivegroupsRule= 0.98;
	    	Configuration config = new Configuration();
	    	config.addImport("com.cor.cep.util.*");	// package import
	    	String Pm10_unhealthyforsensitivegroupsPattern ="@Name('PM10_UnhealthyForSensitiveGroups') "
	    			+ "@Priority(2) "
	    			+ "insert into PullutantLevel "
	    			+ "select a1.timestamp as timestamp, a1.stationId as stationId, 3 as levelNumber, 'PM10_UnhealthyForSensitiveGroups' as levelName, "
	    			+ " a1.prob *  "
	    			+ "com.cor.cep.util.UBooleans.uAnd(com.cor.cep.util.UReals.ge(a1.value,155.0), com.cor.cep.util.UReals.lt(a1.value,255.0)).getC() * "
	    			+ P_Pm10_unhealthyforsensitivegroupsRule + " as prob\r\n" 
	    			+ "from pattern [every a1 = PM10_Avg(com.cor.cep.util.UBooleans.toBoolean(com.cor.cep.util.UBooleans.uAnd("
	    			+ "com.cor.cep.util.UReals.ge(a1.value,155.0),"
	    			+ "com.cor.cep.util.UReals.lt(a1.value,255.0))))]";
	    			
        return Pm10_unhealthyforsensitivegroupsPattern;
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
        sb.append("\n* [ALERT] : PM10_UnhealthyForSensitiveGroups EVENT DETECTED! ");
        sb.append("\n* " + timestamp + " , " + stationId + " , " + levelNumber+ " , " + levelName);
        sb.append("\n***************************************");

        //LOG.debug(sb.toString());
    }

}

