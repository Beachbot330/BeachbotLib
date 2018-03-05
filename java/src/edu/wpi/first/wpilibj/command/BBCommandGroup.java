package edu.wpi.first.wpilibj.command;

import org.usfirst.frc330.util.Logger;
import org.usfirst.frc330.util.Logger.Severity;

public class BBCommandGroup extends CommandGroup {
    
	boolean m_completed = false;
	
	@Override
	public void start(){
		m_completed = false;
		super.start();
	}
	
	@Override
	void _initialize(){
		super._initialize();
		Logger.getInstance().println(this.getClass().getName() + " initialized", false, Severity.COMMAND);
		m_completed = false;
	}

	@Override
	void _end(){
		super._end();
		Logger.getInstance().println(this.getClass().getName() + " ended", false, Severity.COMMAND);
		m_completed = true;
	}
	
	@Override
	void _interrupted(){
		super._interrupted();
		Logger.getInstance().println(this.getClass().getName() + " interrupted", false, Severity.COMMAND);
	}
    
}
