/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import java.net.Socket;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.*;
import frc.robot.Constants;
import frc.robot.Constants.*;
import edu.wpi.first.wpilibj.Ultrasonic;
import frc.robot.commands.*;

public class DriveTrain extends Subsystem {
  TalonSRX rightFront;
  TalonSRX rightFollower;
  TalonSRX leftFront;
  TalonSRX leftFollower;
  Ultrasonic ultrasonicSensor;
  private boolean busy = false;

  public DriveTrain(){
    rightFront    = new TalonSRX(Constants.CANRightFrontMasterController);
    rightFollower = new TalonSRX(Constants.CANRightFrontFollowerController);
    leftFront     = new TalonSRX(Constants.CANLeftFrontMasterController);
    leftFollower  = new TalonSRX(Constants.CANLeftFrontFollowerController);

    //Reset all factory defaults
    rightFront.configFactoryDefault();
    rightFollower.configFactoryDefault();
    leftFront.configFactoryDefault();
    leftFollower.configFactoryDefault();

    //ToDo : Is this necessary? Does it actually fix the Talon issue?
    //leftFollower.configReverseSoftLimitEnable(true);

    //Configure drive train
    //ToDo : Change for integrated PID instead of wpilib
    //Make constants different to those used for the lift
    rightFront.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
    rightFront.setSensorPhase(Constants.kSensorPhase);
    rightFront.setInverted(Constants.kMotorInvert);
    rightFront.configNominalOutputForward(0, Constants.kTimeoutMs);
    rightFront.configNominalOutputReverse(0, Constants.kTimeoutMs);
    rightFront.configPeakOutputForward(1, Constants.kTimeoutMs);
    rightFront.configPeakOutputReverse(-1, Constants.kTimeoutMs);
    rightFront.configAllowableClosedloopError(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
    rightFront.config_kF(Constants.kPIDLoopIdx, Constants.kGains.kF, Constants.kTimeoutMs);
		rightFront.config_kP(Constants.kPIDLoopIdx, Constants.kGains.kP, Constants.kTimeoutMs);
		rightFront.config_kI(Constants.kPIDLoopIdx, Constants.kGains.kI, Constants.kTimeoutMs);
    rightFront.config_kD(Constants.kPIDLoopIdx, Constants.kGains.kD, Constants.kTimeoutMs);
    rightFront.setInverted(true);
		/* Set the quadrature (relative) sensor to match absolute */
    rightFront.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);

    leftFront.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
    leftFront.setSensorPhase(Constants.kSensorPhase);
    leftFront.setInverted(Constants.kMotorInvert);
    leftFront.configNominalOutputForward(0, Constants.kTimeoutMs);
    leftFront.configNominalOutputReverse(0, Constants.kTimeoutMs);
    leftFront.configPeakOutputForward(1, Constants.kTimeoutMs);
    leftFront.configPeakOutputReverse(-1, Constants.kTimeoutMs);
    leftFront.configAllowableClosedloopError(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
    leftFront.config_kF(Constants.kPIDLoopIdx, Constants.kGains.kF, Constants.kTimeoutMs);
		leftFront.config_kP(Constants.kPIDLoopIdx, Constants.kGains.kP, Constants.kTimeoutMs);
		leftFront.config_kI(Constants.kPIDLoopIdx, Constants.kGains.kI, Constants.kTimeoutMs);
    leftFront.config_kD(Constants.kPIDLoopIdx, Constants.kGains.kD, Constants.kTimeoutMs);
    leftFront.setInverted(true);
		/* Set the quadrature (relative) sensor to match absolute */
    leftFront.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);

    //Set rampe rate. ToDo : Dynamically change PID in the joystick controlled code to effectively set different forward and backwards rates. Don't forget to configure for semi auto actions though !!
    rightFront.configOpenloopRamp(.3, 1000);
    leftFront.configOpenloopRamp(.3, 1000);

    /* set up followers */
    rightFollower.follow(rightFront);
    leftFollower.follow(leftFront);

    rightFollower.setInverted(InvertType.FollowMaster);
    leftFollower.setInverted(InvertType.FollowMaster);
    rightFront.setSensorPhase(true);
    leftFront.setSensorPhase(true);

    leftFront.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 6,  Constants.kTimeoutMs);
    rightFront.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 7 ,  Constants.kTimeoutMs);
    leftFront.setNeutralMode(NeutralMode.Brake);
    rightFront.setNeutralMode(NeutralMode.Brake);
    leftFollower.setNeutralMode(NeutralMode.Brake);
    rightFollower.setNeutralMode(NeutralMode.Brake);

    //Configure ultrasonic range finder
    ultrasonicSensor = new Ultrasonic(Constants.DigUltrasonicPingChannel, Constants.DigUltrasonicEchoChannel, Unit.kMillimeters);

    resetEncoders();
  }

  private  void resetEncoders(){
    //ToDo : Check the parameters. They are supposed to be the count, PID & timeout values  the CAN ID
//    _leftFront.setSelectedSensorPosition(6,1,1);
//    _rightFront.setSelectedSensorPosition(8,1,1);
    leftFront.setSelectedSensorPosition(0,0,Constants.kTimeoutMs);
    rightFront.setSelectedSensorPosition(0,0,Constants.kTimeoutMs);
  }

  private double getLeftEncoderTicks(){
    return leftFront.getSelectedSensorPosition();
  }

  private double getRightEncoderTicks(){
    return rightFront.getSelectedSensorPosition();
  }

  private double getLeftEncoderInches(){
    return leftFront.getSelectedSensorPosition() / Constants.wheelTicksPerInch;
  }

  private double getRightEncoderInches(){
    return rightFront.getSelectedSensorPosition() / Constants.wheelTicksPerInch;
  }

  public void setSpeedPercent(double leftSpeed, double rightSpeed){
    setSpeedRaw(leftSpeed * Constants.SpeedMaxTicksPer100mS, rightSpeed * Constants.SpeedMaxTicksPer100mS);
  }

  public void driveDistanceStraight(double distance) {
    //Drive straight for the specified distance at the default speed, no ultrasonic override
    driveDistanceStraight(distance, 0.5, -1000);
  }

  public void driveDistanceStraight(double distance, double speed) {
    //Drive straight for the specified distance at the specified speed, no ultrasonic override
    driveDistanceStraight(distance, 0.5, -1000);
  }

  public void driveDistanceStraight(double distance, double speed, double stopDistance) {
    boolean driveStopped = false;
    double distanceTraveled;
    double leftDistanceTraveled;
    double rightDistanceTraveled;
    double leftRightDistanceDelta;
    double leftRightSpeedCorrection;
    
    busy = true;
    resetEncoders();//Reset the encoders so we can simply count from here
    while(!driveStopped){
      leftDistanceTraveled  = getLeftEncoderInches();
      rightDistanceTraveled = getRightEncoderInches(); 
      distanceTraveled = (leftDistanceTraveled + rightDistanceTraveled) / 2;//Average the left and right encoders
      if (distanceTraveled >= distance){//Check if gone the entire distance
        driveStopped = true;
      }
      else if (ultrasonicSensor.getRangeInches() < stopDistance){//Check if too close
        driveStopped = true;
      }
      else{//Otherwise make sure driving straight
        leftRightDistanceDelta = leftDistanceTraveled - rightDistanceTraveled;
        leftRightSpeedCorrection = leftRightDistanceDelta * Constants.DriveStraightPGain;
        setSpeedPercent(speed - leftRightSpeedCorrection, speed + leftRightSpeedCorrection);
      }
    }
    //Not sure if this is needed or not. Joystick should take over on exit. ToDo : Check if needed
    //setSpeedPercent(0, 0);
    busy = false;
  }


  private void setSpeedRaw(double leftSpeed, double rightSpeed){
    //Speed is ticks per 100mS ?
    leftFront.set(ControlMode.Velocity, leftSpeed);
    rightFront.set(ControlMode.Velocity, leftSpeed);
  }

  @Override
  public void initDefaultCommand() {
    // If not doing anything else then drive with the joysticks
    setDefaultCommand(new DrivesWithJoysticks());
  }

  public boolean isBusy(){
    return busy;
  }
}
