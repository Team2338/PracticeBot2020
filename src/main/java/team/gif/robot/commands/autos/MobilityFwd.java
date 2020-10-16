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

public class MobilityFwd extends SequentialCommandGroup {

    public Command forward () {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
            List.of(
                new Pose2d(Units.feetToMeters(0.0), 0, new Rotation2d(0)),
                new Pose2d(Units.feetToMeters(4.0), 0, new Rotation2d(0))
            ),
            RobotTrajectory.getInstance().configForward
        );
        // create the command using the trajectory
        RamseteCommand rc = RobotTrajectory.getInstance().createRamseteCommand(trajectory);
        // Run path following command, then stop at the end.
        return rc.andThen(() -> Drivetrain.getInstance().tankDriveVolts(0, 0));
    }

    public MobilityFwd() {
        System.out.println("Auto: Mobility Forward Selected");

        addCommands(
            new PrintCommand("Auto: Mobility Forward Started"),
            forward(),
            new PrintCommand("Auto: Mobility Forward Ended")
        );
    }

}