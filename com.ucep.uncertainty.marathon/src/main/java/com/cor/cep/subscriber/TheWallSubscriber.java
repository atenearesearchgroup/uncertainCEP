package com.cor.cep.subscriber;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cor.cep.util.UReal;
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
	    	final double P_TheWallRule= 0.999;
	    	String TheWallPattern ="insert into TheWall \r\n" 
		    			+ "select r3.point as point, r3.dorsal as dorsal, r3.NTimeSeconds as ntimeseconds, r3.prob * \r\n"
		    			+" com.cor.cep.util.UReals.gt(com.cor.cep.util.UReals.minus(r3.NTimeSeconds,r2.NTimeSeconds),"
		    			+" com.cor.cep.util.UReals.add(com.cor.cep.util.UReals.minus(r2.NTimeSeconds,r1.NTimeSeconds),60)).getC() * "
		    			+" com.cor.cep.util.UBooleans.uAnd(com.cor.cep.util.UReals.lt(r1.NTimeSeconds,r2.NTimeSeconds),"
		    			+" com.cor.cep.util.UReals.lt(r2.NTimeSeconds,r3.NTimeSeconds)).getC() * "
		    			+ P_TheWallRule + " as prob \r\n"
		    			+ "from pattern ["
		    			+ "(every r1 = RunnerEvent(r1.point=25.0)) ->\r\n"
	    	            + "(every r2 = RunnerEvent(r2.dorsal=r1.dorsal and r2.point=30.0))->\r\n"
		    			+ "(every r3 = RunnerEvent(r3.dorsal=r2.dorsal and r3.point=35.0 and "
		    			+ "com.cor.cep.util.UBooleans.toBoolean(com.cor.cep.util.UReals.gt(com.cor.cep.util.UReals.minus(r3.NTimeSeconds,r2.NTimeSeconds), com.cor.cep.util.UReals.add(com.cor.cep.util.UReals.minus(r2.NTimeSeconds,r1.NTimeSeconds),60.0)))))]";
        return TheWallPattern;
    }
    
    /**
     * Listener method called when Esper has detected a pattern match.
     */
    public void update(Map<String, Object> eventMap) {
    		
    		    	
        double point = (Double) eventMap.get("point");
        String dorsal = (String) eventMap.get("dorsal");
        UReal ntimeseconds = (UReal) eventMap.get("ntimeseconds");
        double prob = (Double) eventMap.get("prob");
        
        StringBuilder sb = new StringBuilder();
        sb.append("***************************************");
        sb.append("\n* [ALERT] : The Wall EVENT DETECTED! ");
        sb.append("\n* " + point + " , " + dorsal + " , " + ntimeseconds + " , "+ prob);
        sb.append("\n***************************************");
        count++;
        LOG.debug(sb.toString() + Integer.toString(count));
    }

}

