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
        public static final double WHEEL_DIAMETER = 0.0;
        public static final double BUMPER_LENGTH = 0.0;
        public static final double BUMPER_WIDTH = 0.0;
        public static final double ENCODER_TICKS_PER_REV = 4096;
        public static final double TICKS_TO_DPP = (WHEEL_DIAMETER * Math.PI) / (ENCODER_TICKS_PER_REV);
    }

    public static class Shooter {
        public static final double kP = 0.0008;
        public static final double kF = 0.000177;
        public static final double RPM = 3900;
        public static final double maxVelocity = 5000;
    }

    /* For Trajectory/Pathfinder in auto
     Values are only placeholders until we characterize
     */
    public static class TrajectoryConstants {
        public static final double ksVolts = 0;
        public static final double kvVoltsSecondsPerMeter = 0;
        public static final double kvVoltsPerSquaredMeter = 0;

        public static final double kPDriveVel = 0;

        public static final double kTrackwidthMeters = 0; // DPP (Distance Per Pulse)
        public static final DifferentialDriveKinematics kDriveKinematics = new DifferentialDriveKinematics(kTrackwidthMeters);

        public static final double kMaxMetersPerSecond = 0;
        public static final double kMaxAccelerationMetersPerSecondSquared = 0;
    }

    // For RamseteController, WPILib's built-in trajectory tracker
    public static class RamseteConstants {
        public static final double kRamseteB = 0.0;
        public static final double kRamseteZeta = 0.0;
    }
}
