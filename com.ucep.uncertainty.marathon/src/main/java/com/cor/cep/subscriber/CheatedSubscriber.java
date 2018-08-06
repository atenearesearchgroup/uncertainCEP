package com.cor.cep.subscriber;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cor.cep.util.UReal;
import com.espertech.esper.client.Configuration;

@Component
public class  CheatedSubscriber implements StatementSubscriber{
	private int count = 0;
	

    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(CheatedSubscriber.class);

    /**
     * {@inheritDoc}
     */
    public String getStatement() {
    	
	    	Configuration config = new Configuration();
	    	config.addImport("com.cor.cep.util.*");	// package import
	    	final double P_CheatedRule= 0.999;
	    	final double PFN_Runner=0.0001;
	    	String CheatedPattern ="insert into Cheated\r\n" 
		    			+ "select point, dorsal, ntimeseconds, Finisher.prob *  \r\n"
		    			+ PFN_Runner + " * "
		    			+ P_CheatedRule + " as prob \r\n"
		    			+ "from Finisher "
	    				+ "where not exists (select dorsal from RunnerEvent.win:time(100 sec) where dorsal=Finisher.dorsal and RunnerEvent.point=21.097)";
        return CheatedPattern;
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
        sb.append("\n* [ALERT] : Cheated EVENT DETECTED! ");
        sb.append("\n* " + point + " , " + dorsal + " , " + ntimeseconds+ " , " + prob);
        sb.append("\n***************************************");
        count++;
        LOG.debug(sb.toString() + Integer.toString(count));
    }

}



