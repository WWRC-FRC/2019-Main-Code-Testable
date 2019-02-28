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
  TalonSRX lift;
  VictorSPX liftFollower;

  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public void liftToPosition(double distance) {
    lift.set(ControlMode.Position, distance);
  }

  //Constructor creates the motor controller connections and configures them
  public Lift(){
    lift         = new TalonSRX(Constants.CANLiftMasterController);
    liftFollower = new VictorSPX(Constants.CANLiftFollowerController);

    //ToDo : 'Constants' should really be noted as lift specific
    lift.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,    Constants.kPIDLoopIdx,    Constants.kTimeoutMs);
    lift.setSensorPhase(Constants.kSensorPhase);
    lift.setInverted(Constants.kMotorInvert);
    lift.configNominalOutputForward(0, Constants.kTimeoutMs);
    lift.configNominalOutputReverse(0, Constants.kTimeoutMs);
    lift.configPeakOutputForward(1, Constants.kTimeoutMs);
    lift.configPeakOutputReverse(-.6, Constants.kTimeoutMs);
    lift.configAllowableClosedloopError(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
    lift.config_kF(Constants.kPIDLoopIdx, Constants.kGains.kF, Constants.kTimeoutMs);
    lift.config_kP(Constants.kPIDLoopIdx, Constants.kGains.kP, Constants.kTimeoutMs);
    lift.config_kI(Constants.kPIDLoopIdx, Constants.kGains.kI, Constants.kTimeoutMs);
    lift.config_kD(Constants.kPIDLoopIdx, Constants.kGains.kD, Constants.kTimeoutMs);
    /* Set the quadrature (relative) sensor to match absolute */
    lift.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
    lift.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 10, Constants.kTimeoutMs);
    //Configure lift follower
    liftFollower.follow(lift);

    resetEncoder();
  }

  private  void resetEncoder(){
    //ToDo : Check the parameters. They are supposed to be the count, PID & timeout values  the CAN ID
//    lift.setSelectedSensorPosition(11,1,1);
    lift.setSelectedSensorPosition(0,0,Constants.kTimeoutMs);
  }

  public double getLiftEncoderTicks(){
    return lift.getSelectedSensorPosition();
  }

  public double getLiftEncoderInches(){
    return lift.getSelectedSensorPosition() / Constants.liftTicksPerInch;//encoderMeters; 38150
  }

  @Override
  public void initDefaultCommand() {
  }

  
}
