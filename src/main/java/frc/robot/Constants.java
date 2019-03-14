/**
 * Simple class containing constants used throughout project
 */
package frc.robot;

public class Constants {
	//CAN bus channels for devices
	public static final int    CANLeftFrontMasterController    = 6;
	public static final int    CANRightFrontMasterController   = 7;
	public static final int    CANLeftFrontFollowerController  = 11;
	public static final int    CANRightFrontFollowerController = 9;
	public static final int    CANLiftMasterController         = 10;
	public static final int    CANLiftFollowerController       = 12;
	public static final int    CANIntakeController             = 8;

	//Pneumatic constants
	public static final int     PneuStroke1Channel             = 0;
	public static final int     PneuStroke2Channel             = 1;
	public static final boolean PneuLiftOut                    = true;//ToDo : Check correct order
	public static final boolean PneuLiftIn                     = false;

	//Digital IO channels
	public static final int    DigUltrasonicPingChannel        = 1;
	public static final int    DigUltrasonicEchoChannel        = 2;

	//Drive train constants
	public static final int    WheelCountsPerRev               = 4096;
	public static final double WheelDiameter                   = 6;//6 inch diameter
	public static final double WheelCircumference              = Math.PI*WheelDiameter;
	public static final double WheelTicksPerInch               = WheelCountsPerRev / WheelCircumference;
	public static final int    SpeedMaxTicksPer100mS           = 6000;//ToDo : Need to measure
	public static final double DriveStraightPGain              = 0.1;//ToDo : Need to tune

	//Lift constants
	public static final double LiftSprocketCircumference       = 4;//16 teeth spaced at 1/4 inch
	public static final double LiftCountsPerRev                = 4096;//ToDo : Check this
	public static final double LiftTicksPerInch                = (LiftCountsPerRev / LiftSprocketCircumference) / 2;//Divided by 2 because of two-stage lift
	public static final double LiftMaxHeight                   = 68;
	public static final double LiftPositionTolerance           = 2;

	//Auto constants
	public static final double AutoInSpeed                = 0.4;//Speed at which the semi-auto moves towards the target
	public static final double AutoOutSpeed               = 0.6;//Speed at which the semi-auto moves away from the target
	public static final double AutoStopFromDistanceCargo  = 6.0;//Distance from the target to stop for cargo (account for the hook)
	public static final double AutoStopFromDistanceHatch  = 4.0;//Distance from the target to stop for hatches (needs to be inside target)
	public static final double AutoStopMaxDistance        = 30.0;//Max distance to try to travel when moving towards target
	public static final double AutoDefaultLiftHeight      = 30.0;//Height to move lift to after picking up/depositing something
	public static final double AutoBackoffDistance        = 12.0;//Distance to back up after picking up/depositing something

	//Cargo constants
	public static final double CargoDepositLowHeight       = 3.0;
	public static final double CargoDepositMidHeight       = 29.0;
	public static final double CargoDepositHighHeight      = 57.0;
	public static final double CargoDepositRoverHeight     = 33.0;
	public static final double CargoRetrieveDepotHeight    = 40.0;
	public static final double CargoRetrieveGroundHeight   = 2.0;
	public static final int    CargoRetrieveLocationGround = 0;//Enumeration for different locations
	public static final int    CargoRetrieveLocationDepot  = 1;//Enumeration for different locations
	public static final double CargoEjectDelay             = 0.1;

	//Intake constants
	public static final boolean IntakeIn         = true;
	public static final boolean IntakeOut        = false;
	public static final double  IntakeInSpeed    = 0.75;
	public static final double  IntakeEjectSpeed = -1;
	public static final double  IntakeHoldSpeed  = 0.2;

	//Hatch constants
	//Heights are the height at which the robot should be at to be able to go through center ToDo : THESE HEIGHTS NEED TO BE CHANGED 
	public static final double  HatchDepositLowHeight       = 8.0;
	public static final double  HatchDepositMidHeight       = 34.0;
	public static final double  HatchDepositHighHeight      = 62.0;
	public static final double  HatchDepositRoverHeight     = 4.0;
	public static final double  HatchRetrieveDepotHeight    = 4.0;
	public static final double  HatchDepositDelta           = 4.0;//Distance to move down in order to unhook when depositing
	public static final double  HatchGrabDelta              = 4.0;//Distance to move up to grab when capturing
	public static final boolean HatchDownState              = false;//Flag to note if hook is up or down
	public static final boolean HatchUpState                = true;

	//Controller slots
	public static final int   MainController               = 0;
	public static final int   ButtonController             = 1;

	//Button allocations
	public static final int   TestButton                   = 1;
	public static final int   LiftMoveUpButton             = 2;
	public static final int   CargoIntakeOutButton         = 3;
	public static final int   LiftMoveDownButton           = 5;
	public static final int   CargoIntakeInButton          = 6;
	public static final int   CargoDepositLowButton        = 7;
	public static final int   CargoDepositMidButton        = 8;
	public static final int   CargoDepositHighButton       = 9;
	public static final int   CargoDepositRoverButton      = 10;
	public static final int   HatchDepositLowButton        = 11;
	public static final int   HatchDepositMidButton        = 12;
	public static final int   HatchDepositHighButton       = 13;
	public static final int   CargoRetrieveGroundButton    = 14;
	public static final int   HatchRetrieveDepotButton     = 15;
	public static final int   CargoRetrieveDepotButton     = 16;
	public static final int   HatchDepositRoverButton      = 11;
	
	//Controller button allocations
	public static int   LiftExtendButton             = 1;
	public static int   LiftRetractButton            = 2;
	public static int   LiftUpButton                 = 3;
	public static int   LiftDownButton               = 4;
	public static int   HatchLiftButton              = 5;
	public static int   HatchDropButton              = 6;

	//Lift PID parameters
	public static final int     LiftkPIDkSlotIdx     = 0;
	public static final int     LiftkkPIDLoopIdx     = 0;
	public static final int     LiftkTimeoutMs       = 30;
	public static final boolean LiftkSensorPhase     = true;
	public static final boolean LiftkMotorInvert     = true;
	//kp, ki, kd, kf, izone, peak output
	//ToDo : Need to set these parameters
	public static final double  LiftPIDkP             = 0.3;
	public static final double  LiftPIDkI             = 0.0;
	public static final double  LiftPIDkD             = 1.0;
	public static final double  LiftPIDkF             = 0.0;
	public static final double  LiftPIDizone          = 0.0;
	public static final double  LiftPIDpeakoutputUp   = 1.0;
	public static final double  LiftPIDpeakoutputDown = 0.6;
	public static final int     LiftPIDmaxerror       = 1000;

	//Drivetrain PID parameters
	//Joystick parameters
	public static final double  JoystickAccelleration = 0.1;
	public static final double  JoystickDecelleration = 0.07;
	public static final double  CrawlSpeed            = 0.15;

	//PID parameters
	public static final int     DrivekPIDkSlotIdx     = 1;
	public static final int     DrivekkPIDLoopIdx     = 0;
	public static final int     DrivekTimeoutMs       = 30;
	public static final boolean DrivekSensorPhase     = true;
	public static final boolean DrivekMotorInvert     = true;
	//kp, ki, kd, kf, izone, peak output
	//ToDo : Need to set these parameters
	public static final double  DrivePIDkP            = 0.2;
	public static final double  DrivePIDkI            = 0.0;
	public static final double  DrivePIDkD            = 1.0;
	public static final double  DrivePIDkF            = 0.0;
	public static final double  DrivePIDizone         = 0.0;
	public static final double  DrivePIDpeakoutput    = 1.0;
	public static final int     DrivePIDmaxerror      = 0;


	/**
	 * Which PID slot to pull gains from. Starting 2018, you can choose from
	 * 0,1,2 or 3. Only the first two (0,1) are visible in web-based
	 * configuration.
	 */
//	public static final int kSlotIdx = 0;

	/**
	 * Talon SRX/ Victor SPX will supported multiple (cascaded) PID loops. For
	 * now we just want the primary one.
	 */
//	public static final int kPIDLoopIdx = 0;

	/**
	 * Set to zero to skip waiting for confirmation, set to nonzero to wait and
	 * report to DS if action fails.
	 */
	//public static final int kTimeoutMs = 30;
	
	/* Choose so that Talon does not report sensor out of phase */
	//public static boolean kSensorPhase = true;

	/**
	 * Choose based on what direction you want to be positive,
	 * this does not affect motor invert. 
	 */
	//public static boolean kMotorInvert = true;

	/**
	 * Gains used in Positon Closed Loop, to be adjusted accordingly
     * Gains(kp, ki, kd, kf, izone, peak output);
     */
	//static final Gains kGains = new Gains(0.02, 0.0, 1.0, 0.0, 0, 1.0);
	//public static final Gains kGains = new Gains(0.3, 0.0, 1.0, 0.0, 0, 1.0);
}
