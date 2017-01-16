package org.usfirst.frc330.wpilibj;

public class PIDGains {
	private double P, I, D, F, maxOutput, maxOutputStep;
	private String name;
	
	public PIDGains(double p, double i, double d, double f, double maxOutput, double maxOutputStep, String name) {
		P = p;
		I = i;
		D = d;
		F = f;
		this.maxOutput = maxOutput;
		this.maxOutputStep = maxOutputStep;
		this.name = name;
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
	
	public String getName() {
		return name;
	}
}