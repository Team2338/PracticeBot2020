package team.gif.robot.commands.autos;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.*;
import team.gif.lib.RobotTrajectory;
import team.gif.robot.commands.intake.IntakeDown;
import team.gif.robot.commands.intake.IntakeRun;
import team.gif.robot.commands.shooter.Fire;
import team.gif.robot.commands.shooter.RevFlywheel;
import team.gif.robot.subsystems.Drivetrain;

import java.util.List;

public class OppSixBall extends SequentialCommandGroup {

    // TODO: make actual measurements

    public Command reverse() {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                List.of(
                        new Pose2d(Units.feetToMeters(0.0), 0, new Rotation2d(0.0)),     //zerod
                        new Pose2d(Units.feetToMeters(-92/12.0), 0, new Rotation2d(0.0)) // move backward 6ft
                ),
                RobotTrajectory.getInstance().configReverseSlow
        );
        // create the command using the trajectory
        RamseteCommand rc = RobotTrajectory.getInstance().createRamseteCommand(trajectory);
        // Run path following command, then stop at the end.
        return rc.andThen(() -> Drivetrain.getInstance().tankDriveVolts(0, 0));
    }

    public Command forward(){
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                List.of(
                        new Pose2d(Units.feetToMeters(-92/12.0), 0, new Rotation2d(0.0)),
                        new Pose2d(Units.feetToMeters(-2.0), Units.feetToMeters(-7.5), new Rotation2d(Units.degreesToRadians(-20.0)))
                ),
                RobotTrajectory.getInstance().configForwardFast
        );
        // create the command using the trajectory
        RamseteCommand rc = RobotTrajectory.getInstance().createRamseteCommand(trajectory);
        // Run path following command, then stop at the end.MaxVelocityConstraint
      return rc.andThen(() -> Drivetrain.getInstance().tankDriveVolts(0, 0));
    }

    /*public Command midPoint() {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                List.of(
                        new Pose2d(Units.feetToMeters(-2.0),  Units.feetToMeters(-7.5), new Rotation2d(Units.degreesToRadians(-18.0))),
                        new Pose2d(Units.feetToMeters(-2.0), Units.feetToMeters(-12.5), new Rotation2d(Units.degreesToRadians(125.0)))
                ),
                RobotTrajectory.getInstance().configReverse
        );
        // create the command using the trajectory
        RamseteCommand rc = RobotTrajectory.getInstance().createRamseteCommand(trajectory);
        // Run path following command, then stop at the end.
        return rc; //return rc.andThen(() -> Drivetrain.getInstance().tankDriveVolts(0, 0));
    }*/

    public Command reverseAgain() {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                List.of(
                        new Pose2d(Units.feetToMeters(-2.0),  Units.feetToMeters(-7.5), new Rotation2d(Units.degreesToRadians(-20.0))),
                        new Pose2d(Units.feetToMeters(-2.0), Units.feetToMeters(-12.5), new Rotation2d(Units.degreesToRadians(125.0))),
                        new Pose2d(Units.feetToMeters(-90/12.0), Units.feetToMeters(-261/12.0), new Rotation2d(0.0)) // move backward 6ft
                ),
                RobotTrajectory.getInstance().configReverse
        );
        // create the command using the trajectory
        RamseteCommand rc = RobotTrajectory.getInstance().createRamseteCommand(trajectory);
        // Run path following command, then stop at the end.
        return rc.andThen(() -> Drivetrain.getInstance().tankDriveVolts(0, 0));
    }

    public Command forwardAgain(){
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                List.of(
                        new Pose2d(Units.feetToMeters(-90/12.0), Units.feetToMeters(-261/12.0), new Rotation2d(0.0)),
                        new Pose2d(Units.feetToMeters(-4.0), Units.feetToMeters(-232/12.0), new Rotation2d(Units.degreesToRadians(15.0)))
                ),
                RobotTrajectory.getInstance().configForward
        );
        // create the command using the trajectory
        RamseteCommand rc = RobotTrajectory.getInstance().createRamseteCommand(trajectory);
        // Run path following command, then stop at the end.MaxVelocityConstraint
        return rc.andThen(() -> Drivetrain.getInstance().tankDriveVolts(0, 0));
    }

    public OppSixBall() {
        addCommands(
                //new PrintCommand("Auto: Opponent 6 Ball Selected"),
                new ParallelDeadlineGroup(
                        reverse(),
                        new IntakeDown(),
                        new IntakeRun()),//enemy ball heist
                new IntakeRun().withTimeout(.75),
                new ParallelDeadlineGroup(
                        forward(),    //get out of there
                        new RevFlywheel()),
                new ParallelDeadlineGroup(
                        // let it rip
                        new RevFlywheel().withTimeout(2.25),
                        new Fire(false)),
                //midPoint(),
                new ParallelDeadlineGroup(
                        reverseAgain(),
                        new IntakeRun()),
                new IntakeRun().withTimeout(0.4),
                new ParallelDeadlineGroup(
                        forwardAgain(),    //get out of there #2
                        new RevFlywheel()),
                new ParallelCommandGroup(
                        // let it rip #2
                        new RevFlywheel().withTimeout(3.0),
                        new Fire(false))
        );
    }
}