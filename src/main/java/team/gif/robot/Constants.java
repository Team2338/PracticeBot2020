/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package team.gif.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    public static class Shooter {
        public static final double kP = 0.0005;
        public static final double kF = 0.000166;
        public static final double RPM = Robot.isCompBot ? 4300 : 4600; // C:4300 P: 4600 // P was 4500
        public static final double maxVelocity = 5000;
    }

    public static class Hanger {
        // Elevator
        public static final double P = 0.0; // Connor said 4
        public static final double I = 0.0;
        public static final double D = 0.0;
        public static final double F = 0; //0.425
        public static final double REV_F = 0.38;

        public static final double GRAV_FEED_FORWARD = 300 / 1023.0; // Percent constant to counteract gravity

        public static final int ALLOWABLE_ERROR = 100; // Error to allow move command to end
        public static final int MAX_VELOCITY = 2000; // RPM
        public static final int MIN_VELOCITY = 0;
        public static final int MAX_ACCELERATION = 1500;

        public static final int MAX_POS = 160;
        public static final int MIN_POS = 0;


        public static final int DEPLOYED_POS = 15000;

        // Color Wheel
        public static final int COLOR_WHEEL_POSITION = 30;
    }

    public static class Pivot {
        //public static double marginx = 0;
        public static double marginxF = 1.8;//OG 2 in the 3 ball auto
        public static double marginxI = 4;//og 4
        public static double kPx = .05;//from .037
        public static double kIx = .007;//from .025
    }
    public static double WheelDiameter = 0.127;
    public static double encoderEPR;
}
