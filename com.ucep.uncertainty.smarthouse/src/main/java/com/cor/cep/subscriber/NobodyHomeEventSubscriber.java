package com.cor.cep.subscriber;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cor.cep.util.UReal;
import com.espertech.esper.client.Configuration;

/**
 * Wraps Esper Statement and Listener. No dependency on Esper libraries.
 */
@Component
public class NobodyHomeEventSubscriber implements StatementSubscriber {

    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(NobodyHomeEventSubscriber.class);

    /**
     * {@inheritDoc}
     */
    public String getStatement() {
    	
	    	Configuration config = new Configuration();
	    	config.addImport("com.cor.cep.util.*");	// package import
	    	final double P_NobodyHomeRule= 0.97;
	    	
	    	String crtiticalEventExpression = "insert into NobodyHome\r\n" + 
	    	"select p.ts as ts, h.x as x, h.y as y, h.id as id, p.pid as pid, "
	    	+ " h.prob * p.prob * "
	    	+ " com.cor.cep.util.UBooleans.uand(com.cor.cep.util.UBooleans.unot(h.dopen),"
	    	+ " com.cor.cep.util.UBooleans.uor("
	    	+ " com.cor.cep.util.UBooleans.uor("
	    	+ " com.cor.cep.util.UBooleans.uor("
	    	+ " com.cor.cep.util.UReals.le(p.x, com.cor.cep.util.UReals.minus(h.x, com.cor.cep.util.UReals.divideBy(com.cor.cep.util.UReals.sqrt(h.sqre),2))) "
	    	+ ","
	    	+ " com.cor.cep.util.UReals.ge(p.x, com.cor.cep.util.UReals.add(h.x, com.cor.cep.util.UReals.divideBy(com.cor.cep.util.UReals.sqrt(h.sqre),2))))"
	    	+ ","
	    	+ " com.cor.cep.util.UReals.le(p.y, com.cor.cep.util.UReals.minus(h.y, com.cor.cep.util.UReals.divideBy(com.cor.cep.util.UReals.sqrt(h.sqre),2))))"
	    	+ ","
	    	+ "com.cor.cep.util.UReals.ge(p.y, com.cor.cep.util.UReals.add(h.y, com.cor.cep.util.UReals.divideBy(com.cor.cep.util.UReals.sqrt(h.sqre),2)))) "
	    	+ ").getC() * "
//	    	+ "com.cor.cep.util.UReals.lt(h.ts,p.ts).getC() * "
	    	+ P_NobodyHomeRule + " as prob\r\n "
	    	+"from pattern [(every h=HomeEvent(com.cor.cep.util.UBooleans.toBoolean(com.cor.cep.util.UBooleans.unot(h.dopen))) "
	    	+ "-> (p=PersonEvent(\r\n" + 
	    	"com.cor.cep.util.UBooleans.toBoolean(\r\n" + 
	    	"com.cor.cep.util.UReals.le(p.x, com.cor.cep.util.UReals.minus(h.x, com.cor.cep.util.UReals.divideBy(com.cor.cep.util.UReals.sqrt(h.sqre),2)))) or \r\n" + 
	    	"com.cor.cep.util.UBooleans.toBoolean(\r\n" + 
	    	"com.cor.cep.util.UReals.ge(p.x, com.cor.cep.util.UReals.add(h.x, com.cor.cep.util.UReals.divideBy(com.cor.cep.util.UReals.sqrt(h.sqre),2)))) or\r\n" + 
	    	"com.cor.cep.util.UBooleans.toBoolean(\r\n" + 
	    	"com.cor.cep.util.UReals.le(p.y, com.cor.cep.util.UReals.minus(h.y, com.cor.cep.util.UReals.divideBy(com.cor.cep.util.UReals.sqrt(h.sqre),2)))) or \r\n" + 
	    	"com.cor.cep.util.UBooleans.toBoolean(\r\n" + 
	    	"com.cor.cep.util.UReals.ge(p.y, com.cor.cep.util.UReals.add(h.y, com.cor.cep.util.UReals.divideBy(com.cor.cep.util.UReals.sqrt(h.sqre),2)))))))\r\n" + 
	    	"where timer:within(10 seconds)]"; 
	    	
//	    	String crtiticalEventExpression = "insert into NobodyHome\r\n" + 
//	    	"select p.ts as ts, h.x as x, h.y as y, h.id as id, p.pid as pid, "
//	    	+ " h.prob * p.prob * "
//	    	+ " com.cor.cep.util.UBooleans.uand(com.cor.cep.util.UBooleans.unot(h.dopen),"
//	    	+ " com.cor.cep.util.UBooleans.uor("
//	    	+ " com.cor.cep.util.UBooleans.uor("
//	    	+ " com.cor.cep.util.UBooleans.uor("
//	    	+ " com.cor.cep.util.UReals.le(p.x, com.cor.cep.util.UReals.minus(h.x, com.cor.cep.util.UReals.divideBy(com.cor.cep.util.UReals.sqrt(h.sqre),2))) "
//	    	+ ","
//	    	+ " com.cor.cep.util.UReals.ge(p.x, com.cor.cep.util.UReals.add(h.x, com.cor.cep.util.UReals.divideBy(com.cor.cep.util.UReals.sqrt(h.sqre),2))))"
//	    	+ ","
//	    	+ " com.cor.cep.util.UReals.le(p.y, com.cor.cep.util.UReals.minus(h.y, com.cor.cep.util.UReals.divideBy(com.cor.cep.util.UReals.sqrt(h.sqre),2))))"
//	    	+ ","
//	    	+ "com.cor.cep.util.UReals.ge(p.y, com.cor.cep.util.UReals.add(h.y, com.cor.cep.util.UReals.divideBy(com.cor.cep.util.UReals.sqrt(h.sqre),2)))) "
//	    	+ ").getC() * "
//	    	+ "com.cor.cep.util.UReals.lt(h.ts,p.ts).getC() * "
//	    	+ P_NobodyHomeRule + " as prob\r\n "
//	    	+"from pattern [(every h=HomeEvent(com.cor.cep.util.UBooleans.toBoolean(com.cor.cep.util.UBooleans.unot(h.dopen))) "
//	    	+ "-> (p=PersonEvent(\r\n" + 
//	    	"com.cor.cep.util.UBooleans.toBoolean(\r\n" + 
//	    	"com.cor.cep.util.UReals.le(p.x, com.cor.cep.util.UReals.minus(h.x, com.cor.cep.util.UReals.divideBy(com.cor.cep.util.UReals.sqrt(h.sqre),2)))) or \r\n" + 
//	    	"com.cor.cep.util.UBooleans.toBoolean(\r\n" + 
//	    	"com.cor.cep.util.UReals.ge(p.x, com.cor.cep.util.UReals.add(h.x, com.cor.cep.util.UReals.divideBy(com.cor.cep.util.UReals.sqrt(h.sqre),2)))) or\r\n" + 
//	    	"com.cor.cep.util.UBooleans.toBoolean(\r\n" + 
//	    	"com.cor.cep.util.UReals.le(p.y, com.cor.cep.util.UReals.minus(h.y, com.cor.cep.util.UReals.divideBy(com.cor.cep.util.UReals.sqrt(h.sqre),2)))) or \r\n" + 
//	    	"com.cor.cep.util.UBooleans.toBoolean(\r\n" + 
//	    	"com.cor.cep.util.UReals.ge(p.y, com.cor.cep.util.UReals.add(h.y, com.cor.cep.util.UReals.divideBy(com.cor.cep.util.UReals.sqrt(h.sqre),2)))))))\r\n" + 
//	    	"where timer:within(10 seconds)]"; 
	    	
        return crtiticalEventExpression;
    }
    
    /**
     * Listener method called when Esper has detected a pattern match.
     */
    public void update(Map<String, Object> eventMap) {
    	
    	int id = (Integer) eventMap.get("id");
    	int pid = (Integer) eventMap.get("pid");
        UReal x = (UReal) eventMap.get("x");
        UReal y = (UReal) eventMap.get("y");
        UReal ts = (UReal) eventMap.get("ts");
        double prob = (Double) eventMap.get("prob");
        
        StringBuilder sb = new StringBuilder();
        sb.append("* [ALERT] : Nobody home EVENT DETECTED: ");
        sb.append("Home "+ id + ": " + "x=" + x + ", y=" + y + ", ts=" + ts + ", prob=" + prob);

        LOG.debug(sb.toString());
    }

    
}
