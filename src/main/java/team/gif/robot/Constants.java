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

<<<<<<< HEAD
    /*        //pivot globals
    public static double marginx =1;
    public static double marginx1 =.5;
    public static double kPx =.04;
    public static double kIx = .025;

     */

    // Drivetrain globals
    public static double WHEEL_DIAMETER = 0.127; // DIAMETER AND RADIUS ARE IN METERS
    public static double WHEEL_RADIUS = 0.0635;
    public static double ENCODER_EPR = 4096; // EPR = Edges per revolution (IN TICKS)

    public static double TICKS_TO_METERS = (WHEEL_DIAMETER * Math.PI) / (ENCODER_EPR);
    public static double DPS_TO_MPS = ((WHEEL_DIAMETER * 0.0254 * Math.PI) / (360) * (10)); // Degrees per sec. to meters per sec.

    public static double kTrackWidth = 0.76884201;

    // Must be tuned
    public static double DRIVE_P = 0.0;
    public static double DRIVE_I = 0.0;
    public static double DRIVE_D = 0.0;
    public static double DRIVE_ANGLE_P = 0.0;
    public static double DRIVE_ANGLE_I = 0.0;
    public static double DRIVE_ANGLE_D = 0.0;
    public static double DRIVE_ANGLE_I_ZONE = 0.0;
    public static double DRIVE_STRAIGHT_ANGLE_P = 0.0;
    public static double DRIVE_DIST_TOLERANCE = 0;
    public static double DRIVE_ANGLE_TOLERANCE = 0;
=======
    //        //pivot globals
    public static class Pivot {
        //public static double marginx = 0;
        public static double marginxF = 1.8;//OG 2 in the 3 ball auto
        public static double marginxI = 4;//og 4
        public static double kPx = .037;//from .04
        public static double kIx = .007;//from .025
    }
    public static double WheelDiameter = 0.127;
    public static double encoderEPR;
    //public static double
    //public static double
>>>>>>> master

}
