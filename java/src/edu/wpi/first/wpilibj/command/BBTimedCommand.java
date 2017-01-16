package edu.wpi.first.wpilibj.command;

public abstract class BBTimedCommand extends BBCommand {

	public BBTimedCommand(String name, double timeout) {
		super(name, timeout);
	}

	public BBTimedCommand(double timeout) {
		super(timeout);
	}

	protected boolean isFinished() {
		return isTimedOut();
	}

}
