package com.cor.cep.util;

import java.awt.print.Book;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import com.cor.cep.handler.EventHandler;
import com.cor.cep.event.OxigenEvent;
//import com.cor.cep.event.RunnerEvent;
import com.cor.cep.event.TempEvent;
import com.cor.cep.event.TrafficJamEvent;


@Component
public class EventGenerator {

	/** Logger */
	private static Logger LOG = LoggerFactory.getLogger(EventGenerator.class);

	@Autowired
	private EventHandler eventHandler;

	public void startSendingEvents() {
		
		ExecutorService xrayExecutor = Executors.newSingleThreadExecutor();

		xrayExecutor.submit(new Runnable() {
			public void run() {
				 
				LOG.debug(getStartingMessage());
				
				
				FileReader fr = null;
                BufferedReader br = null;
                
                try {
                		fr= new FileReader("inputEvents/OTT_1000_x400.csv");
                		br= new BufferedReader(fr);
                		String s;//= br.readLine();
                		
                		while((s=br.readLine())!=null) {
                			StringTokenizer st = new StringTokenizer(s,";");
                			String tipo = st.nextToken();
                			
                			if(tipo.equals("OxigenEvent"))
                			{
                				double ts = Double.parseDouble(st.nextToken());
                				double con = Double.parseDouble(st.nextToken());
                				double km = Double.parseDouble(st.nextToken());
                				

                				OxigenEvent ve = new OxigenEvent(ts, con,km);
                				eventHandler.handle(ve);
                         }
                			else if(tipo.equals("TempEvent"))
                			{
                				double ts = Double.parseDouble(st.nextToken());
                				double km = Double.parseDouble(st.nextToken());
                				double value = Double.parseDouble(st.nextToken());

                				TempEvent ve = new TempEvent(ts, km,value);
                				eventHandler.handle(ve);
                         }
                			else if (tipo.equals("TrafficJamEvent"))
                			{
                				double ts = Double.parseDouble(st.nextToken());
                				double km = Double.parseDouble(st.nextToken());
                				
                				TrafficJamEvent ve = new TrafficJamEvent(ts, km);
                				eventHandler.handle(ve);
                         }
                		}	
                        
                  } catch (IOException e) {
                        e.printStackTrace();
                  } 
				
			}	
			});
		
				
	}
	
	/**
	 * Creates simple random Oxigen events and lets the implementation class
	 * handle them.
	 */
	public Map<Double, OxigenEvent> startSendingOxigenReadings() {

		Map<Double, OxigenEvent> map = new HashMap<Double, OxigenEvent>();
		
		try {
			FileReader fr = new FileReader("inputEvents/Oxigen.csv");
			BufferedReader br = new BufferedReader(fr);
			String s = br.readLine(); // discard the first line
			while ((s = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(s, ";");
				st.nextToken();
				double ts = Double.parseDouble(st.nextToken());
				double con = Double.parseDouble(st.nextToken());
				double km = Double.parseDouble(st.nextToken());
				

				OxigenEvent ve = new OxigenEvent(ts, con,km);
				map.put(ts, ve);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return map;
	}

	
    public Map<Double, TempEvent> startSendingTempReadings() {

    	Map<Double, TempEvent> map = new HashMap<Double,TempEvent>();
    	
                
    	try {
			FileReader fr = new FileReader("inputEvents/temperature.csv");
			BufferedReader br = new BufferedReader(fr);
			String s = br.readLine(); // discard the first line
			while ((s = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(s, ";");
				st.nextToken();
				double ts = Double.parseDouble(st.nextToken());
				double km = Double.parseDouble(st.nextToken());
				double value = Double.parseDouble(st.nextToken());

				TempEvent ve = new TempEvent(ts, km,value);
				map.put(ts, ve);

			}

				} catch (IOException e) {
					e.printStackTrace();
				}
    	
    	return map;

    }
    
    public Map<Double, TrafficJamEvent> startSendingTrafficReadings() {

		Map<Double, TrafficJamEvent> map = new HashMap<Double, TrafficJamEvent>();
		
		try {
			FileReader fr = new FileReader("inputEvents/TrafficJam.csv");
			BufferedReader br = new BufferedReader(fr);
			String s = br.readLine(); // discard the first line
			while ((s = br.readLine()) != null) {
				
				StringTokenizer st = new StringTokenizer(s, ";");
				
				st.nextToken();
				
				double ts = Double.parseDouble(st.nextToken());
				double km = Double.parseDouble(st.nextToken());
				
				
				TrafficJamEvent ve = new TrafficJamEvent(ts, km);
				map.put(ts, ve);
				
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
		
		return map;
	}

	
	private String getStartingMessage() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n\n************************************************************");
		sb.append("\n* STARTING ");
		sb.append("\n************************************************************\n");
		return sb.toString();
	}
}
