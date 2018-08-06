package com.cor.cep.event;

public class TrafficJamEvent {
	
	private double ts;
	private double km;
	
	public double getTs() {
        return ts;
    }
    
    public TrafficJamEvent(double ts, double k) {
    		this.ts = ts;
        this.km = k;
    }
   
    public double getKm() {
        return km;
    }

    @Override
    public String toString() {
        return "TrafficJamEvent [" + ts + "," + km + "]";
    }

}
