package team.gif.robot.commands.drivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.Constants;
import team.gif.robot.subsystems.Drivetrain;
import team.gif.robot.subsystems.drivers.Pigeon;

public class rotate extends CommandBase {

    public double deltaDegrees = 0;
    public double kPVolts = Constants.drivetrain.kPDriveVelLeft;
    public double kSVolts = Constants.drivetrain.ksVolts;
    public double marginDegrees = 0;
    // we will be +- margin degrees degrees of zero

    public double targetHeadingDegrees = 0;  // 0 to 360 counterclockwise
    public double currentHeadingDegrees = 0; // 0 to 360 counterclockwise
    public double initialHeadingDegrees = 0;  // 0 to 360 counterclockwise
    public boolean exitCommand = false;

    // amount of voltage we want to apply to the motors for this test
    public double motorVolts = 6.0;

    public rotate(){
        System.out.println("rotate");
    }

    @Override
    public void initialize() {
        initialHeadingDegrees = Pigeon.getInstance().get360Heading();
        deltaDegrees = ((((targetHeadingDegrees - initialHeadingDegrees)+ 540)%360)-180);
        // result is between 179.99 and -180 where positive is counterclockwise and negative is clockwise

        // applied voltage must be at least the voltage which will make the robot move
        motorVolts = motorVolts < kSVolts ? kSVolts : motorVolts;

        // if degrees are negative, turn clockwise, otherwise turn counterclockwise
        motorVolts = deltaDegrees < 0 ? motorVolts : -motorVolts;

        Drivetrain.getInstance().tankDriveVolts(motorVolts, -motorVolts);
    }

    @Override
    public void execute() {

        // above math came from the math stack exchange ie stackoverflow but with nerds
        // + offset turn right by convention
        /*
        if (deltaDegrees < -margin_degrees) {
            steering_adjustvolts = Math.abs(kPvolts *deltaDegrees) < kSvolts ? kSvolts : kPvolts*deltaDegrees;
            rightVolts = steering_adjustvolts;
            rightVolts = -steering_adjustvolts;
        } else if (deltaDegrees > margin_degrees) {
            steering_adjustvolts = Math.abs(kPvolts *deltaDegrees) < kSvolts ? -kSvolts : kPvolts*deltaDegrees;
            rightVolts = steering_adjustvolts;
            rightVolts = -steering_adjustvolts;
        } else if (Math.abs(deltaDegrees) <= margin_degrees){
            rightVolts = 0;
            rightVolts = 0;
            exitCommand = true;
        }
 */
        double degreesTurned;

        currentHeadingDegrees = Pigeon.getInstance().get360Heading();
        degreesTurned = Math.abs( ((((initialHeadingDegrees - currentHeadingDegrees)+ 540)%360)-180) );

        if (degreesTurned >= Math.abs(deltaDegrees)){
            Drivetrain.getInstance().setSpeed(0,0);
            exitCommand = true;
        }
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return exitCommand;
    }
}