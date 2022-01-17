package org.usfirst.frc330.wpilibj;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

public class BBSolenoid extends Solenoid {

	boolean value;
	public BBSolenoid(int moduleNumber, PneumaticsModuleType moduleType, int channel) {
		super(moduleNumber, moduleType, channel);
		value = get();
	}

	public BBSolenoid(PneumaticsModuleType moduleType, int channel) {
		super(moduleType, channel);
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
