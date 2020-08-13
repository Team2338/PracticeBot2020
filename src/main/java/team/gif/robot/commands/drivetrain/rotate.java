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
    public double margin = 1;
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
        if (offset < -margin ) {
            steering_adjust = kP*offset - kF;
        } else if (offset > margin) {
            steering_adjust = kP*offset + kF;
        }

        leftpower += steering_adjust;
        rightpower -= steering_adjust;

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
