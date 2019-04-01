
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.can.*;
import frc.robot.Constants;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;
import frc.robot.commands.*;

/**
 * Add your docs here.
 */
public class Vision extends Subsystem {
  static SerialPort JeVoisVision;
  private static String CommandName = "Vision";

  public Vision(){
    Robot.logMessage(CommandName, "constructor");
    if (Robot.isReal()){
      SerialPort JeVoisVision = new SerialPort(9600, SerialPort.Port.kMXP);
  //    SerialPort JeVoisVision = new SerialPort(9600, SerialPort.Port.kOnboard);
  //    SerialPort JeVoisVision = new SerialPort(9600, SerialPort.Port.kUSB);
  //    SerialPort JeVoisVision = new SerialPort(9600, SerialPort.Port.kUSB1);
  //    SerialPort JeVoisVision = new SerialPort(9600, SerialPort.Port.kUSB2);
      JeVoisVision.setWriteBufferMode(SerialPort.WriteBufferMode.kFlushOnAccess );
    }
}

  public double getretroTargetError(){
      //This function returns an error factor to be used to point in the direction of the retro-flective targets

      return 0;
  }

  public void updateVisionSimulation(){
  }

  public void triggerMeasurement(){
    if (Robot.isReal()){
      JeVoisVision.writeString("?");
    }
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new LocateTargets());
  }
}
