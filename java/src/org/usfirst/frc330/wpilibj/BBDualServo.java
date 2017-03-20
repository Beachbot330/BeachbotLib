package org.usfirst.frc330.wpilibj;

import edu.wpi.first.wpilibj.PWM.PeriodMultiplier;
import edu.wpi.first.wpilibj.PWMConfigDataResult;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.livewindow.LiveWindowSendable;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

public class BBDualServo implements LiveWindowSendable {
	Servo servo1, servo2;

	public void setPosition(double pos) {
		servo1.setPosition(pos);
		servo2.setPosition(pos);
	}

	public void set(double value) {
		servo1.set(value);
		servo2.set(value);
	}

	public double get() {
		return servo1.get();
	}

	public void setAngle(double degrees) {
		servo1.setAngle(degrees);
		servo2.setAngle(degrees);
	}


	public void enableDeadbandElimination(boolean eliminateDeadband) {
		servo1.enableDeadbandElimination(eliminateDeadband);
		servo2.enableDeadbandElimination(eliminateDeadband);
	}

	public double getAngle() {
		return servo1.getAngle();
	}

	public void setBounds(double max, double deadbandMax, double center, double deadbandMin, double min) {
		servo1.setBounds(max, deadbandMax, center, deadbandMin, min);
		servo2.setBounds(max, deadbandMax, center, deadbandMin, min);
	}

	public PWMConfigDataResult getRawBounds() {
		return servo1.getRawBounds();
	}


	public double getPosition() {
		return servo1.getPosition();
	}

	public void setSpeed(double speed) {
		servo1.setSpeed(speed);
		servo2.setSpeed(speed);
	}

	public double getSpeed() {
		return servo1.getSpeed();
	}

	public void setRaw(int value) {
		servo1.setRaw(value);
		servo2.setRaw(value);
	}

	public int getRaw() {
		return servo1.getRaw();
	}

	public void setDisabled() {
		servo1.setDisabled();
		servo2.setDisabled();
	}

	public void setPeriodMultiplier(PeriodMultiplier mult) {
		servo1.setPeriodMultiplier(mult);
		servo2.setPeriodMultiplier(mult);
	}

	public BBDualServo(Servo servo1, Servo servo2) {
		this.servo1 = servo1;
		this.servo2 = servo2;
	}

	/*
	 * Live Window code, only does anything if live window is activated.
	 */
	public String getSmartDashboardType() {
		return "Servo";
	}

	private ITable m_table;
	private ITableListener m_tableListener;

	public void initTable(ITable subtable) {
		m_table = subtable;
		updateTable();
	}

	public void updateTable() {
		if (m_table != null) {
			m_table.putNumber("Value", get());
		}
	}

	public void startLiveWindowMode() {
		m_tableListener = new ITableListener() {
			Double tmpValue;
			public void valueChanged(ITable itable, String key, Object value, boolean bln) {
				tmpValue = (Double)value;
				if (tmpValue != 0)
					set(tmpValue);
			}
		};
		m_table.addTableListener("Value", m_tableListener, true);
	}

	public void stopLiveWindowMode() {
		// TODO: Broken, should only remove the listener from "Value" only.
		m_table.removeTableListener(m_tableListener);
	}

	@Override
	public ITable getTable() {
		return m_table;
	}



}
