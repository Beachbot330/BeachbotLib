package edu.wpi.first.wpilibj.buttons;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.buttons.Trigger.ButtonScheduler;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TwoJoystickButton extends Trigger {
    
	private GenericHID joy;
	private int triggerButton, commandSelectButton;
	
	/**
	 * Create a trigger for choosing one of two commands based on joystick buttons
	 * @param joy Joystick to use
	 * @param triggerButton Joystick Button to start a command
	 * @param commandSelectButton Joystick button to choose which command is run
	 */
	public TwoJoystickButton(GenericHID joy, int triggerButton, int commandSelectButton) {
		this.joy = joy;
		this.triggerButton = triggerButton;
		this.commandSelectButton = commandSelectButton;
	}	
	
	/**
	 * Starts one of the given commands when triggerButton becomes pressed.
	 * Determines which command to run based on commandSelectButton.
	 * @param falseCommand command to run when commandSelectButton is false
	 * @param trueCommand command to run when commandSelectButton is true
	 */
	public void whenPressed(final Command falseCommand, final Command trueCommand) {
        new ButtonScheduler() {

            boolean pressedLast = get();

            public void execute() {
                if (get()) {
                    if (!pressedLast) {
                        pressedLast = true;
                        if (joy.getRawButton(commandSelectButton)) {
                        	trueCommand.start(); 
                        }
                        else {
                        	falseCommand.start();
                        }
                    }
                } else {
                    pressedLast = false;
                }
            }
        } .start();
	}
	
	/**
	 * Runs one of the given commands when triggerButton is held.
	 * Determines which command to run based on commandSelectButton.
	 * @param falseCommand command to run when commandSelectButton is false
	 * @param trueCommand command to run when commandSelectButton is true
	 */
	public void whileActive(final Command falseCommand, final Command trueCommand) {
		new ButtonScheduler() {

			private boolean m_pressedLast = get();
			private Command m_selectedCommand = falseCommand;
			private boolean m_selectedCommandState = false;

			@Override
			public void execute() {
				if (get()) {				
					if (m_pressedLast == false) {
						if (joy.getRawButton(commandSelectButton)) {
							m_selectedCommand = trueCommand;
	                    }
	                    else {
	                    	m_selectedCommand = falseCommand;
	                    }
						m_selectedCommand.start();
						m_pressedLast = true;
						m_selectedCommandState = joy.getRawButton(commandSelectButton);
					}
					else {
						if (joy.getRawButton(commandSelectButton) != m_selectedCommandState) {
							m_selectedCommand.cancel();
						}
					}
				} else {
					if (m_pressedLast) {
						m_pressedLast = false;
						m_selectedCommand.cancel();
					}
				}
			}
		}.start();
	}
    
    public boolean get() {
        return joy.getRawButton(triggerButton);
    }
}
