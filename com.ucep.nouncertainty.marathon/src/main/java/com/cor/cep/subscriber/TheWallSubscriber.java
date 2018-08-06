package com.cor.cep.subscriber;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.espertech.esper.client.Configuration;

@Component
public class TheWallSubscriber implements StatementSubscriber{
	private int count = 0;

    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(TheWallSubscriber.class);

    /**
     * {@inheritDoc}
     */
    public String getStatement() {
    	
	    	Configuration config = new Configuration();
	    	config.addImport("com.cor.cep.util.*");	// package import
	    	String TheWallPattern ="insert into TheWall \r\n" 
		    			+ "select r3.point as point, r3.dorsal as dorsal, r3.NTimeSeconds as ntimeseconds \r\n"
		    			+ "from pattern [(every r1 = RunnerEvent(r1.point=25))->\r\n"
		    			+ "                      (every r2 = RunnerEvent((r2.dorsal=r1.dorsal) and (r2.point=30)))->\r\n"
		    			+ "                      (every r3 = RunnerEvent((r3.dorsal=r2.dorsal) and (r3.point=35) and ((r3.NTimeSeconds-r2.NTimeSeconds)>((r2.NTimeSeconds-r1.NTimeSeconds)+60))))]";
        return TheWallPattern;
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
        sb.append("\n* [ALERT] : The Wall EVENT DETECTED! ");
        sb.append("\n* " + point + " , " + dorsal + " , " + ntimeseconds);
        sb.append("\n***************************************");
        count++;
        //LOG.debug(sb.toString() + Integer.toString(count));
    }

}

