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
    	
    		final double P_TvsMalfun3Rule=0.99;
        // Example using 'Match Recognise' syntax.
        String crtiticalEventExpression = "insert into TvsMalfun3 "
        			+ "select o1.km as km, t1.value as temp, o1.concentr as ox, "
        			+ "o1.prob * t1.prob * "
        			+ "com.cor.cep.util.UBooleans.uand("
        			+ "com.cor.cep.util.UReals.le(o1.concentr,18.0),"
        			+ "com.cor.cep.util.UBooleans.uand("
        			+ "com.cor.cep.util.UReals.lt(com.cor.cep.util.UReals.minus(o1.km,10.0),t1.km),"
        			+ "com.cor.cep.util.UReals.lt(t1.km, com.cor.cep.util.UReals.add(o1.km,10.0)))).getC() * "
        			+ "com.cor.cep.util.UReals.lt(o1.ts, t1.ts).getC() * "
        			+ P_TvsMalfun3Rule+ " as prob "
        			+ "from pattern [(every (o1 = OxigenEvent(com.cor.cep.util.UBooleans.toBoolean(com.cor.cep.util.UReals.le(o1.concentr,18.0)))) -> "
        			+ "(t1 = TempEvent("
        			+ "com.cor.cep.util.UBooleans.toBoolean("
        			+ "com.cor.cep.util.UBooleans.uand("
        			+ "com.cor.cep.util.UReals.lt(com.cor.cep.util.UReals.minus(o1.km,10),t1.km), "
        			+ "com.cor.cep.util.UReals.lt(t1.km, com.cor.cep.util.UReals.add(o1.km,10)))))))"
        			+ "where timer:within(5 minutes)]";
        
        return crtiticalEventExpression;
    }
    
    /**
     * Listener method called when Esper has detected a pattern match.
     */
    public void update(Map<String, Object> eventMap) {

    		double prob = (Double) eventMap.get("prob");
        StringBuilder sb = new StringBuilder();
        sb.append("***************************************");
        sb.append("\n* [ALERT] : TVS3 MALFUNCTIONING EVENT DETECTED! ");
        sb.append("Prob = " + prob );
        sb.append("\n***************************************");
        count++;
        //LOG.debug(sb.toString() + Integer.toString(count));
    }

    
}
