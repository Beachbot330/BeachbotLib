package edu.wpi.first.wpilibj.command;

/**
 * A {@link TimedCommand} will wait for a timeout before finishing.
 * {@link TimedCommand} is used to execute a command for a given amount of time.
 */
public abstract class BBTimedCommand extends BBCommand {

	/**
	 * Instantiates a TimedCommand with the given name and timeout.
	 *
	 * @param name the name of the command
	 * @param timeout the time the command takes to run (seconds)
	 */
	public BBTimedCommand(String name, double timeout) {
		super(name, timeout);
	}

	/**
	 * Instantiates a TimedCommand with the given timeout.
	 *
	 * @param timeout the time the command takes to run (seconds)
	 */
	public BBTimedCommand(double timeout) {
		super(timeout);
	}

	/**
	 * Ends command when timed out.
	 */
	protected boolean isFinished() {
		return isTimedOut();
	}

}
