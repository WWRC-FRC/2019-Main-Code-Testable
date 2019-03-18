/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;
//import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.Constants;

//import com.ctre.phoenix.motorcontrol.ControlMode;

//import edu.wpi.first.hal.sim.mockdata.DriverStationDataJNI;
//import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
//import frc.robot.Robot;

/**
 * An example command.  You can replace me with your own command.
 */
public class DrivesWithJoysticks extends Command {
  public DrivesWithJoysticks() {
    // Use requires() here to declare subsystem dependencies
    //requires(Robot.driveTrain);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double targetLeftSpeed;
    double targetRightSpeed;
    double currentLeftSpeed;
    double currentRightSpeed;
    double newLeftSpeed;
    double newRightSpeed;
  
//    targetLeftSpeed =  -OI.getControllerDr().getY(Hand.kLeft); //positive is forward ToDo : Check since print shows not the case
//    targetRightSpeed =  -OI.getControllerDr().getY(Hand.kRight); 
    targetLeftSpeed =  Robot.operatorInterface.getControllerStickLeft();
    targetRightSpeed = Robot.operatorInterface.getControllerStickRight();

    if (Math.abs(targetLeftSpeed) < 0.10) 
    {
      targetLeftSpeed = 0;
    }
    if (Math.abs(targetRightSpeed) < 0.10) {
      targetRightSpeed = 0;
  }

  currentLeftSpeed = Robot.driveTrain.getLeftSpeedPercent();
  currentRightSpeed = Robot.driveTrain.getRightSpeedPercent();

  if(Robot.operatorInterface.getControllerTriggerRight() > 0.05){
    targetLeftSpeed*=.75;
    targetRightSpeed*=.75;
   }
   
    //if(OI.getControllerDr().getXButton()){
    if(Robot.operatorInterface.getControllerButtonState(Constants.XBoxButtonX)){
      targetLeftSpeed = Constants.CrawlSpeed;
      targetRightSpeed = Constants.CrawlSpeed;
    }

    newLeftSpeed = adjustSpeed(currentLeftSpeed, targetLeftSpeed, Constants.JoystickAccelleration, Constants.JoystickDecelleration);
    newRightSpeed = adjustSpeed(currentRightSpeed, targetRightSpeed, Constants.JoystickAccelleration, Constants.JoystickDecelleration);
  
    Robot.driveTrain.setSpeedPercentJoystick(newLeftSpeed, newRightSpeed);
  }
  
  private double adjustSpeed(double current, double target, double upDelta, double downDelta)
  {
    double result = 0.0;

    //If need to slow down...
    if (target < current){
      result = current - downDelta;
      //Check if gone too far
      if (result < target)
        result = target;
    }
    //Need to speed up
    else if (target > current){
      result = current + upDelta;
      //Check if gone too far
      if (result > target)
        result = target;
    }
    else
      result = target;
      
    return result;
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
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
