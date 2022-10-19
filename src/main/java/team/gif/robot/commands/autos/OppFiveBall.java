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

public class OppFiveBall extends SequentialCommandGroup {

    public Command reverse() {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
            List.of(
                    new Pose2dFeet().set(0.0, 0.0, 0.0), //zerod
                    new Pose2dFeet().set(-92/12.0, 0.0, 0.0) // move backward 6ft
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
                    new Pose2dFeet().set(-92/12.0, 0.0, 0.0),
                    new Pose2dFeet().set(-2.0, -14.0, 7.0)
            ),
            RobotTrajectory.getInstance().configForward
        );
        // create the command using the trajectory
        RamseteCommand rc = RobotTrajectory.getInstance().createRamseteCommand(trajectory);
        // Run path following command, then stop at the end.MaxVelocityConstraint
        return rc.andThen(() -> Drivetrain.getInstance().tankDriveVolts(0, 0));
    }

    public OppFiveBall() {

        System.out.println("Auto: OppFiveBall Selected");

        addCommands(
                new PrintCommand("Auto: OppFiveBall Started"),
                new IntakeDown(),
                new ParallelDeadlineGroup(
                    reverse(),
                    new IntakeRun()),//enemy ball heist
                new IntakeRun().withTimeout(.75),
                new ParallelDeadlineGroup(
                    forward(),    //get out of there
                    new RevFlywheel()),
                new ParallelCommandGroup(
                    // let it rip
                    new RevFlywheel(),
                    new Fire()),
                new PrintCommand("Auto: OppFiveBall Ended")
        );
    }
}