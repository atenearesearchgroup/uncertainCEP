package com.cor.cep.event;

public class QualityAirEvent {
	/** Timestamp in miliseconds. */
    private Long ts;
    private Integer id;
    private Double pm2_5;
    private Double pm10;
    private Double o3;
    private Double no2;
    private Double so2;
    private Double co;
    
    
    
  
    /**
     * Motorbike constructor.
     */
    public QualityAirEvent(Long ts, int id, Double pm2_5, Double pm10, Double o3, Double no2, Double so2, Double co) {
        this.setTimestamp(ts);
        this.setId(id);
        this.setPM2_5(pm2_5);
        this.setPM10(pm10);
        this.setO3(o3);
        this.setNO2(no2);
        this.setSO2(so2);
        this.setCO(co);
    }


    @Override
    public String toString() {
        return "QualityAirEvent [" + ts +"," + id +"," + pm2_5 + "," + pm10 + "," + o3 + "," + no2 + "," + so2 + "," + co + "]";
    }



	public long getTimestamp() {
		return ts;
	}


	public void setTimestamp(long tsp) {
		this.ts = tsp;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer qaId) {
		this.id = qaId;
	}
	
	
	public Double getPM2_5() {
		return pm2_5;
	}


	public void setPM2_5(Double pm2_5qa) {
		this.pm2_5 = pm2_5qa;
	}


	public Double getPM10() {
		return pm10;
	}


	public void setPM10(Double pm10qa) {
		this.pm10 = pm10qa;
	}

	public Double getO3() {
		return o3;
	}


	public void setO3(Double o3qa) {
		this.o3 = o3qa;
	}

	public Double getNO2() {
		return no2;
	}


	public void setNO2(Double no2qa) {
		this.no2 = no2qa;
	}

	public Double getSO2() {
		return so2;
	}


	public void setSO2(Double so2qa) {
		this.so2 = so2qa;
	}
	
	public Double getCO() {
		return co;
	}


	public void setCO(Double coqa) {
		this.co = coqa;
	}
}



