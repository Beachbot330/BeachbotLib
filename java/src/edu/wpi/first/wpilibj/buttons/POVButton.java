package edu.wpi.first.wpilibj.buttons;

import edu.wpi.first.wpilibj.Joystick;

/**
 *
 */
public class POVButton extends Button {
    
	private Joystick joy;
	private int pov;
	private int dir, alt1, alt2;
	private int current;
	/**
	 * Create a trigger for running a command from a POV direction and up to two alternates
	 * @param joy Joystick to use
	 * @param pov POV to use
	 */
	public POVButton(Joystick joy, int pov, int dir) {
		this.joy = joy;
		this.pov = pov;
		this.dir = dir;
		this.alt1 = dir;
		this.alt2 = dir;
	}
	
	public POVButton(Joystick joy, int pov, int dir, int alt1, int alt2) {
		this.joy = joy;
		this.pov = pov;
		this.dir = dir;
		this.alt1 = alt1;
		this.alt2 = alt2;
	}	
    
    public boolean get() {
    	current = joy.getPOV(pov);
        return ((current == dir) || (current == alt1) || (current == alt2)) && joy.getPOVCount()>0;
    }
}
