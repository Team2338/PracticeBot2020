package team.gif.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import team.gif.robot.commands.ExampleCommand;
import team.gif.robot.subsystems.ExampleSubsystem;

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
    * Example Trajectory which uses start, list.of positions, and end.
    *   List does not contain angle of direction
    */
    /*
    Trajectory exampleTrajectory = TrajectoryGenerator.generateTrajectory(
        // Start at the origin facing the +X direction
        new Pose2d(0, 0, new Rotation2d(0)),
        // Pass through these two interior waypoints, making an 's' curve path
        List.of(
            new Translation2d(Units.feetToMeters(2.0), Units.feetToMeters(-1.0)),
            new Translation2d(Units.feetToMeters(5.0), Units.feetToMeters(1.0))
        ),
        // End 7 feet straight ahead of where we started, facing forward
        new Pose2d(Units.feetToMeters(7.0), 0, new Rotation2d(0)),
        configForward
    );
    */

   /**
    * Example Trajectory which uses list.of positions each of which contains
    *   angle of direction (from 180 to -180, 0 is facing forward)
    *       + angle is counterclockwise
    *       - angle is clockwise
    *
    *   Position:
    *       +x is forward
    *       +y is left
    *
    *   Use configForward for driving forward
    *   Use configReverse for driving backward
    *   Can not combine forward and reverse into same trajectory
    *
    *   If initially driving backward, start at 0,0 and go to -x
    *   e.g.
    *       new Pose2d( 0, 0, new Rotation2d(0)),
    *       new Pose2d(-3, 0, new Rotation2d(0))
    *
    *   multiple trajectories need to carry over from each other
    *   e.g. the following example is not the correct way to code the trajectory to end up in the same spot as the start
    *       new Pose2d(0, 0, new Rotation2d(0)), <= init position
    *       new Pose2d(3, 0, new Rotation2d(0))  <= move forward 3 meters
    *     and
    *       new Pose2d( 0, 0, new Rotation2d(0)), <= goes to 0,0 (moves backward 3 meters
    *       new Pose2d(-3, 0, new Rotation2d(0))  <= moves backward another 3 meters
    *     resulting in the bot moving forward 3 meters and then backward 6 meters, net backward 3 meters
    *
    */
    /*
    Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
        List.of(
            new Pose2d(Units.feetToMeters(0.0), 0, new Rotation2d(0)),
            new Pose2d(Units.feetToMeters(9.0), 0, new Rotation2d(Units.degreesToRadians(45)))
        ),
        configForward
    );
    */
}