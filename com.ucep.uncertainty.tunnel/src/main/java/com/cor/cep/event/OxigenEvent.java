package com.cor.cep.event;
import com.cor.cep.util.UReal;


public class OxigenEvent {

	private UReal ts;
    private UReal concentr;
    private UReal km;
    private double prob;
    
    
    public OxigenEvent(UReal ts, UReal c, UReal k, double prob) {
        this.ts = ts;
    		this.concentr = c;
        this.km = k;
        this.prob = prob;
    }

    public UReal getTs() {
        return ts;
    }
    
    public UReal getConcentr() {
        return concentr;
    }
       
   
    public UReal getKm() {
        return km;
    }
    
    public double getProb() {
        return prob;
    }

    @Override
    public String toString() {
        return "OxigenEvent [" + ts + ", " + concentr + "," + km + "," + prob + "]";
    }

}

