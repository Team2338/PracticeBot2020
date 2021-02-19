package team.gif.robot.commands.autos;

import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.*;
import team.gif.lib.Pose2dFeet;
import team.gif.lib.RobotTrajectory;
import team.gif.robot.commands.intake.IntakeDown;
import team.gif.robot.commands.intake.IntakeRun;
import team.gif.robot.subsystems.Drivetrain;

import java.util.List;


public class GalacticSearchBlue extends SequentialCommandGroup {

    public Command reverse () {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
            List.of(
                new Pose2dFeet().set(0.0, 0.0, 0.0),
                new Pose2dFeet().set(-2.5, 0.0, 0.0)
            ),
            RobotTrajectory.getInstance().configReverse
        );
        // create the command using the trajectory
        RamseteCommand rc = RobotTrajectory.getInstance().createRamseteCommand(trajectory);
        // Run path following command, then stop at the end.
        return rc.andThen(() -> Drivetrain.getInstance().tankDriveVolts(0, 0));
    }

    public GalacticSearchBlue() {
        System.out.println("Auto: Galactic Search Blue Selected");

        addCommands(
            new PrintCommand("Auto: Galactic Search Blue Started"),
            //new IntakeDown(),
            new ParallelDeadlineGroup(
                reverse(),
                new IntakeRun()),
            new PrintCommand("Auto: Galactic Search Blue Ended")
        );
    }
}
