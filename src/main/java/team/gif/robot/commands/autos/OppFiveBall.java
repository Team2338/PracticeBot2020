package team.gif.robot.commands.autos;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.trajectory.constraint.*;
import edu.wpi.first.wpilibj.trajectory.constraint.TrajectoryConstraint;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.*;
import team.gif.lib.RobotTrajectory;/*
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.*;
import team.gif.robot.Robot;
import team.gif.robot.commands.autoaim.Pivot;
import team.gif.robot.commands.drivetrain.AutoDrive;*/
import team.gif.robot.commands.intake.IntakeDown;
import team.gif.robot.commands.intake.IntakeRun;
import team.gif.robot.commands.shooter.Fire;
import team.gif.robot.commands.shooter.RevFlywheel;
import team.gif.robot.subsystems.Drivetrain;

import javax.swing.*;
import java.util.List;

public class OppFiveBall extends SequentialCommandGroup {



    public Command drive1 () {
        //TrajectoryConstraint accelconstraint = new TrajectoryConstraint ();
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                List.of(
                        new Pose2d(Units.feetToMeters(0.0), 0, new Rotation2d(0)),//zerod
                        new Pose2d(Units.feetToMeters(-92/12.0), 0, new Rotation2d(0))// move backward 6ft
                ),
                RobotTrajectory.getInstance().configReverseSlow
        );
        // create the command using the trajectory
        RamseteCommand rc = RobotTrajectory.getInstance().createRamseteCommand(trajectory);
        // Run path following command, then stop at the end.
        return rc.andThen(() -> Drivetrain.getInstance().tankDriveVolts(0, 0));
    }

    public Command drive2 (){
        //CentripetalAccelerationConstraint constraint = new CentripetalAccelerationConstraint(30);
        //TrajectoryConstraint.MinMax accelconstraint = new TrajectoryConstraint.MinMax(3,0);



        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                List.of(
                        new Pose2d(Units.feetToMeters(-92/12.0), 0, new Rotation2d(0)),
                        new Pose2d(Units.feetToMeters(-2), Units.feetToMeters(-14.0), new Rotation2d(Units.degreesToRadians(-7)))
                ),
                RobotTrajectory.getInstance().configForward// this is what was added
        );
        // create the command using the trajectory
        RamseteCommand rc = RobotTrajectory.getInstance().createRamseteCommand(trajectory);
        // Run path following command, then stop at the end.MaxVelocityConstraint
        return rc.andThen(() -> Drivetrain.getInstance().tankDriveVolts(0, 0));

    }

    /*public Command drive3(){
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                List.of(
                        new Pose2d(Units.feetToMeters(94.36/12), 0, new Rotation2d(0)),
                        new Pose2d(Units.feetToMeters(4), -, new Rotation2d(-35))
                ),
                RobotTrajectory.getInstance().configReverse
        );
        // create the command using the trajectory
        RamseteCommand rc = RobotTrajectory.getInstance().createRamseteCommand(trajectory);
        // Run path following command, then stop at the end.
        return rc.andThen(() -> Drivetrain.getInstance().tankDriveVolts(0, 0));
    }*/

    public OppFiveBall() {


        //System.out.println("Auto: Opponent 5 Ball Selected");
        /*
         * TODO: speed up moveback
         * */
/*
        addCommands(
                new IntakeDown(),
                new AutoDrive(.5,.30,.30), // slow rampup to reduce jitter when starting
                new AutoDrive(1,.5,.5),
                new ParallelDeadlineGroup(new AutoDrive(.5,.30,.30), // drive backwards),//OG 1.7 "1.9 @ .47" // 2.75
                                          new IntakeRun()),

                new ParallelDeadlineGroup(new AutoDrive(1.0,0,0), // hold to collect
                                          new IntakeRun()),
                new AutoDrive(0.6,-.5,-.5),//OG 2.5  // drive forward
                new AutoDrive(.55,-.5,.5),//OG 2.5   // turn clockwise //.65
                new AutoDrive(2.1,-.6,-.6),//OG 2.5  // drive forward 2.5 @ .5
                new AutoDrive(.60,.5,-.5),//OG 2.5   // turn counterclockwise
                new AutoDrive(0.8,.3,.3),//OG 2.5    // drive backward
                new ParallelCommandGroup(new Pivot(),                          // let it rip
                                         new RevFlywheel(),
                                         new Fire(true))

        );*/

        addCommands(
                new PrintCommand("Auto: Opponent 5 Ball Selected"),// init
                new IntakeDown(),
                new ParallelDeadlineGroup(drive1(),new IntakeRun()),//enemy ball heist
                new IntakeRun().withTimeout(.75),
                new ParallelDeadlineGroup(
                    drive2(),//get out of there
                    new RevFlywheel()),
                //drive3(), //turn and move forward
                new ParallelCommandGroup(
                        // let it rip
                        new RevFlywheel(),
                        new Fire(false))

        );
    }
}
/** my dude i think i found probable issue
 * the issue is probably that the centripital acceleration cap is to low
 * i think this because it doesnt turn all the way to the measurement we want it to
 * because it is looking for a larger radius not given by the waypoints or a higher tangential velocity
 * i think that increasing the centripital acceleration cap might be a viable solution because the bot
 * is relatively difficult to flip because it is lower to the ground
 * **/