/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;
import edu.wpi.first.wpilibj.command.Subsystem;
//import javax.swing.text.StyleContext.SmallAttributeSet;
//import java.net.Socket;
import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.*;
import frc.robot.Constants;
//import frc.robot.Constants.*;
import edu.wpi.first.wpilibj.Ultrasonic;
import frc.robot.commands.*;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import frc.robot.Robot;

public class DriveTrain extends Subsystem {
  TalonSRX rightFront;
  VictorSPX rightFollower;
  TalonSRX leftFront;
  TalonSRX leftFollower;
  Ultrasonic ultrasonicSensor;
  private double leftCurrentPercent = 0.0;
  private double rightCurrentPercent = 0.0;
  private String CommandName = "DriveTrain";
  private int leftEncoderSimulation = 1021210;//Set to random value to check that we actually reset correctly
  private int rightEncoderSimulation = -1213310;
  private double ultrasonicRangeSimulation = 0;
  private double leftSpeedSimulation = 0;
  private double rightSpeedSimulation = 0;

  private void DriveTrainInit(){
    rightFront    = new TalonSRX(Constants.CANRightFrontMasterController);
    rightFollower = new VictorSPX(Constants.CANRightFrontFollowerController);
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
		/* Set the quadrature (relative) sensor to match absolute */
    rightFront.setSelectedSensorPosition(0, Constants.DrivekkPIDLoopIdx, Constants.DrivekTimeoutMs);

    leftFront.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.DrivekkPIDLoopIdx, Constants.DrivekTimeoutMs);
    leftFront.setSensorPhase(Constants.DrivekSensorPhase);
    leftFront.setInverted(!Constants.DrivekMotorInvert);
    leftFront.configNominalOutputForward(0, Constants.DrivekTimeoutMs);
    leftFront.configNominalOutputReverse(0, Constants.DrivekTimeoutMs);
    leftFront.configPeakOutputForward(Constants.DrivePIDpeakoutput, Constants.DrivekTimeoutMs);
    leftFront.configPeakOutputReverse(-Constants.DrivePIDpeakoutput, Constants.DrivekTimeoutMs);
    leftFront.configAllowableClosedloopError(Constants.DrivekkPIDLoopIdx, Constants.DrivePIDmaxerror);
    leftFront.config_kF(Constants.DrivekkPIDLoopIdx, Constants.DrivePIDkF, Constants.DrivekTimeoutMs);
		leftFront.config_kP(Constants.DrivekkPIDLoopIdx, Constants.DrivePIDkP, Constants.DrivekTimeoutMs);
		leftFront.config_kI(Constants.DrivekkPIDLoopIdx, Constants.DrivePIDkI, Constants.DrivekTimeoutMs);
    leftFront.config_kD(Constants.DrivekkPIDLoopIdx, Constants.DrivePIDkD, Constants.DrivekTimeoutMs);
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

    //leftFront.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 6,  Constants.DrivekTimeoutMs);
    //rightFront.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 7 ,  Constants.DrivekTimeoutMs);
    leftFront.setNeutralMode(NeutralMode.Brake);
    rightFront.setNeutralMode(NeutralMode.Brake);
    leftFollower.setNeutralMode(NeutralMode.Brake);
    rightFollower.setNeutralMode(NeutralMode.Brake);

    //Configure ultrasonic range finder
//    ultrasonicSensor = new Ultrasonic(Constants.DigUltrasonicPingChannel, Constants.DigUltrasonicEchoChannel, Unit.kMillimeters);
    ultrasonicSensor = new Ultrasonic(Constants.DigUltrasonicPingChannel, Constants.DigUltrasonicEchoChannel);

  }

  public DriveTrain(){
    //Drive train constructor
    Robot.logMessage(CommandName, "constructor");
    //Initialize the drive train
    if (Robot.isReal() == true){
      DriveTrainInit();
    }
    resetEncoders();
  }

  public void resetEncoders(){
    //ToDo : Check the parameters. They are supposed to be the count, PID & timeout values  the CAN ID
//    _leftFront.setSelectedSensorPosition(6,1,1);
//    _rightFront.setSelectedSensorPosition(8,1,1);
    if (Robot.isReal() == true){
      leftFront.setSelectedSensorPosition(0,0,Constants.DrivekTimeoutMs);
      rightFront.setSelectedSensorPosition(0,0,Constants.DrivekTimeoutMs);
    }
    else{
      leftEncoderSimulation = 0;
      rightEncoderSimulation = 0;
    }
  }

  private double getLeftEncoderTicks(){
    if (Robot.isReal() == true)
      return leftFront.getSelectedSensorPosition();
    else
      return leftEncoderSimulation;
  }

  private double getRightEncoderTicks(){
    if (Robot.isReal() == true)
      return rightFront.getSelectedSensorPosition();
    else
      return rightEncoderSimulation;
  }

  public double getLeftEncoderInches(){
    return getLeftEncoderTicks() / Constants.WheelTicksPerInch;
  }

  public double getRightEncoderInches(){
    return getRightEncoderTicks() / Constants.WheelTicksPerInch;
  }

  public void setSpeedPercent(double leftSpeed, double rightSpeed){
    leftCurrentPercent = leftSpeed;
    rightCurrentPercent = rightSpeed;
    setSpeedRaw(leftSpeed * Constants.SpeedMaxTicksPer100mS, rightSpeed * Constants.SpeedMaxTicksPer100mS);
  }

  public double getLeftSpeedPercent(){
    return leftCurrentPercent;
  }

  public double getRightSpeedPercent(){
    return rightCurrentPercent;
  }
/*
  public void driveDistanceStraight(double distance) {
    //Drive straight for the specified distance at the default speed, no ultrasonic override
    driveDistanceStraight(distance, 0.5, -1000);
  }

  public void driveDistanceStraight(double distance, double speed) {
    //Drive straight for the specified distance at the specified speed, no ultrasonic override
    driveDistanceStraight(distance, 0.5, -1000);
  }
*/
  public double getUltrasonicRange(){
    if (Robot.isReal() == true)
      return ultrasonicSensor.getRangeInches();
    else {
      return ultrasonicRangeSimulation;
    }
  }

  private void setSpeedRaw(double leftSpeed, double rightSpeed){
    //Speed is ticks per 100mS ?
    if (Robot.isReal() == true){
      leftFront.set(ControlMode.Velocity, leftSpeed);
      rightFront.set(ControlMode.Velocity, rightSpeed);
    }
    else
    {
      leftSpeedSimulation = leftSpeed;
      rightSpeedSimulation = rightSpeed;
    }
  }

  @Override
  public void initDefaultCommand() {
    // If not doing anything else then drive with the joysticks
    setDefaultCommand(new DrivesWithJoysticks());
  }

  public void updateDrivetrainSimulation(){
    leftEncoderSimulation = (int)(leftEncoderSimulation + (leftSpeedSimulation / 50));
    rightEncoderSimulation = (int)(rightEncoderSimulation + (rightSpeedSimulation / 50));
  }


}
