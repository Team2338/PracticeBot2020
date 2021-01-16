package team.gif.robot.commands.autos;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import team.gif.lib.RobotTrajectory;
import team.gif.robot.subsystems.Drivetrain;

import java.util.List;

public class Slalom extends SequentialCommandGroup {

    /*
     * Lengthwise Field
     *
     * +Y
     *  @ +X --> Stating direction (0 degrees)
     *
     * (+) <- degree -> (-)
     *      ^ Robot ^
     */

    public Command forward () {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
            List.of(
                new Pose2d(Units.feetToMeters(2.5), Units.feetToMeters(2.5), new Rotation2d(Units.degreesToRadians(0))),
                new Pose2d(Units.feetToMeters(7.5), Units.feetToMeters(5.0), new Rotation2d(Units.degreesToRadians(45))),
                new Pose2d(Units.feetToMeters(22.5), Units.feetToMeters(5.0), new Rotation2d(Units.degreesToRadians(-45))),
                new Pose2d(Units.feetToMeters(27.5), Units.feetToMeters(5.0), new Rotation2d(Units.degreesToRadians(90))),
                new Pose2d(Units.feetToMeters(22.5), Units.feetToMeters(5.0), new Rotation2d(Units.degreesToRadians(-135))),
                new Pose2d(Units.feetToMeters(7.5), Units.feetToMeters(5.0), new Rotation2d(Units.degreesToRadians(135))),
                new Pose2d(Units.feetToMeters(2.5), Units.feetToMeters(7.5), new Rotation2d(Units.degreesToRadians(180)))
            ),
            RobotTrajectory.getInstance().configForward
        );
        // create the command using the trajectory
        RamseteCommand rc = RobotTrajectory.getInstance().createRamseteCommand(trajectory);
        // Run path following command, then stop at the end.
        return rc.andThen(() -> Drivetrain.getInstance().tankDriveVolts(0, 0));
    }

    public Slalom() {
        System.out.println("Auto: Slalom Selected");

        addCommands(
            new PrintCommand("Auto: Slalom Started"),
            forward(),
            new PrintCommand("Auto: Slalom Ended")
        );
    }
}
