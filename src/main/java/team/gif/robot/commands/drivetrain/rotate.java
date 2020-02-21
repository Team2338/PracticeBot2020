
package team.gif.robot.commands.drivetrain;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import team.gif.robot.Constants;
import team.gif.robot.Robot;
import team.gif.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.subsystems.drivers.Pigeon;


/**
 * An example command that uses an example subsystem.
 */
public class rotate extends CommandBase {
    //@SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})


    public rotate(double angle) {
        xoffset = angle;
        kPx = Constants.DriverCommands.kPx;
        kIx = Constants.DriverCommands.kIx;
        addRequirements(Drivetrain.getInstance());
    }
    public boolean endthing = false;
    public static double powerL =0;
    public static double powerR =0;
    public static double marginxI = 0;
    public static double marginx = 0;
    public static double kIx =1;
    public static double kPx =1;
    public static double Ilooper=0;
    public static double xoffset =0;
    public static double initial = 0;
    public static double target = 0;
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        SmartDashboard.putNumber("target",target);
        System.out.println("rotate init "+ target);
        endthing = false;
        initial = Pigeon.getInstance().getYPR()[0];
        target = xoffset + initial;
        xoffset = target - Pigeon.getInstance().getYPR()[0];
        marginx = Constants.DriverCommands.marginx;
        marginxI = Constants.DriverCommands.marginxI;
        kIx = Constants.DriverCommands.kIx;
        kPx = Constants.DriverCommands.kPx;
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        System.out.println("rotating");
        SmartDashboard.putNumber("Ilooper",Ilooper);

        double xoffset = Pigeon.getInstance().getYPR()[0]- target;
        SmartDashboard.putNumber("xoffset",xoffset);
        //double yoffset = Robot.limelight.getYOffset();

        SmartDashboard.putBoolean("see target",Robot.limelight.hasTarget());
        //if(xoffset>marginx ||xoffset<-marginx ) {//aligning to x offset
        //SmartDashboard.putBoolean("see target1",Robot.limelight.hasTarget());

        if(Math.abs(xoffset)<marginx){
             endthing = true;
        }else if(Math.abs(xoffset)<marginxI){
             Ilooper += xoffset;
             powerL = -1*kPx*xoffset+ Ilooper*kIx;
             powerR = 1*kPx*xoffset+ Ilooper*kIx;
        }else{
             powerL = -1*kPx*xoffset;
             powerR = 1*kPx*xoffset;
        }
        Drivetrain.getInstance().setSpeed(powerR ,powerL);
        SmartDashboard.putNumber("PowerL",powerL);
        SmartDashboard.putNumber("PowerR",powerR);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        Drivetrain.getInstance().setSpeed(0, 0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return endthing;
    }
}