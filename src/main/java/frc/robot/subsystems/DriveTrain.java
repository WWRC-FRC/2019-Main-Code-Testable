/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import java.lang.Math;

//import java.net.Socket;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.*;
import frc.robot.Constants;
//import frc.robot.Constants.*;
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
    rightFront.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.DrivekkPIDLoopIdx, Constants.DrivekTimeoutMs);
    rightFront.setSensorPhase(Constants.DrivekSensorPhase);
    rightFront.setInverted(Constants.DrivekMotorInvert);
    rightFront.configNominalOutputForward(0, Constants.DrivekTimeoutMs);
    rightFront.configNominalOutputReverse(0, Constants.DrivekTimeoutMs);
    rightFront.configPeakOutputForward(Constants.DrivePIDpeakoutput, Constants.DrivekTimeoutMs);
    rightFront.configPeakOutputReverse(-Constants.DrivePIDpeakoutput, Constants.DrivekTimeoutMs);
    rightFront.configAllowableClosedloopError(Constants.DrivePIDmaxerror, Constants.DrivekkPIDLoopIdx, Constants.DrivekTimeoutMs);
    rightFront.config_kF(Constants.DrivekkPIDLoopIdx, Constants.DrivePIDkF, Constants.DrivekTimeoutMs);
		rightFront.config_kP(Constants.DrivekkPIDLoopIdx, Constants.DrivePIDkP, Constants.DrivekTimeoutMs);
		rightFront.config_kI(Constants.DrivekkPIDLoopIdx, Constants.DrivePIDkI, Constants.DrivekTimeoutMs);
    rightFront.config_kD(Constants.DrivekkPIDLoopIdx, Constants.DrivePIDkD, Constants.DrivekTimeoutMs);
    rightFront.setInverted(true);
		/* Set the quadrature (relative) sensor to match absolute */
    rightFront.setSelectedSensorPosition(0, Constants.DrivekkPIDLoopIdx, Constants.DrivekTimeoutMs);

    leftFront.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.DrivekkPIDLoopIdx, Constants.DrivekTimeoutMs);
    leftFront.setSensorPhase(Constants.DrivekSensorPhase);
    leftFront.setInverted(Constants.DrivekMotorInvert);
    leftFront.configNominalOutputForward(0, Constants.DrivekTimeoutMs);
    leftFront.configNominalOutputReverse(0, Constants.DrivekTimeoutMs);
    leftFront.configPeakOutputForward(Constants.DrivePIDpeakoutput, Constants.DrivekTimeoutMs);
    leftFront.configPeakOutputReverse(-Constants.DrivePIDpeakoutput, Constants.DrivekTimeoutMs);
    leftFront.configAllowableClosedloopError(Constants.DrivePIDmaxerror, Constants.DrivekkPIDLoopIdx, Constants.DrivekTimeoutMs);
    leftFront.config_kF(Constants.DrivekkPIDLoopIdx, Constants.DrivePIDkF, Constants.DrivekTimeoutMs);
		leftFront.config_kP(Constants.DrivekkPIDLoopIdx, Constants.DrivePIDkP, Constants.DrivekTimeoutMs);
		leftFront.config_kI(Constants.DrivekkPIDLoopIdx, Constants.DrivePIDkI, Constants.DrivekTimeoutMs);
    leftFront.config_kD(Constants.DrivekkPIDLoopIdx, Constants.DrivePIDkD, Constants.DrivekTimeoutMs);
    leftFront.setInverted(true);
		/* Set the quadrature (relative) sensor to match absolute */
    leftFront.setSelectedSensorPosition(0, Constants.DrivekkPIDLoopIdx, Constants.DrivekTimeoutMs);

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

    leftFront.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 6,  Constants.DrivekTimeoutMs);
    rightFront.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 7 ,  Constants.DrivekTimeoutMs);
    leftFront.setNeutralMode(NeutralMode.Brake);
    rightFront.setNeutralMode(NeutralMode.Brake);
    leftFollower.setNeutralMode(NeutralMode.Brake);
    rightFollower.setNeutralMode(NeutralMode.Brake);

    //Configure ultrasonic range finder
//    ultrasonicSensor = new Ultrasonic(Constants.DigUltrasonicPingChannel, Constants.DigUltrasonicEchoChannel, Unit.kMillimeters);
    ultrasonicSensor = new Ultrasonic(Constants.DigUltrasonicPingChannel, Constants.DigUltrasonicEchoChannel);

    resetEncoders();
  }

  private  void resetEncoders(){
    //ToDo : Check the parameters. They are supposed to be the count, PID & timeout values  the CAN ID
//    _leftFront.setSelectedSensorPosition(6,1,1);
//    _rightFront.setSelectedSensorPosition(8,1,1);
    leftFront.setSelectedSensorPosition(0,0,Constants.DrivekTimeoutMs);
    rightFront.setSelectedSensorPosition(0,0,Constants.DrivekTimeoutMs);
  }
/*
  private double getLeftEncoderTicks(){
    return leftFront.getSelectedSensorPosition();
  }

  private double getRightEncoderTicks(){
    return rightFront.getSelectedSensorPosition();
  }
*/
  private double getLeftEncoderInches(){
    return leftFront.getSelectedSensorPosition() / Constants.WheelTicksPerInch;
  }

  private double getRightEncoderInches(){
    return rightFront.getSelectedSensorPosition() / Constants.WheelTicksPerInch;
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
    //distance = distance to travel. +ve = forward, -ve = backwards
    //speed    = speed percentage. Should be +ve
    boolean driveStopped = false;
    double distanceTraveled;
    double leftDistanceTraveled;
    double rightDistanceTraveled;
    double leftRightDistanceDelta;
    double leftRightSpeedCorrection;
    double direction;
    
    busy = true;
    //Note which direction we are going. Used to set motors later
    if (distance < 0)
      direction = -1.0;
    else
      direction = 1.0;

    resetEncoders();//Reset the encoders so we can simply count from here
    while(!driveStopped){
      leftDistanceTraveled  = getLeftEncoderInches();
      rightDistanceTraveled = getRightEncoderInches(); 
      distanceTraveled = (leftDistanceTraveled + rightDistanceTraveled) / 2;//Average the left and right encoders
      if (Math.abs(distanceTraveled) >= Math.abs(distance)){//Check if gone the entire distance (note, direction is important)
        driveStopped = true;
      }
      else if ((ultrasonicSensor.getRangeInches() < stopDistance) && (distance > 0.0)){//Check if too close. Can't use for reverse, but don't need at the moment
        driveStopped = true;
      }
      else{//Otherwise make sure driving straight
        leftRightDistanceDelta = leftDistanceTraveled - rightDistanceTraveled;
        leftRightSpeedCorrection = leftRightDistanceDelta * Constants.DriveStraightPGain;
        setSpeedPercent(direction * (speed - leftRightSpeedCorrection), direction * (speed + leftRightSpeedCorrection));
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
