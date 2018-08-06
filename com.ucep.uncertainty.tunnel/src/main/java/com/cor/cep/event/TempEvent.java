package com.cor.cep.event;
import com.cor.cep.util.UReal;

public class TempEvent {
	
	private UReal ts;
    private UReal km;
    private UReal value;
    private double prob;
    
    
    public TempEvent(UReal ts, UReal k, UReal v, double prob) {
        this.ts = ts;
    		this.km = k;
        this.value = v;
        this.prob = prob;
    }

    public UReal getTs() {
        return ts;
    }
    
    public UReal getValue() {
        return value;
    }
       
   
    public UReal getKm() {
        return km;
    }
    
    public double getProb() {
        return prob;
    }

    @Override
    public String toString() {
        return "TempEvent [" + ts + "," + km + "," + value + "," + prob +"]";
    }

}
