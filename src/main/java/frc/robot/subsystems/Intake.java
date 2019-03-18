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
import com.ctre.phoenix.motorcontrol.*;
import frc.robot.Robot;

/**
 * Add your docs here.
 */
public class Intake extends Subsystem {
  static TalonSRX intake;
  static DigitalInput limitSwitch;
  private static String CommandName = "Intake";
  private static boolean limitSwitchSimulation = false;
  private static int limitSwitchCounterSimulation = 0;

  public Intake(){
    Robot.logMessage(CommandName, "constructor");
    if (Robot.isReal() == true){
      intake = new TalonSRX(Constants.CANIntakeController);
      limitSwitch = new DigitalInput(0);
    }
  }

  public static boolean isBallIn(){
    if (Robot.isReal() == true)
      return limitSwitch.get();//ToDo : Check polarity. True SHOULD mean the cargo is in
    else
      return limitSwitchSimulation;
  }

  public static void setIntakeSpeed(double speed){
    if (Robot.isReal() == true)
      intake.set(ControlMode.Velocity, speed);
    else
      if ((speed < 0) && (limitSwitchSimulation == false)){
        limitSwitchCounterSimulation = -100;//Wait some simulation time before updating limit switch
        Robot.logMessage(CommandName, "Intaking ball");
      }
      else if((speed > 0) && (limitSwitchSimulation == true)){
        limitSwitchCounterSimulation = 100;
        Robot.logMessage(CommandName, "Ejecting ball");
      }
  }

  public void updateIntakeSimulation(){
    if (limitSwitchCounterSimulation < 0){
      limitSwitchCounterSimulation ++;
      if (limitSwitchCounterSimulation == 0)
        limitSwitchSimulation = true;
//      Robot.logMessage(CommandName, "updateIntakeSimulation : " + limitSwitchCounterSimulation);
    }
    else if (limitSwitchCounterSimulation > 0){
      limitSwitchCounterSimulation --;
      if (limitSwitchCounterSimulation == 0)
        limitSwitchSimulation = false;
//      Robot.logMessage(CommandName, "updateIntakeSimulation : " + limitSwitchCounterSimulation);
    }
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
