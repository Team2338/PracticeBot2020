package team.gif.lib;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.util.Units;

public class Pose2dFeet extends Pose2d {
    Pose2d poseFeet;
    /*                      0
    *   Forward            +x    Heading can be -180 to 180 or 0 to 359, either will work
    *     _^_               ^       0 points straight ahead either way
    *    |   |   -90        |       code auto determines which is being used
    *    |   |    or  +y <--   90
    *    |___|   270
    *                      180
    */
    public Pose2dFeet(double x, double y, int heading){
        // if compass heading is being used, convert to -180:180 scale
        heading = heading>180 ? -360 + heading : heading;

        poseFeet = new Pose2d( Units.feetToMeters(x),
                               Units.feetToMeters(y),
                               new Rotation2d(Units.degreesToRadians(-heading))); // negative because Rotation2d uses west = +90
    }

    public Pose2d get(){
        return poseFeet;
    }
}
