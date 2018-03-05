package edu.wpi.first.wpilibj;

import org.usfirst.frc330.util.Logger;

import edu.wpi.first.wpilibj.hal.FRCNetComm.tInstances;
import edu.wpi.first.wpilibj.hal.FRCNetComm.tResourceType;
import edu.wpi.first.wpilibj.hal.HAL;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * @deprecated use TimedRobot instead
 *
 */
public class BBIterativeRobot extends IterativeRobot {
	
	protected boolean m_disconnectedInitialized;
    protected boolean m_disabledInitialized;
    protected boolean m_autonomousInitialized;
    protected boolean m_teleopInitialized;
    protected boolean m_testInitialized;
	
	public BBIterativeRobot() {
		super();
		m_disconnectedInitialized = false;
        m_disabledInitialized = false;
        m_autonomousInitialized = false;
        m_teleopInitialized = false;
        m_testInitialized = false;
	}

	@Override
	public void startCompetition() {
		HAL.report(tResourceType.kResourceType_Framework,
				tInstances.kFramework_Iterative);

		robotInit();

		// Tell the DS that the robot is ready to be enabled
		HAL.observeUserProgramStarting();

		// loop forever, calling the appropriate mode-dependent function
		LiveWindow.setEnabled(false);
		while (true) {
			// Call the appropriate function depending upon the current robot mode
			m_ds.waitForData(0.05);
			if (isDisabled()) {
				// call DisabledInit() if we are now just entering disabled mode from
				// either a different mode or from power-on
				if (!m_disabledInitialized) {
					LiveWindow.setEnabled(false);
					disabledInit();
					m_disabledInitialized = true;
					// reset the initialization flags for the other modes
					m_autonomousInitialized = false;
					m_teleopInitialized = false;
					m_testInitialized = false;
					m_disconnectedInitialized = false;
				}

				HAL.observeUserProgramDisabled();
				disabledPeriodic();
			} else if (isTest()) {
				// call TestInit() if we are now just entering test mode from either
				// a different mode or from power-on
				if (!m_testInitialized) {
					LiveWindow.setEnabled(true);
					testInit();
					m_testInitialized = true;
					m_autonomousInitialized = false;
					m_teleopInitialized = false;
					m_disabledInitialized = false;
					m_disconnectedInitialized = false;
				}
				HAL.observeUserProgramTest();
				testPeriodic();

			} else if (isAutonomous()) {
				// call Autonomous_Init() if this is the first time
				// we've entered autonomous_mode
				if (!m_autonomousInitialized) {
					LiveWindow.setEnabled(false);
					// KBS NOTE: old code reset all PWMs and relays to "safe values"
					// whenever entering autonomous mode, before calling
					// "Autonomous_Init()"
					autonomousInit();
					m_autonomousInitialized = true;
					m_testInitialized = false;
					m_teleopInitialized = false;
					m_disabledInitialized = false;
					m_disconnectedInitialized = false;
				}
				HAL.observeUserProgramAutonomous();
				autonomousPeriodic();
			} else if (isEnabled()){
				// call Teleop_Init() if this is the first time
				// we've entered teleop_mode
				if (!m_teleopInitialized) {
					LiveWindow.setEnabled(false);
					teleopInit();
					m_teleopInitialized = true;
					m_testInitialized = false;
					m_autonomousInitialized = false;
					m_disabledInitialized = false;
					m_disconnectedInitialized = false;
				}
				HAL.observeUserProgramTeleop();
				teleopPeriodic();
			}
			else {
				// call Disconnected_Init() if this is the first time
				// we've entered disconnected_mode
				if (!m_disconnectedInitialized) {
					LiveWindow.setEnabled(false);
					disconnectedInit();
					m_teleopInitialized = false;
					m_testInitialized = false;
					m_autonomousInitialized = false;
					m_disabledInitialized = false;
					m_disconnectedInitialized = true;
				}
				disconnectedPeriodic();
			}			
		}
	}

	@Override
	public boolean isDisabled() {
	    return !m_ds.isEnabled() && m_ds.isDSAttached();
	}
	
	public boolean isDisconnected() {
		return !m_ds.isDSAttached();
	}
	
	  /**
	   * Initialization code for disconnected mode should go here.
	   *
	   * Users should override this method for initialization code which will be
	   * called each time the robot enters disconnected mode.
	   */
	  public void disconnectedInit() {
	    System.out.println("Default BBIterativeRobot.disconnectedInit() method... Overload me!");
	  }
	  
	  private boolean dipFirstRun = true;

	  /**
	   * Periodic code for disconnected mode should go here.
	   *
	   * Users should override this method for code which will be called
	   * periodically at a regular rate while the robot is in disconnected mode.
	   */
	  public void disconnectedPeriodic() {
	    if (dipFirstRun) {
	      System.out.println("Default BBIterativeRobot.disconnectedPeriodic() method... Overload me!");
	      dipFirstRun = false;
	    }
	  }
	
}
