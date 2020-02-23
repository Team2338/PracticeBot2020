/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package team.gif.robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import team.gif.lib.chosenAuto;
import team.gif.robot.commands.drivetrain.Drive;
import team.gif.robot.commands.indexer.IndexerScheduler;
import team.gif.robot.subsystems.Drivetrain;
import team.gif.robot.subsystems.Indexer;
import team.gif.robot.subsystems.Shooter;
import team.gif.robot.subsystems.drivers.Limelight;
//import team.gif.robot.commands.autos.IndexerScheduler;
import team.gif.robot.commands.autos.*;
import team.gif.robot.subsystems.drivers.Pigeon;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand = null;
  private Command driveCommand = new Drive();
  private Command indexCommand = new IndexerScheduler();
  private SendableChooser<chosenAuto> autoModeChooser = new SendableChooser<>();

  private chosenAuto auto;

  public static Limelight limelight;
  private final Compressor compressor = new Compressor();

  //private NetworkTableEntry pressureEntry;

  private RobotContainer m_robotContainer;

  public static ShuffleboardTab autotab;
  public static ShuffleboardTab teleoptab;
  public static ShuffleboardTab indextab;//includes intake
  public static ShuffleboardTab shootertab;
  public static ShuffleboardTab climbertab ;
  public static ShuffleboardTab drivetraintab;//includes compressor and pigeon

  public OI oi;
  private final Drivetrain drivetrain = Drivetrain.getInstance();

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {

    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    tabsetup();
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

    auto = autoModeChooser.getSelected();
    autotab.add("auto selected",auto);

    updateDashboard();


    CommandScheduler.getInstance().run();

    // pneumatics
    //SmartDashboard.putNumber("Pressure", 250 * (pressureSensor.getAverageVoltage() / RobotController.getVoltage5V()));
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
    //m_autonomousCommand = m_robotContainer.getAutonomousCommand();
    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
      compressor.stop();
    }
    indexCommand.schedule();
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    CommandScheduler.getInstance().run();
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

    compressor.start();
    driveCommand.schedule();
    indexCommand.schedule();
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    CommandScheduler.getInstance().run();

    boolean state = Indexer.getInstance().getKnopf();
    //SmartDashboard.putBoolean("High/Low", state);
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

  public void tabsetup() {
    /**seting up tabs
     * TODO : auto tab needs to display current individual command bieng run
     * **/
    autotab = Shuffleboard.getTab("auto");
    teleoptab = Shuffleboard.getTab("teleop");
    indextab = Shuffleboard.getTab("intake");
    shootertab = Shuffleboard.getTab("limelight/shooter");
    climbertab = Shuffleboard.getTab("climber");

    //adding autos to autotab
    autoModeChooser.addOption("Mobility", chosenAuto.MOBILITY);
    autoModeChooser.addOption("Move and shoot", chosenAuto.Shootcollectshoot);
    autotab.add("Select an Auto ", autoModeChooser).withWidget(BuiltInWidgets.kComboBoxChooser);
  }

  public void updateauto(){

    if(auto ==chosenAuto.MOBILITY){
      m_autonomousCommand = new Mobility();
      System.out.println("mobility selected");
    }else if(auto ==chosenAuto.Shootcollectshoot){
      m_autonomousCommand = new shootcollectshoot();
      System.out.println("shootcollectshoot selected");
    }
    System.out.println("selected auto :"+auto);
  }

  public void updateDashboard(){
    // runs periodically, updates each of the tabs with relavant data
    teleoptab.add("One", Indexer.getInstance().getState()[1]);
    teleoptab.add("Two", Indexer.getInstance().getState()[2]);
    teleoptab.add("Three", Indexer.getInstance().getState()[3]);
    teleoptab.add("Four", Indexer.getInstance().getState()[4]);
    teleoptab.add("Five", Indexer.getInstance().getState()[5]);

    teleoptab.add("tx",limelight.getXOffset());
    teleoptab.add("hastarget",limelight.hasTarget());

    teleoptab.add("Pressure", compressor.getPressureSwitchValue());

    indextab.add("One", Indexer.getInstance().getState()[1]);
    indextab.add("Two", Indexer.getInstance().getState()[2]);
    indextab.add("Three", Indexer.getInstance().getState()[3]);
    indextab.add("Four", Indexer.getInstance().getState()[4]);
    indextab.add("Five", Indexer.getInstance().getState()[5]);

    shootertab.add("tx",limelight.getXOffset());
    shootertab.add("ty",limelight.getYOffset());
    shootertab.add("hastarget",limelight.hasTarget());
    shootertab.add("turned",Constants.DriverCommands.turned);
    shootertab.add("RPM", Shooter.getInstance().getVelocity());
    shootertab.add("latency",limelight.getLatency());

    drivetraintab.add("Pigeon yaw", Pigeon.getInstance().getYPR()[0]);
    drivetraintab.add("Pigeon yaw", Pigeon.getInstance().getYPR()[1]);
    drivetraintab.add("Pigeon yaw", Pigeon.getInstance().getYPR()[2]);

    drivetraintab.add("Pressure", compressor.getPressureSwitchValue());

  }
}
