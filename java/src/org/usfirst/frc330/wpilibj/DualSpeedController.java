package org.usfirst.frc330.wpilibj;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.livewindow.LiveWindowSendable;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

public class DualSpeedController implements SpeedController, LiveWindowSendable {
	SpeedController motor1, motor2;
	
	public DualSpeedController(SpeedController motor1, SpeedController motor2) {
		this(motor1, motor2, false, false);
	}
	
	public DualSpeedController(SpeedController motor1, SpeedController motor2, boolean invert1, boolean invert2) {
		this.motor1 = motor1;
		this.motor2 = motor2;
		this.motor1.setInverted(invert1);
		this.motor2.setInverted(invert2);
	}

	@Override
	public void pidWrite(double output) {
		set(output);
	}

	@Override
	public double get() {
		return motor1.get();
	}

	@Override
	public void set(double speed) {
		motor1.set(speed);
		motor2.set(speed);
	}

	@Override
	public void disable() {
		motor1.disable();
		motor2.disable();
	}
	
    /**
     * Limit motor values to the -1.0 to +1.0 range.
     */
    protected static double limit(double num) {
        if (num > 1.0) {
            return 1.0;
        }
        if (num < -1.0) {
            return -1.0;
        }
        return num;
    }
    
    private ITable m_table;
	private ITableListener m_table_listener;

	@Override
	public void initTable(ITable subtable) {
		m_table = subtable;
		updateTable();
	}

	@Override
	public ITable getTable() {
		return m_table;
	}

	@Override
	public String getSmartDashboardType() {
		return "Speed Controller";
	}

	@Override
	public void updateTable() {
		if (m_table != null) {
			m_table.putNumber("Value", get());
		}
	}

	@Override
	public void startLiveWindowMode() {
		set(0); // Stop for safety
		m_table_listener = new ITableListener() {
			public void valueChanged(ITable itable, String key, Object value, boolean bln) {
				set(((Double) value).doubleValue());
			}
		};
		m_table.addTableListener("Value", m_table_listener, true);		
	}

	@Override
	public void stopLiveWindowMode() {
		set(0);
	}

	@Override
	public void setInverted(boolean isInverted) {
		motor1.setInverted(isInverted);
		motor2.setInverted(isInverted);
	}

	@Override
	public boolean getInverted() {
		return motor1.getInverted();
	}
	
	@Override
	public void stopMotor() {
		motor1.stopMotor();
		motor2.stopMotor();
	}

}
