package org.usfirst.frc330.util;

import java.util.LinkedHashMap;
import java.util.Map;

import edu.wpi.first.wpilibj.Watchdog;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc330.util.LoggerData;

public class CSVLogger {
	private static CSVLogger instance = null;
	private LoggerData loggerData;

	private double kDefaultTimeout = 0.01;
	
	private LinkedHashMap<String,CSVLoggable> table;
	
	public static final int SDUpdateRate = 10; //send data every SDUpdateRate call (10 = 5hz).
	
	private java.text.SimpleDateFormat sdf_comma = new java.text.SimpleDateFormat("yyyy,MM,dd,HH,mm,ss,SSS");

	boolean usbWorking = true;
	
	private CSVLogger(String roboRIOPath, String usbPath, String filePrefix) {
		table = new LinkedHashMap<String,CSVLoggable>();
		loggerData = new LoggerData(roboRIOPath, usbPath, filePrefix, ".csv");	
	}
	
	private CSVLogger() {
		this("/home/lvuser", "/media/sda1", "BB" + BeachbotLibVersion.Version.substring(0, 4) + "_CSV");
	}
	
	public static CSVLogger getInstance() {
		if(instance == null) {
		 instance = new CSVLogger();
	  }
	  return instance;
	}
	
	public void updateDate() {
		loggerData.updateDate(false);
	}
	
	public void add(String name, CSVLoggable data) {
		if (table.containsKey(name))
			throw new UnsupportedOperationException("CSVLogger key " + name + " already exists");
		if (data.getNetworkTableEntry() != null)
			data.setNetworkTableEntry(data.getShuffleboardTab().add(name, 0.0).getEntry());
		table.put(name, data);
	}

	public void writeHeader() {
		String header = "Year, Month, Day, H, M, S, mS, ";
		
		for (String key : table.keySet()) {
			header = header + key + ", ";
		}
		header = header + "\r\n";
		loggerData.write(header);
		
	}
	
	private int counter = 0;
	CSVLoggable value;
	StringBuilder b = new StringBuilder(1000);
	double test;
	Watchdog wd = new Watchdog(kDefaultTimeout, () -> {});

	public void writeData(boolean flush) {
		wd.reset();
		b.setLength(0);
		
		counter++;
		if (counter == SDUpdateRate)
			counter = 0;
		b.append(sdf_comma.format(System.currentTimeMillis()));
		b.append(", ");

		wd.addEpoch("writeData start");	
		for(Map.Entry<String, CSVLoggable> me : table.entrySet()){
			value = ((CSVLoggable) me.getValue());
			test = value.get();
			b.append(test);
			b.append(", ");
			if (value.isSendToSmartDashboard() && (counter % SDUpdateRate == 0)) {
				SmartDashboard.putNumber((String)me.getKey(), value.get());
			}
			if(value.getNetworkTableEntry() != null && (counter % SDUpdateRate == 0)) {
				value.getNetworkTableEntry().setDouble(value.get());
			}
			wd.addEpoch("writeData " + me.getKey());
		}		
		
		b.append("\r\n");
		
		loggerData.write(b.toString(), flush);
		wd.addEpoch("writeData write");
		wd.disable();
		if (wd.isExpired() && printOnTimeout)
			wd.printEpochs();
	}

	public void writeData() {
		writeData(true);
	}

	/**
	 * Sets how long the logging can take before printing the execution times
	 * @param timeout the timeout in seconds
	 */
	public void setWatchdogTimeout(double timeout) {
		wd.setTimeout(timeout);
	}

	boolean printOnTimeout = false;
	/**
	 * 
	 * @param printOnTimeout true to print execution time if watchdog expires
	 */
	public void setPrintOnTimeout(boolean printOnTimeout) {
		this.printOnTimeout = printOnTimeout;
	}

	public void printEpochs() {
		wd.printEpochs();
	}

	
}
