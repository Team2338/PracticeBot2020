
package team.gif.robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import team.gif.lib.autoMode;
import team.gif.lib.delay;
import team.gif.robot.commands.autos.*;
import team.gif.robot.commands.drivetrain.Drive;
import team.gif.robot.commands.hanger.ResetHanger;
import team.gif.robot.commands.drivetrain.ResetHeading;
import team.gif.robot.commands.indexer.IndexerScheduler;
import team.gif.robot.subsystems.Drivetrain;
import team.gif.robot.subsystems.Hanger;
import team.gif.robot.subsystems.Indexer;
import team.gif.robot.subsystems.Shooter;
import team.gif.robot.subsystems.drivers.Limelight;
import edu.wpi.first.wpilibj.DriverStation;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  public static final boolean isCompBot = true;

  private boolean _runAutoScheduler = true;

  private Command m_autonomousCommand = null;

  private Command driveCommand = null; // new Drive(Drivetrain.getInstance());
  private Command indexCommand = new IndexerScheduler();

  private SendableChooser<autoMode> autoModeChooser = new SendableChooser<>();
  private SendableChooser<delay> delayChooser = new SendableChooser<>();

  private autoMode chosenAuto;
  private delay chosenDelay;
  private Timer _elapsedTime = new Timer();


  public static Limelight limelight;
  private final Compressor compressor = new Compressor();

  private RobotContainer m_robotContainer;

  public static ShuffleboardTab autoTab = Shuffleboard.getTab("PreMatch");
  private NetworkTableEntry allianceEntry = autoTab.add("Alliance","Startup")
                                                    .withPosition(3,0)
                                                    .withSize(1,1)
                                                    .getEntry();

  public static OI oi;
  public static Hanger hanger;
  private Drivetrain drivetrain = null; // Drivetrain.getInstance();
  //private final ColorSensor colorsensor = ColorSensor.getInstance();


  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    System.out.println("robot init");
    tabsetup();
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();
    hanger = new Hanger();
    hanger.zeroEncoder();

    driveCommand = new Drive(Drivetrain.getInstance());
    drivetrain = Drivetrain.getInstance();
    limelight = new Limelight();

    // Puts a button on the dashboard which sets the current
    // hanger position as the 0 position. Does this by calling
    // the commandBase specifically made for this ResetHanger()
    SmartDashboard.putData("Hanger", new ResetHanger());
    setLimelightPipeline();
    limelight.setLEDMode(1);//force off

    SmartDashboard.putData("ResetHead", new ResetHeading());
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {

    //System.out.println("robot periodic");
    chosenAuto = autoModeChooser.getSelected();
    chosenDelay = delayChooser.getSelected();

    SmartDashboard.putBoolean("One", Indexer.getInstance().getState()[1]);
    SmartDashboard.putBoolean("Two", Indexer.getInstance().getState()[2]);
    SmartDashboard.putBoolean("Three", Indexer.getInstance().getState()[3]);
    SmartDashboard.putBoolean("Four", Indexer.getInstance().getState()[4]);
    SmartDashboard.putBoolean("Five", Indexer.getInstance().getState()[5]);

//    SmartDashboard.putNumber("tx",limelight.getXOffset());
//    SmartDashboard.putNumber("ty",limelight.getYOffset());

    SmartDashboard.putString("RPM", Shooter.getInstance().getVelocity_Shuffleboard());
//    SmartDashboard.putBoolean("hastarget",limelight.hasTarget());
    CommandScheduler.getInstance().run();

    // pneumatics
//    SmartDashboard.putBoolean("Pressure", compressor.getPressureSwitchValue());

    SmartDashboard.putBoolean("Enable Indexer", Globals.indexerEnabled);

    // Hanger
    SmartDashboard.putString("Hanger Brake", Robot.hanger.getLockState());
    SmartDashboard.putString("Hang Position", Robot.hanger.getPosition_Shuffleboard());
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   */
  @Override
  public void disabledInit() {
    limelight.setLEDMode(1);//force off
  }

  @Override
  public void disabledPeriodic() {
  }

  /**
   * This autonomous runs the autonomous command selected by your {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {
    System.out.println("autonomous init");

    // used for delaying the start of autonomous
    _elapsedTime.reset();
    _elapsedTime.start();

    drivetrain.resetEncoders();
    drivetrain.resetPose();
    drivetrain.resetPigeon();

    setLimelightPipeline();
    limelight.setLEDMode(1);//force off
    updateauto();
    compressor.stop();
    indexCommand.schedule();
    _runAutoScheduler = true;
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {

      if ( (_elapsedTime.get() > (chosenDelay.getValue())) && _runAutoScheduler) {
          if (m_autonomousCommand != null) {
              System.out.println("Delay over. Auto selection scheduler started.");
              m_autonomousCommand.schedule();
          }
          _runAutoScheduler = false;
          _elapsedTime.stop();
      }
  }

  @Override
  public void teleopInit() {
    System.out.println("teleop init");

    setLimelightPipeline();
    limelight.setLEDMode(1);//force off
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
    oi = new OI();
    compressor.start();
    driveCommand.schedule();
    indexCommand.schedule();
  }

  @Override
  public void teleopPeriodic() {

    // run the color sensor during teleop
    /* may not need if periodic is called automatically */
//    ColorSensor.getInstance().periodic();

    // Rumble the joysticks at specified time
    // to notify the driver to begin to climb
    double matchTime = DriverStation.getInstance().getMatchTime();
    oi.setRumble(matchTime > 18.0 && matchTime < 22.0);
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {
  }

  @Override
  public void simulationInit() {
    //System.out.println("simulation init");
  }

  @Override
  public void simulationPeriodic(){
    //System.out.println("simulation periodic" );
  }

  public void tabsetup(){

    autoTab = Shuffleboard.getTab("PreMatch");

    autoModeChooser.addOption("Mobility", autoMode.MOBILITY);
    autoModeChooser.addOption("Fwd Mobility", autoMode.MOBILITY_FWD);
    autoModeChooser.addOption("3 Ball Auto", autoMode.SAFE_3_BALL);
    autoModeChooser.addOption("Opp 5 Ball Auto", autoMode.OPP_5_BALL);
    autoModeChooser.addOption("8 Ball Auto", autoMode.SAFE_8_BALL);
    autoModeChooser.setDefaultOption("6 Ball Auto", autoMode.SAFE_6_BALL);

    autoTab.add("Auto Select",autoModeChooser)
            .withWidget(BuiltInWidgets.kComboBoxChooser)
            .withPosition(1,0)
            .withSize(2,1);

    delayChooser.setDefaultOption("0", delay.DELAY_0);
    delayChooser.addOption("1", delay.DELAY_1);
    delayChooser.addOption("2", delay.DELAY_2);
    delayChooser.addOption("3", delay.DELAY_3);
    delayChooser.addOption("4", delay.DELAY_4);
    delayChooser.addOption("5", delay.DELAY_5);
    delayChooser.addOption("6", delay.DELAY_6);
    delayChooser.addOption("7", delay.DELAY_7);
    delayChooser.addOption("8", delay.DELAY_8);
    delayChooser.addOption("9", delay.DELAY_9);
    delayChooser.addOption("10", delay.DELAY_10);
    delayChooser.addOption("11", delay.DELAY_11);
    delayChooser.addOption("12", delay.DELAY_12);
    delayChooser.addOption("13", delay.DELAY_13);
    delayChooser.addOption("14", delay.DELAY_14);
    delayChooser.addOption("15", delay.DELAY_15);

    autoTab.add("Delay", delayChooser)
            .withPosition(0,0)
            .withSize(1,1);

    // calibration information
    // RGB_Shuffleboard
//    calibrationTab = Shuffleboard.getTab("Calibration");          // adds the calibration tab to the shuffleboard (getTab creates if not exist)
//    Shuffleboard.getTab("Calibration").add("Red",0);    // adds the Red text box, persists over power down
//    Shuffleboard.getTab("Calibration").add("Green",0);  // adds the Green text box, persists over power down
//    Shuffleboard.getTab("Calibration").add("Blue",0);   // adds the Blue text box, persists over power down
  }

  public void updateauto(){

    if(chosenAuto == autoMode.MOBILITY){
        m_autonomousCommand = new Mobility();
    } else if(chosenAuto == autoMode.MOBILITY_FWD){
        m_autonomousCommand = new MobilityFwd();
    } else if(chosenAuto == autoMode.SAFE_3_BALL){
        m_autonomousCommand = new SafeThreeBall();
    } else if(chosenAuto == autoMode.SAFE_6_BALL){
        m_autonomousCommand = new SafeSixBall();
    } else if(chosenAuto == autoMode.OPP_5_BALL){
        m_autonomousCommand = new OppFiveBall();
    } else if(chosenAuto == autoMode.SAFE_8_BALL){
      m_autonomousCommand = new SafeEightBall();
    }else if(chosenAuto ==null) {
        System.out.println("Autonomous selection is null. Robot will do nothing in auto :(");
    }
  }

  public void setLimelightPipeline(){/**sets the limelight pipeline to red side or blue side**/
    if( DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Blue ) {
      allianceEntry.setString("         Blue");
      limelight.setPipeline(0);
    } else if (DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Red) {
      allianceEntry.setString("          Red");
      limelight.setPipeline(1);
    } else {
      allianceEntry.setString("  !ERROR!");
    }
  }
}