package com.cor.cep.subscriber;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.espertech.esper.client.Configuration;

@Component
public class Pm2_5_avgSubscriber implements StatementSubscriber{
	

    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(Pm2_5_avgSubscriber.class);

    /**
     * {@inheritDoc}
     */
    public String getStatement() {
    	
    		final double P_Pm2_5_avgRule= 0.98;
	    	Configuration config = new Configuration();
	    	config.addImport("com.cor.cep.util.*");	// package import
	    	String Pm2_5_avgPattern ="@Name('PM2_5_Avg') "
	    			+ "@Priority(3) "
	    			+ "insert into PM2_5_Avg "
	    			+ "select current_timestamp() as timestamp, a1.id as stationId, a1.PM2_5 as value, "
	    			+ " a1.prob *  "
	    			+ "com.cor.cep.util.UReals.uDistinct(a1.PM2_5,-1).getC() * "
	    			+ P_Pm2_5_avgRule + " as prob\r\n" 
	    			+ "from pattern [every a1 = QualityAirEvent(com.cor.cep.util.UBooleans.toBoolean(com.cor.cep.util.UReals.uDistinct(a1.PM2_5,-1)))].win:time(86400000 milliseconds)"
	    			+ "group by a1.id";
        return Pm2_5_avgPattern;
    }
    
    /**
     * Listener method called when Esper has detected a pattern match.
     */
    public void update(Map<String, Object> eventMap) {
    		
    	
        Long timestamp = (Long) eventMap.get("timestamp");
        
        Integer stationId = (Integer) eventMap.get("stationId");

        Double value = (Double) eventMap.get("value");

        StringBuilder sb = new StringBuilder();
        sb.append("***************************************");
        sb.append("\n* [ALERT] : Pm2_5_Avg EVENT DETECTED! ");
        sb.append("\n* " + timestamp + " , " + stationId + " , " + value);
        sb.append("\n***************************************");

        //LOG.debug(sb.toString());
    }

}
