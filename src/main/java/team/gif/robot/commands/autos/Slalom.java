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

public class Slalom extends SequentialCommandGroup {

    /*
     * Lengthwise Field
     *
     * +Y
     *  @ +X --> Stating direction (0 degrees)
     *
     * (-) <- degree -> (+)
     *      ^ Robot ^
     */

    public Command forward () {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
            List.of(
            /* E1 */new Pose2dFeet().set(2.5 - 2.5, 2.5 - 2.5, 0),
            /* E2 */new Pose2dFeet().set(5.0 - 2.5, 2.5 - 2.5, 0),
            /* D3 */new Pose2dFeet().set(7.5 - 2.5, 5.0 - 2.5, -55),
            /* B6 */new Pose2dFeet().set(15.0 - 2.5, 10.0 - 2.5, 0),
            /* ~D9 */new Pose2dFeet().set(23.5 - 2.5, 5.0 - 2.5, 90),
            /* ~E10 */new Pose2dFeet().set(26.0 - 2.5, 2.0 - 2.5, 0),
            /* ~D11 */new Pose2dFeet().set(29.5 - 2.5, 5.0 - 2.5, -90),
            /* ~C10 */new Pose2dFeet().set(26.0 - 2.5, 7.5 - 2.5, -180),
            /* ~D9 */new Pose2dFeet().set(23.5 - 2.5, 5.0 - 2.5, 90),
            /* ~E8 */new Pose2dFeet().set(20.0 - 2.5, 2.0 - 2.5, -180),
            /* ~E4 */new Pose2dFeet().set(11.0 - 2.5, 2.0 - 2.5, -180),
            /* D3 */new Pose2dFeet().set(8.0 - 2.5, 5.0 - 2.5, -90),
            /* C2 */new Pose2dFeet().set(5.0 - 2.5, 7.5 - 2.5, -180),
            /* C1 */new Pose2dFeet().set(2.5 - 2.5, 7.5 - 2.5, -180)
            ),
            RobotTrajectory.getInstance().configForwardIRFast
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
