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
        public static final double kP = 0.0008;
        public static final double kF = 0.000177;
        public static final double RPM = 4550;
        public static final double maxVelocity = 5000;
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
