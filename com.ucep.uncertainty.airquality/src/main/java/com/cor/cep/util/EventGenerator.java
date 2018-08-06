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
//import org.springframework.stereotype.Component;


import com.cor.cep.event.QualityAirEvent;
import com.cor.cep.handler.QualityAirEventHandler;

public class EventGenerator {

	/** Logger */
    private static Logger LOG = LoggerFactory.getLogger(EventGenerator.class);

    /** The TemperatureEventHandler - wraps the Esper engine and processes the Events  */
    @Autowired
    private QualityAirEventHandler qualityAirEventHandler;

    /**
     * Creates simple random Temperature events and lets the implementation class handle them.
     */
    public void startSendingQualityAirReadings() {

    	ExecutorService xrayExecutor = Executors.newSingleThreadExecutor();

        xrayExecutor.submit(new Runnable() {
            public void run() {

                LOG.debug(getStartingMessage());
                
                
                
                FileReader fr = null;
                BufferedReader br = null;
                final double PFP_AIR= 0.0000000000000001;
                
                try {
                		fr= new FileReader("inputEvents/QA_inc_1000_X10.csv");
                		br= new BufferedReader(fr);
                		String s;//= br.readLine();
                		long cont=0;
                		
                		
                		while((s=br.readLine())!=null) {
                			StringTokenizer st = new StringTokenizer(s,";");
                			//st.nextToken();
                			
                			double ts=Double.parseDouble(st.nextToken());
                			
                			int id=Integer.parseInt(st.nextToken());
                			
                			double pm2_5=Double.parseDouble(st.nextToken().replace(',', '.'));
                			
                			double pm10=Double.parseDouble(st.nextToken().replace(',', '.'));
                			
                			double o3=Double.parseDouble(st.nextToken().replace(',', '.'));
                			
                			double no2=Double.parseDouble(st.nextToken().replace(',', '.'));
                			
                			double so2=Double.parseDouble(st.nextToken().replace(',', '.'));
                			double co=Double.parseDouble(st.nextToken().replace(',', '.'));
                			
                			
                			double tsu=Double.parseDouble(st.nextToken());
                			double pm2_5u = Double.parseDouble(st.nextToken().replace(',', '.'));
            				double pm10u = Double.parseDouble(st.nextToken().replace(',', '.'));
            				double o3u = Double.parseDouble(st.nextToken().replace(',', '.'));
            				double no2u = Double.parseDouble(st.nextToken().replace(',', '.'));
            				double so2u = Double.parseDouble(st.nextToken().replace(',', '.'));
            				double cou = Double.parseDouble(st.nextToken().replace(',', '.'));
                			
            				
                			
                        	QualityAirEvent ve = new QualityAirEvent(new UReal(ts,tsu), id, new UReal(pm2_5,pm2_5u), new UReal(pm10,pm10u), new UReal(o3,o3u), new UReal(no2,no2u), new UReal(so2,so2u), new UReal(co,cou),(1-PFP_AIR));
                        	qualityAirEventHandler.handle(ve);
                        	
                        	
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
