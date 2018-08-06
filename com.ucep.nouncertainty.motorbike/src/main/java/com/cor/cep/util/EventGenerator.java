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
//import com.cor.cep.event.RunnerEvent;
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
                
                FileReader fr = null;
                BufferedReader br = null;
                
                try {
                		fr= new FileReader("inputEvents/motorbike_1000_x400.csv");
                		br= new BufferedReader(fr);
                		String s;//= br.readLine();
                		
                		while((s=br.readLine())!=null) {
                			StringTokenizer st = new StringTokenizer(s,";");
                			//st.nextToken();
                			long timestamp=Long.parseLong(st.nextToken());
                			int id=Integer.parseInt(st.nextToken());
                			String location=st.nextToken();
                			double speed=Double.parseDouble(st.nextToken());
                			double preassure1=Double.parseDouble(st.nextToken());
                			double preassure2=Double.parseDouble(st.nextToken());
                			boolean seat = Boolean.parseBoolean(st.nextToken());
                			
                			MotorbikeEvent ve = new MotorbikeEvent(timestamp, id, location, speed, preassure1, preassure2, seat);
                			motorbikeEventHandler.handle(ve);
                        	
                        }
                        
                    } catch (IOException e) {
                        e.printStackTrace();
                    } 
                	    System.out.println("Motorbike finito");
                

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
