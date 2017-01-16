package edu.wpi.first.wpilibj.buttons;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.Trigger;
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
    
    public boolean get() {
        return joy.getRawButton(triggerButton);
    }
}
