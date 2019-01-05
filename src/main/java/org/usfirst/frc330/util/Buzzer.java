package org.usfirst.frc330.util;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DigitalOutput;

/**
 * Class for managing multiple users accessing the buzzer
 */
public class Buzzer {
	
	double endTime=0;
	boolean enabled=false;
	private DigitalOutput buzzer;
	/**
	 * Constructor
	 * @param buzzerOutput the DigitalOutput the buzzer is connected to.
	 */
	public Buzzer(DigitalOutput buzzerOutput) {
		buzzer = buzzerOutput;
	}
	
	/**
	 * Starts a timed buzzer for a given duration. Will extend a current
	 * buzzer, but will never shorten one.
	 * 
	 * @param durationInSeconds The duration of the buzzer in seconds
	 */
	public void enable(double durationInSeconds) {
		if (System.currentTimeMillis() + 1000 * durationInSeconds > endTime){
			buzzerOn();
			endTime = System.currentTimeMillis() + 1000 * durationInSeconds;
			enabled = true;
		}
	}
	
	/**
	 * Periodic function that checks if it is time to turn off the buzzer
	 */
	public void update() {
		if (enabled){
			if(System.currentTimeMillis() > endTime){
				buzzerOff();
				enabled = false;
			}
		}
	}
	
	/**
	 * Disables the buzzer
	 */
	public void disable() {
		buzzerOff();
		enabled = false;
	}
	
	public void buzzerOn() {
		buzzer.set(true);
	}
	
	public void buzzerOff() {
		buzzer.set(false);
	}
}
