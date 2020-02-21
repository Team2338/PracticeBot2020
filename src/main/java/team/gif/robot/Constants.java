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
        // public static final double BUMPER_LENGTH = 0;
        // public static final double BUMPER_WIDTH = 0;
        public static final double TICKS_PER_REV = 4096; // Using mag encoders
        public static final double DPS_TO_MPS = (WHEEL_DIAMETER * 0.0254 * Math.PI) / (360); // Degrees per sec. to meters per sec.
        public static final double TICKS_TO_METERS = (WHEEL_DIAMETER * Math.PI) / (TICKS_PER_REV);
    }

    public static class Shooter {
        public static final double kP = 0.0008;
        public static final double kF = 0.000177;
        public static final double RPM = 4500;
        public static final double maxVelocity = 5000;
    }

    //        //pivot globals
    public static double marginx =1;
    public static double marginx1 =.5;
    public static double kPx =.05;
    public static double kFx = .2;

    public static class DriverCommands{
        public static double marginx = 1.5;
        public static double marginxI = 3;
        public static double turned=0;
        public static double kPx = .05;
        public static double kFx = 0;//connors idea
        public static double kIx = .05;
    }

    public static double WheelDiameter = 0.127;
    public static double encoderEPR;
    //public static double
    //public static double

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
