package team.gif.robot.commands.drivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.Constants;
import team.gif.robot.subsystems.Drivetrain;
import team.gif.robot.subsystems.drivers.Pigeon;

public class rotate extends CommandBase {

    public double rightpower =0;
    public double leftpower =0;
    public double offset = 0;
    public double kP = Constants.drivetrain.kPDriveVelLeft;
    public double kF = Constants.drivetrain.ksVolts;
    public double margin = 2;
    public double steering_adjust = 0;
    public double target = 0;
    public double pos = 0;

    public rotate(){
        System.out.println("rotate");
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {

        pos = Pigeon.getInstance().getYPR()[0];
        offset = ((((target - pos)+ 540)%360)-180);
        // above math came from the math stack exchange ie stackoverflow but with nerds
        // + offset turn right by convention
        if (offset < -margin/2) {
            steering_adjust = kP*offset - kF;
            leftpower += steering_adjust;
            rightpower -= steering_adjust;
        } else if (offset > margin/2) {
            steering_adjust = kP*offset + kF;
            leftpower += steering_adjust;
            rightpower -= steering_adjust;
        } else if (Math.abs(offset) <= margin/2){
            leftpower = 0;
            rightpower = 0;
        }

        // i thought that leftpower = kP
        // in the limelight docs they use += instead
        // this does not make sense to me because if it is not at the target but close it still acelerates towards it
        // just not as fast and
        // In their offset they have offset > or < 1 which does not make sense to me
        // if that was a deadband of sorts then it should have been < -1 or > 1 making a margin of 2 degrees
        // i just noticed that the indentations for all the files are not consistant so can we go to the standard 5 space

        Drivetrain.getInstance().tankDriveVolts(leftpower,rightpower);
    }

    @Override
    public void end(boolean interrupted) {
        leftpower = 0;
        rightpower = 0;
        Drivetrain.getInstance().setSpeed(leftpower,rightpower);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
