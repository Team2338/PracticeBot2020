

package team.gif.robot;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 *
 * set kF to get close to the target, just under (set kP = 0)
 *
 */
public final class Constants {

    public static class Shooter {
        public static final double kP = 0.0007; // 0.0005; // set to 0 and find
        public static final double kF = 0.000175; // 0.000166;
        public static final double RPM_LOW = Robot.isCompBot ? 4300 : 4600; // C:4300 P: 4600 // P was 4500
        //public static final double RPM_RAPID_FIRE = 4650; // 4300
        public static final double RPM_HIGH = 4650; // was 4550
    }

    public static class Hanger {
        // Elevator
        public static final double P = 0.0; // Connor said 4
        public static final double I = 0.0;
        public static final double D = 0.0;
        public static final double F = 0; //0.425
        public static final double REV_F = 0.38;

        public static final double GRAV_FEED_FORWARD = 300 / 1023.0; // Percent constant to counteract gravity

        public static final int MAX_POS = 150; //145; //150; // 160;
        public static final int MIN_POS = 1;

        // Color Wheel
        public static final int COLOR_WHEEL_POSITION = 25;
    }

    public static class Pivot {
        //public static double marginx = 0;
        public static double marginxF = 1.8;//OG 2 in the 3 ball auto
        public static double marginxI = 4;//og 4
        public static double kPx = .05;//from .037
        public static double kIx = .007;//from .025
    }

    public static class drivetrain {
        public static double WHEEL_DIAMETER = 0.127; // IN METERS
        public static double WHEEL_CICUMFERENCE = WHEEL_DIAMETER * 3.14159; // IN METERS

        public static double TICKS_TO_METERS = 10000;
        public static double TICKS_TO_METERS_LEFT = 10115; //10058; // Pushed bot 17 feet, recorded ticks (51816), converted to meters
        public static double TICKS_TO_METERS_RIGHT = 10005; // 9915; // 9915; // Pushed bot 17 feet, recorded ticks (51816), converted to meters

        // trajectory
        // from FRC Characterization Tool
        public static final double ksVolts = 1.37;
        public static final double kvVoltSecondsPerMeter = 2.46;
        public static final double kaVoltSecondsSquaredPerMeter = 0.773;
        public static final double kPDriveVelLeft = 10.0;
        public static final double kPDriveVelRight = kPDriveVelLeft;
        public static final double kTrackWidthMeters = 1.0;
        public static final DifferentialDriveKinematics kDriveKinematics = new DifferentialDriveKinematics(kTrackWidthMeters);
    }
    public static class autoConstants {
        // part of trajectory but numbers are from example
        public static final double kMaxSpeedMetersPerSecond = 3.0;
        public static final double kMaxAccelerationMetersPerSecondSquared = 3.0;
        public static final double kSlowSpeedMetersPerSecond = 1.5;
        public static final double kSlowAccelerationMetersPerSecondSquared = 1.5;
        public static final double kFastSpeedMetersPerSecond = 4.0;
        public static final double kFastAccelerationMetersPerSecondSquared = 4.0;
        public static final double kRamseteB = 2;
        public static final double kRamseteZeta = 0.7;
    }
}
