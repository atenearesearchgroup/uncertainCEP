package com.cor.cep.event;
import com.cor.cep.util.UReal;

public class QualityAirEvent {
	/** Timestamp in miliseconds. */
    private UReal ts;
    private Integer id;
    private UReal pm2_5;
    private UReal pm10;
    private UReal o3;
    private UReal no2;
    private UReal so2;
    private UReal co;
    private double prob;
    
    
    
  
    /**
     * Motorbike constructor.
     */
    public QualityAirEvent(UReal ts, int id, UReal pm2_5, UReal pm10, UReal o3, UReal no2, UReal so2, UReal co, double prob) {
        this.setTimestamp(ts);
        this.setId(id);
        this.setPM2_5(pm2_5);
        this.setPM10(pm10);
        this.setO3(o3);
        this.setNO2(no2);
        this.setSO2(so2);
        this.setCO(co);
        this.setProb(prob);
    }


    @Override
    public String toString() {
        return "QualityAirEvent [" + ts +"," + id +"," + pm2_5 + "," + pm10 + "," + o3 + "," + no2 + "," + so2 + "," + co + "," + prob +"]";
    }


    public double getProb() {
    		return prob;
    }
    
    public void setProb(double prob) {
		this.prob=prob;
}
    

	public UReal getTimestamp() {
		return ts;
	}


	public void setTimestamp(UReal tsp) {
		this.ts = tsp;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer qaId) {
		this.id = qaId;
	}
	
	
	public UReal getPM2_5() {
		return pm2_5;
	}


	public void setPM2_5(UReal pm2_5qa) {
		this.pm2_5 = pm2_5qa;
	}


	public UReal getPM10() {
		return pm10;
	}


	public void setPM10(UReal pm10qa) {
		this.pm10 = pm10qa;
	}

	public UReal getO3() {
		return o3;
	}


	public void setO3(UReal o3qa) {
		this.o3 = o3qa;
	}

	public UReal getNO2() {
		return no2;
	}


	public void setNO2(UReal no2qa) {
		this.no2 = no2qa;
	}

	public UReal getSO2() {
		return so2;
	}


	public void setSO2(UReal so2qa) {
		this.so2 = so2qa;
	}
	
	public UReal getCO() {
		return co;
	}


	public void setCO(UReal coqa) {
		this.co = coqa;
	}
}



