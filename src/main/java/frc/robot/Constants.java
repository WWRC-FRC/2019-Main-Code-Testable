/**
 * Simple class containing constants used throughout project
 */
package frc.robot;

public class Constants {
	//CAN bus channels for devices
	public static int    CANLeftFrontMasterController    = 6;
	public static int    CANRightFrontMasterController   = 7;
	public static int    CANLeftFrontFollowerController  = 11;
	public static int    CANRightFrontFollowerController = 9;
	public static int    CANLiftMasterController         = 10;
	public static int    CANLiftFollowerController       = 12;
	public static int    CANIntakeController             = 8;

	//Pneumatic constants
	public static int     PneuStroke1Channel             = 0;
	public static int     PneuStroke2Channel             = 1;
	public static boolean PneuLiftOut                    = true;//ToDo : Check correct order
	public static boolean PneuLiftIn                     = false;

	//Digital IO channels
	public static int    DigUltrasonicPingChannel        = 1;
	public static int    DigUltrasonicEchoChannel        = 2;

	//Drive train constants
	public static int    WheelCountsPerRev               = 4096;
	public static double WheelDiameter                   = 6;//6 inch diameter
	public static double WheelCircumference              = Math.PI*WheelDiameter;
	public static double WheelTicksPerInch               = WheelCountsPerRev / WheelCircumference;
	public static int    SpeedMaxTicksPer100mS           = 1000;//ToDo : Need to measure
	public static double DriveStraightPGain              = 0.1;//ToDo : Need to tune

	//Lift constants
	public static double LiftSprocketCircumference       = 4;//16 teeth spaced at 1/4 inch
	public static double LiftCountsPerRev                = 4096;//ToDo : Check this
	public static double LiftTicksPerInch                = (LiftCountsPerRev / LiftSprocketCircumference) / 2;//Divided by 2 because of two-stage lift
	public static double LiftMaxHeight                   = 68;
	public static double LiftPositionTolerance           = 2;

	//Auto constants
	public static double AutoInSpeed                = 0.4;//Speed at which the semi-auto moves towards the target
	public static double AutoOutSpeed               = 0.6;//Speed at which the semi-auto moves away from the target
	public static double AutoStopFromDistanceCargo  = 6.0;//Distance from the target to stop for cargo (account for the hook)
	public static double AutoStopFromDistanceHatch  = 4.0;//Distance from the target to stop for hatches (needs to be inside target)
	public static double AutoStopMaxDistance        = 30.0;//Max distance to try to travel when moving towards target
	public static double AutoDefaultLiftHeight      = 30.0;//Height to move lift to after picking up/depositing something
	public static double AutoBackoffDistance        = 12.0;//Distance to back up after picking up/depositing something

	//Cargo constants
	public static double CargoDepositLowHeight       = 3.0;
	public static double CargoDepositMidHeight       = 29.0;
	public static double CargoDepositHighHeight      = 57.0;
	public static double CargoDepositRoverHeight     = 33.0;
	public static double CargoRetrieveDepotHeight    = 40.0;
	public static double CargoRetrieveGroundHeight   = 2.0;
	public static int    CargoRetrieveLocationGround = 0;//Enumeration for different locations
	public static int    CargoRetrieveLocationDepot  = 1;//Enumeration for different locations
	public static double CargoEjectDelay             = 0.1;

	//Intake constants
	public static boolean IntakeIn         = true;
	public static boolean IntakeOut        = false;
	public static double  IntakeInSpeed    = 0.75;
	public static double  IntakeEjectSpeed = -1;
	public static double  IntakeHoldSpeed  = 0.2;

	//Hatch constants
	//Heights are the height at which the robot should push at to deposit or move in at to retrieve
	public static double  HatchDepositLowHeight       = 8.0;
	public static double  HatchDepositMidHeight       = 34.0;
	public static double  HatchDepositHighHeight      = 62.0;
	public static double  HatchDepositRoverHeight     = 4.0;
	public static double  HatchRetrieveDepotHeight    = 4.0;
	public static double  HatchDepositDelta           = 4.0;//Distance to move down in order to unhook when depositing
	public static double  HatchGrabDelta              = 4.0;//Distance to move up to grab when capturing
	public static boolean HatchDownState              = false;//Flag to note if hook is up or down
	public static boolean HatchUpState                = true;

	//Button allocations
	public static int 	TestButton                   = 1;
	public static int 	LiftMoveUpButton             = 2;
	public static int   CargoIntakeOutButton         = 3;
	public static int   LiftMoveDownButton           = 5;
	public static int   CargoIntakeInButton          = 6;
	public static int   CargoDepositLowButton        = 7;
	public static int   CargoDepositMidButton        = 8;
	public static int   CargoDepositHighButton       = 9;
	public static int   CargoDepositRoverButton      = 10;
	public static int   HatchDepositLowButton        = 11;
	public static int   HatchDepositMidButton        = 12;
	public static int   HatchDepositHighButton       = 13;
	public static int   CargoRetrieveGroundButton    = 14;
	public static int   HatchRetrieveDepotButton     = 15;
	public static int   CargoRetrieveDepotButton     = 16;
	public static int   HatchDepositRoverButton      = 11;
	

	//Controller button allocations
	public static int   LiftExtendButton             = 1;
	public static int   LiftRetractButton            = 2;

	/**
	 * Which PID slot to pull gains from. Starting 2018, you can choose from
	 * 0,1,2 or 3. Only the first two (0,1) are visible in web-based
	 * configuration.
	 */
	public static final int kSlotIdx = 0;

	/**
	 * Talon SRX/ Victor SPX will supported multiple (cascaded) PID loops. For
	 * now we just want the primary one.
	 */
	public static final int kPIDLoopIdx = 0;

	/**
	 * Set to zero to skip waiting for confirmation, set to nonzero to wait and
	 * report to DS if action fails.
	 */
	public static final int kTimeoutMs = 30;
	
	/* Choose so that Talon does not report sensor out of phase */
	public static boolean kSensorPhase = true;

	/**
	 * Choose based on what direction you want to be positive,
	 * this does not affect motor invert. 
	 */
	public static boolean kMotorInvert = true;

	/**
	 * Gains used in Positon Closed Loop, to be adjusted accordingly
     * Gains(kp, ki, kd, kf, izone, peak output);
     */
	//static final Gains kGains = new Gains(0.02, 0.0, 1.0, 0.0, 0, 1.0);
	public static final Gains kGains = new Gains(0.3, 0.0, 1.0, 0.0, 0, 1.0);
}
