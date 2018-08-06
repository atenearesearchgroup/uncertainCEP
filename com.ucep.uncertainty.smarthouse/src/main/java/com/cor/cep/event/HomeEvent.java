package com.cor.cep.event;

//import java.util.Date;

import com.cor.cep.util.UBoolean;
import com.cor.cep.util.UReal;

/**
 * Immutable Home Event class. The process control system creates these events. The
 * HomeEventHandler picks these up and processes them.
 */
public class HomeEvent {

	private int id;
    private UReal temp;
    private UReal co;
    private UBoolean dopen;
    private UReal x;
    private UReal y;
    private UReal sqre;
    private UReal ts;
    private double prob;
    
  
    public HomeEvent(int id, UReal temp, UReal co, UBoolean dopen, UReal x, UReal y, UReal sqre, UReal ts, double prob) {
    	this.id = id;
        this.temp = temp;
        this.co = co;
        this.dopen = dopen;
        this.x = x;
        this.y = y;
        this.sqre = sqre;
        this.ts = ts;
        this.prob = prob;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UReal getTemp() {
		return temp;
	}

	public void setTemp(UReal temp) {
		this.temp = temp;
	}

	public UReal getCo() {
		return co;
	}

	public void setCo(UReal co) {
		this.co = co;
	}

	public UBoolean getDopen() {
		return dopen;
	}

	public void setDopen(UBoolean dopen) {
		this.dopen = dopen;
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

	public UReal getSqre() {
		return sqre;
	}

	public void setSqre(UReal sqre) {
		this.sqre = sqre;
	}

	public UReal getTs() {
		return ts;
	}

	public void setTs(UReal ts) {
		this.ts = ts;
	}
	
	public double getProb() {
		return prob;
	}

	public void setProb(double prob) {
		this.prob = prob;
	}

	@Override
	public String toString() {
		return "HomeEvent (id=" + id + ", temp=" + temp + ", co=" + co + ", dopen=" + dopen + ", x=" + x + ", y=" + y
				+ ", sqre=" + sqre + ", ts=" + ts + ", prob=" + prob +")";
	}

}
