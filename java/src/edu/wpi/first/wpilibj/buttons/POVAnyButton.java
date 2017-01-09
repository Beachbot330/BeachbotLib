package edu.wpi.first.wpilibj.buttons;

import edu.wpi.first.wpilibj.Joystick;

/**
 *
 */
public class POVAnyButton extends Button {
    
	private Joystick joy;
	private int pov;
	
	/**
	 * Create a trigger for choosing one of two commands based on joystick buttons
	 * @param joy Joystick to use
	 * @param pov POV to use
	 */
	public POVAnyButton(Joystick joy, int pov) {
		this.joy = joy;
		this.pov = pov;
	}	
    
    public boolean get() {
        return joy.getPOV(pov) != -1 && joy.getPOVCount()>0;
    }
}
