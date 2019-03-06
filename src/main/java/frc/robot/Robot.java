/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import com.ctre.phoenix.motorcontrol.Faults;
import com.analog.adis16470.frc.ADIS16470_IMU;
import edu.wpi.first.cameraserver.*;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.GenericHID.Hand;
//import edu.wpi.first.wpilibj.Ultrasonic.Unit;
//import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.DrivesWithJoysticks;
//import frc.robot.commands.*;
import frc.robot.subsystems.*;

//https://www.bing.com/videos/search?q=best+tom+and+jerry&view=detail&mid=ADE99A515A0765A73415ADE99A515A0765A73415&FORM=VIRE 
public class Robot extends TimedRobot {
//  private OI m_oi;
//  LiftToPosition test;
  Command m_autonomousCommand;
  //SendableChooser<Command> m_chooser = new SendableChooser<>();
  Command driveWithJoystick;
  //Command liftToPosition;
  public static Hand leftStick = Hand.kLeft;
  public static Hand rightStick = Hand.kRight;

  public static DriveTrain driveTrain;
  public static ADIS16470_IMU imu;
  public static Lift liftSystem;
  public static Pneumatics pneumaticSystem;
//  public static Vision vision;
  public static Intake IntakeSystem;
//  public static UltrasonicSensor ultrasonicSensorVision;
//  public static UltrasonicSensorDrive ultrasonicSensorDrive;
  //Not needed anymore? public static DifferentialDrive driveSystem;
  
  //public static DrivesWithJoysticks driveIntake = new DrivesWithJoysticks();
  public static DrivesWithJoysticks driveIntake;
    //public static boolean;
  //  static ADXRS450_Gyro gyro = new ADXRS450_Gyro();

    Faults _faults_L = new Faults();
    Faults _faults_R = new Faults();

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    driveTrain = new DriveTrain();
    liftSystem = new Lift();
    pneumaticSystem = new Pneumatics();
    IntakeSystem = new Intake();
    driveWithJoystick = new DrivesWithJoysticks();
    
//    ADIS16470_IMU imu = new ADIS16470_IMU();
//    vision = new Vision();
//    ultrasonicSensorVision = new UltrasonicSensor();
//    ultrasonicSensorDrive = new UltrasonicSensorDrive();
  
    //Not needed anymore? driveSystem = new DifferentialDrive(_leftFront, _rghtFront);

//    m_oi = new OI();
   

     // pneumaticsSmash.setClosedLoopControl(true);
    
     if (Robot.isReal() == true){
      CameraServer.getInstance().startAutomaticCapture();
     }
 
  }




  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }




  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.
   */
  @Override
  public void disabledInit() {
  }




  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();//ToDo : Do we really want the scheduler to run when disabled?
  }




  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
//    if(driveWithJoystick != null) 
//      driveWithJoystick.start();
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
    
    }
 
    @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
//      if(driveWithJoystick != null) 
//        driveWithJoystick.start();
//      compressor.setClosedLoopControl(true);
     
  } 

  /**
   * This function is called periodically during operator control.
   */
  
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

//  public static double ultrasonicDistance(){
//    return ultrasonicSensor.getRangeInches();
//  }
 
 }
