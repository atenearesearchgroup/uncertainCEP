package com.cor.cep.event;

//import java.util.Date;

//import com.cor.cep.util.UReal;

/**
 * Immutable Temperature Event class. The process control system creates these events. The
 * TemperatureEventHandler picks these up and processes them.
 */
public class PersonEvent {

	private int pid;
    private double x;
    private double y;
    private double ts;
    
    /**
     * Single value constructor.
     * @param value Temperature in Celsius.
     */
    /**
     * Temeratur constructor.
     * @param temperature Temperature in Celsius
     * @param timeOfReading Time of Reading
     */
    public PersonEvent(int pid, double x, double y, double ts) {
    	this.pid = pid;
        this.x = x;
        this.y = y;
        this.ts = ts;
    }
    
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getTs() {
		return ts;
	}

	public void setTs(double ts) {
		this.ts = ts;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	@Override
	public String toString() {
		return "PersonEvent [pid=" + pid + ", x=" + x + ", y=" + y + ", ts=" + ts + "]";
	}

}
