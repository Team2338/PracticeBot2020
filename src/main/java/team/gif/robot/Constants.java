/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package team.gif.robot;

import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    public static class Drivetrain {
        // All units for characterization are in meters!
        public static final double WHEEL_DIAMETER = 0.127;
        public static final double BUMPER_LENGTH = 0.06;
        public static final double BUMPER_WIDTH = 0.06;
        public static final double TICKS_PER_REV = 4096; // Using mag encoders
        public static final double DPS_TO_MPS = (WHEEL_DIAMETER * 0.0254 * Math.PI) / (360); // Degrees per sec to meters per sec
        public static final double TICKS_TO_METERS = (WHEEL_DIAMETER * Math.PI) / (TICKS_PER_REV);
    }

    public static class Shooter {
        public static final double kP = 0.0005; // 0.0005 0.0008
        public static final double kF = 0.000166;
        public static final double RPM = 4500;
        public static final double maxVelocity = 5000;
    }

    /* For Trajectory/Pathfinder in auto
     Values are only placeholders until we characterize
     */
    public static class TrajectoryConstants {
        public static final double ksVolts = 1.13;
        public static final double kvVoltSecondsPerMeter = 2.56;
        public static final double kaVoltSecondsSquaredPerMeter = 0.606;

        public static final double kPDriveVel = 18.5;

        public static final double kTrackwidthMeters = 0.76884201; // DPP (Distance Per Pulse)
        public static final DifferentialDriveKinematics kDriveKinematics = new DifferentialDriveKinematics(kTrackwidthMeters);

        // Velocity and acceleration constraints
        public static final double kMaxMetersPerSecond = 0.2;
        public static final double kMaxAccelerationMetersPerSecondSquared = 0.04;
    }

    // For RamseteController, WPILib's built-in trajectory tracker
    public static class RamseteConstants {
        public static final double kRamseteB = 2.0;
        public static final double kRamseteZeta = 0.7;
    }
}
