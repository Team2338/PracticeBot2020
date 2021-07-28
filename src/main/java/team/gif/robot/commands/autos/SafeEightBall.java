package team.gif.robot.commands.autos;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.*;
import team.gif.lib.Pose2dFeet;
import team.gif.lib.RobotTrajectory;
import team.gif.robot.Robot;
import team.gif.robot.commands.autoaim.LimelightAutoAim;
import team.gif.robot.commands.autoaim.Pivot;
import team.gif.robot.commands.drivetrain.AutoDrive;
import team.gif.robot.commands.drivetrain.forward;
import team.gif.robot.commands.intake.IntakeDown;
import team.gif.robot.commands.intake.IntakeRun;
import team.gif.robot.commands.shooter.Fire;
import team.gif.robot.commands.shooter.RapidFire;
import team.gif.robot.commands.shooter.RevFlywheel;
import team.gif.robot.subsystems.Drivetrain;

import java.util.List;


public class SafeEightBall extends SequentialCommandGroup {

    private static double m_reverseOffset = 0.3;  // larger numbers move bot left (away from sideline)

    public Command reverse () {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                List.of(
                        new Pose2dFeet().set(0.0, 0.0, 0.0),
                        new Pose2dFeet().set(-11.0, 0.0, 0.0)
                ),
                RobotTrajectory.getInstance().configReverse
        );
        // create the command using the trajectory
        RamseteCommand rc = RobotTrajectory.getInstance().createRamseteCommand(trajectory);
        // Run path following command, then stop at the end.
        return rc.andThen(() -> Drivetrain.getInstance().tankDriveVolts(0, 0));
    }
    public Command forward () {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                List.of(
                        new Pose2dFeet().set(-11.0, 0.0, 0.0),
                        new Pose2dFeet().set(-7.0, 1.0, -14.0)
                ),
                RobotTrajectory.getInstance().configForwardFast
        );
        // create the command using the trajectory
        RamseteCommand rc = RobotTrajectory.getInstance().createRamseteCommand(trajectory);
        // Run path following command, then stop at the end.
        return rc.andThen(() -> Drivetrain.getInstance().tankDriveVolts(0, 0));
    }
    public Command reverseAgain () {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                List.of(
                        new Pose2dFeet().set(-7.0, 1.0, -14.0),
                        new Pose2dFeet().set(-14.0, m_reverseOffset, 0.0),
                        new Pose2dFeet().set(-19, m_reverseOffset, 0.0)
                ),
                RobotTrajectory.getInstance().configReverseSlow
        );
        // create the command using the trajectory
        RamseteCommand rc = RobotTrajectory.getInstance().createRamseteCommand(trajectory);
        // Run path following command, then stop at the end.
        return rc.andThen(() -> Drivetrain.getInstance().tankDriveVolts(0, 0));
    }
    public Command forwardAgain () {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                List.of(
                        new Pose2dFeet().set(-19, m_reverseOffset, 0.0),
                        new Pose2dFeet().set(-14.0, m_reverseOffset, 0.0),
                        new Pose2dFeet().set(-7.0, 1.0, 0.0) // -9
                ),
                RobotTrajectory.getInstance().configForwardFast
        );
        // create the command using the trajectory
        RamseteCommand rc = RobotTrajectory.getInstance().createRamseteCommand(trajectory);
        // Run path following command, then stop at the end.
        return rc.andThen(() -> Drivetrain.getInstance().tankDriveVolts(0, 0));
    }

    public SafeEightBall() {
        System.out.println("Auto: Safe Eight Ball Selected");

        // still under development, right now just drives backward and
        // turns ~45 degrees, moving to the right 3 feet
        addCommands(

                new PrintCommand("Auto: Safe Eight Ball Started"),
                new IntakeDown(),
                new ParallelDeadlineGroup(
                        reverse(),
                        new IntakeRun()),
                new ParallelDeadlineGroup(
                        forward(),
                        new RevFlywheel()),
                new ParallelDeadlineGroup(
                        new RevFlywheel().withTimeout(1.75),
                        new RapidFire()),
                new ParallelDeadlineGroup(
                        reverseAgain(),
                        new IntakeRun()),
                new IntakeRun().withTimeout(0.75),
                new ParallelDeadlineGroup(
                        forwardAgain(),
                        new RevFlywheel()),
                new ParallelDeadlineGroup(
                        new RevFlywheel().withTimeout(1.75),
                        new LimelightAutoAim()),
                new PrintCommand("Auto: Safe Eight Ball Ended")
        );
    }
}
