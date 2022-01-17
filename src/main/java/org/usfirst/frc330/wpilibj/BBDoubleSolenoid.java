package org.usfirst.frc330.wpilibj;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

public class BBDoubleSolenoid extends DoubleSolenoid {
	DoubleSolenoid.Value value = Value.kOff;
	public BBDoubleSolenoid(int moduleNumber, PneumaticsModuleType moduleType, int forwardChannel,
			int reverseChannel) {
		super(moduleNumber, moduleType, forwardChannel, reverseChannel);
		value = get();
	}

	public BBDoubleSolenoid(PneumaticsModuleType moduleType, int forwardChannel, int reverseChannel) {
		super(moduleType, forwardChannel, reverseChannel);
		value = get();
	}

	@Override
	public void set(Value value) {
		this.value = value;
		super.set(value);
	}

	@Override
	public Value get() {
		return value;
	}
	
	public int getInt() {
		try {
			return value.ordinal(); 
		} catch (NullPointerException ex) {
			return -1;
		}
	}

}
