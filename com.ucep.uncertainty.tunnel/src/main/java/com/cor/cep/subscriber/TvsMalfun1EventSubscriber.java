package com.cor.cep.subscriber;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
//import com.espertech.esper.client.Configuration;

import com.cor.cep.util.UReal;

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
        
    		final double P_TvsMalfun1Rule = 0.99;
    		
        // Example using 'Match Recognise' syntax.

    		

    			String crtiticalEventExpression = "insert into TvsMalfun1 "
    		          + "select o1.ts as ots, t1.ts as tts, o1.km as km, t1.value as temp, o1.concentr as ox, "
    		  		    + "o1.prob * t1.prob * "
    		          + "com.cor.cep.util.UBooleans.uand(com.cor.cep.util.UReals.le(o1.concentr,18.0),"
    		  		  + "com.cor.cep.util.UBooleans.uand(com.cor.cep.util.UReals.gt(t1.value,30.0),"
    		          + "com.cor.cep.util.UBooleans.uand(com.cor.cep.util.UReals.lt(com.cor.cep.util.UReals.minus(o1.km,10.0),t1.km), com.cor.cep.util.UReals.lt(t1.km,com.cor.cep.util.UReals.add(o1.km,20))))).getC() * "
    		  		    + "com.cor.cep.util.UReals.lt(o1.ts, t1.ts).getC() * "
    		          + P_TvsMalfun1Rule + " as prob "
    		          + "from pattern [every (o1 = OxigenEvent(com.cor.cep.util.UBooleans.toBoolean(com.cor.cep.util.UReals.le(o1.concentr,18.0)))) -> "
    		          + "(t1 = TempEvent(com.cor.cep.util.UBooleans.toBoolean("
    		          + "                com.cor.cep.util.UBooleans.uand(com.cor.cep.util.UReals.gt(t1.value,30.0), "
    		          + "                com.cor.cep.util.UBooleans.uand("
    		          + "                com.cor.cep.util.UReals.lt(com.cor.cep.util.UReals.minus(o1.km,20.0),t1.km), "
    		          + "                com.cor.cep.util.UReals.lt(t1.km, com.cor.cep.util.UReals.add(o1.km,20.0))))))) "
    		          + "where timer:within(5 minutes)]";
    		
        return crtiticalEventExpression;
    }
    
    /**
     * Listener method called when Esper has detected a pattern match.
     */
    public void update(Map<String, Object> eventMap) {

    		double prob = (Double) eventMap.get("prob");
    		UReal ots = (UReal) eventMap.get("ots");
    		UReal tts = (UReal) eventMap.get("tts");
    		
        StringBuilder sb = new StringBuilder();
        sb.append("***************************************");
        sb.append("\n* [ALERT] : TVS1 MALFUNCTIONING EVENT DETECTED! ");
        sb.append("Prob = " + prob );
        sb.append("\nOTS = " + ots );
        sb.append("\nTTS = " + tts );
        sb.append("\n***************************************");
        count++;
    		
        //LOG.debug(sb.toString() + Integer.toString(count));
    		
    }

    
}
