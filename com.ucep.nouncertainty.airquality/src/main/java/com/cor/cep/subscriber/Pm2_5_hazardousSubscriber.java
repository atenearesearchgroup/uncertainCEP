package com.cor.cep.subscriber;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.espertech.esper.client.Configuration;

@Component
public class Pm2_5_hazardousSubscriber implements StatementSubscriber{
	

    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(Pm2_5_hazardousSubscriber.class);

    /**
     * {@inheritDoc}
     */
    public String getStatement() {
    	
	    	Configuration config = new Configuration();
	    	config.addImport("com.cor.cep.util.*");	// package import
	    	String Pm2_5_hazardousPattern ="@Name('PM2_5_Hazardous') "
	    			+ "@Priority(2) "
	    			+ "insert into PullutantLevel "
	    			+ "select a1.timestamp as timestamp, a1.stationId as stationId, 6 as levelNumber, 'PM2_5_Hazardous' as levelName "
	    			+ "from pattern [every a1 = PM2_5_Avg(a1.value >=250.5 and a1.value <= 500.4)]";
	    			
        return Pm2_5_hazardousPattern;
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
        sb.append("\n* [ALERT] : PM2_5_Hazardous EVENT DETECTED! ");
        sb.append("\n* " + timestamp + " , " + stationId + " , " + levelNumber+ " , " + levelName);
        sb.append("\n***************************************");

        //LOG.debug(sb.toString());
    }

}

