package team.gif.robot.commands.drivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.Constants;
import team.gif.robot.subsystems.Drivetrain;
import team.gif.robot.subsystems.drivers.Pigeon;

public class rotate extends CommandBase {

    public double deltaDegrees = 0;
    public double kPvolts = Constants.drivetrain.kPDriveVelLeft;
    public double kSvolts = Constants.drivetrain.ksVolts;
    public double marginDegrees = 0;
    // we will be +- margin degrees degrees of zero
    public double motorVolts = 0;
    public double targetHeadingDegrees = 0;
    public double currentHeadingDegrees = 0;
    public boolean exitCommand = false;

    public rotate(){
        System.out.println("rotate");
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {

        currentHeadingDegrees = Pigeon.getInstance().getHeading();
        deltaDegrees = ((((targetHeadingDegrees - currentHeadingDegrees)+ 540)%360)-180);

        // result is between -180 and 180 where positive is clockwise and - is counter clockwise
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

        motorVolts = deltaDegrees<0 ? kSvolts : -kSvolts;

        if (Math.abs(deltaDegrees) <= marginDegrees){
            motorVolts = 0;
            exitCommand = true;
        }

        Drivetrain.getInstance().tankDriveVolts(motorVolts, -motorVolts);
    }

    @Override
    public void end(boolean interrupted) {
        Drivetrain.getInstance().setSpeed(0,0);
    }

    @Override
    public boolean isFinished() {
        return exitCommand;
    }
}