package com.cor.cep.event;

//import java.util.Date;

//import com.cor.cep.util.UBoolean;
//import com.cor.cep.util.UReal;

/**
 * Immutable Home Event class. The process control system creates these events. The
 * HomeEventHandler picks these up and processes them.
 */
public class HomeEvent {

	private int id;
    private double temp;
    private double co;
    private boolean dopen;
    private double x;
    private double y;
    private double sqre;
    private double ts;
    
  
    public HomeEvent(int id, double temp, double co, boolean dopen, double x, double y, double sqre, double ts) {
    		this.id = id;
        this.temp = temp;
        this.co = co;
        this.dopen = dopen;
        this.x = x;
        this.y = y;
        this.sqre = sqre;
        this.ts = ts;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getTemp() {
		return temp;
	}

	public void setTemp(double temp) {
		this.temp = temp;
	}

	public double getCo() {
		return co;
	}

	public void setCo(double co) {
		this.co = co;
	}

	public boolean getDopen() {
		return dopen;
	}

	public void setDopen(boolean dopen) {
		this.dopen = dopen;
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

	public double getSqre() {
		return sqre;
	}

	public void setSqre(double sqre) {
		this.sqre = sqre;
	}

	public double getTs() {
		return ts;
	}

	public void setTs(double ts) {
		this.ts = ts;
	}

	@Override
	public String toString() {
		return "HomeEvent (id=" + id + ", temp=" + temp + ", co=" + co + ", dopen=" + dopen + ", x=" + x + ", y=" + y
				+ ", sqre=" + sqre + ", ts=" + ts + ")";
	}

}
