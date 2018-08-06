package com.cor.cep.event;

import com.cor.cep.util.UReal;

public class TrafficJamEvent {
	private UReal ts;
	private UReal km;
	private double prob;
    
    public TrafficJamEvent(UReal ts, UReal k, double prob) {
        this.ts = ts;
    		this.km = k;
        this.prob = prob;
    }
   
    public UReal getTs() {
        return ts;
    }
    
    public UReal getKm() {
        return km;
    }
    
    public double getProb() {
        return prob;
    }

    @Override
    public String toString() {
        return "TrafficJamEvent [" + ts + "," + km + ", " + prob + "]";
    }

}
