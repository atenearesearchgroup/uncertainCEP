package com.cor.cep.subscriber;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cor.cep.util.UReal;
import com.espertech.esper.client.Configuration;

@Component
public class  RecordSubscriber implements StatementSubscriber{
	private int count = 0;
	

    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(RecordSubscriber.class);

    /**
     * {@inheritDoc}
     */
    public String getStatement() {
    	
	    	Configuration config = new Configuration();
	    	config.addImport("com.cor.cep.util.*");	// package import
	    	final double P_RecordRule= 0.999;
	    	final double PFN_Cheated=0.0001;
	    	String RecordPattern ="insert into Record\r\n" 
		    			+ "select point, dorsal, ntimeseconds, Finisher.prob * com.cor.cep.util.UReals.lt(Finisher.ntimeseconds,7418).getC() * \r\n"
		    			+ PFN_Cheated + " * "
		    			+ P_RecordRule + " as prob \r\n"
		    			+ "from Finisher "
	    				+ "where com.cor.cep.util.UBooleans.toBoolean(com.cor.cep.util.UReals.lt(Finisher.ntimeseconds,7418)) and not exists (select dorsal from Cheated.win:time(100 sec) where dorsal=Finisher.dorsal)";
        return RecordPattern;
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
        sb.append("\n* [ALERT] : Record EVENT DETECTED! ");
        sb.append("\n* " + point + " , " + dorsal + " , " + ntimeseconds+ " , " + prob);
        sb.append("\n***************************************");
        count++;
        LOG.debug(sb.toString() + Integer.toString(count));
    }

}



