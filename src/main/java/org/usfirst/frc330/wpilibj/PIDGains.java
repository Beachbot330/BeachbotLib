package org.usfirst.frc330.wpilibj;

public class PIDGains {
	private double P, I, D, F, maxOutput, maxOutputStep, minStartOutput;
	private String name;
	
	/**
	 *  Construct a PIDGains object to use with DriveCommands.
	 *   
	 *   maxOutput, maxOutputStep, and minStartOutput are used like the drawing below.  
	 * <pre>
	 *   _ maxOutput
	 *  /
	 * /  maxOutputStep
	 * |  minStartOutput
	 * </pre>
	 * 
	 * 
	 * @param p the proportional coefficient
	 * @param i the integral coefficient
	 * @param d the derivative coefficient
	 * @param f the feed forward coefficient
	 * @param maxOutput the largest value to output (0 to 1.0)
	 * @param maxOutputStep the value to ramp up each time step (max 1.0)
	 * @param minStartOutput The value to start ramp up from
	 * @param name The name to use to use when logging
	 */
	public PIDGains(double p, double i, double d, double f, double maxOutput, double maxOutputStep, double minStartOutput, String name) {
		P = p;
		I = i;
		D = d;
		F = f;
		this.maxOutput = maxOutput;
		this.maxOutputStep = maxOutputStep;
		this.name = name;
		this.minStartOutput = minStartOutput;
	}

	public double getP() {
		return P;
	}
	public double getI() {
		return I;
	}
	public double getD() {
		return D;
	}
	public double getF() {
		return F;
	}
	public double getMaxOutput() {
		return maxOutput;
	}
	public double getMaxOutputStep() {
		return maxOutputStep;
	}
	public double getMinStartOutput() {
		return minStartOutput;
	}
	public String getName() {
		return name;
	}
}