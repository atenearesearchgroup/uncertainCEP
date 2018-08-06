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
public class TvsMalfun1EventSubscriber implements StatementSubscriber {
	private int count = 0;

    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(TvsMalfun1EventSubscriber.class);
    
    /**
     * {@inheritDoc}
     */
    public String getStatement() {
        
      
        String crtiticalEventExpression = "insert into TvsMalfun1 "
                + "select o1.ts as ots, t1.ts as tts, o1.km as km, t1.value as temp, o1.concentr as ox\r\n"
                + "from pattern [every (o1 = OxigenEvent(o1.concentr<=18))->(t1=TempEvent(t1.value>30 and (o1.km-20.0)<t1.km and t1.km<(o1.km+20.0)))\r\n"
                + "where timer:within(5 minutes)]";
        
        return crtiticalEventExpression;
    }
    
    /**
     * Listener method called when Esper has detected a pattern match.
     */
    public void update(Map<String, Object> eventMap) {
    		
    		double ots = (Double) eventMap.get("ots");
    		double tts = (Double) eventMap.get("tts");
    		
        StringBuilder sb = new StringBuilder();
        sb.append("***************************************");
        sb.append("\n* [ALERT] : TVS1 MALFUNCTIONING EVENT DETECTED! ");
        sb.append("\nOTS = " + ots );
        sb.append("\nTTS = " + tts );
        sb.append("\n***************************************");
        count++;
        //LOG.debug(sb.toString() + Integer.toString(count));
    }

    
}
