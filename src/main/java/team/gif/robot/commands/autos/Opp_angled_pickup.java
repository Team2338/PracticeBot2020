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

public class Opp_angled_pickup extends SequentialCommandGroup {

    //only 2 tunable #s
    public double theta = -20;
    public double y = 24/12;


    // for sake of not having to tune as much, math
    public double x1 = -10/12;
    public double x2 = 10/12;



    public Command reverse() {

        x2 = -y*Math.tan(theta);
        //x1 = ((linetoball-robotlongth)-x2)+x1df;

        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
            List.of(
                new Pose2d(Units.feetToMeters(0.0), 0.0, new Rotation2d(0)),     //zerod
                new Pose2d(Units.feetToMeters(-36/12), 0.0, new Rotation2d(0)), // move backward 6ft
                new Pose2d(Units.feetToMeters(-125/12),Units.feetToMeters(34/12), new Rotation2d(Units.degreesToRadians(theta)))
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
                        new Pose2d(Units.feetToMeters(-125/12),Units.feetToMeters(34/12), new Rotation2d(Units.degreesToRadians(theta))),
                        new Pose2d(Units.feetToMeters(-2), Units.feetToMeters(0), new Rotation2d(Units.degreesToRadians(theta)))
                ),
                RobotTrajectory.getInstance().configForward
        );
        // create the command using the trajectory
        RamseteCommand rc = RobotTrajectory.getInstance().createRamseteCommand(trajectory);
        // Run path following command, then stop at the end.MaxVelocityConstraint
        return rc.andThen(() -> Drivetrain.getInstance().tankDriveVolts(0, 0));
    }

    public Opp_angled_pickup() {
        addCommands(
                new PrintCommand("Auto: angle thingy"),
                new IntakeDown(),
                new ParallelDeadlineGroup(
                    reverse(),
                    new IntakeRun()),//enemy ball heist
                new IntakeRun().withTimeout(.75),
                forward()    //get out of there
        );
    }
}