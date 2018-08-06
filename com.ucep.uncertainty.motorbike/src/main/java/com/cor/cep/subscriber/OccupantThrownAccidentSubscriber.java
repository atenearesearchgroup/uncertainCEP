package com.cor.cep.subscriber;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.cor.cep.util.UReal;

import com.espertech.esper.client.Configuration;

@Component
public class OccupantThrownAccidentSubscriber implements StatementSubscriber {
	private int count = 0;

    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(BlowOutTireSubscriber.class);

    /**
     * {@inheritDoc}
     */
    public String getStatement() {
    	
    		final double P_AccidentRule= 0.99;
    	
	    	Configuration config = new Configuration();
	    	config.addImport("com.cor.cep.util.*");	// package import
	    	String OccupantThrownAccidentPattern ="@Name('OccupantThrownAccident') "
	    			+ "insert into OccupantThrownAccident "
	    			+ "select current_timestamp() as timestamp, a3.motorbikeId as motorbikeId, a3.location as location, "
	    			+ "a1.prob * a2.prob * a3.prob * "
	    			+ "com.cor.cep.util.UBooleans.uand(com.cor.cep.util.UReals.gt(a2.timestamp, a1.timestamp), com.cor.cep.util.UReals.lt(a2.timestamp, a3.timestamp)).getC() * "
	    			+ P_AccidentRule + " as prob "
	    			+ "from pattern [every-distinct(a1.motorbikeId, a1.timestamp) a1 = BlowOutTire -> "
	    			+ "  (a2 = Crash(a1.motorbikeId = a2.motorbikeId) -> "
	    			+ "  a3 = DriverLeftSeat(a1.motorbikeId = a3.motorbikeId)) where timer:within(3 milliseconds)]";
	    	    
        return OccupantThrownAccidentPattern;
    }
    
    /**
     * Listener method called when Esper has detected a pattern match.
     */
    public void update(Map<String, Object> eventMap) {
    	
        Long timestamp = (Long) eventMap.get("timestamp");
        
        Integer motorbikeId = (Integer) eventMap.get("motorbikeId");

        String location = (String) eventMap.get("location");
        
        double prob = (double) eventMap.get("prob");

        StringBuilder sb = new StringBuilder();
        sb.append("***************************************");
        sb.append("\n* [ALERT] : Occupant Thrown Accident EVENT DETECTED! ");
        sb.append("\n* " + timestamp + " , " + motorbikeId + " , " + location+ " , " + prob);
        sb.append("\n***************************************");

        //LOG.debug(sb.toString());
    }

}
