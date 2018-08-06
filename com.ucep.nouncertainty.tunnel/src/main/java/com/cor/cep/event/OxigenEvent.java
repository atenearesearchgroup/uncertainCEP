package com.cor.cep.event;


public class OxigenEvent {

	private double ts;
    private double concentr;
    private double km;
    
    
    public OxigenEvent(double ts, double c, double k) {
    		this.ts = ts;
        this.concentr = c;
        this.km = k;
    }

    public double getTs() {
        return ts;
    }
    
    public double getConcentr() {
        return concentr;
    }
       
   
    public double getKm() {
        return km;
    }

    @Override
    public String toString() {
        return "OxigenEvent [" + ts + "," + concentr + "," + km + "]";
    }

}

