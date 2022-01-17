package org.usfirst.frc330.wpilibj;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.PIDOutput;

public class SpeedControllerGroup implements SpeedController, PIDOutput {

    private List<SpeedController> motors = new ArrayList<>();
    private List<Boolean> inverts = new ArrayList<>();
    private double power = 0;
    private boolean reversed = false;

    /**
     * Creates a MotorGroup.
     */
    public SpeedControllerGroup() {
        super();
    }

    /**
     * Creates a MotorGroup that consists of motorArray's Motors.
     *
     * @param motorArray The Motors to be a part of this MotorGroup.
     */
    public SpeedControllerGroup(SpeedController... motorArray) {
        super();
        for (SpeedController m : motorArray) {
            motors.add(m);
            inverts.add(false);
        }
    }

    /**
     * Creates a MotorGroup full of Motors of class "type".
     *
     * @param type The class that the Motor objects will be.
     * @param ids  The IDs of all the motors.
     * @param <T>  Any class that extends Motor.
     */
    public <T extends SpeedController> SpeedControllerGroup(Class<T> type, Integer... ids) {
        super();
        addMotors(type, ids);
    }

    /**
     * Adds a Motor to this MotorGroup.
     *
     * @param m The Motor to add.
     * @return This MotorGroup.
     */
    public SpeedControllerGroup addMotor(SpeedController m) {
        addMotor(m, false);
        return this;
    }

    /**
     * Adds a Motor to this MotorGroup.
     *
     * @param m The Motor to add.
     * @param invert Whether the Motor's direction should be reversed.
     * @return This MotorGroup.
     */
    public SpeedControllerGroup addMotor(SpeedController m, boolean invert) {
        motors.add(m);
        inverts.add(invert);
        return this;
    }

    /**
     * Adds Motors to this MotorGroup.
     *
     * @param moreMotors The Motors to be added.
     * @return This MotorGroup.
     */
    public SpeedControllerGroup addMotors(SpeedController... moreMotors) {
        for (SpeedController m : moreMotors) {
            motors.add(m);
            inverts.add(false);
        }
        return this;
    }

    /**
     * Adds Motors of class "type".
     *
     * @param type The class that the Motor objects will be.
     * @param ids  The IDs of the motors.
     * @param <T>  Any class that extends Motor.
     * @return This MotorGroup.
     */
    public <T extends SpeedController> SpeedControllerGroup addMotors(Class<T> type, Integer... ids) {
    	SpeedController sc = null;
        for (int i = 0; i < ids.length; i++) {
            try {
                Constructor[] constructors = type.getConstructors();
                if (constructors.length > 0) {
                    for (Constructor c : constructors) {
                        Class[] paramTypes = c.getParameterTypes();
                        if (paramTypes.length == 1 && (paramTypes[0] == Integer.class || paramTypes[0] == int.class)) {
                            sc = (SpeedController) c.newInstance(i);
                            motors.add(sc);
                            break;
                        }
                    }
                    if (sc == null) System.out.println("The SpeedController class given to Motor.java does not have a " +
                            "constructor that accepts an Integer as its only argument!");
                }
            } catch (Exception ignored) {
            }
            inverts.add(false);
        }
        return this;
    }

    /**
     * Clears the list of Motors.
     */
    public void clearMotors() {
        motors.clear();
    }

    /**
     * Sets the invert statuses of all the Motors in this MotorGroup.
     *
     * @param newInverts All the new invert statuses. Must be the same length as the motor list.
     * @return This MotorGroup.
     */
    public SpeedControllerGroup setInverts(Boolean... newInverts) {
        if (newInverts.length == motors.size()) {
            inverts.clear();
            Collections.addAll(inverts, newInverts);
        } else {
            System.out.println("[ERROR] MotorGroup.setInverts() got an array of inverts that is not the same size as the motor list!");
        }
        return this;
    }

    @Override
    public void set(double d) {
        int index = 0;
        for (SpeedController m : motors) {
            double speed = inverts.get(index) ? -d : d; //Inverts the speed based off of that motor's invert value
            m.set(reversed ? -speed : speed); //Sets the motor's speed and possibly inverts it if the MotorGroup as a whole is inverted
            index++;
        }
        power = d;
    }

    @Override
    public double get() {
        return power;
    }

    @Override
    public void setInverted(boolean reversed) {
        this.reversed = reversed;
    }

    /**
     * Gets all the motors in this MotorGroup.
     *
     * @return All the motors in this MotorGroup.
     */
    public List<SpeedController> getMotors() {
        return new ArrayList<>(motors);
    }

    /**
     * Gets how many motors are in this MotorGroup.
     *
     * @return How many motors are in this MotorGroup.
     */
    public int getMotorCount() {
        return motors.size();
    }

	@Override
	public void pidWrite(double output) {
		set(output);
		
	}

	@Override
	public boolean getInverted() {
		return this.reversed;
	}

	@Override
	public void disable() {
        for (SpeedController m : motors) {
            m.disable();
        }
	}
	
	@Override
	public void stopMotor() {
        for (SpeedController m : motors) {
            m.stopMotor();
        }
	}
}
