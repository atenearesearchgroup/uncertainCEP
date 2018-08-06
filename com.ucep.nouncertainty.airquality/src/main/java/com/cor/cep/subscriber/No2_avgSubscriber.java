package com.cor.cep.subscriber;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.espertech.esper.client.Configuration;

@Component
public class No2_avgSubscriber implements StatementSubscriber{
	private int count = 0;

    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(No2_avgSubscriber.class);

    /**
     * {@inheritDoc}
     */
    public String getStatement() {
    	
	    	Configuration config = new Configuration();
	    	config.addImport("com.cor.cep.util.*");	// package import
	    	String No2_avgPattern ="@Name('NO2_Avg') "
	    			+ "@Priority(3) "
	    			+ "insert into NO2_Avg "
	    			+ "select current_timestamp() as timestamp, a1.id as stationId, a1.NO2 as value "
	    			+ "from pattern [every a1 = QualityAirEvent(NO2 <> -1)].win:time(3600000 milliseconds)"
	    			+ "group by a1.id";
        return No2_avgPattern;
    }
    
    /**
     * Listener method called when Esper has detected a pattern match.
     */
    public void update(Map<String, Object> eventMap) {
    		
    	
        Long timestamp = (Long) eventMap.get("timestamp");
        
        Integer stationId = (Integer) eventMap.get("stationId");

        Double value = (Double) eventMap.get("value");

        StringBuilder sb = new StringBuilder();
        sb.append("***************************************");
        sb.append("\n* [ALERT] : NO2_Avg EVENT DETECTED! ");
        sb.append("\n* " + timestamp + " , " + stationId + " , " + value);
        sb.append("\n***************************************");
        count++;
        //LOG.debug(sb.toString() + Integer.toString(count));
    }

}

