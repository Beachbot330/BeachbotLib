package org.usfirst.frc330.wpilibj;

import edu.wpi.first.wpilibj.Solenoid;

public class BBSolenoid extends Solenoid {

	boolean value;
	public BBSolenoid(int moduleNumber, int channel) {
		super(moduleNumber, channel);
		value = get();
	}

	public BBSolenoid(int channel) {
		super(channel);
		value = get();
	}
	
	@Override
	public void set(boolean on) {
		value = on;
		super.set(on);
	}

	@Override
	public boolean get() {
		return value;
	}
	
	public int getInt() {
		if (value == false)
			return 0;
		else 
			return 1;
	}

}
