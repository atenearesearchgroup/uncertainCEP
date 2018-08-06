package com.cor.cep.subscriber;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.espertech.esper.client.Configuration;

@Component
public class  RecordSubscriber implements StatementSubscriber{
	
	private int count = 0;
    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(RecordSubscriber.class);

    /**
     * {@inheritDoc}
     */
    public String getStatement() {
    	
	    	Configuration config = new Configuration();
	    	config.addImport("com.cor.cep.util.*");	// package import
	    	String RecordPattern ="insert into Record\r\n" 
		    			+ "select point, dorsal, ntimeseconds \r\n"
		    			+ "from Finisher "
	    				+ "where Finisher.ntimeseconds<7418 and not exists (select dorsal from Cheated.win:time(100 sec) where dorsal=Finisher.dorsal)";
        return RecordPattern;
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
        sb.append("\n* [ALERT] : Record EVENT DETECTED! ");
        sb.append("\n* " + point + " , " + dorsal + " , " + ntimeseconds);
        sb.append("\n***************************************");
        count++;
        //LOG.debug(sb.toString() + Integer.toString(count));
    }

}



