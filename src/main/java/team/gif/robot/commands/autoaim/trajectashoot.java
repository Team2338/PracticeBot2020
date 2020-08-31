package team.gif.robot.commands.autoaim;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.*;
import team.gif.lib.RobotTrajectory;
import team.gif.robot.Robot;
import team.gif.robot.commands.intake.IntakeDown;
import team.gif.robot.commands.intake.IntakeRun;
import team.gif.robot.commands.shooter.Fire;
import team.gif.robot.commands.shooter.RevFlywheel;
import team.gif.robot.subsystems.Drivetrain;

import java.util.List;

public class trajectashoot extends SequentialCommandGroup {

    public double xoffset = 0;

    public Command forward() {
        Drivetrain.getInstance().resetPose();
        // this should zero the position of the bot
        /**theory
         * trajectory has a bunch more constants, and accounts for much more
         * physics than we have time to, and is incredibly accurate
         * so we use it to move in position to the target
         * for use in teleop only bc we have to zero our position
         *
         * where is target
         * move forward
         * back into shooting spot
         * O yeah
         **/
        xoffset = Robot.limelight.getXOffset();
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
            List.of(
                new Pose2d(Units.feetToMeters(0.0), 0, new Rotation2d(0)),     //zerod
                new Pose2d(Units.feetToMeters(1), 0, new Rotation2d(0)) // move backward 6ft
            ),
            RobotTrajectory.getInstance().configForward
        );
        // create the command using the trajectory
        RamseteCommand rc = RobotTrajectory.getInstance().createRamseteCommand(trajectory);
        // Run path following command, then stop at the end.
        return rc.andThen(() -> Drivetrain.getInstance().tankDriveVolts(0, 0));
    }

    public Command aim() {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                List.of(
                        new Pose2d(Units.feetToMeters(1), 0, new Rotation2d(0)),
                        new Pose2d(Units.feetToMeters(0), 0, new Rotation2d(xoffset)) // move backward 6ft
                ),
                RobotTrajectory.getInstance().configReverseSlow
        );
        // create the command using the trajectory
        RamseteCommand rc = RobotTrajectory.getInstance().createRamseteCommand(trajectory);
        // Run path following command, then stop at the end.
        return rc.andThen(() -> Drivetrain.getInstance().tankDriveVolts(0, 0));
    }

    public trajectashoot() {
        addCommands(
                new PrintCommand("trajectashoot aimbot"),
                forward(),
                aim(),
                new PrintCommand("error: "+ Robot.limelight.getXOffset())
        );
    }
}