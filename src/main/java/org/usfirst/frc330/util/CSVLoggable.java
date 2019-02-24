package org.usfirst.frc330.util;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

/**
 * interface for logging data to CSV files. Optionally defines the data
 * to also be sent to Smart Dashboard. 
 *
 */
public abstract class CSVLoggable {
	
	private ShuffleboardTab tab = null;
	private NetworkTableEntry entry = null;
	private boolean sendToSmartDashboard = false;

	/**
	 * abstract method for returning the data to log
	 * @return the value to log
	 */
	public abstract double get();


	/**
	 * Constructor that also sends data to Smart Dashboard
	 * @param sendToSmartDashboard whether to send data to Smart Dashboard
	 */
	public CSVLoggable(boolean sendToSmartDashboard) {
		this.sendToSmartDashboard = sendToSmartDashboard;
	}

	public CSVLoggable(ShuffleboardTab tab) {
		sendToSmartDashboard = false;
		this.tab = tab;
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

	protected void setNetworkTableEntry(NetworkTableEntry entry) {
		this.entry = entry;
	}

	protected ShuffleboardTab getShuffleboardTab() {
		return tab;
	}

	protected NetworkTableEntry getNetworkTableEntry() {
		return entry;
	}

}
