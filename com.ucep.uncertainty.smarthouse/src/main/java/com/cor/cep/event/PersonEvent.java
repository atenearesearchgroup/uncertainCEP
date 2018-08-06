package com.cor.cep.event;

//import java.util.Date;

import com.cor.cep.util.UReal;

/**
 * Immutable Temperature Event class. The process control system creates these events. The
 * TemperatureEventHandler picks these up and processes them.
 */
public class PersonEvent {

	private int pid;
    private UReal x;
    private UReal y;
    private UReal ts;
    private double prob;
    
    /**
     * Single value constructor.
     * @param value Temperature in Celsius.
     */
    /**
     * Temeratur constructor.
     * @param temperature Temperature in Celsius
     * @param timeOfReading Time of Reading
     */
    public PersonEvent(int pid, UReal x, UReal y, UReal ts, double prob) {
    	this.pid = pid;
        this.x = x;
        this.y = y;
        this.ts = ts;
        this.prob = prob;
    }
    
	public UReal getX() {
		return x;
	}

	public void setX(UReal x) {
		this.x = x;
	}

	public UReal getY() {
		return y;
	}

	public void setY(UReal y) {
		this.y = y;
	}

	public UReal getTs() {
		return ts;
	}

	public void setTs(UReal ts) {
		this.ts = ts;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}
	
	public double getProb() {
		return prob;
	}

	public void setProb(double prob) {
		this.prob = prob;
	}

	@Override
	public String toString() {
		return "PersonEvent [pid=" + pid + ", x=" + x + ", y=" + y + ", ts=" + ts + ", prob=" + prob+ "]";
	}

}
