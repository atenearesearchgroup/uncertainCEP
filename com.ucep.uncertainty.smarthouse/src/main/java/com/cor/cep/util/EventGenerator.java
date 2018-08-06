package com.cor.cep.util;

import java.awt.print.Book;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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

import com.cor.cep.event.HomeEvent;
import com.cor.cep.event.PersonEvent;
import com.cor.cep.handler.EventHandler;
//import com.cor.cep.handler.PersonEventHandler;

/**
 * Just a simple class to create a number of Random TemperatureEvents and pass
 * them off to the TemperatureEventHandler.
 */
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
				double t0 = System.currentTimeMillis();
				 
				LOG.debug(getStartingMessage());
				
				Map<Double, HomeEvent> homeMap = startSendingTemperatureReadings();
				Map<Double, PersonEvent> personMap = startSendingPersonReadings();
				Set<Double> keys = new HashSet<Double>();
				keys.addAll(homeMap.keySet());
				keys.addAll(personMap.keySet());
				
				System.out.println("Sending events");
				List<Double> sortedKeys = new ArrayList<Double>(new TreeSet<Double>(keys));
				
				double t1 = System.currentTimeMillis();
				
				int it = 1; // 3600 * it
				for (int i = 0; i < it; i++) {
					for (Double ts : sortedKeys) {
						if (homeMap.get(ts)!=null) {
							eventHandler.handle(homeMap.get(ts));
						}
						if (personMap.get(ts)!=null) {
							eventHandler.handle(personMap.get(ts));
						}
					}
				}
				double t2 = System.currentTimeMillis();
				System.out.println("Time sending events: "+(t2-t1)/1000);
				System.out.println("Time loading files and sending events: "+(t2-t0)/1000);
			}	
			});
				
	}
	
	/**
	 * Creates simple random Temperature events and lets the implementation class
	 * handle them.
	 */
	public Map<Double, HomeEvent> startSendingTemperatureReadings() {

		Map<Double, HomeEvent> map = new HashMap<Double, HomeEvent>();
		final double PFP_HOME= 0.05;
		
		try {
			FileReader fr = new FileReader("inputEvents/DatosSinteticosHome.csv");
			BufferedReader br = new BufferedReader(fr);
			String s = br.readLine(); // discard the first line
			while ((s = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(s, ";");
				st.nextToken();
				// System.out.println(s);
				int id = Integer.parseInt(st.nextToken());
				double ts = Double.parseDouble(st.nextToken());
				double x = Double.parseDouble(st.nextToken());
				double y = Double.parseDouble(st.nextToken());
				double sqre = Double.parseDouble(st.nextToken());
				double temp = Double.parseDouble(st.nextToken());
				double co = Double.parseDouble(st.nextToken());
				boolean dopen = Boolean.parseBoolean(st.nextToken());
				double tsu = Double.parseDouble(st.nextToken());
				double xu = Double.parseDouble(st.nextToken().replace(',', '.'));
				double yu = Double.parseDouble(st.nextToken().replace(',', '.'));
				double tempu = Double.parseDouble(st.nextToken().replace(',', '.'));
				double cou = Double.parseDouble(st.nextToken().replace(',', '.'));
				double dopenc = Double.parseDouble(st.nextToken().replace(',', '.'));
                //double sqreu = Double.parseDouble(st.nextToken().replace(',', '.'));

				HomeEvent ve = new HomeEvent(id, new UReal(temp, tempu), new UReal(co, cou),
						new UBoolean(dopen, dopenc), new UReal(x, xu), new UReal(y, yu), new UReal(sqre),
						new UReal(ts, tsu), (1-PFP_HOME));
				map.put(ts, ve);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}

	
    public Map<Double, PersonEvent> startSendingPersonReadings() {

    	Map<Double, PersonEvent> map = new HashMap<Double, PersonEvent>();
    	final double PFP_PERSON= 0.01;  
    	
    	try {
			FileReader fr = new FileReader("inputEvents/DatosSinteticosPerson.csv");
			BufferedReader br = new BufferedReader(fr);
			String s = br.readLine(); // discard the first line
			while ((s = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(s, ";");
				st.nextToken();
				// System.out.println(s);
				int pid = Integer.parseInt(st.nextToken());
				double ts = Double.parseDouble(st.nextToken());
				double x = Double.parseDouble(st.nextToken());
				double y = Double.parseDouble(st.nextToken());
				double tsu = Double.parseDouble(st.nextToken());
				double xu = Double.parseDouble(st.nextToken().replace(',', '.'));
				double yu = Double.parseDouble(st.nextToken().replace(',', '.'));

				PersonEvent ve = new PersonEvent(pid, new UReal(x, xu), new UReal(y, yu), new UReal(ts, tsu), (1-PFP_PERSON));
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
