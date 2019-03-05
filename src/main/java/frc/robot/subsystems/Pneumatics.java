/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Compressor;
import frc.robot.Constants;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class Pneumatics extends Subsystem {
  public static Pneumatics pneumaticsSystem;
  public static DoubleSolenoid solenoid;
  public static Compressor compressor;

  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private void initPneumatics(){
//    pneumaticsSystem = new Pneumatics();//Creating here is circular !!!
    solenoid = new DoubleSolenoid(Constants.PneuStroke1Channel, Constants.PneuStroke2Channel);
    compressor = new Compressor();

    compressor.setClosedLoopControl(true);
  }

  public Pneumatics(){
    initPneumatics();
  }

  public static void setLiftPiston(boolean state){
    if(state == Constants.PneuLiftIn)
      solenoid.set(DoubleSolenoid.Value.kReverse);
    else
      solenoid.set(DoubleSolenoid.Value.kForward);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
