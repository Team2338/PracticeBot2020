package team.gif.robot.commands.autos;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.*;
import team.gif.lib.RobotTrajectory;
import team.gif.robot.Robot;
import team.gif.robot.commands.autoaim.Pivot;
import team.gif.robot.commands.drivetrain.AutoDrive;
import team.gif.robot.commands.drivetrain.forward;
import team.gif.robot.commands.intake.IntakeDown;
import team.gif.robot.commands.intake.IntakeRun;
import team.gif.robot.commands.shooter.Fire;
import team.gif.robot.commands.shooter.RevFlywheel;
import team.gif.robot.subsystems.Drivetrain;

import java.util.List;


public class SafeFiveBall extends SequentialCommandGroup {
    public Command reverse () {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                List.of(
                        new Pose2d(Units.feetToMeters(0.0), 0, new Rotation2d(0)),
                        // new Pose2d(Units.feetToMeters(-6.0), 0, new Rotation2d(0)),
                        new Pose2d(Units.feetToMeters(-11.0), Units.feetToMeters(0.0), new Rotation2d(Units.degreesToRadians(0)))
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
                        new Pose2d(Units.feetToMeters(-11.0), 0, new Rotation2d(0)),
                        // new Pose2d(Units.feetToMeters(-6.0), 0, new Rotation2d(0)),
                        new Pose2d(Units.feetToMeters(-7.0), Units.feetToMeters(1.0), new Rotation2d(Units.degreesToRadians(12)))
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
                        new Pose2d(Units.feetToMeters(-7.0), Units.feetToMeters(1.0), new Rotation2d(12)),
                        // new Pose2d(Units.feetToMeters(-6.0), 0, new Rotation2d(0)),
                        new Pose2d(Units.feetToMeters(-14.0), Units.feetToMeters(0.0), new Rotation2d(Units.degreesToRadians(0)))
                ),
                RobotTrajectory.getInstance().configReverse
        );
        // create the command using the trajectory
        RamseteCommand rc = RobotTrajectory.getInstance().createRamseteCommand(trajectory);
        // Run path following command, then stop at the end.
        return rc.andThen(() -> Drivetrain.getInstance().tankDriveVolts(0, 0));
    }
    public Command forwardAgain () {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                List.of(
                        new Pose2d(Units.feetToMeters(-14.0), Units.feetToMeters(0.0), new Rotation2d(0)),
                        // new Pose2d(Units.feetToMeters(-6.0), 0, new Rotation2d(0)),
                        new Pose2d(Units.feetToMeters(-11.0), Units.feetToMeters(1.0), new Rotation2d(Units.degreesToRadians(12)))
                ),
                RobotTrajectory.getInstance().configForward
        );
        // create the command using the trajectory
        RamseteCommand rc = RobotTrajectory.getInstance().createRamseteCommand(trajectory);
        // Run path following command, then stop at the end.
        return rc.andThen(() -> Drivetrain.getInstance().tankDriveVolts(0, 0));
    }

    public SafeFiveBall() {
        System.out.println("Auto: Safe Five Ball Selected");

        // still under development, right now just drives backward and
        // turns ~45 degrees, moving to the right 3 feet
        addCommands(
                new PrintCommand("Auto: Safe Five Ball Started"),
                new IntakeDown(),
                new ParallelDeadlineGroup(
                        reverse(),
                        new IntakeRun()),
                new ParallelDeadlineGroup(
                        forward(),
                        new RevFlywheel()),
                new ParallelDeadlineGroup(
                        new RevFlywheel().withTimeout(2.5),
                        new Fire(false)),
                new ParallelDeadlineGroup(
                        reverseAgain(),
                        new IntakeRun()),
                new ParallelDeadlineGroup(
                        forwardAgain(),
                        new RevFlywheel()),
                new ParallelDeadlineGroup(
                        new RevFlywheel().withTimeout(0.5),
                        new Fire(false))
        );
    }

    /*public SafeFiveBall(){
        System.out.println("Auto: Safe Five Ball Selected");

        /*
        * TODO: speed up moveback
        *

        addCommands(
                new IntakeDown(),
                new ParallelDeadlineGroup(new AutoDrive(2.3,.47,.47),//OG 2.5 ----- time was 2.3
                                          new IntakeRun()),
                new IntakeRun().withTimeout(0.5), // sit in position to fully collect ball
                new ParallelCommandGroup(new Pivot(),
                                         new RevFlywheel()).withTimeout(2.5),
                new ParallelCommandGroup(new Pivot(),
                                         new RevFlywheel(),
                                         new Fire(true))
        );
    }*/
}
