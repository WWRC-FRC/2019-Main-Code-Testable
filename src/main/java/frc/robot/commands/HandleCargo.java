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

public class HandleCargo extends Command {
  private boolean localInOut;
  private boolean localBlock;
  private boolean isDone = false; 
  
  public HandleCargo(boolean inOut, boolean block) {
    requires(Robot.IntakeSystem);
    this.localInOut = inOut;
    this.localBlock = block;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    //Set the intake motor direction
    if(localInOut == Constants.IntakeIn){
      Intake.setIntakeSpeed(Constants.IntakeInSpeed);
    }
    else {
      Intake.setIntakeSpeed(Constants.IntakeEjectSpeed);
    }

    //Now check if we need to wait or not...
    //if((inOut == Constants.IntakeIn) && (!Intake.isBallIn())){//Asking for intake and ball is not yet in
    if (localBlock == true){
      if (localInOut == Constants.IntakeIn){
        //Blocking and intaking so wait for the ball before finishing
        if (Intake.isBallIn() == true){
          //Yes, intaking and ball is captured
          isDone = true;
        }
      }
      else {//Ejecting the ball so make sure it is out
        if (Intake.isBallIn() == false){
          //Yes, ejecting and ball is out
          //Need to delay a little though to allow the ball to really get out
          Timer.delay(Constants.CargoEjectDelay);
          isDone = true;
        }
      }
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
    //Intake.setIntakeSpeed(Constants.IntakeHoldSpeed);//Can't do this since we need the intake to remain running sometimes
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Intake.setIntakeSpeed(Constants.IntakeHoldSpeed);
  }
}
