package team.gif.robot.commands.drivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.Constants;
import team.gif.robot.subsystems.Drivetrain;
import team.gif.robot.subsystems.drivers.Pigeon;

public class rotate extends CommandBase {

    public double rightvolts =0;
    public double leftvolts =0;
    public double deltadegrees = 0;
    public double kPvolts = Constants.drivetrain.kPDriveVelLeft;
    public double kSvolts = Constants.drivetrain.ksVolts;
    public double margin_degrees = 2;
    // we will be +- margin degrees degrees of zero
    public double steering_adjustvolts = 0;
    public double target = 0;
    public double pos = 0;
    public double current_heading_degrees = 0;
    public boolean exitCommand = false;

    public rotate(){
        System.out.println("rotate");
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {

        current_heading_degrees = Pigeon.getInstance().getHeading();
        deltadegrees = ((((target - pos)+ 540)%360)-180);
        // result is between -180 and 180 where positive is clockwise and - is counter clockwise
        // above math came from the math stack exchange ie stackoverflow but with nerds
        // + offset turn right by convention

        if (deltadegrees < -margin_degrees) {
            steering_adjustvolts = Math.abs(kPvolts *deltadegrees) < kSvolts ? kSvolts : kPvolts*deltadegrees;
            rightvolts = steering_adjustvolts;
            rightvolts = -steering_adjustvolts;
        } else if (deltadegrees > margin_degrees) {
            steering_adjustvolts = Math.abs(kPvolts *deltadegrees) < kSvolts ? -kSvolts : kPvolts*deltadegrees;
            rightvolts = steering_adjustvolts;
            rightvolts = -steering_adjustvolts;
        } else if (Math.abs(deltadegrees) <= margin_degrees){
            rightvolts = 0;
            rightvolts = 0;
            exitCommand = true;
        }



        Drivetrain.getInstance().tankDriveVolts(leftvolts, rightvolts);
    }

    @Override
    public void end(boolean interrupted) {
        leftvolts = 0;
        rightvolts = 0;
        Drivetrain.getInstance().setSpeed(leftvolts, rightvolts);
    }

    @Override
    public boolean isFinished() {
        return exitCommand;
    }
}
