package org.usfirst.frc330.util;

/**
 * interface for logging data to CSV files. Optionally defines the data
 * to also be sent to Smart Dashboard. 
 *
 */
public abstract class CSVLoggable {
	public abstract double get();
	
	private boolean sendToSmartDashboard;
	/**
	 * Constructor that also sends data to Smart Dashboard
	 * @param sendToSmartDashboard whether to send data to Smart Dashboard
	 */
	public CSVLoggable(boolean sendToSmartDashboard) {
		this.sendToSmartDashboard = sendToSmartDashboard;
	}

	/**
	 * Default constructor does not send data to Smart Dashoard.
	 */
	public CSVLoggable() {
		this(false);
	}

	public boolean isSendToSmartDashboard() {
		return sendToSmartDashboard;
	}

}
