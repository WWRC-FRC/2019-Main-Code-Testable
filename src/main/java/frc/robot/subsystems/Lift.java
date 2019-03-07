/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import frc.robot.Constants;

/**
 * Add your docs here.
 */
public class Lift extends Subsystem {
  static TalonSRX liftMotor;
  VictorSPX liftFollower;
  static double liftPositionTarget = 0;

  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public void liftToPositionTicks(double heightTicks) {
    liftMotor.set(ControlMode.Position, heightTicks);
  }

  public static void liftToPositionInches(double heightInches, double offset) {
    //Note where we were asked to go to, excluding the offset
    liftPositionTarget = heightInches;

    //The actual target is the selected height plus the hatch offset
    double target = liftPositionTarget + offset;

    //Make sure not too high/low
    if (target > Constants.LiftMaxHeight)
      target = Constants.LiftMaxHeight;
    else if (target < 0)
      target = 0;

    liftMotor.set(ControlMode.Position, target * Constants.LiftTicksPerInch);
  }

  private void configureMotors(){
    liftMotor         = new TalonSRX(Constants.CANLiftMasterController);
    liftFollower = new VictorSPX(Constants.CANLiftFollowerController);

    //ToDo : 'Constants' should really be noted as lift specific
    liftMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.LiftkkPIDLoopIdx, Constants.LiftkTimeoutMs);
    liftMotor.setSensorPhase(Constants.LiftkSensorPhase);
    liftMotor.setInverted(Constants.LiftkMotorInvert);
    liftMotor.configNominalOutputForward(0, Constants.LiftkTimeoutMs);
    liftMotor.configNominalOutputReverse(0, Constants.LiftkTimeoutMs);
    liftMotor.configPeakOutputForward(Constants.LiftPIDpeakoutputUp, Constants.LiftkTimeoutMs);
    liftMotor.configPeakOutputReverse(-Constants.LiftPIDpeakoutputDown, Constants.LiftkTimeoutMs);
    liftMotor.configAllowableClosedloopError(Constants.LiftPIDmaxerror, Constants.LiftkkPIDLoopIdx, Constants.LiftkTimeoutMs);
    liftMotor.config_kP(Constants.LiftkkPIDLoopIdx, Constants.LiftPIDkP, Constants.LiftkTimeoutMs);
    liftMotor.config_kI(Constants.LiftkkPIDLoopIdx, Constants.LiftPIDkI, Constants.LiftkTimeoutMs);
    liftMotor.config_kD(Constants.LiftkkPIDLoopIdx, Constants.LiftPIDkD, Constants.LiftkTimeoutMs);
    liftMotor.config_kF(Constants.LiftkkPIDLoopIdx, Constants.LiftPIDkF, Constants.LiftkTimeoutMs);
    /* Set the quadrature (relative) sensor to match absolute */
    liftMotor.setSelectedSensorPosition(0, Constants.LiftkkPIDLoopIdx, Constants.LiftkTimeoutMs);
    liftMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 10, Constants.LiftkTimeoutMs);
    //Configure lift follower
    liftFollower.follow(liftMotor);

  }

  //Constructor creates the motor controller connections and configures them
  public Lift(){
    configureMotors();
    resetEncoder();
  }

  private  void resetEncoder(){
    //ToDo : Check the parameters. They are supposed to be the count, PID & timeout values not the CAN ID
//    lift.setSelectedSensorPosition(11,1,1);
    liftMotor.setSelectedSensorPosition(0,0,Constants.LiftkTimeoutMs);
  }

  public static double getLiftPositionTicks(){
    return liftMotor.getSelectedSensorPosition();
  }

  public static double getLiftPositionInches(){
    return liftMotor.getSelectedSensorPosition() / Constants.LiftTicksPerInch;
  }

  public void moveLift(double moveDelta){
    //Move to where we are right now plus some delta
    //Offset = 0 since we are reading where we really are first so any offsets become invalid anyhow
    liftToPositionInches(getLiftPositionInches() + moveDelta, 0);
  }

  public static double getLiftPositionTarget(){
    return liftPositionTarget;
  }

  public static double getLiftPositionError(){
    return getLiftPositionInches() - getLiftPositionTarget();
  }

  public static double getLiftPositionErrorPID(){
    return liftMotor.getClosedLoopError();
  }

  public static double getLiftMotorPower(){
    return liftMotor.getMotorOutputPercent();
  }

  public static double getLiftVelocity(){
    return liftMotor.getSelectedSensorVelocity();
  }

  public static boolean isBusy(){
    //Use various measurements to determine if the lift is busy moving to where we asked it to
    //Options are...
    //Check the velocity
    //Check the motor output power
    //Check the absolute position error
    //Check the PID calculated position error
    if ((getLiftVelocity() < 100) && (getLiftPositionError() < Constants.LiftPositionTolerance))
      return false;
    else
      return true;
  }

  @Override
  public void initDefaultCommand() {
  }
}
