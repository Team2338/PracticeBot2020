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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import team.gif.robot.commands.drivetrain.Drive;
import team.gif.robot.commands.indexer.IndexerScheduler;
import team.gif.robot.subsystems.Drivetrain;
import team.gif.robot.subsystems.Indexer;
import team.gif.robot.subsystems.Shooter;
import team.gif.robot.subsystems.drivers.Limelight;
import edu.wpi.first.wpilibj.DriverStation;
import team.gif.robot.subsystems.ColorSensor;

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

  public static Limelight limelight;
  private final Compressor compressor = new Compressor();

  //private NetworkTableEntry pressureEntry;

  private RobotContainer m_robotContainer;

  public OI oi;
  private final Drivetrain drivetrain = Drivetrain.getInstance();
  private final ColorSensor colorsensor = ColorSensor.getInstance();

  public static final boolean isCompBot = false;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {

    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();
    oi = new OI();
    limelight = new Limelight();
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

    SmartDashboard.putBoolean("One", Indexer.getInstance().getState()[1]);
    SmartDashboard.putBoolean("Two", Indexer.getInstance().getState()[2]);
    SmartDashboard.putBoolean("Three", Indexer.getInstance().getState()[3]);
    SmartDashboard.putBoolean("Four", Indexer.getInstance().getState()[4]);
    SmartDashboard.putBoolean("Five", Indexer.getInstance().getState()[5]);


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

    //ColorSensor
    String detectedColor = ColorSensor.getInstance().getColor();
//    SmartDashboard.putNumber("Red", detectedColor.red);
//    SmartDashboard.putNumber("Green", detectedColor.green);
//    SmartDashboard.putNumber("Blue", detectedColor.blue);
//    SmartDashboard.putNumber("Confidence", match.confidence);
    SmartDashboard.putString("Detected Color", detectedColor);

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
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
      compressor.stop();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
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

    // Rumble the joysticks at specified time
    // to notify the driver to begin to climb
    double matchTime = DriverStation.getInstance().getMatchTime();
    System.out.println("Match time: " + matchTime);
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
}
