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
import edu.wpi.first.wpilibj.DigitalInput;

/**
 * Add your docs here.
 */
public class Intake extends Subsystem {
  TalonSRX intake;
  DigitalInput limitSwitch;

  public Intake(){
    intake = new TalonSRX(Constants.CANIntakeController);
    limitSwitch = new DigitalInput(0);
  }

  public boolean isBallIn(){
    return limitSwitch.get();
  }


  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
