package com.cor.cep.subscriber;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.espertech.esper.client.Configuration;

@Component
public class  ScapedSubscriber implements StatementSubscriber{
	
	private int count = 0;
    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(ScapedSubscriber.class);

    /**
     * {@inheritDoc}
     */
    public String getStatement() {
    	
	    	Configuration config = new Configuration();
	    	config.addImport("com.cor.cep.util.*");	// package import
	    	String ScapedPattern ="insert into Scaped\r\n" 
	    				+ "select point , dorsal , NTimeSeconds as ntimeseconds  \r\n"
	    				+ "from RunnerEvent.win:length_batch(2) "
	    				+ "where ((Select NTimeSeconds from RunnerEvent.std:lastevent())-(select NTimeSeconds from RunnerEvent.std:firstevent()))>30";
	    	return ScapedPattern;
    }
    
    /**
     * Listener method called when Esper has detected a pattern match.
     */
    public void update(Map<String, Object> eventMap) {
    		
    		
    	
        double point = (Double) eventMap.get("point");
        String dorsal = (String) eventMap.get("dorsal");
        double ntimeseconds = (Double) eventMap.get("ntimeseconds");
        
        
        StringBuilder sb = new StringBuilder();
        sb.append("***************************************");
        sb.append("\n* [ALERT] : Scaped EVENT DETECTED! ");
        sb.append("\n* " + point + " , " + dorsal + " , " + ntimeseconds);
        sb.append("\n***************************************");
        count++;
        //LOG.debug(sb.toString() + Integer.toString(count));
    }

}




