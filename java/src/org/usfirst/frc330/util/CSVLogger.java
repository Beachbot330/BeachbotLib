package org.usfirst.frc330.util;

import java.util.LinkedHashMap;
import java.util.Map;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc330.util.LoggerData;

public class CSVLogger {
	private static CSVLogger instance = null;
	private LoggerData loggerData;
	
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
	
//	String data;
	private int counter = 0;
	CSVLoggable value;
	StringBuilder b = new StringBuilder(1000);
	double test;
	public void writeData() {
//		double executeTime=0;
		b.setLength(0);
		
		counter++;
		if (counter == SDUpdateRate)
			counter = 0;
		b.append(sdf_comma.format(System.currentTimeMillis()));
		b.append(", ");

//		executeTime = Timer.getFPGATimestamp();		
		for(Map.Entry<String, CSVLoggable> me : table.entrySet()){

			value = ((CSVLoggable) me.getValue());
			test = value.get();
			b.append(test);
			b.append(", ");
			if (value.isSendToSmartDashboard() && (counter % SDUpdateRate == 0)) {
				SmartDashboard.putNumber((String)me.getKey(), value.get());
			}
		}
//		executeTime = Timer.getFPGATimestamp() - executeTime;
//		System.out.println("Log write time: " + executeTime);		

		
		b.append("\r\n");
		
		loggerData.write(b.toString());
	}
	
}
