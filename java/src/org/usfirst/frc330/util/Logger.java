package org.usfirst.frc330.util;

import java.io.File;
import java.util.Arrays;

import edu.wpi.first.wpilibj.DriverStation;


/**
 * Class for logging text files.
 * 
 * Logs to a USB stick if connected, and to the roboRIO flash if USB isn't available.
 * Timestamps all log entries.
 */
public class Logger {
	private static Logger instance = null;
	private LoggerData loggerData;

	
	public void updateDate() {
		loggerData.updateDate(true);
	}

	public enum Severity{
		INFO,ERROR,WARNING,COMMAND,DEBUG
	}
	
	
	
	/**
	 * Constructor
	 * 
	 * @param roboRIOPath location for roboRIO log file (eg /home/lvuser)
	 * @param usbPath location for usb log file (eg /media/sda1)
	 * @param filePrefix prefix of the file (timestamp is appended automatically (eg BB2015_log)
	 */
	private Logger(String roboRIOPath, String usbPath, String filePrefix) {
		loggerData = new LoggerData(roboRIOPath, usbPath, filePrefix, ".txt");
		

		
		File programFile = new File("/home/lvuser/FRCUserProgram.jar");
		
		println("Logger filename: " + loggerData.getFilename(),true);
		println("Program date: " + loggerData.getSDF().format(programFile.lastModified()),true);

	}
	
	/**
	 * Default constructor using /home/lvuser for roboRIOPath, /media/sda1 for usbPath, 
	 * and BB(Year)_Log for file prefix.
	 */
	private Logger() {
		this("/home/lvuser", "/media/sda1", "BB" + BeachbotLibVersion.Version.substring(0, 4) + "_Log");
	}
	
	public static Logger getInstance() {
		if(instance == null) {
		 instance = new Logger();
	  }
	  return instance;
	}
	

	
	/**
	 * Print a string to the log file at info Severity
	 * @param data string to write.
	 * @deprecated
	 */
	@Deprecated
	public void println(String data) {
		println(data, false);
	}
	
	/**
	 * Print a string to the log file and optionally System.out. Prints at severity Info.
	 * @param data string to write
	 * @param printToSystemOut write to System.out if true
	 * @deprecated
	 */
	@Deprecated
	public void println(String data, boolean printToSystemOut) {
		println(data, printToSystemOut, Severity.INFO);
	}
	
	/**
	 * Print a string to the log file and optionally System.out depending on severity
	 * ERROR, WARNING, and DEBUG get printed to System.out.
	 * @param data string to write
	 * @param severity the Severity to print at.
	 */
	public void println(String data, Severity severity) {
		println(data, false, severity);
	}
	
	/**
	 * Print a string to the log file and optionally System.out
	 * @param data string to write
	 * @param printToSystemOut write to System.out if true
	 * @param severity the Severity to print at.
	 */
	public void println(String data, boolean printToSystemOut, Severity severity) {
		data = loggerData.getSDF().format(System.currentTimeMillis()) + ", " + severity + "       ".substring(0, 7-severity.toString().length()) + ", " + data + "\r\n";
		
		if (severity == Severity.ERROR)
			DriverStation.reportError(data, false);
		else if (severity == Severity.WARNING)
			DriverStation.reportWarning(data, false);
		if (printToSystemOut || severity == Severity.DEBUG)
			System.out.println(data);
		
		loggerData.write(data);
	}
	
	/**
	 * Print the stack trace of the exception to the log.
	 * Goes to both the log file and System.out
	 * @param ex exception to print the stacktrace of
	 */
	public void printStackTrace(Throwable ex) {
		println(Arrays.toString(ex.getStackTrace()), true, Severity.ERROR);
	}
}
