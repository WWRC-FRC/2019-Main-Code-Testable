/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Robot;
import frc.robot.Constants;
import frc.robot.subsystems.*;

public class HandleHatch extends Command {
  private boolean localUpDown;
  private boolean localBlock;
  private boolean isDone = false; 
  
  public HandleHatch(boolean upDown, boolean block) {
    requires(Robot.IntakeSystem);
    this.localUpDown = upDown;
    this.localBlock = block;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    //If 'up' then 

    //Now check if we need to wait or not...
    if (localBlock == true){
    }
    else{
      isDone = true;//Not blocking so finished
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return isDone;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
