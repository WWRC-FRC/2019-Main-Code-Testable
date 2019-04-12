
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import java.util.Objects;

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
  private static SerialPort JeVoisVision;
  private static String CommandName = "Vision";
  private static boolean jeVoisAlive = false;
  private static String rXString = "";
  private static double xPosition = 0; 
  private static double yPosition = 0;
  private static double xPositionTemp = 0; 
  private static double yPositionTemp = 0;
  private static boolean isBusy = false;
  private static int testCount = 0;

  public Vision(){
    Robot.logMessage(CommandName, "constructor");
    if (Robot.isReal()){
      try {
        Robot.logMessage(CommandName, "Creating JeVois SerialPort...");
        JeVoisVision = new SerialPort(115200,SerialPort.Port.kUSB);
        jeVoisAlive = true;
        JeVoisVision.setWriteBufferMode(SerialPort.WriteBufferMode.kFlushOnAccess );
        JeVoisVision.enableTermination();
        Robot.logMessage(CommandName, "JeVois GOOD" + testCount);
        testCount = testCount + 1;
      } catch (Exception e) {
        jeVoisAlive = false;
        Robot.logMessage(CommandName, "JeVois FAILED");
        e.printStackTrace();
      }  
  //    SerialPort JeVoisVision = new SerialPort(9600, SerialPort.Port.kOnboard);
  //    SerialPort JeVoisVision = new SerialPort(9600, SerialPort.Port.kUSB);
  //    SerialPort JeVoisVision = new SerialPort(9600, SerialPort.Port.kUSB1);
  //    SerialPort JeVoisVision = new SerialPort(9600, SerialPort.Port.kUSB2);
    }
}

  public double getRetroTargetError(){
      //This function returns an error factor to be used to point in the direction of the retro-flective targets
      return xPosition;
  }

  public void updateVisionSimulation(){
  }

  private void triggerMeasurement(){
    if (Robot.isReal() && (jeVoisAlive == true) && (isBusy == false)){
      isBusy = true;
      JeVoisVision.writeString("GO\n");
      //Robot.logMessage(CommandName, "Vision triggered");
    }
  }

  public void updateVision(){
    if (isBusy == false)
      triggerMeasurement();
    scanBuffer();
  }

  private void  scanBuffer() {
    String rXChar;
    if ((jeVoisAlive == true) && (isBusy == true)){
      //Check if there is a character in the buffer
      //Robot.logMessage(CommandName, "Waiting for JeVois " + JeVoisVision.getBytesReceived());
    
      while(JeVoisVision.getBytesReceived() > 0){
        //Robot.logMessage(CommandName, "Received something");
        //Character there so add to retrieved string and check if end of string marker
        rXChar = JeVoisVision.readString(1);
        //Robot.logMessage(CommandName, "RX = " + rXChar);          
        if (Objects.equals(rXChar, "X")){
          //Characters received so far denote X position
          xPositionTemp = Double.parseDouble(rXString);
          rXString = "";
          //Robot.logMessage(CommandName, "Received X = " + xPositionTemp);
        }
        else if (Objects.equals(rXChar, "Y")){
          //Characters received so far denote Y position (not used currently)
          yPositionTemp = Double.parseDouble(rXString);
          rXString = "";
          //Robot.logMessage(CommandName, "Received Y = " +yPositionTemp);
        }
        else if (Objects.equals(rXChar, "T")){
          //Characters received so far denote timestamp (not used currently)
          rXString = "";
          //Robot.logMessage(CommandName, "Received T = ???");
        }
        else if (Objects.equals(rXChar, "G")){
          //End of sequence marker to indicate all results are good
          rXString = "";
          xPosition = xPositionTemp;
          yPosition = yPositionTemp;
          isBusy = false;
          //Robot.logMessage(CommandName, "Vision done" + Double.toString(xPosition));
        }
        else if (Objects.equals(rXChar, "O")){}//Ignore "OK" and new lines
        else if (Objects.equals(rXChar, "K")){}
        else if (Objects.equals(rXChar, "/n")){}
        else{
          rXString = rXString + rXChar;
        }
      }
    }
  }

  public boolean searchingIsFinished(){
    return !isBusy;
  }

  @Override
  public void initDefaultCommand() {
    //setDefaultCommand(new MonitorJeVois());
  }

  public double getTargetError() {
    return xPosition;
  }
  
}
