package com.cor.cep.subscriber;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.espertech.esper.client.Configuration;

@Component
public class AirqualitylevelSubscriber implements StatementSubscriber{
	

    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(AirqualitylevelSubscriber.class);

    /**
     * {@inheritDoc}
     */
    public String getStatement() {
    	
    		final double P_AirqualitylevelRule= 0.98;
	    	Configuration config = new Configuration();
	    	config.addImport("com.cor.cep.util.*");	// package import
	    	String AirqualitylevelPattern ="@Name('CO_Avg') "
	    			+ "@Priority(1) "
	    			+ "insert into AirQualityLevel "
	    			+ "select current_timestamp() as timestamp, a1.stationId as stationId, max(a1.levelNumber) as level, "
	    			+ " a1.prob *  "
	    			+ P_AirqualitylevelRule + " as prob\r\n" 
	    			+ "from pattern [every a1 = PullutantLevel].win:time_batch(6000 milliseconds)"
	    			+ "group by a1.stationId "
	    			+ "having max(a1.levelNumber) is not null";
        return AirqualitylevelPattern;
    }
    
    /**
     * Listener method called when Esper has detected a pattern match.
     */
    public void update(Map<String, Object> eventMap) {
    		
    	
        Long timestamp = (Long) eventMap.get("timestamp");
        
        Integer stationId = (Integer) eventMap.get("stationId");

        Integer level = (Integer) eventMap.get("level");

        StringBuilder sb = new StringBuilder();
        sb.append("***************************************");
        sb.append("\n* [ALERT] : Airqualitylevel EVENT DETECTED! ");
        sb.append("\n* " + timestamp + " , " + stationId + " , " + level);
        sb.append("\n***************************************");

        //LOG.debug(sb.toString());
    }

}

