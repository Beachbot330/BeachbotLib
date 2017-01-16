package org.usfirst.frc330.wpilibj;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class BBDoubleSolenoid extends DoubleSolenoid {
	DoubleSolenoid.Value value;
	public BBDoubleSolenoid(int moduleNumber, int forwardChannel,
			int reverseChannel) {
		super(moduleNumber, forwardChannel, reverseChannel);
		value = get();
	}

	public BBDoubleSolenoid(int forwardChannel, int reverseChannel) {
		super(forwardChannel, reverseChannel);
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
