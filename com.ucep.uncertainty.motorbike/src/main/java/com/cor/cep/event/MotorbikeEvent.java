package com.cor.cep.event;

import com.cor.cep.util.UBoolean;
import com.cor.cep.util.UReal;

/**
 * Immutable Motorbike Event class. The process control system creates these events. The
 * MotorbikeEventHandler picks these up and processes them.
 */
public class MotorbikeEvent {

    /** Timestamp in miliseconds. */
    private UReal timestamp;
    private Integer motorbikeId;
    private String location;
    private UReal speed;
    private UReal tirePressure1;
    private UReal tirePressure2;
    private UBoolean seat;
    private double prob;

    
    
  
    /**
     * Motorbike constructor.
     */
    public MotorbikeEvent(UReal timestamp, int motorbikeId, String location, UReal speed, UReal tirePressure1, UReal tirePressure2, UBoolean seat, double prob) {
        this.setTimestamp(timestamp);
        this.setMotorbikeId(motorbikeId);
        this.setLocation(location);
        this.setSpeed(speed);
        this.setTirePressure1(tirePressure1);
        this.setTirePressure2(tirePressure2);
        this.setSeat(seat);
        this.setProb(prob);
    }


    @Override
    public String toString() {
        return "MotorbikeEvent [" + timestamp +"," + motorbikeId +"," + location + "," + speed + "," + tirePressure1 + "," + tirePressure2 + "," + seat + ", prob=" + prob +"]";
    }


	public UBoolean getSeat() {
		return seat;
	}


	public void setSeat(UBoolean seat) {
		this.seat = seat;
	}


	public UReal getTirePressure2() {
		return tirePressure2;
	}


	public void setTirePressure2(UReal tirePressure2) {
		this.tirePressure2 = tirePressure2;
	}


	public UReal getTirePressure1() {
		return tirePressure1;
	}


	public void setTirePressure1(UReal tirePressure1) {
		this.tirePressure1 = tirePressure1;
	}


	public UReal getSpeed() {
		return speed;
	}


	public void setSpeed(UReal speed) {
		this.speed = speed;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public Integer getMotorbikeId() {
		return motorbikeId;
	}


	public void setMotorbikeId(Integer motorbikeId) {
		this.motorbikeId = motorbikeId;
	}


	public UReal getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(UReal timestamp) {
		this.timestamp = timestamp;
	}
	
	public double getProb() {
		return prob;
	}

	public void setProb(double prob) {
		this.prob = prob;
	}

}
