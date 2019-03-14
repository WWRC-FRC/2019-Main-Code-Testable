/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import frc.robot.commands.*;
//import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.*;
import edu.wpi.first.wpilibj.XboxController;
/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

  private static Joystick buttonBoard =   new Joystick(Constants.ButtonController);
//  private Joystick secondaryJoystick = new Joystick(1);
  private static XboxController driveJoystick = new XboxController(Constants.MainController);

  //ToDo : Need to check button assignments
  Button ButtonLiftOut =               new JoystickButton(driveJoystick, Constants.LiftExtendButton);//ToDo : Check which is in and which is out
  Button ButtonLiftIn =                new JoystickButton(driveJoystick, Constants.LiftRetractButton);//ToDo : pneumatics need constants defined correctly
  Button ButtonLiftUp =                new JoystickButton(driveJoystick,Constants.LiftUpButton);
  Button ButtonLiftDown =              new JoystickButton(driveJoystick,Constants.LiftDownButton);
  Button ButtonHatchLift =             new JoystickButton(driveJoystick,Constants.HatchLiftButton);
  Button ButtonHatchDrop =             new JoystickButton(driveJoystick,Constants.HatchDropButton);
  Button ButtonTest =                  new JoystickButton(buttonBoard, Constants.TestButton);

  Button ButtonIntakeIn =              new JoystickButton(buttonBoard,Constants.CargoIntakeInButton);
  Button ButtonIntakeOut =             new JoystickButton(buttonBoard,Constants.CargoIntakeOutButton);
  Button ButtonCargoInGround =         new JoystickButton(buttonBoard,Constants.CargoRetrieveGroundButton);
  Button ButtonCargoInDepot =          new JoystickButton(buttonBoard,Constants.CargoRetrieveDepotButton);
  Button ButtonCargoOutLow =           new JoystickButton(buttonBoard,Constants.CargoDepositLowButton);
  Button ButtonCargoOutMid =           new JoystickButton(buttonBoard,Constants.CargoDepositMidButton);
  Button ButtonCargoOutHigh =          new JoystickButton(buttonBoard,Constants.CargoDepositHighButton);
  Button ButtonCargoOutRover =         new JoystickButton(buttonBoard,Constants.CargoDepositRoverButton);
  Button ButtonHatchOutLow =           new JoystickButton(buttonBoard,Constants.HatchDepositLowButton);
  Button ButtonHatchOutMid =           new JoystickButton(buttonBoard,Constants.HatchDepositMidButton);
  Button ButtonHatchOutHigh =          new JoystickButton(buttonBoard,Constants.HatchDepositHighButton);


  //// CREATING BUTTONS
  // One type of button is a joystick button which is any button on a
  //// joystick.
  // You create one by telling it which joystick it's on and which button
  // number it is.
//    private static XboxController controllerDr;
  //  private static Button LiftUp;

  // There are a few additional built in buttons you can use. Additionally,
  // by subclassing Button you can create cusLift triggers and bind those to
  // commands the same as any other Button.

  //// TRIGGERING COMMANDS WITH BUTTONS
  // Once you have a button, it's trivial to bind it to a button in one of
  // three ways:

  // Start the command when the button is pressed and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenPressed(new ExampleCommand());

  // Run the command while the button is being held down and interrupt it once
  // the button is released.
  // button.whileHeld(new ExampleCommand());

  // Start the command when the button is released and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenReleased(new ExampleCommand());
  public OI(){

    ButtonLiftIn.whenPressed(       new TogglePneumatics(false));
    ButtonLiftOut.whenPressed(      new TogglePneumatics(true));

    ButtonIntakeIn.whileHeld(       new HandleCargo(Constants.IntakeIn,false));
    ButtonIntakeOut.whileHeld(      new HandleCargo(Constants.IntakeOut,false));

    ButtonLiftUp.whileHeld(         new HandleCargo(Constants.IntakeIn,false));
    ButtonLiftDown.whileHeld(       new HandleCargo(Constants.IntakeOut,false));
    
    ButtonCargoOutRover.whileHeld(  new LiftAndDepositCargo(Constants.CargoDepositRoverHeight));
    ButtonCargoOutLow.whileHeld(    new LiftAndDepositCargo(Constants.CargoDepositLowHeight));
    ButtonCargoOutMid.whileHeld(    new LiftAndDepositCargo(Constants.CargoDepositMidHeight));
    ButtonCargoOutHigh.whileHeld(   new LiftAndDepositCargo(Constants.CargoDepositHighHeight));
    ButtonCargoInGround.whileHeld(  new CollectCargo(Constants.CargoRetrieveLocationGround)); 
    ButtonCargoInDepot.whileHeld(   new CollectCargo(Constants.CargoRetrieveLocationDepot));

    ButtonHatchOutLow.whileHeld(    new LiftAndDepositHatch(Constants.HatchDepositLowHeight));
    ButtonHatchOutMid.whileHeld(    new LiftAndDepositHatch(Constants.HatchDepositMidHeight));
    ButtonHatchOutHigh.whileHeld(   new LiftAndDepositHatch(Constants.HatchDepositHighHeight));
    ButtonHatchLift.whenPressed(    new HandleHatch(Constants.HatchUpState, true));
    ButtonHatchDrop.whenPressed(    new HandleHatch(Constants.HatchDownState, true));

    ButtonTest.whenPressed(         new TestCommand());
  }

  public static boolean getButtonPad1(){
    return buttonBoard.getRawButton(1);
  }

  public static XboxController getControllerDr() {//ToDo : This really is not testable. Need to abstract functions for each thing we want to read
    return driveJoystick;
  }

}                    