package com.cor.cep.subscriber;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cor.cep.util.UReal;
import com.espertech.esper.client.Configuration;

@Component
public class  ScapedSubscriber implements StatementSubscriber{
	private int count = 0;
	

    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(ScapedSubscriber.class);

    /**
     * {@inheritDoc}
     */
    public String getStatement() {
    	
	    	Configuration config = new Configuration();
	    	config.addImport("com.cor.cep.util.*");	// package import
	    	final double P_ScapedRule= 0.999;
	    	String ScapedPattern ="insert into Scaped\r\n" 
	    				+ "select point , dorsal , NTimeSeconds as ntimeseconds, RunnerEvent.prob * RunnerEvent.prob * "
	    			    + "com.cor.cep.util.UReals.gt(com.cor.cep.util.UReals.minus((select NTimeSeconds from RunnerEvent.std:lastevent()), (select NTimeSeconds from RunnerEvent.std:firstevent())),30.0).getC() * \r\n"
	    				+ P_ScapedRule + " as prob \r\n"
	    				+ "from RunnerEvent.win:length_batch(2) "
	    				+ "where com.cor.cep.util.UBooleans.toBoolean(com.cor.cep.util.UReals.gt(com.cor.cep.util.UReals.minus((Select NTimeSeconds from RunnerEvent.std:lastevent()), (select NTimeSeconds from RunnerEvent.std:firstevent())), 30))";
	    	return ScapedPattern;
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
        sb.append("\n* [ALERT] : Scaped EVENT DETECTED! ");
        sb.append("\n* " + point + " , " + dorsal + " , " + ntimeseconds + " , " + prob);
        sb.append("\n***************************************");
        count++;
        LOG.debug(sb.toString() + Integer.toString(count));
    }

}




