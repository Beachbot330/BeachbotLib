package org.usfirst.frc330.wpilibj;

import edu.wpi.first.wpilibj.SpeedController;

@Deprecated
public class DualSpeedController extends SpeedControllerGroup {
	
	public DualSpeedController(SpeedController motor1, SpeedController motor2) {
		super(motor1, motor2);
	}
	
	public DualSpeedController(SpeedController motor1, SpeedController motor2, boolean invert1, boolean invert2) {
		super(motor1,motor2);
		motor1.setInverted(invert1);
		motor2.setInverted(invert2);
	}
}
