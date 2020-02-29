/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package team.gif.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.SparkMax;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import team.gif.lib.autoMode;
import team.gif.lib.delay;
import team.gif.robot.commands.autos.Mobility;
import team.gif.robot.commands.autos.ShootCollectShoot;
import team.gif.robot.commands.drivetrain.Drive;
import team.gif.robot.commands.indexer.IndexerScheduler;
import team.gif.robot.subsystems.Drivetrain;
import team.gif.robot.subsystems.Indexer;
import team.gif.robot.subsystems.Shooter;
import team.gif.robot.subsystems.drivers.Limelight;
import edu.wpi.first.wpilibj.DriverStation;
import team.gif.robot.subsystems.drivers.Pigeon;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
// for init commit
public class Robot extends TimedRobot {
  private Command m_autonomousCommand = null;
  private Command driveCommand = new Drive(Drivetrain.getInstance());
  private Command indexCommand = new IndexerScheduler();

  private SendableChooser<autoMode> autoModeChooser = new SendableChooser<>();
  private SendableChooser<delay> delayChooser = new SendableChooser<>();

  private autoMode chosenAuto;
  private delay chosenDelay;

  public static Limelight limelight;
  private final Compressor compressor = new Compressor();

  //private NetworkTableEntry pressureEntry;

  private RobotContainer m_robotContainer;

  public static ShuffleboardTab Autotab;
  public static ShuffleboardTab Teletab;
  public static ShuffleboardTab Endgametab;

  public static ShuffleboardTab Shootertab;
  public static ShuffleboardTab Drivetraintab;
  public static ShuffleboardTab IndexerIntaketab;
  public static ShuffleboardTab Climbertab;
  public static ShuffleboardTab PigeonLimelighttab;




  public OI oi;
  private final Drivetrain drivetrain = Drivetrain.getInstance();

  public static final boolean isCompBot = true;

  public CANSparkMax climbermotor = new CANSparkMax(RobotMap.CLIMBER, CANSparkMaxLowLevel.MotorType.kBrushless);
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {

    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    tabsetup();
    climbermotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();
    oi = new OI();
    limelight = new Limelight();
    updateauto();
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
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.

    chosenAuto = autoModeChooser.getSelected();
    chosenDelay = delayChooser.getSelected();

    CommandScheduler.getInstance().run();

  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
    CommandScheduler.getInstance().run();
  }

  /**
   * This autonomous runs the autonomous command selected by your {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {
    updateauto();
    compressor.stop();
    indexCommand.schedule();

    // schedule the autonomous command (example)
    /*if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
      compressor.stop();
    }*/
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    double matchTime = DriverStation.getInstance().getMatchTime();
    boolean runAuto = false;

    if (matchTime < (15.0 - chosenDelay.getValue()) && !runAuto) {
      if (m_autonomousCommand != null) {
        m_autonomousCommand.schedule();
      }
      runAuto = true;
    }
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
    climbermotor.set(0);
    compressor.start();
    driveCommand.schedule();
    indexCommand.schedule();
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    climbermotor.set(0);
    CommandScheduler.getInstance().run();

    boolean state = Indexer.getInstance().getKnopf();
    //SmartDashboard.putBoolean("High/Low", state);

    // Rumble the joysticks at specified time
    // to notify the driver to begin to climb
    double matchTime = DriverStation.getInstance().getMatchTime();
    //System.out.println("Match time: " + matchTime);
    oi.setRumble(matchTime > 18.0 && matchTime < 22.0);
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

  public void tabsetup(){
    //setup tabs
    Autotab = Shuffleboard.getTab("auto tab");
    Teletab = Shuffleboard.getTab("Tele tab");
    Endgametab = Shuffleboard.getTab("EndGame tab");

    Shootertab = Shuffleboard.getTab("Shooter Tab");
    Drivetraintab = Shuffleboard.getTab("Drivetrain Tab");
    IndexerIntaketab = Shuffleboard.getTab("Indexer+Intake tab");
    PigeonLimelighttab = Shuffleboard.getTab("Pigeon+Limelight tab");

    Climbertab = Shuffleboard.getTab("Climber tab");

    //setup the auto choosers
    autoModeChooser.setDefaultOption("Mobility", autoMode.MOBILITY);
    autoModeChooser.addOption("5 Ball Auto", autoMode.SHOOTCOLLECTSHOOT);
    Autotab.add("Auto Select",autoModeChooser).withWidget(BuiltInWidgets.kComboBoxChooser);

    //setup delay choosers
    delayChooser.setDefaultOption("0", delay.DELAY_0);
    //adding options to the delay chooser
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


    Autotab.add("Delay", delayChooser);
  }

  public void updateShuffle(){

    // the stuff i want to see on the board

    //indexer
    IndexerIntaketab.add("One", Indexer.getInstance().getState()[1]);
    IndexerIntaketab.add("Two", Indexer.getInstance().getState()[2]);
    IndexerIntaketab.add("Three", Indexer.getInstance().getState()[3]);
    IndexerIntaketab.add("Four", Indexer.getInstance().getState()[4]);
    IndexerIntaketab.add("Five", Indexer.getInstance().getState()[5]);

    //Pigeon Limelight tab
    PigeonLimelighttab.add("tx",limelight.getXOffset());
    PigeonLimelighttab.add("ty",limelight.getYOffset());
    PigeonLimelighttab.add("area",limelight.getArea());

    PigeonLimelighttab.add("Limelight 3D X",limelight.getCamTran()[0]);
    PigeonLimelighttab.add("Limelight 3D Y",limelight.getCamTran()[1]);
    PigeonLimelighttab.add("Limelight 3D Z",limelight.getCamTran()[2]);
    PigeonLimelighttab.add("Limelight 3D yaw",limelight.getCamTran()[3]);
    PigeonLimelighttab.add("Limelight 3D pitch",limelight.getCamTran()[4]);
    PigeonLimelighttab.add("Limelight 3D roll",limelight.getCamTran()[5]);
    PigeonLimelighttab.add("Limelight hastarget",limelight.hasTarget());

    PigeonLimelighttab.add("Gyro YPR", Pigeon.getInstance().getYPR());
    PigeonLimelighttab.add("Gyro Accel angles", Pigeon.getInstance().getAccelAngles());
    PigeonLimelighttab.add("Gyro Acumulated Gyro", Pigeon.getInstance().getAccumulatedGyro());
    PigeonLimelighttab.add("Gyro Biased Accel", Pigeon.getInstance().getBiasedAccel());
    PigeonLimelighttab.add("Gyro biasedMagnetometer", Pigeon.getInstance().getBiasedMagnetometer());
    PigeonLimelighttab.add("Gyro Quaternions", Pigeon.getInstance().getQuaternions());
    PigeonLimelighttab.add("Gyro rawGyro", Pigeon.getInstance().getrawGyro());
    PigeonLimelighttab.add("Gyro RawMagnet", Pigeon.getInstance().getRawMagnet());
    PigeonLimelighttab.add("Gyro Temp", Pigeon.getInstance().getTemp());

    //shooter tab
    Shootertab.add("RPM", Shooter.getInstance().getVelocity());
    Shootertab.add("hastarget",limelight.hasTarget());


    // teleop tab
    Teletab.add("Pressure", compressor.getPressureSwitchValue());
    Teletab.add("Enable Indexer", Globals.indexerEnabled);
    Teletab.add("tx",limelight.getXOffset());
    Teletab.add("ty",limelight.getYOffset());

    Teletab.add("One", Indexer.getInstance().getState()[1]);
    Teletab.add("Two", Indexer.getInstance().getState()[2]);
    Teletab.add("Three", Indexer.getInstance().getState()[3]);
    Teletab.add("Four", Indexer.getInstance().getState()[4]);
    Teletab.add("Five", Indexer.getInstance().getState()[5]);

    //drivetrain tab


  }

  public void updateauto(){
    if(chosenAuto == autoMode.MOBILITY){
      m_autonomousCommand = new Mobility();
      System.out.println("mobility selected");
    } else if(chosenAuto == autoMode.SHOOTCOLLECTSHOOT){
      m_autonomousCommand = new ShootCollectShoot();
      System.out.println("shootcollectshoot was chosen");
    }else if(chosenAuto ==null) {
      System.out.println("Auto is null");
    }

    System.out.println("auto " + chosenAuto);

  }
}