package com.cor.cep.event;


/**
 * Immutable Motorbike Event class. The process control system creates these events. The
 * MotorbikeEventHandler picks these up and processes them.
 */
public class RunnerEvent {

    /** Timestamp in miliseconds. */
    private double point;
    private int position;
    private String dorsal;
    private String name;
    private String surname;
    private String category;
    private String otime;
    private String ntime;
    private double ntimeseconds;
    
    
  
    /**
     * Motorbike constructor.
     */
    public RunnerEvent(double point, int position, String dorsal, String name, String surname, String category, String otime, String ntime, double ntimeseconds) {
        this.setPoint(point);
        this.setPosition(position);
        this.setDorsal(dorsal);
        this.setName(name);
        this.setSurname(surname);
        this.setOTime(otime);
        this.setNTime(ntime);
        this.setNTimeSeconds(ntimeseconds);
    }


    @Override
    public String toString() {
        return "RunnerEvent [" + point +"," + dorsal +"," + ntimeseconds + "]";
    }


	public double getPoint() {
		return point;
	}


	public void setPoint(double point) {
		this.point = point;
	}


	public int getPosition() {
		return position;
	}


	public void setPosition(int position) {
		this.position = position;
	}


	public String getDorsal() {
		return dorsal;
	}


	public void setDorsal(String dorsal) {
		this.dorsal = dorsal;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getSurname() {
		return surname;
	}


	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public String getOTime() {
		return otime;
	}


	public void setOTime(String otime) {
		this.otime = otime;
	}


	public String getNTime() {
		return ntime;
	}


	public void setNTime(String ntime) {
		this.ntime = ntime;
	}
	
	public double getNTimeSeconds() {
		return ntimeseconds;
	}


	public void setNTimeSeconds (double ntimeseconds) {
		this.ntimeseconds = ntimeseconds;
	}

}
