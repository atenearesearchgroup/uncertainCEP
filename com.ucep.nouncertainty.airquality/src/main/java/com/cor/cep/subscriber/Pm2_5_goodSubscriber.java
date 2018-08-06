package com.cor.cep.subscriber;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.espertech.esper.client.Configuration;

@Component
public class Pm2_5_goodSubscriber implements StatementSubscriber{
	private int count = 0;

    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(Pm2_5_goodSubscriber.class);

    /**
     * {@inheritDoc}
     */
    public String getStatement() {
    	
	    	Configuration config = new Configuration();
	    	config.addImport("com.cor.cep.util.*");	// package import
	    	String Pm2_5_goodPattern ="@Name('PM2_5_Good') "
	    			+ "@Priority(2) "
	    			+ "insert into PullutantLevel "
	    			+ "select a1.timestamp as timestamp, a1.stationId as stationId, 1 as levelNumber, 'PM2_5_Good' as levelName "
	    			+ "from pattern [every a1 = PM2_5_Avg(a1.value >=0 and a1.value < 12.1)]";
	    			
        return Pm2_5_goodPattern;
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
        sb.append("\n* [ALERT] : PM2_5_Good EVENT DETECTED! ");
        sb.append("\n* " + timestamp + " , " + stationId + " , " + levelNumber+ " , " + levelName);
        sb.append("\n***************************************");
        count++;
        //LOG.debug(sb.toString() + Integer.toString(count));
    }

}
