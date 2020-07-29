package team.gif.lib;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.constraint.CentripetalAccelerationConstraint;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj.trajectory.constraint.TrajectoryConstraint;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import team.gif.robot.Constants;
import team.gif.robot.subsystems.Drivetrain;

import java.awt.*;

public class RobotTrajectory {

    public RobotTrajectory(){}

    private static RobotTrajectory instance = null;

    public static RobotTrajectory getInstance() {
        if (instance == null) {
            instance = new RobotTrajectory();
        }
        return instance;
    }

    /**
     * Creates a common DifferentialDriveVoltageConstraint
     * for all autonomous commands to utilize
     */
    public DifferentialDriveVoltageConstraint autoVoltageConstraint =
        new DifferentialDriveVoltageConstraint(
            new SimpleMotorFeedforward(Constants.drivetrain.ksVolts,
                Constants.drivetrain.kvVoltSecondsPerMeter,
                Constants.drivetrain.kaVoltSecondsSquaredPerMeter),
            Constants.drivetrain.kDriveKinematics,
            10);

    /**
     * Creates a config for Forward trajectory
     */
    public TrajectoryConfig configForward = new TrajectoryConfig(
        Constants.autoConstants.kMaxSpeedMetersPerSecond,
        Constants.autoConstants.kMaxAccelerationMetersPerSecondSquared)
        // Add kinematics to ensure max speed is actually obeyed
        .setKinematics(Constants.drivetrain.kDriveKinematics)
        //.setReversed(false)
        // Apply the voltage constraint
        .addConstraint(autoVoltageConstraint)
        .addConstraint( new CentripetalAccelerationConstraint(1));

    public TrajectoryConfig configForwardFast = new TrajectoryConfig(
            Constants.autoConstants.kFastSpeedMetersPerSecond,
            Constants.autoConstants.kFastAccelerationMetersPerSecondSquared)
            // Add kinematics to ensure max speed is actually obeyed
            .setKinematics(Constants.drivetrain.kDriveKinematics)
            //.setReversed(false)
            // Apply the voltage constraint
            .addConstraint(autoVoltageConstraint);

    /**
     * Creates a config for Reverse trajectory
     */
    public TrajectoryConfig configReverse = new TrajectoryConfig(
        Constants.autoConstants.kMaxSpeedMetersPerSecond,
        Constants.autoConstants.kMaxAccelerationMetersPerSecondSquared)
        // Add kinematics to ensure max speed is actually obeyed
        .setKinematics(Constants.drivetrain.kDriveKinematics)
        .setReversed(true)
        // Apply the voltage constraint
        .addConstraint(autoVoltageConstraint);

    /**
     * Creates a config for Reverse trajectory
     */
    public TrajectoryConfig configReverseSlow = new TrajectoryConfig(
        Constants.autoConstants.kSlowSpeedMetersPerSecond,
        Constants.autoConstants.kSlowAccelerationMetersPerSecondSquared)
        // Add kinematics to ensure max speed is actually obeyed
            .setKinematics(Constants.drivetrain.kDriveKinematics)
            .setReversed(true)
            // Apply the voltage constraint
            .addConstraint(autoVoltageConstraint);
    /**
     * Creates a Ramsete command given the defined trajectory
     */
    public RamseteCommand createRamseteCommand(Trajectory trajectory) {
        RamseteCommand rc = new RamseteCommand(trajectory,
            Drivetrain.getInstance()::getPose,
            new RamseteController(Constants.autoConstants.kRamseteB, Constants.autoConstants.kRamseteZeta),
            new SimpleMotorFeedforward(Constants.drivetrain.ksVolts,
                Constants.drivetrain.kvVoltSecondsPerMeter,
                Constants.drivetrain.kaVoltSecondsSquaredPerMeter),
            Constants.drivetrain.kDriveKinematics,
            Drivetrain.getInstance()::getWheelSpeeds,
            new PIDController(Constants.drivetrain.kPDriveVelLeft, 0, 0),
            new PIDController(Constants.drivetrain.kPDriveVelRight, 0, 0),
            // RamseteCommand passes volts to the callback
            Drivetrain.getInstance()::tankDriveVolts,
            Drivetrain.getInstance());

        return rc;
    }
}
