package com.cor.cep.event;

public class TempEvent {
	
	private double ts;
    private double km;
    private double value;
    
    public double getTs() {
        return ts;
    }
    
    public TempEvent(double ts, double k, double v) {
    		this.ts = ts;
        this.km = k;
        this.value = v;
    }

    
    public double getValue() {
        return value;
    }
       
   
    public double getKm() {
        return km;
    }

    @Override
    public String toString() {
        return "TempEvent [" + ts + "," + km + "," + value + "]";
    }

}
