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
public class TvsMalfun2EventSubscriber implements StatementSubscriber {
	private int count = 0;

    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(TvsMalfun2EventSubscriber.class);
    
    /**
     * {@inheritDoc}
     */
    public String getStatement() {
        
        // Example using 'Match Recognise' syntax.
        String crtiticalEventExpression = "insert into TvsMalfun2 "
        			+ "select t1.km as km, t1.value as temp\r\n"
        			+ "from pattern [(every (t1 = TempEvent(t1.value>30)) -> (not tj = TrafficJamEvent(t1.km-10<tj.km and tj.km<t1.km+10)))\r\n"
        			+ "where timer:within(5 minutes)]";
        
        return crtiticalEventExpression;
    }
    
    /**
     * Listener method called when Esper has detected a pattern match.
     */
    public void update(Map<String, Object> eventMap) {

        StringBuilder sb = new StringBuilder();
        sb.append("***************************************");
        sb.append("\n* [ALERT] : TVS2 MALFUNCTIONING EVENT DETECTED! ");
        sb.append("\n***************************************");
        count++;
        //LOG.debug(sb.toString() + Integer.toString(count));
    }

    
}
