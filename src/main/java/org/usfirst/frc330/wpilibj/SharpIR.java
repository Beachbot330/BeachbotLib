package org.usfirst.frc330.wpilibj;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.SendableBase;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * Class for interfacing with Sharp IR Analog distance Sensors, GP2Y0A51SK0F
 * (4-30cm range) or GP2Y0A41SK0F (2-15cm range). @link setCustomFactors
 * can be used to support other sensors.
 * 
 * @author Joe
 *
 */
public class SharpIR extends SendableBase {
	public enum SharpType {
		/**
		 * Represents the GP2Y0A51SK0F Sharp IR sensor which detects in the range 4-30cm
		 */
		GP2Y0A51SK0F,		/// 4-30cm
		/**
		 * Represents the GP2Y0A41SK0F Sharp IR sensor which detects in the range 2-15cm
		 */
		GP2Y0A41SK0F		/// 2-15cm
	}
	
	private final double GP2Y0A51SK0F_mul = 5.0585 / 2.54;
	private final double GP2Y0A51SK0F_exp = -1.191;
	private final double GP2Y0A41SK0F_mul = 12.354 / 2.54;
	private final double GP2Y0A41SK0F_exp = -1.07;
	
	private double mul, exp;
	private AnalogInput input;

	/**
	 * Construct a Sharp IR sensor with custom scaling factors on the given 
	 * AnalogInput object.
	 * @param sensorType sensorType the PN of the Sharp IR sensor
	 * @param input AnalogInput object to read from
	 */
	public SharpIR(SharpType sensorType, AnalogInput input) {
		if (sensorType == SharpType.GP2Y0A51SK0F) {
			setCustomFactors(GP2Y0A51SK0F_mul, GP2Y0A51SK0F_exp);
		}
		else if (sensorType == SharpType.GP2Y0A41SK0F) {
			setCustomFactors(GP2Y0A41SK0F_mul, GP2Y0A41SK0F_exp);
		}
		else throw new RuntimeException("Unknown SharpType: " + sensorType.toString());
		this.input = input;
		input.setAverageBits(10);
	}
	
	/**
	 * Construct a Sharp IR sensor with custom scaling factors on the given 
	 * analog input port number.
	 * 
	 * @param sensorType the PN of the Sharp IR sensor
	 * @param analogInputPort The analog input port to use (0-7)
	 */
	public SharpIR(SharpType sensorType, int analogInputPort) {
		this(sensorType, new AnalogInput(analogInputPort));
	}
	
	/**
	 * Construct a Sharp IR sensor with custom scaling factors on the given 
	 * analog input port number.
	 * @see setCustomFactor for description of mul and exp
	 * @param mul the multiplier
	 * @param exp the power portion
	 * @param analogInputPort The analog input port to use (0-7)
	 */
	public SharpIR(double mul, double exp, int analogInputPort) {
		this(mul, exp, new AnalogInput(analogInputPort));
	}
	
	/**
	 * Construct a Sharp IR sensor with custom scaling factors on the given 
	 * AnalogInput object.
	 * @see setCustomFactor for description of mul and exp
	 * @param mul the multiplier
	 * @param exp the power portion
	 * @param analogInputPort The analog input port to use (0-7)
	 */
	public SharpIR(double mul, double exp, AnalogInput input) {
		setCustomFactors(mul, exp);
		this.input = input;
		input.setAverageBits(10);
	}
	
	/**
	 * @deprecated use getDistance instead
	 * @return the voltage read by the sensor
	 */
	public double getVoltage() {
		return input.getAverageVoltage();
	}
	
	/**
	 * Gets the distance returned by the sensor. The units will depend on the scaling
	 * multiplier. If used with one of the default SharpTypes, the distance will be in
	 * inches.
	 * @return distance
	 */
	public double getDistance() { 
		return Math.pow(input.getAverageVoltage(), exp) * mul;  
	}

	@Override
	public void initSendable(SendableBuilder builder) {
	  builder.setSmartDashboardType("Ultrasonic");
	  builder.addDoubleProperty("Value", this::getDistance, null);
	}
	
	/**
	 * Sets a custom inverse function for converting the analog voltage returned 
	 * by the sensor to distance. It is in the form of mul * voltage ^ exp.
	 * This can be calculated using the spreadsheet in the 2018 beachcloud 
	 * software folder. 
	 * http://beachcloud.team330.org/index.php/apps/files/ajax/download.php?dir=%2F2018%20Software%20(shared)&files=SharpIRSensors.xlsx
	 * 
	 * @param mul the multiplier
	 * @param exp the power portion
	 */
	public void setCustomFactors(double mul, double exp) {
		this.mul = mul;
		this.exp = exp;
	}
	
	public double getNoise() {
		double distance = getDistance();
		if (distance < 13) {
			return 1.0;
		}
		else return distance * 0.1;
	}
	
}
