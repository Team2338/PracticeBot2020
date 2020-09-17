package team.gif.robot.commands.autoaim;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.*;
import team.gif.lib.RobotTrajectory;
import team.gif.robot.OI;
import team.gif.robot.Robot;
import team.gif.robot.commands.drivetrain.Drive;
import team.gif.robot.commands.intake.IntakeDown;
import team.gif.robot.commands.intake.IntakeRun;
import team.gif.robot.commands.shooter.Fire;
import team.gif.robot.commands.shooter.RevFlywheel;
import team.gif.robot.subsystems.Drivetrain;

import java.sql.SQLOutput;
import java.util.List;

public class trajectashoot extends CommandBase {

    public double xoffset = 0;
    public RamseteCommand forward;
    public RamseteCommand aim;

    /*
    public Command forward() {
        Drivetrain.getInstance().resetPose();
        // this should zero the position of the bot
        *theory
         * trajectory has a bunch more constants, and accounts for much more
         * physics than we have time to, and is incredibly accurate
         * so we use it to move in position to the target
         * for use in teleop only bc we have to zero our position
         *
         * where is target
         * move forward
         * back into shooting spot
         * O yeah
         *
        xoffset = Robot.limelight.getXOffset();
        System.out.println("              forward");
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
            List.of(
                new Pose2d(Units.feetToMeters(0.0), 0, new Rotation2d(0)),     //zerod
                new Pose2d(Units.feetToMeters(4), 0, new Rotation2d(0)) // move backward 6ft
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
                        new Pose2d(Units.feetToMeters(4), 0, new Rotation2d(0)),
                        new Pose2d(Units.feetToMeters(0), 0, new Rotation2d(Units.degreesToRadians(-xoffset)))
                ),
                RobotTrajectory.getInstance().configReverseSlow
        );
        // create the command using the trajectory
        RamseteCommand rc = RobotTrajectory.getInstance().createRamseteCommand(trajectory);
        // Run path following command, then stop at the end.
        return rc.andThen(() -> Drivetrain.getInstance().tankDriveVolts(0, 0));
    } */
    public trajectashoot() {

    }

    @Override
    public void initialize() {
        System.out.println("             trajectashooting");

        Drivetrain.getInstance().resetPose();
        xoffset = Robot.limelight.getXOffset();

        // this should zero the position of the

        Trajectory forwardtrajectory = TrajectoryGenerator.generateTrajectory(
                List.of(
                        new Pose2d(Units.feetToMeters(0), 0, new Rotation2d(Units.degreesToRadians(0))),     //zerod
                        new Pose2d(Units.feetToMeters(4), 0,new Rotation2d(Units.degreesToRadians(0))) // move backward 6ft
                ),
                RobotTrajectory.getInstance().configForwardSlow
        );

        // create the command using the trajectory
        forward = RobotTrajectory.getInstance().createRamseteCommand(forwardtrajectory);

        Trajectory aimtrajectory = TrajectoryGenerator.generateTrajectory(
                List.of(
                        new Pose2d(Units.feetToMeters(4), 0, new Rotation2d(Units.degreesToRadians(0))),
                        new Pose2d(Units.feetToMeters(0), 0, new Rotation2d(Units.degreesToRadians(-xoffset)))
                ),
                RobotTrajectory.getInstance().configReverseSlow
        );
        // create the command using the trajectory
        aim = RobotTrajectory.getInstance().createRamseteCommand(aimtrajectory);
        // Run path following command, then stop at the end.

        CommandScheduler.getInstance().schedule(
            new SequentialCommandGroup(
                    forward.andThen(() -> Drivetrain.getInstance().tankDriveVolts(0, 0)),
                    aim.andThen(() -> {
                        Drivetrain.getInstance().tankDriveVolts(0, 0);
                        Robot.indexCommand.schedule();
                        Robot.driveCommand.schedule();
                        //Robot.oi = new OI();
                    })

            )
        );
    }
    @Override
    public void end(boolean interrupted) {
        Robot.driveCommand.schedule();
        Robot.indexCommand.schedule();
        System.out.println("ended");
    }
}