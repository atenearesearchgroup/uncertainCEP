package com.cor.cep.subscriber;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cor.cep.util.UReal;
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
	    	final double P_NegativeSplitRule= 0.999;
	    	String NegativePattern ="insert into NegativeSplit \r\n" 
		    			+ "select r3.point as point, r3.dorsal as dorsal, r3.NTimeSeconds as ntimeseconds, r3.prob * r2.prob * \r\n"
	    			    + "com.cor.cep.util.UReals.le(com.cor.cep.util.UReals.minus(r3.NTimeSeconds,r2.NTimeSeconds),r2.NTimeSeconds).getC() * "
		    			+ "com.cor.cep.util.UReals.lt(r2.NTimeSeconds, r3.NTimeSeconds).getC() * "
		    			+ P_NegativeSplitRule + " as prob \r\n"
		    			+ "from pattern [(every r2 = RunnerEvent(r2.point=21.097))->\r\n"
		    			+ "              (every r3 = RunnerEvent((r3.dorsal=r2.dorsal) and (r3.point=42.195) and (com.cor.cep.util.UBooleans.toBoolean(com.cor.cep.util.UReals.le(com.cor.cep.util.UReals.minus(r3.NTimeSeconds,r2.NTimeSeconds),r2.NTimeSeconds)))))]";
		    			
        return NegativePattern;
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
        sb.append("\n* [ALERT] : Negative Split EVENT DETECTED! ");
        sb.append("\n* " + point + " , " + dorsal + " , " + ntimeseconds + " , " + prob);
        sb.append("\n***************************************");
        count++;
        LOG.debug(sb.toString() + Integer.toString(count));
    }

}


