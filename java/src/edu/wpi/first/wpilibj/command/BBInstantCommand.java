/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2016-2017. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.command;

import edu.wpi.first.wpilibj.command.BBCommand;

/**
 * This command will execute once, then finish immediately afterward.
 *
 * <p>Subclassing {@link InstantCommand} is shorthand for returning true from
 * {@link Command isFinished}.
 */
public class BBInstantCommand extends BBCommand {

  public BBInstantCommand() {
	  super();
  }


  protected boolean isFinished() {
    return true;
  }
}
