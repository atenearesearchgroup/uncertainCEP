package com.cor.cep.event;

import java.util.Date;
import com.cor.cep.util.UReal;

/**
 * Immutable Temperature Event class. The process control system creates these events. The
 * TemperatureEventHandler picks these up and processes them.
 */
public class TemperatureEvent {

    /** Temperature in Celcius. */
    private UReal temperature;
    
    /** Time temerature reading was taken. */
    private Date timeOfReading;
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
    public TemperatureEvent(UReal temperature, Date timeOfReading, double prob) {
        this.temperature = temperature;
        this.timeOfReading = timeOfReading;
        this.prob = prob;
    }

    /**
     * Get the Temperature.
     * @return Temperature in Celsius
     */
    public UReal getTemperature() {
        return temperature;
    }
       
    /**
     * Get time Temperature reading was taken.
     * @return Time of Reading
     */
    public Date getTimeOfReading() {
        return timeOfReading;
    }
    
    public double getProb() {
        return prob;
    }

    @Override
    public String toString() {
        return "TemperatureEvent [" + temperature + "C " + prob + "]";
    }

}
