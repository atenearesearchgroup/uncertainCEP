package com.cor.cep.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cor.cep.event.MotorbikeEvent;
import com.cor.cep.handler.MotorbikeEventHandler;

/**
 * Just a simple class to create a number of Random TemperatureEvents and pass them off to the
 * TemperatureEventHandler.
 */
@Component
public class EventGenerator {

    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(EventGenerator.class);

    /** The TemperatureEventHandler - wraps the Esper engine and processes the Events  */
    @Autowired
    private MotorbikeEventHandler motorbikeEventHandler;

    /**
     * Creates simple random Temperature events and lets the implementation class handle them.
     */
    public void startSendingMotorbikesReadings() {

        ExecutorService xrayExecutor = Executors.newSingleThreadExecutor();

        xrayExecutor.submit(new Runnable() {
            public void run() {

                LOG.debug(getStartingMessage());
                
                long time_start, time_end;
                final double PFP_MOTORBIKE= 0.0000000000000001;
            		time_start = System.currentTimeMillis();
        		
        		try {
        			FileReader fr = new FileReader("inputEvents/motorbike_inc_1000_x10.csv");
        			BufferedReader br = new BufferedReader(fr);
        			String s;// = br.readLine(); // discard the first line
        			while((s=br.readLine())!=null) {
        				
        				StringTokenizer st = new StringTokenizer(s, ";");
        	
        				double ts = Double.parseDouble(st.nextToken());
        				
        				int id = Integer.parseInt(st.nextToken());
        				String location= st.nextToken();
        				double speed = Double.parseDouble(st.nextToken());
        				double pre1 = Double.parseDouble(st.nextToken());
        				double pre2 = Double.parseDouble(st.nextToken());
        				boolean seat = Boolean.parseBoolean(st.nextToken());
        				double tsu = Double.parseDouble(st.nextToken());
        				double spu = Double.parseDouble(st.nextToken().replace(',', '.'));
        				double pre1u = Double.parseDouble(st.nextToken().replace(',', '.'));
        				double pre2u = Double.parseDouble(st.nextToken().replace(',', '.'));
        				double seatu = Double.parseDouble(st.nextToken().replace(',', '.'));
        				
        				
        				MotorbikeEvent ve = new MotorbikeEvent(new UReal(ts,tsu),id,location,new UReal(speed,spu), new UReal(pre1,pre1u),
        						                               new UReal(pre2,pre2u), new UBoolean(seat,seatu),(1-PFP_MOTORBIKE));
        				
        				motorbikeEventHandler.handle(ve);
        				
        				
        				
        				
        			}
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
                
         }    
                    
         
        });
    }

    
    private String getStartingMessage(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n\n************************************************************");
        sb.append("\n* STARTING - ");
        sb.append("\n* PLEASE WAIT -");
        sb.append("\n* A WHILE TO SEE WARNING AND CRITICAL EVENTS!");
        sb.append("\n************************************************************\n");
        return sb.toString();
    }
}
