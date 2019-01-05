/**
 * 
 */
package edu.wpi.first.wpilibj.command;

import org.usfirst.frc330.util.Logger;
import org.usfirst.frc330.util.Logger.Severity;

import edu.wpi.first.wpilibj.command.Command;

/**
 * @author allenpeters
 *
 */
public abstract class BBCommand extends Command {
	
	public BBCommand() {
		super();
	}

	public BBCommand(double timeout) {
		super(timeout);
	}

	public BBCommand(String name, double timeout) {
		super(name, timeout);
	}

	public BBCommand(String name) {
		super(name);
	}

	boolean m_initialized = false;
	
	@Override
	public void start(){
		super.start();
	}
	
	@Override
	void _initialize(){
		super._initialize();
		Logger.getInstance().println(this.getClass().getName() + " initialized", false, Severity.COMMAND);
		m_initialized = true;
	}

	@Override
	void _end(){
		super._end();
		if(this.isTimedOut())
			Logger.getInstance().println(this.getClass().getName() + " timed out", false, Severity.WARNING);
		else
			Logger.getInstance().println(this.getClass().getName() + " ended", false, Severity.COMMAND);
		m_initialized = false;
	}
	
	@Override
	void _interrupted(){
		super._interrupted();
		Logger.getInstance().println(this.getClass().getName() + " interrupted", false, Severity.COMMAND);
		m_initialized = false;
	}
	
	public boolean isInitialized() {
		return m_initialized;
	}

}
