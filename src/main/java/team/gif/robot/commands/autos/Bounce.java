package team.gif.robot.commands.autos;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import team.gif.lib.Pose2dFeet;
import team.gif.lib.RobotTrajectory;
import team.gif.robot.subsystems.Drivetrain;

import java.util.List;

public class Bounce extends SequentialCommandGroup {

    /*
     * Lengthwise Field
     *
     * +Y
     *  @ +X --> Stating direction (0 degrees)
     *
     * (-) <- degree -> (+)
     *      ^ Robot ^
     */

    private double xInit = 3.5;
    private double yInit = 7.5;

    public Command leg1() {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
            List.of(
                    new Pose2dFeet().set(0.0, 0.0, 0.0), // C1
                    new Pose2dFeet().set(7.5 - xInit, 11.5 - yInit, -90.0) // A3 *
            ),
            RobotTrajectory.getInstance().configForwardIRFast
        );
        // create the command using the trajectory
        RamseteCommand rc = RobotTrajectory.getInstance().createRamseteCommand(trajectory);
        // Run path following command, then stop at the end.
        return rc.andThen(() -> Drivetrain.getInstance().tankDriveVolts(0, 0));
    }

    public Command leg2() {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                List.of(
                        new Pose2dFeet().set(7.5 - xInit, 11.5 - yInit, -90.0), // A3 *
                        new Pose2dFeet().set(10.0 - xInit, 5.0 - yInit, -90.0), // D4
                        new Pose2dFeet().set(13.0 - xInit, 2.5 - yInit, 180.0), // ~E5
                        new Pose2dFeet().set(15.0 - xInit, 2.5 - yInit, 180.0),
                        new Pose2dFeet().set(14.75 - xInit, 6.0 - yInit, 90.0), // D6
                        new Pose2dFeet().set(14.75 - xInit, 10.75 - yInit, 90.0) // A6 *
                ),
                RobotTrajectory.getInstance().configReverseIRSlow
        );
        // create the command using the trajectory
        RamseteCommand rc = RobotTrajectory.getInstance().createRamseteCommand(trajectory);
        // Run path following command, then stop at the end.
        return rc.andThen(() -> Drivetrain.getInstance().tankDriveVolts(0, 0));
    }

    public Command leg3() {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                List.of(
                        new Pose2dFeet().set(14.75 - xInit, 10.75 - yInit, 90.0), // A6 *
                        new Pose2dFeet().set(17.5 - xInit, 2.5 - yInit, 0.0), // E7
                        new Pose2dFeet().set(22.5 - xInit, 2.5 - yInit, 0.0), // E9
                        new Pose2dFeet().set(23.0 - xInit, 11.5 - yInit, -90.0) // A9 *
                ),
                RobotTrajectory.getInstance().configForward
        );
        // create the command using the trajectory
        RamseteCommand rc = RobotTrajectory.getInstance().createRamseteCommand(trajectory);
        // Run path following command, then stop at the end.
        return rc.andThen(() -> Drivetrain.getInstance().tankDriveVolts(0, 0));
    }

    public Command leg4() {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                List.of(
                        new Pose2dFeet().set(23.0 - xInit, 11.5 - yInit, -90.0), // A9 *
                        new Pose2dFeet().set(27.5 - xInit, 7.5 - yInit, 180.0) // C11
                ),
                RobotTrajectory.getInstance().configReverseIRFast
        );
        // create the command using the trajectory
        RamseteCommand rc = RobotTrajectory.getInstance().createRamseteCommand(trajectory);
        // Run path following command, then stop at the end.
        return rc.andThen(() -> Drivetrain.getInstance().tankDriveVolts(0, 0));
    }

    public Bounce() {
        System.out.println("Auto: Mobility Selected");

        addCommands(
            new PrintCommand("Auto: Mobility Started"),
            leg1(),
            leg2(),
            leg3(),
            leg4(),
            new PrintCommand("Auto: Mobility Ended")
        );
    }
}
