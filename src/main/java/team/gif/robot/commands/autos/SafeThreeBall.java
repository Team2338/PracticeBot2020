package team.gif.robot.commands.autos;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.*;
import team.gif.lib.RobotTrajectory;
import team.gif.robot.Robot;
import team.gif.robot.RobotContainer;
import team.gif.robot.commands.autoaim.Pivot;
import team.gif.robot.commands.drivetrain.AutoDrive;
import team.gif.robot.commands.intake.IntakeDown;
import team.gif.robot.commands.intake.IntakeRun;
import team.gif.robot.commands.shooter.Fire;
import team.gif.robot.commands.shooter.RevFlywheel;
import team.gif.robot.subsystems.Drivetrain;

import java.util.List;

public class SafeThreeBall extends SequentialCommandGroup {
    public Command reverse () {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
            List.of(
                new Pose2d(Units.feetToMeters(0.0), 0, new Rotation2d(0)),
               // new Pose2d(Units.feetToMeters(-6.0), 0, new Rotation2d(0)),
                new Pose2d(Units.feetToMeters(-9.0), Units.feetToMeters(-9.0), new Rotation2d(Units.degreesToRadians(45.0)))
            ),
            RobotTrajectory.getInstance().configReverse
        );
        // create the command using the trajectory
        RamseteCommand rc = RobotTrajectory.getInstance().createRamseteCommand(trajectory);
        // Run path following command, then stop at the end.
        return rc.andThen(() -> Drivetrain.getInstance().tankDriveVolts(0, 0));
    }

    public SafeThreeBall() {
        System.out.println("Auto: Safe Three Ball Selected");

        // still under development, right now just drives backward and
        // turns ~45 degrees, moving to the right 3 feet
        addCommands(
            new PrintCommand("Auto: Safe Three Ball Started"),
            reverse()
        );
    }

/*    public SafeThreeBall(){
        System.out.println("Auto: Safe Three Ball Selected");
        addCommands(
                new AutoDrive(1.0,.47,.47),
                new ParallelCommandGroup(new Pivot(),
                                         new RevFlywheel()).withTimeout(2.5),
                new ParallelCommandGroup(new Pivot(),
                                         new RevFlywheel(),
                                         new Fire(true))
        );
    }
*/
}
