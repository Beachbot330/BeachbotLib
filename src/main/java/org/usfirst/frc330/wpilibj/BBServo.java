package org.usfirst.frc330.wpilibj;

import edu.wpi.first.wpilibj.Servo;

public class BBServo extends Servo {
	
	boolean m_invert = false;

	public BBServo(int channel) {
		super(channel);
	}
	
	public BBServo(int channel, boolean invert) {
		super(channel);
		m_invert = invert;
	}
	
	@Override
	public void setPosition(double pos) {
		if (m_invert)
			pos = 1 - pos;
		super.setPosition(pos);
	}

	@Override
	public void set(double value) {
		if (m_invert)
			value = 1.0 - value;
		super.set(value);
	}

	@Override
	public void setAngle(double degrees) {
		if (m_invert)
			degrees = 180.0 - degrees; //TODO change 180.0 to kMaxServoAngle
		super.setAngle(degrees);
	}


	@Override
	public void setRaw(int value) {
		if (m_invert)
			value = this.getRawBounds().max - value;
		super.setRaw(value);
	}

}
