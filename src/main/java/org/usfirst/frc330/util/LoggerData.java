package org.usfirst.frc330.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

public class LoggerData {
	
	private File roboRIOFile, usbFile;
	private BufferedWriter roboRIOWriter, usbWriter;
	private String m_roboRIOPath, m_usbPath, m_filePrefix, m_fileExt;
	private GregorianCalendar calendar = new java.util.GregorianCalendar();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SSS");
	private Date date;
	private static boolean usbWorking = true;
	private long startTime = 0;
	private double startFPGATime = 0;
	private File summaryRoboRIO = null;
	private File summaryUSB = null;
	private BufferedWriter summaryUSBWriter = null, summaryroboRIOWriter = null;
	private String filename;
	private String prefix;
	
	public LoggerData(String roboRIOPath, String usbPath, String filePrefix, String fileExtension) {
		m_roboRIOPath = roboRIOPath;
		m_usbPath = usbPath;
		m_filePrefix = filePrefix;
		m_fileExt = fileExtension;
		
		if (startTime == 0) {
			startTime = System.currentTimeMillis();
			startFPGATime = (long)(Timer.getFPGATimestamp()*1000);
			calendar.setTimeInMillis(startTime);
			date = calendar.getTime();
		}
		
		prefix = getMatchPrefix();
		
		filename = m_filePrefix + "_"+ getMatchPrefix() + 
				"_" + sdf.format(date) + m_fileExt;

		roboRIOFile = new File(m_roboRIOPath + "/" + filename);
		usbFile = new File(m_usbPath + "/" + filename);
		summaryRoboRIO = new File(m_roboRIOPath + "/" + "LogDirectory.txt");
		summaryUSB = new File(m_usbPath + "/" + "LogDirectory.txt");
	
		try {
			usbWriter = new BufferedWriter(new FileWriter(usbFile));
		} catch (IOException e) {
			usbWorking = false;
			DriverStation.reportError("USB not working", false);
			e.printStackTrace();
		}
		try {
			roboRIOWriter = new BufferedWriter(new FileWriter(roboRIOFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (Files.exists(Paths.get("/home/lvuser/networktables.ini" ))) {
			try {
				Files.copy(Paths.get("/home/lvuser/networktables.ini"), Paths.get(m_usbPath + "/" + "networktables" + "_" + sdf.format(date) + ".ini"), StandardCopyOption.REPLACE_EXISTING);
				if (!usbWorking) {
					Files.copy(Paths.get("/home/lvuser/networktables.ini"), Paths.get(m_roboRIOPath + "/" + "networktables" + "_" + sdf.format(date) + ".ini"), StandardCopyOption.REPLACE_EXISTING);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if (usbWorking) {
			try {
				summaryUSBWriter = new BufferedWriter(new FileWriter(summaryUSB,true));
				summaryUSBWriter.write(filename + "\r\n");
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
			summaryroboRIOWriter.write(filename+"\r\n");
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
	}
	
	/**
	 * rename the files if the driver station has connected and updated the date. 
	 * Currently this checks whether the date changed from before 2015 to 2015 or later.
	 * This logic may need to change in the future.
	 * Should be called occasionally, for example in disabledInit.
	 * 
	 * @param writeToLog if true, write text to current log file indicating it was renamed. Set to false for csv.
	 */
	public void updateDate(boolean writeToLog) {
		boolean success;
		long currentSystemTime = System.currentTimeMillis();
		double currentFPGATime = Timer.getFPGATimestamp();
		if (Math.abs(currentSystemTime - startTime - (long)((currentFPGATime)*1000) -startFPGATime) > 60*1000 || !prefix.matches(getMatchPrefix()) )
		{
			Date oldDate = date;
			startTime = currentSystemTime - (long)(currentFPGATime*1000);
			calendar.setTimeInMillis(startTime);
			date = calendar.getTime();
			prefix = getMatchPrefix();
			
			if (calendar.get(GregorianCalendar.YEAR) >= 2018) {
				File tempFile = new File(m_roboRIOPath + "/" + m_filePrefix + "_" + getMatchPrefix() + "_" + sdf.format(date) + m_fileExt);
				success = roboRIOFile.renameTo(tempFile);
				if (writeToLog) 
					write("RoboRIO File Renamed: " + success + " " + m_filePrefix + getMatchPrefix() + "_" + "_" + sdf.format(date) + m_fileExt + "\r\n");
				if (success)
					roboRIOFile = tempFile;
				tempFile = new File(m_usbPath + "/" + m_filePrefix + "_" + getMatchPrefix() + "_" + sdf.format(date) + m_fileExt);
				success = usbFile.renameTo(tempFile);
				usbWorking &= success;
				if (writeToLog) {
					write("USB File Renamed: " + success + " " + m_filePrefix + "_" + getMatchPrefix() + "_" + sdf.format(date) + m_fileExt + "\r\n");
				}
				if (success) 
					usbFile = tempFile;
				File summaryRoboRIO = new File(m_roboRIOPath + "/" + "LogDirectory.txt");
				File summaryUSB = new File(m_usbPath + "/" + "LogDirectory.txt");
				if (usbWorking) {
					try {
						summaryUSBWriter = new BufferedWriter(new FileWriter(summaryUSB, true));
						summaryUSBWriter.write(m_filePrefix + "_" + getMatchPrefix() + "_" + sdf.format(oldDate) + m_fileExt + " renamed to: " + m_filePrefix + "_" + getMatchPrefix() + "_" + sdf.format(date) + m_fileExt + "\r\n");
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
					summaryroboRIOWriter.write(m_filePrefix + "_" + getMatchPrefix() + "_" + sdf.format(oldDate) + m_fileExt + " renamed to: " + m_filePrefix + "_" + getMatchPrefix() + "_" + sdf.format(date) + m_fileExt + "\r\n");
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
	
	public void write(String data, boolean flush) {

		if (usbWorking) {
			try {
				usbWriter.write(data);
				if (flush)
					usbWriter.flush();
			} catch (IOException e) {
				usbWorking = false;
				e.printStackTrace();
			}
		}
		else {
			try {
				roboRIOWriter.write(data);
				if (flush)
					roboRIOWriter.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
	}

	public void write(String data) {
		write(data, true);
	}
	
	public String getFilename() {
		return filename;
	}
	
	public SimpleDateFormat getSDF() {
		return sdf;
	}
	
	public String getMatchPrefix() {
		return DriverStation.getInstance().getEventName() + "_" + 
				DriverStation.getInstance().getMatchType() + 
				DriverStation.getInstance().getMatchNumber();
	}

}
