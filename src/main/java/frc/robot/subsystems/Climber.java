/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.can.*;
import frc.robot.Constants;
import frc.robot.Robot;

/**
 * Add your docs here.
 */
public class Climber extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  VictorSPX climber;

  /*private void configureMotors(){
    climber = new VictorSPX(Constants.CANClimberController);
    climber.configFactoryDefault();

    climber.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.ClimberkkPIDLoopIdx, Constants.ClimberkTimeoutMs);
    climber.setSensorPhase(Constants.ClimberkSensorPhase);
    climber.setInverted(Constants.ClimberkMotorInvert);
    climber.configNominalOutputForward(0, Constants.ClimberkTimeoutMs);
    climber.configNominalOutputReverse(0, Constants.ClimberkTimeoutMs);
    climber.configPeakOutputForward(Constants.ClimberPIDpeakoutput, Constants.ClimberkTimeoutMs);
    climber.configPeakOutputReverse(-Constants.ClimberPIDpeakoutput, Constants.ClimberkTimeoutMs);
    climber.configAllowableClosedloopError(Constants.ClimberkkPIDLoopIdx, Constants.ClimberPIDmaxerror);
    climber.config_kP(Constants.ClimberkkPIDLoopIdx, Constants.ClimberPIDkP, Constants.ClimberkTimeoutMs);
    climber.config_kI(Constants.ClimberkkPIDLoopIdx, Constants.ClimberPIDkI, Constants.ClimberkTimeoutMs);
    climber.config_kD(Constants.ClimberkkPIDLoopIdx, Constants.ClimberPIDkD, Constants.ClimberkTimeoutMs);
    climber.config_kF(Constants.ClimberkkPIDLoopIdx, Constants.ClimberPIDkF, Constants.ClimberkTimeoutMs);
    //Set the quadrature (relative) sensor to match absolute
    climber.setSelectedSensorPosition(0, Constants.ClimberkkPIDLoopIdx, Constants.ClimberkTimeoutMs);
    //climber.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 10, Constants.ClimberkTimeoutMs); 
  }*/

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
