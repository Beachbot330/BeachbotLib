package org.usfirst.frc330.wpilibj;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

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
/*	
	private ITableListener m_tableListener;
	
	@Override
	public void startLiveWindowMode() {
		m_tableListener = new ITableListener() {
			Double tmpValue;
			public void valueChanged(ITable itable, String key, Object value, boolean bln) {
				tmpValue = (Double)value;
				if (tmpValue != 0)
					set(tmpValue);
			}
		};
		getTable().addTableListener("Value", m_tableListener, true);
	}*/

}
