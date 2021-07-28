package team.gif.robot.commands.autos;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.*;
import team.gif.lib.Pose2dFeet;
import team.gif.lib.RobotTrajectory;
import team.gif.robot.commands.shooter.Fire;
import team.gif.robot.commands.shooter.RevFlywheel;
import team.gif.robot.subsystems.Drivetrain;

import java.util.List;

public class SafeThreeBall extends SequentialCommandGroup {
    public Command reverse () {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
            List.of(
                    new Pose2dFeet().set(0.0, 0.0, 0.0),
                    new Pose2dFeet().set(-3.0, 0.0, 0.0)
            ),
            RobotTrajectory.getInstance().configReverse
        );
        // create the command using the trajectory
        RamseteCommand rc = RobotTrajectory.getInstance().createRamseteCommand(trajectory);
        // Run path following command, then stop at the end.
        return rc.andThen(() -> Drivetrain.getInstance().tankDriveVolts(0, 0));
    }

    /* needs testing */
    public SafeThreeBall() {
        System.out.println("Auto: Safe Three Ball Selected");

        addCommands(
            new PrintCommand("Auto: Safe Three Ball Started"),
            new ParallelDeadlineGroup(
                reverse(),
                new RevFlywheel()),
            new ParallelDeadlineGroup(
                new RevFlywheel().withTimeout(3.0),
                new Fire()),
            new PrintCommand("Auto: Safe Three Ball Ended")
        );
    }
}
