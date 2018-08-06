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

import com.cor.cep.event.RunnerEvent;
import com.cor.cep.handler.RunnerEventHandler;

/**
 * Just a simple class to create a number of Random TemperatureEvents and pass them off to the
 * TemperatureEventHandler.
 */
@Component
public class RunnerEventGenerator {
	/** Logger */
    private static Logger LOG = LoggerFactory.getLogger(RunnerEventGenerator.class);

    /** The TemperatureEventHandler - wraps the Esper engine and processes the Events  */
    @Autowired
    private RunnerEventHandler runnerEventHandler;

    /**
     * Creates simple random Temperature events and lets the implementation class handle them.
     */
    public void startSendingRunnerReadings() {

        ExecutorService xrayExecutor = Executors.newSingleThreadExecutor();

        xrayExecutor.submit(new Runnable() {
            public void run() {

                LOG.debug(getStartingMessage());
                
                
             
                FileReader fr = null;
                BufferedReader br = null;
                
                final double PFP_RUNNER=0.00000001;
                
                try {
                		fr= new FileReader("inputEvents/marathon_2018_inc_1000.csv");
                		br= new BufferedReader(fr);
                		String s;//= br.readLine();
                		
                		while((s=br.readLine())!=null) {
                			//System.out.println(s);
                			StringTokenizer st = new StringTokenizer(s,";");
                			//st.nextToken();
                			double point=Double.parseDouble(st.nextToken().replace(',', '.'));
                			int position=Integer.parseInt(st.nextToken());
                			String dorsal=st.nextToken();
                			String name=st.nextToken();
                			String surname=st.nextToken();
                			String category=st.nextToken();
                			String otime=st.nextToken();
                			String ntime=st.nextToken();
                			double ntimeseconds=Double.parseDouble(st.nextToken());
          				double pointu = Double.parseDouble(st.nextToken().replace(',', '.'));
            				double tu = Double.parseDouble(st.nextToken());
            				
                        	RunnerEvent ve = new RunnerEvent(point, position, dorsal, name, surname, category, otime, ntime, new UReal(ntimeseconds,tu),(1-PFP_RUNNER));
                         runnerEventHandler.handle(ve);
                        	
                        }
                		
                        
                    } catch (IOException e) {
                        e.printStackTrace();
                    } 
                System.out.println("Finito");
                 
            }

            //}
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
