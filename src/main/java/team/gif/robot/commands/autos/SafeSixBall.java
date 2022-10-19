package team.gif.robot.commands.autos;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.*;
import team.gif.lib.Pose2dFeet;
import team.gif.lib.RobotTrajectory;
import team.gif.robot.commands.intake.IntakeDown;
import team.gif.robot.commands.intake.IntakeRun;
import team.gif.robot.commands.shooter.Fire;
import team.gif.robot.commands.shooter.RevFlywheel;
import team.gif.robot.subsystems.Drivetrain;

import java.util.List;


public class SafeSixBall extends SequentialCommandGroup {
    public Command reverse () {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                List.of(
                        new Pose2dFeet().set(0.0, 0.0, 0.0),
                        new Pose2dFeet().set(-11.0, 0.0, 0.0)
                ),
                RobotTrajectory.getInstance().configReverseSlow
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
                        new Pose2dFeet().set(-7.0, 1.0, -12.0)
                ),
                RobotTrajectory.getInstance().configForward
        );
        // create the command using the trajectory
        RamseteCommand rc = RobotTrajectory.getInstance().createRamseteCommand(trajectory);
        // Run path following command, then stop at the end.
        return rc.andThen(() -> Drivetrain.getInstance().tankDriveVolts(0, 0));
    }
    public Command reverseAgain () {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                List.of(
                        new Pose2dFeet().set(-7.0, 1.0, -12.0),
                        new Pose2dFeet().set(-14.0, 0.0, 0.0)
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
                        new Pose2dFeet().set(-14.0, 0.0, 0.0),
                        new Pose2dFeet().set(-11.0, 1.0, -12.0)
                ),
                RobotTrajectory.getInstance().configForward
        );
        // create the command using the trajectory
        RamseteCommand rc = RobotTrajectory.getInstance().createRamseteCommand(trajectory);
        // Run path following command, then stop at the end.
        return rc.andThen(() -> Drivetrain.getInstance().tankDriveVolts(0, 0));
    }

    public SafeSixBall() {
        System.out.println("Auto: Safe Six Ball Selected");

        // still under development, right now just drives backward and
        // turns ~45 degrees, moving to the right 3 feet
        addCommands(
                new PrintCommand("Auto: Safe Six Ball Started"),
                new IntakeDown(),
                new ParallelDeadlineGroup(
                        reverse(),
                        new IntakeRun()),
                new ParallelDeadlineGroup(
                        forward(),
                        new RevFlywheel()),
                new ParallelDeadlineGroup(
                        new RevFlywheel().withTimeout(2.25),
                        new Fire()),
                new ParallelDeadlineGroup(
                        reverseAgain(),
                        new IntakeRun()),
                new ParallelDeadlineGroup(
                        forwardAgain(),
                        new RevFlywheel()),
                new ParallelDeadlineGroup(
                        new RevFlywheel().withTimeout(3),
                        new Fire()),
                new PrintCommand("Auto: Safe Six Ball Ended")
        );
    }
}
