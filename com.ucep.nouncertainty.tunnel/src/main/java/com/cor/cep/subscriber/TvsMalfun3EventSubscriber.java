package com.cor.cep.subscriber;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

//import com.espertech.esper.client.Configuration;

/**
 * Wraps Esper Statement and Listener. No dependency on Esper libraries.
 */
@Component
public class TvsMalfun3EventSubscriber implements StatementSubscriber {
	private int count = 0;
	
	/** Logger */
    private static Logger LOG = LoggerFactory.getLogger(TvsMalfun3EventSubscriber.class);
    
    /**
     * {@inheritDoc}
     */
    public String getStatement() {
        
        // Example using 'Match Recognise' syntax.
        String crtiticalEventExpression = "insert into TvsMalfun3 "
        			+ "select o1.km as km, avg(t1.value) as temp, o1.concentr as ox\r\n"
        			+ "from pattern [(every (o1 = OxigenEvent(o1.concentr<=18)) -> (t1 = TempEvent((o1.km-10<t1.km) and (t1.km<o1.km+10))))\r\n"
        			+ "where timer:within(5 minutes)]";
        
        return crtiticalEventExpression;
    }
    
    /**
     * Listener method called when Esper has detected a pattern match.
     */
    public void update(Map<String, Object> eventMap) {

        StringBuilder sb = new StringBuilder();
        sb.append("***************************************");
        sb.append("\n* [ALERT] : TVS3 MALFUNCTIONING EVENT DETECTED! ");
        sb.append("\n***************************************");
        count++;
        //LOG.debug(sb.toString() + Integer.toString(count));
    }

    
}
