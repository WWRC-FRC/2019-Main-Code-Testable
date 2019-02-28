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

  public static void liftToPositionInches(double heightInches) {
    //Make sure not too high/low
    if (heightInches > Constants.LiftMaxHeight)
      heightInches = Constants.LiftMaxHeight;
    else if (heightInches < 0)
      heightInches = 0;

    liftPositionTarget = heightInches;

    liftMotor.set(ControlMode.Position, heightInches * Constants.LiftTicksPerInch);
  }

  private void configureMotors(){
    liftMotor         = new TalonSRX(Constants.CANLiftMasterController);
    liftFollower = new VictorSPX(Constants.CANLiftFollowerController);

    //ToDo : 'Constants' should really be noted as lift specific
    liftMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
    liftMotor.setSensorPhase(Constants.kSensorPhase);
    liftMotor.setInverted(Constants.kMotorInvert);
    liftMotor.configNominalOutputForward(0, Constants.kTimeoutMs);
    liftMotor.configNominalOutputReverse(0, Constants.kTimeoutMs);
    liftMotor.configPeakOutputForward(1, Constants.kTimeoutMs);
    liftMotor.configPeakOutputReverse(-.6, Constants.kTimeoutMs);
    liftMotor.configAllowableClosedloopError(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
    liftMotor.config_kP(Constants.kPIDLoopIdx, Constants.kGains.kP, Constants.kTimeoutMs);
    liftMotor.config_kI(Constants.kPIDLoopIdx, Constants.kGains.kI, Constants.kTimeoutMs);
    liftMotor.config_kD(Constants.kPIDLoopIdx, Constants.kGains.kD, Constants.kTimeoutMs);
    liftMotor.config_kF(Constants.kPIDLoopIdx, Constants.kGains.kF, Constants.kTimeoutMs);
    /* Set the quadrature (relative) sensor to match absolute */
    liftMotor.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
    liftMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 10, Constants.kTimeoutMs);
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
    liftMotor.setSelectedSensorPosition(0,0,Constants.kTimeoutMs);
  }

  public static double getLiftPositionTicks(){
    return liftMotor.getSelectedSensorPosition();
  }

  public static double getLiftPositionInches(){
    return liftMotor.getSelectedSensorPosition() / Constants.LiftTicksPerInch;//encoderMeters; 38150
  }

  public void moveLift(double moveDelta){
    liftToPositionInches(getLiftPositionInches() + moveDelta);
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
    // Check the velocity
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
