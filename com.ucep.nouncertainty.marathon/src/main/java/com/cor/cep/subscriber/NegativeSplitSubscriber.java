package com.cor.cep.subscriber;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.espertech.esper.client.Configuration;

@Component
public class  NegativeSplitSubscriber implements StatementSubscriber{
	private int count = 0;

    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(NegativeSplitSubscriber.class);

    /**
     * {@inheritDoc}
     */
    public String getStatement() {
    	
	    	Configuration config = new Configuration();
	    	config.addImport("com.cor.cep.util.*");	// package import
	    	String NegativePattern ="insert into NegativeSplit \r\n" 
		    			+ "select r3.point as point, r3.dorsal as dorsal, r3.NTimeSeconds as ntimeseconds \r\n"
		    			+ "from pattern [(every r2 = RunnerEvent(r2.point=21.097))->\r\n"
		    			+ "              (every r3 = RunnerEvent((r3.dorsal=r2.dorsal) and (r3.point=42.195) and ((r3.NTimeSeconds-r2.NTimeSeconds)<=(r2.NTimeSeconds))))]";
        return NegativePattern;
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
        sb.append("\n* [ALERT] : Negative Split EVENT DETECTED! ");
        sb.append("\n* " + point + " , " + dorsal + " , " + ntimeseconds);
        sb.append("\n***************************************");
        count++;
        //LOG.debug(sb.toString() + Integer.toString(count));
    }

}


