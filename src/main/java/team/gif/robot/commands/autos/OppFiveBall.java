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
import team.gif.robot.commands.intake.IntakeDown;
import team.gif.robot.commands.intake.IntakeRun;
import team.gif.robot.commands.shooter.Fire;
import team.gif.robot.commands.shooter.RevFlywheel;
import team.gif.robot.subsystems.Drivetrain;

import java.util.List;

public class OppFiveBall extends SequentialCommandGroup {
    public Command backward6 () {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
            List.of(
                new Pose2d(Units.feetToMeters(0.0), 0, new Rotation2d(0)),
                new Pose2d(Units.feetToMeters(-6.0), 0, new Rotation2d(0))
            ),
            RobotTrajectory.getInstance().configReverse
        );
        // create the command using the trajectory
        RamseteCommand rc = RobotTrajectory.getInstance().createRamseteCommand(trajectory);
        // Run path following command, then stop at the end.
        return rc.andThen(() -> Drivetrain.getInstance().tankDriveVolts(0, 0));
    }
    public Command bigU () {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
            List.of(
                new Pose2d(Units.feetToMeters(0.0), 0, new Rotation2d(0)),
                new Pose2d(Units.feetToMeters(3.0), Units.feetToMeters(-3.0), new Rotation2d(Units.degreesToRadians(-90.0))),
                new Pose2d(Units.feetToMeters(3.0), Units.feetToMeters(-10.0), new Rotation2d(Units.degreesToRadians(-90.0))),
                new Pose2d(Units.feetToMeters(8.0), Units.feetToMeters(-15.0), new Rotation2d(Units.degreesToRadians(0.0)))
            ),
            RobotTrajectory.getInstance().configForward
        );
        // create the command using the trajectory
        RamseteCommand rc = RobotTrajectory.getInstance().createRamseteCommand(trajectory);
        // Run path following command, then stop at the end.
        return rc.andThen(() -> Drivetrain.getInstance().tankDriveVolts(0, 0));
    }
    public OppFiveBall () {
        System.out.println("Auto: Opponent Five Ball Selected");

        // still under development, right now just drives backward and
        // turns ~45 degrees, moving to the right 3 feet
        addCommands(
            new PrintCommand("Auto: Opponent Five Ball Started"),
            //backward6(),
            bigU()
            //forward6()
        );
    }

/*
    public OppFiveBall() {
        System.out.println("Auto: Opponent 5 Ball Selected");
        //
        // TODO: speed up moveback
        //

        addCommands(
                new IntakeDown(),
                new AutoDrive(.5,.30,.30), // slow rampup to reduce jitter when starting
                new AutoDrive(1,.5,.5),
                new ParallelDeadlineGroup(new AutoDrive(.5,.30,.30), // drive backwards),//OG 1.7 "1.9 @ .47" // 2.75
                                          new IntakeRun()),

                new ParallelDeadlineGroup(new AutoDrive(1.0,0,0), // hold to collect
                                          new IntakeRun()),
                new AutoDrive(0.6,-.5,-.5),//OG 2.5  // drive forward
                new AutoDrive(.55,-.5,.5),//OG 2.5   // turn clockwise //.65
                new AutoDrive(2.1,-.6,-.6),//OG 2.5  // drive forward 2.5 @ .5
                new AutoDrive(.60,.5,-.5),//OG 2.5   // turn counterclockwise
                new AutoDrive(0.8,.3,.3),//OG 2.5    // drive backward
                new ParallelCommandGroup(new Pivot(),                          // let it rip
                                         new RevFlywheel(),
                                         new Fire(true))

        );
    }

 */
}

