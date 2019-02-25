package edu.wpi.first.wpilibj.buttons;

import edu.wpi.first.wpilibj.Joystick;

/**
 *
 */
public class BBPOVButton extends Button {
    
	private Joystick joy;
	private int pov;
	private int dir, alt1, alt2;
	private int current;
	/**
	 * Create a trigger for running a command from a POV direction and up to two alternates.
	 * The POV angles start at 0 in the up direction, and increase clockwise (eg right is 90,
     * upper-left is 315).
	 * @param joy Joystick to use
	 * @param pov POV to use
	 * @param dir the pov direction to trigger with
	 */
	public BBPOVButton(Joystick joy, int pov, int dir) {
		this.joy = joy;
		this.pov = pov;
		this.dir = dir;
		this.alt1 = dir;
		this.alt2 = dir;
	}
	
	public BBPOVButton(Joystick joy, int pov, int dir, int alt1, int alt2) {
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
