package com.cor.cep.subscriber;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cor.cep.util.UReal;
import com.espertech.esper.client.Configuration;

@Component
public class  FinisherSubscriber implements StatementSubscriber{
	private int count = 0;
	

    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(FinisherSubscriber.class);

    /**
     * {@inheritDoc}
     */
    public String getStatement() {
    	
	    	Configuration config = new Configuration();
	    	config.addImport("com.cor.cep.util.*");	// package import
	    	
	    	final double P_FinisherRule= 0.999;
	    	String FinisherPattern ="insert into Finisher\r\n" 
		    			+ "select point, dorsal, NTimeSeconds as ntimeseconds, RunnerEvent.prob * com.cor.cep.util.UReals.lt(NTimeSeconds,7418).getC() * "
	    			    + P_FinisherRule + " as prob \r\n"
		    			+ "from RunnerEvent where point =42.195";
        return FinisherPattern;
    }
    
    /**
     * Listener method called when Esper has detected a pattern match.
     */
    public void update(Map<String, Object> eventMap) {
    		
    		
    	
        double point = (Double) eventMap.get("point");
        String dorsal = (String) eventMap.get("dorsal");
        UReal ntimeseconds = (UReal) eventMap.get("ntimeseconds");
        double prob = (Double) eventMap.get("prob");
        
        StringBuilder sb = new StringBuilder();
        sb.append("***************************************");
        sb.append("\n* [ALERT] : Finisher EVENT DETECTED! ");
        sb.append("\n* " + point + " , " + dorsal + " , " + ntimeseconds + " , " + prob);
        sb.append("\n***************************************");
        count++;
        LOG.debug(sb.toString() + Integer.toString(count));
    }

}



