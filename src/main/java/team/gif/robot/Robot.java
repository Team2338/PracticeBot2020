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
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import team.gif.lib.autoMode;
import team.gif.lib.delay;
import team.gif.robot.commands.autos.Mobility;
import team.gif.robot.commands.autos.ShootCollectShoot;
import team.gif.robot.commands.drivetrain.Drive;
import team.gif.robot.commands.hanger.HangerManualControl;
import team.gif.robot.commands.indexer.IndexerScheduler;
import team.gif.robot.subsystems.Drivetrain;
import team.gif.robot.subsystems.Hanger;
import team.gif.robot.subsystems.Indexer;
import team.gif.robot.subsystems.Shooter;
import team.gif.robot.subsystems.drivers.Limelight;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
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

    SmartDashboard.putBoolean("One", Indexer.getInstance().getState()[1]);
    SmartDashboard.putBoolean("Two", Indexer.getInstance().getState()[2]);
    SmartDashboard.putBoolean("Three", Indexer.getInstance().getState()[3]);
    SmartDashboard.putBoolean("Four", Indexer.getInstance().getState()[4]);
    SmartDashboard.putBoolean("Five", Indexer.getInstance().getState()[5]);

    //limelight.setStreamMode(0);
    //the jyoonk i want to see on the board
    SmartDashboard.putNumber("tx",limelight.getXOffset());
    SmartDashboard.putNumber("ty",limelight.getYOffset());

    /*
    SmartDashboard.putNumber(" 3D X",limelight.getCamTran()[0]);
    SmartDashboard.putNumber(" 3D Y",limelight.getCamTran()[1]);
    SmartDashboard.putNumber(" 3D Z",limelight.getCamTran()[2]);
    SmartDashboard.putNumber(" 3D yaw",limelight.getCamTran()[3]);
    SmartDashboard.putNumber(" 3D pitch",limelight.getCamTran()[4]);
    SmartDashboard.putNumber(" 3D roll",limelight.getCamTran()[5]);
*/
    SmartDashboard.putNumber("RPM", Shooter.getInstance().getVelocity());
    //System.out.println("tx"+limelight.getXOffset());
    //System.out.println("ty"+limelight.getYOffset());
    SmartDashboard.putBoolean("hastarget",limelight.hasTarget());
    CommandScheduler.getInstance().run();

    // pneumatics
    SmartDashboard.putBoolean("Pressure", compressor.getPressureSwitchValue());
    //SmartDashboard.putNumber("Pressure", 250 * (pressureSensor.getAverageVoltage() / RobotController.getVoltage5V()));

    SmartDashboard.putBoolean("Enable Indexer", Globals.indexerEnabled);

    // Hanger
    //--SmartDashboard.putNumber("Hang Position", Hanger.getInstance().getPosition());
    //--SmartDashboard.putNumber("Hanger Output", OI.getInstance().aux.getY(GenericHID.Hand.kLeft));
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

    // TODO: also remove
    //--hangerCommand.schedule();
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
    //setp tabs
    Autotab = Shuffleboard.getTab("auto");

    autoModeChooser.setDefaultOption("Mobility", autoMode.MOBILITY);
    autoModeChooser.addOption("5 Ball Auto", autoMode.SHOOTCOLLECTSHOOT);

    Autotab.add("Auto Select",autoModeChooser).withWidget(BuiltInWidgets.kComboBoxChooser);

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


    Autotab.add("Delay", delayChooser);
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