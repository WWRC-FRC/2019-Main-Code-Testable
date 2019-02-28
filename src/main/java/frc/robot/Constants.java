/**
 * Simple class containing constants used throughout project
 */
package frc.robot;

public class Constants {
	//CAN bus channels for devices
	public static int CANLeftFrontMasterController    = 6;
	public static int CANRightFrontMasterController   = 7;
	public static int CANLeftFrontFollowerController  = 11;
	public static int CANRightFrontFollowerController = 9;
	public static int CANLiftMasterController         = 10;
	public static int CANLiftFollowerController       = 12;
	public static int CANIntakeController             = 8;

	//Pneumatic constants
	public static int PneuStroke1Channel              = 0;
	public static int PneuStroke2Channel              = 1;

	//Digital IO channels
	public static int DigUltrasonicPingChannel        = 1;
	public static int DigUltrasonicEchoChannel        = 2;

	//Drive train constants
	public static int    wheelCountsPerRev = 4096;
	public static double wheelDiameter = 6;//6 inch diameter
	public static double wheelCircumference = Math.PI*wheelDiameter;
	public static double wheelTicksPerInch = wheelCountsPerRev / wheelCircumference;
	public static int    SpeedMaxTicksPer100mS = 1000;//ToDo : Need to measure
	public static double DriveStraightPGain = 0.1;//ToDo : Need to tune
//	public final double encoderFeet = wheelCountsPerRev / wheelSize;
//	public final double encoderMeters = encoderFeet*3.281;

	//Lift constants
	public static double liftSprocketCircumference = 4;//16 teeth spaced at 1/4 inch
	public static double liftCountsPerRev = 4096;//ToDo : Check this
	public static double liftTicksPerInch = liftCountsPerRev / liftSprocketCircumference;
  
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
