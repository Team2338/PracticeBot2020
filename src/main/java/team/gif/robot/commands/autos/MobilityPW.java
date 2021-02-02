package team.gif.robot.commands.autos;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import team.gif.lib.Pose2dFeet;
import team.gif.lib.RobotTrajectory;
import team.gif.robot.subsystems.Drivetrain;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class MobilityPW extends SequentialCommandGroup {

    public Command reverse () {
        String trajectoryJSON = "paths/MobilityPW.wpilib.json";

        try {
            // load trajectory filename from Rio File System
            Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryJSON);

            // set the trajectory based off of filename
            Trajectory trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);

            // create the command using the trajectory
            RamseteCommand rc = RobotTrajectory.getInstance().createRamseteCommand(trajectory);

            // Run path following command, then stop at the end.
            return rc.andThen(() -> Drivetrain.getInstance().tankDriveVolts(0, 0));

        } catch (IOException ex) {
            System.out.println("Unable to open trajectory file");
            return null;
        }
    }

    public MobilityPW() {
        System.out.println("Auto: Mobility Selected");

        addCommands(
            new PrintCommand("Auto: Mobility Started"),
            reverse(),
            new PrintCommand("Auto: Mobility Ended")
        );
    }
}
