package org.usfirst.frc330.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.Map;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CSVLogger {
	private static CSVLogger instance = null;
	
	private LinkedHashMap<String,CSVLoggable> table;
	
	public static final int SDUpdateRate = 10; //send data every SDUpdateRate call (10 = 5hz).
	
	private File roboRIOFile, usbFile;
	private BufferedWriter roboRIOWriter, usbWriter;
	private String m_roboRIOPath, m_usbPath, m_filePrefix;
	private GregorianCalendar calendar = new java.util.GregorianCalendar();
	private java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SSS");
	private java.text.SimpleDateFormat sdf_comma = new java.text.SimpleDateFormat("yyyy,MM,dd,HH,mm,ss,SSS");
	private Date date;
	boolean usbWorking = true;
	
	private CSVLogger(String roboRIOPath, String usbPath, String filePrefix) {
		table = new LinkedHashMap<String,CSVLoggable>();
		m_roboRIOPath = roboRIOPath;
		m_usbPath = usbPath;
		m_filePrefix = filePrefix;
		
		
    	calendar.setTimeInMillis(System.currentTimeMillis());
    	date = calendar.getTime();

		roboRIOFile = new File(m_roboRIOPath + "/" + m_filePrefix + "_" + sdf.format(date) + ".csv");
		usbFile = new File(m_usbPath + "/" + m_filePrefix + "_" + sdf.format(date) + ".csv");
		System.out.println("CSV Date: " + sdf.format(date));
		
		try {
			usbWriter = new BufferedWriter(new FileWriter(usbFile));
		} catch (IOException e) {
			usbWorking = false;
			e.printStackTrace();
		}
		try {
			roboRIOWriter = new BufferedWriter(new FileWriter(roboRIOFile));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	private CSVLogger() {
		this("/home/lvuser", "/media/sda1", "BB2017_CSV");
	}
	
	public static CSVLogger getInstance() {
		if(instance == null) {
		 instance = new CSVLogger();
	  }
	  return instance;
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
		
		if (usbWorking) {
	    	try {
				usbWriter.write(header);
			} catch (IOException e) {
				usbWorking = false;
				e.printStackTrace();
			}
		}
		else {
			try {
				roboRIOWriter.write(header);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
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
		
		if (usbWorking) {
	    	try {
				usbWriter.write(b.toString());

			} catch (IOException e) {
				usbWorking = false;
				e.printStackTrace();
			}
		}
		else {
			try {
				roboRIOWriter.write(b.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}

	}
	
}
