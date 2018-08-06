package com.cor.cep.subscriber;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.cor.cep.util.UReal;

import com.espertech.esper.client.Configuration;

@Component
public class AccidentsReportSubscriber implements StatementSubscriber{
	

    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(AccidentsReportSubscriber.class);

    /**
     * {@inheritDoc}
     */
    public String getStatement() {
    	
    		final double P_AccidentReportRule= 0.99;
    		
	    	Configuration config = new Configuration();
	    	config.addImport("com.cor.cep.util.*");	// package import
	    	String AccidentsReportPattern ="@Name('AccidentsReport') "
	    			+ "select current_timestamp() as timestamp, a1.location as location, count(*) as count, "
	    			+ "a1.prob * "
	    			+ P_AccidentReportRule + " as prob "
	    			+ "from pattern [every a1 = OccupantThrownAccident].win:time_batch(86400 milliseconds) "
	    			+ "group by a1.location";
	    	    
        return AccidentsReportPattern;
    }
    
    /**
     * Listener method called when Esper has detected a pattern match.
     */
    public void update(Map<String, Object> eventMap) {
    	
    		
        Long timestamp = (Long) eventMap.get("timestamp");
        Long count = (Long) eventMap.get("count");
        String location = (String) eventMap.get("location");
        double prob = (double) eventMap.get("prob");

        StringBuilder sb = new StringBuilder();
        sb.append("***************************************");
        sb.append("\n* [ALERT] : Accidents Report EVENT DETECTED! ");
        sb.append("\n* " + timestamp + " , " + count + " , " + location+ " , " + prob);
        sb.append("\n***************************************");

        //LOG.debug(sb.toString());
    }

}
