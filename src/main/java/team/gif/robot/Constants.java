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
        public static final double kP = 0.0005; // 0.0005 0.0008
        public static final double kF = 0.000166;
        public static final double RPM = 4500;
        public static final double maxVelocity = 5000;
    }

    public static class Hanger {
        public static final double P = 0.0; // Connor said 4
        public static final double I = 0.0;
        public static final double D = 0.0;
        public static final double F = 0.425;
        public static final double REV_F = 0.38;

        public static final double GRAV_FEED_FORWARD = 300 / 1023.0; // Percent constant to counteract gravity
        //public static final double REV_GRAV_FEED_FORWARD = 50 / 1023.0;

        public static final int ALLOWABLE_ERROR = 100; // Error to allow move command to end
        public static final int MAX_VELOCITY = 2000; // RPM
        public static final int MIN_VELOCITY = 0;
        //public static final int REV_MAX_VELOCITY = 2800;
        public static final int MAX_ACCELERATION = 1500;

        public static final int MAX_POS = 30000;
        public static final int MIN_POS = 1000;

        public static final int DEPLOYED_POS = 15000;
    }

    //        //pivot globals
    public static double marginx =1;
    public static double marginx1 =.5;
    public static double kPx =.05;
    public static double kFx = .2;

    public static double WheelDiameter = 0.127;
    public static double encoderEPR;
    //public static double
    //public static double

}
