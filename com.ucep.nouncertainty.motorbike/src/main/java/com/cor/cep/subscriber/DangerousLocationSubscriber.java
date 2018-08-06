package com.cor.cep.subscriber;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.espertech.esper.client.Configuration;

@Component
public class DangerousLocationSubscriber implements StatementSubscriber{
	
	private int count = 0;
    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(DangerousLocationSubscriber.class);

    /**
     * {@inheritDoc}
     */
    public String getStatement() {
    	
	    	Configuration config = new Configuration();
	    	config.addImport("com.cor.cep.util.*");	// package import
	    	String CrashPattern ="@Name('DangerousLocation') "				
	    			+ "insert into DangerousLocation "
	    			+ "select current_timestamp() as timestamp, a1.location as location "
	    			+ "from pattern [every a1 = Crash(a1.location = 'Cadiz')].win:length_batch(100) "
	    			+ "group by a1.location";
	    	    
        return CrashPattern;
    }
    
    /**
     * Listener method called when Esper has detected a pattern match.
     */
public void update(Map<String, Object> eventMap) {
    	
        Long timestamp = (Long) eventMap.get("timestamp");
        String location = (String) eventMap.get("location");

        StringBuilder sb = new StringBuilder();
        sb.append("***************************************");
        sb.append("\n* [ALERT] : Dangerous Location EVENT DETECTED! ");
        sb.append("\n* " + timestamp + " , " + location);
        sb.append("\n***************************************");
        count++;
        //LOG.debug(sb.toString() + Integer.toString(count));
    }

}
