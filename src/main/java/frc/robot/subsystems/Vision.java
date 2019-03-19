
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.can.*;
import frc.robot.Constants;
import frc.robot.Robot;

/**
 * Add your docs here.
 */
public class Vision extends Subsystem {
  static TalonSRX intake;
  private static String CommandName = "Vision";

  public Vision(){
    Robot.logMessage(CommandName, "constructor");
  }

  public double getretroTargetError(){
      //This function returns an error factor to be used to point in the direction of the retro-flective targets
      return 0;
  }

  public void updateVisionSimulation(){
  }

  @Override
  public void initDefaultCommand() {
  }
}
