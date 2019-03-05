/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;
import edu.wpi.first.wpilibj.command.Command;

public class TestCommand extends Command {
  public TestCommand() {
//    requires(Robot.vision);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    System.out.println("TestCommand : init");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    System.out.println("TestCommand: execute");
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    System.out.println("TestCommand: isFinished");
    return true;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    System.out.println("TestCommand: end");
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    System.out.println("TestCommand: interrupted");
  }
}
