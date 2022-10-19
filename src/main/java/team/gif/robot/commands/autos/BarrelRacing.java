package team.gif.robot.commands.autos;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import team.gif.lib.Pose2dFeet;
import team.gif.lib.RobotTrajectory;
import team.gif.robot.subsystems.Drivetrain;

import java.util.List;

public class BarrelRacing extends SequentialCommandGroup {

    /*
     * Lengthwise Field
     *
     * +Y
     *  @ +X --> Stating direction (0 degrees)
     *
     * (-) <- degree -> (+)
     *      ^ Robot ^
     */

    private double xInit = 3.0;
    private double yInit = 7.5;

    public Command forward () {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
            List.of(
                    new Pose2dFeet().set(0.0, 0.0,0.0), // C1
                    new Pose2dFeet().set(12.5 - xInit, 7.5 - yInit, 0.0), // C5
                    new Pose2dFeet().set(15.5 - xInit, 5.0 - yInit, 90.0), // ~D6
                    new Pose2dFeet().set(12.5 - xInit, 2.0 - yInit, 180.0), // ~E5
                    new Pose2dFeet().set(9.5 - xInit, 5.0 - yInit, -90.0), // ~D4
                    new Pose2dFeet().set(12.5 - xInit, 7.5 - yInit, 0.0), // C5
                    new Pose2dFeet().set(20.0 - xInit, 7.5 - yInit, 0.0), // C8
                    new Pose2dFeet().set(23.0 - xInit, 10.0 - yInit, -90.0), // ~B9
                    new Pose2dFeet().set(20.0 - xInit, 12.75 - yInit, 180.0), // ~A8
                    new Pose2dFeet().set(17.0 - xInit, 10.0 - yInit, 90.0), // ~B7
                    new Pose2dFeet().set(22.5 - xInit, 2.5 - yInit, 0.0), // ~E9
                    new Pose2dFeet().set(27.75 - xInit, 5.0 - yInit, -90.0), // ~D11
                    new Pose2dFeet().set(25.0 - xInit, 7.5 - yInit, 180.0), // C10
                    new Pose2dFeet().set(2.5 - xInit, 7.5 - yInit, 180.0) // C1

            ),
            RobotTrajectory.getInstance().configForward
        );
        // create the command using the trajectory
        RamseteCommand rc = RobotTrajectory.getInstance().createRamseteCommand(trajectory);
        // Run path following command, then stop at the end.
        return rc.andThen(() -> Drivetrain.getInstance().tankDriveVolts(0, 0));
    }

    public BarrelRacing() {
        System.out.println("Auto: Slalom Selected");

        addCommands(
            new PrintCommand("Auto: Slalom Started"),
            forward(),
            new PrintCommand("Auto: Slalom Ended")
        );
    }
}
