/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package team.gif.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import team.gif.robot.commands.ExampleCommand;
import team.gif.robot.subsystems.Drivetrain;
import team.gif.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

import java.util.List;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);



  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {

      // Creates a voltage constraint to prevent excessive acceleration
      var autoVoltageConstraint = new DifferentialDriveVoltageConstraint(
              new SimpleMotorFeedforward(
                      Constants.TrajectoryConstants.ksVolts,
                      Constants.TrajectoryConstants.kvVoltsSecondsPerMeter,
                      Constants.TrajectoryConstants.kaVoltSecondsSquaredPerMeter),
              Constants.TrajectoryConstants.kDriveKinematics, 10);

      // Create config for trajectory
      TrajectoryConfig config = new TrajectoryConfig(Constants.TrajectoryConstants.kMaxMetersPerSecond,
              Constants.TrajectoryConstants.kMaxAccelerationMetersPerSecondSquared)
              // Add kinematics to make sure the max speed is obeyed
              .setKinematics(Constants.TrajectoryConstants.kDriveKinematics)
              // Apply the voltage constraint defined above
              .addConstraint(autoVoltageConstraint);

      // Test trajectory: basicMobility
      Trajectory basicMobility = TrajectoryGenerator.generateTrajectory(
              // Start at the origin facing the positive-X direction
              new Pose2d(0, 0, new Rotation2d(0)),
              // Pass through these two interior waypoints -> Makes an 'S' curve path
              List.of(
                    new Translation2d(1, 1),
                    new Translation2d(2, -1)
              ),
              // End 3 meters straight ahead of where the bot started, facing forward
              new Pose2d(3, 0, new Rotation2d(0)),
              // Pass config
              config
      );

      // RamseteCommand to keep track of trajectory
      RamseteCommand ramseteCommand = new RamseteCommand(
              basicMobility,
              Drivetrain::getPose,
              new RamseteController(Constants.RamseteConstants.kRamseteB, Constants.RamseteConstants.kRamseteZeta),
              new SimpleMotorFeedforward(Constants.TrajectoryConstants.ksVolts,
                      Constants.TrajectoryConstants.kvVoltsSecondsPerMeter,
                      Constants.TrajectoryConstants.kaVoltSecondsSquaredPerMeter),
              Constants.TrajectoryConstants.kDriveKinematics,
//              Drivetrain::getLeftDistancePerPulse
      )

  }
}
