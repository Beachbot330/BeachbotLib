package org.usfirst.frc330.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;


/**
 * Class for logging text files.
 * 
 * Logs to a USB stick if connected, and to the roboRIO flash if USB isn't available.
 * Timestamps all log entries.
 */
public class Logger {
	private static Logger instance = null;
	
	private File roboRIOFile, usbFile;
	private BufferedWriter roboRIOWriter, usbWriter;
	private String m_roboRIOPath, m_usbPath, m_filePrefix;
	private GregorianCalendar calendar = new java.util.GregorianCalendar();
	private java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SSS");
	private Date date;
	boolean usbWorking = true;
	private long startTime;
	private double startFPGATime;
	private File summaryRoboRIO = null;
	private File summaryUSB = null;
	private BufferedWriter summaryUSBWriter = null, summaryroboRIOWriter = null;
	
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
		m_roboRIOPath = roboRIOPath;
		m_usbPath = usbPath;
		m_filePrefix = filePrefix;
		
		startTime = System.currentTimeMillis();
		startFPGATime = (long)(Timer.getFPGATimestamp()*1000);
		calendar.setTimeInMillis(startTime);
		date = calendar.getTime();
		
		String filename = m_filePrefix + "_" + sdf.format(date) + ".txt";

		roboRIOFile = new File(m_roboRIOPath + "/" + filename);
		usbFile = new File(m_usbPath + "/" + filename);
		summaryRoboRIO = new File(m_roboRIOPath + "/" + "LogDirectory.txt");
		summaryUSB = new File(m_usbPath + "/" + "LogDirectory.txt");
	
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
		try {
			Files.copy(Paths.get("/home/lvuser/networktables.ini"), Paths.get(m_usbPath + "/" + "networktables" + "_" + sdf.format(date) + ".ini"), StandardCopyOption.REPLACE_EXISTING);
			if (!usbWorking) {
				Files.copy(Paths.get("/home/lvuser/networktables.ini"), Paths.get(m_roboRIOPath + "/" + "networktables" + "_" + sdf.format(date) + ".ini"), StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (usbWorking) {
			try {
				summaryUSBWriter = new BufferedWriter(new FileWriter(summaryUSB,true));
				summaryUSBWriter.newLine();
				summaryUSBWriter.write(filename);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (summaryUSBWriter != null)
					try {
						summaryUSBWriter.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}
		try {
			summaryroboRIOWriter = new BufferedWriter(new FileWriter(summaryRoboRIO,true));
			summaryroboRIOWriter.newLine();
			summaryroboRIOWriter.write(filename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (summaryroboRIOWriter != null) {
				try {
					summaryroboRIOWriter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		File programFile = new File("/home/lvuser/FRCUserProgram.jar");
		
		println("Logger filename: " + m_filePrefix + "_" + sdf.format(date),true);
		println("Program date: " + sdf.format(programFile.lastModified()),true);

	}
	
	/**
	 * Default constructor using /home/lvuser for roboRIOPath, /media/sda1 for usbPath, 
	 * and BB(Year)_Log for file prefix.
	 */
	private Logger() {
		this("/home/lvuser", "/media/sda1", "BB" + BeachbotLibVersion.Version.substring(0, 3) + "_Log");
	}
	
	public static Logger getInstance() {
		if(instance == null) {
		 instance = new Logger();
	  }
	  return instance;
	}
	
	/**
	 * rename the files if the driver station has connected and updated the date. 
	 * Currently this checks whether the date changed from before 2015 to 2015 or later.
	 * This logic may need to change in the future.
	 * Should be called occasionally, for example in disabledInit.
	 */
	public void updateDate() {
		boolean success;
		long currentSystemTime = System.currentTimeMillis();
		double currentFPGATime = Timer.getFPGATimestamp();
		if (Math.abs(currentSystemTime - startTime - (long)((currentFPGATime)*1000) -startFPGATime) > 60*1000 )
		{
			startTime = currentSystemTime - (long)(currentFPGATime*1000);
			calendar.setTimeInMillis(startTime);
			date = calendar.getTime();
			
			if (calendar.get(GregorianCalendar.YEAR) >= 2017) {
				File tempFile = new File(m_roboRIOPath + "/" + m_filePrefix + "_" + sdf.format(date) + ".txt");
				success = roboRIOFile.renameTo(tempFile);
				println("RoboRIO File Renamed: " + success + " " + m_filePrefix + "_" + sdf.format(date) + ".txt",true);
				tempFile = new File(m_usbPath + "/" + m_filePrefix + "_" + sdf.format(date) + ".txt");
				success = usbFile.renameTo(tempFile);
				usbWorking &= success;
				println("USB File Renamed: " + success + " " + m_filePrefix + "_" + sdf.format(date) + ".txt",true);
				File summaryRoboRIO = new File(m_roboRIOPath + "/" + "LogDirectory.txt");
				File summaryUSB = new File(m_usbPath + "/" + "LogDirectory.txt");
				if (usbWorking) {
					try {
						summaryUSBWriter = new BufferedWriter(new FileWriter(summaryUSB, true));
						summaryUSBWriter.write(" renamed to: " + m_filePrefix + "_" + sdf.format(date) + ".txt");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						if (summaryUSBWriter != null)
							try {
								summaryUSBWriter.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					}
				}
				try {
					summaryroboRIOWriter = new BufferedWriter(new FileWriter(summaryRoboRIO, true));
					summaryroboRIOWriter.write(" renamed to: " + m_filePrefix + "_" + sdf.format(date) + ".txt");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					if (summaryroboRIOWriter != null)
						try {
							summaryroboRIOWriter.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
			}
		}
	}
	
	/**
	 * Print a string to the log file at info Severity
	 * @param data string to write.
	 */
	public void println(String data) {
		println(data, false);
	}
	
	/**
	 * Print a string to the log file and optionally System.out. Prints at severity Info.
	 * @param data string to write
	 * @param printToSystemOut write to System.out if true
	 */
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
		data = sdf.format(System.currentTimeMillis()) + ", " + severity + ", "  + data + "\r\n";
		
		if (severity == Severity.ERROR)
			DriverStation.reportError(data, false);
		else if (severity == Severity.WARNING)
			DriverStation.reportWarning(data, false);
		if (printToSystemOut || severity == Severity.DEBUG)
			System.out.println(data);
		
		if (usbWorking) {
	    	try {
				usbWriter.write(data);
				usbWriter.flush();
			} catch (IOException e) {
				usbWorking = false;
				e.printStackTrace();
			}
		}
		else {
			try {
				roboRIOWriter.write(data);
				roboRIOWriter.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
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
