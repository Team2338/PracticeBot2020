
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
        marginx = Constants.DriverCommands.marginx;
        marginxI= Constants.DriverCommands.marginxI;
        addRequirements(Drivetrain.getInstance());
    }

    public boolean endthing = false;
    public double powerL =0;
    public double powerR =0;
    public double marginxI = 0;
    public double marginx = 0;
    public double kIx =1;
    public double kPx =1;
    public double Ilooper=0;
    public double xoffset =0;
    public double initial = 0;
    public double target = 0;
    public int intarget = 0;
    public int timeintarget = 10;
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {

        System.out.println("rotate start");

        Robot.autotab.add("rotate target",target);
        endthing = false;
        Pigeon.getInstance().setyaw(0);
        initial = Pigeon.getInstance().getYPR()[0];
        target = xoffset + initial;

        marginx = Constants.DriverCommands.marginx;
        marginxI = Constants.DriverCommands.marginxI;
        kIx = Constants.DriverCommands.kIx;
        kPx = Constants.DriverCommands.kPx;

        Robot.autotab.add("rotate trying to get there",true);

        initial = Pigeon.getInstance().getYPR()[0];
        xoffset = Robot.limelight.getXOffset();
        target = xoffset + initial;

        Robot.shootertab.add("target",target);
        Robot.shootertab.add("offset from target",xoffset);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        System.out.println("rotating");
        Robot.autotab.add("Ilooper rotate",Ilooper);

        xoffset = Pigeon.getInstance().getYPR()[0]- target;
        Robot.autotab.add("xoffset",xoffset);

        if(Math.abs(xoffset)<marginx){
             intarget++;
             if(intarget>timeintarget){
                 endthing = true;
             }

        }else if(Math.abs(xoffset)<marginxI){
             Ilooper += xoffset;
             powerL = -1*kPx*xoffset+ Ilooper*kIx;
             powerR = 1*kPx*xoffset+ Ilooper*kIx;
             intarget =0;
        }else{
             powerL = -1*kPx*xoffset;
             powerR = 1*kPx*xoffset;
             intarget =0;
        }
        Drivetrain.getInstance().setSpeed(powerR ,powerL);
        Robot.autotab.add("PowerL",powerL);
        Robot.autotab.add("PowerR",powerR);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        Robot.autotab.add("rotate trying to get there",false);
        Drivetrain.getInstance().setSpeed(0, 0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return endthing;
    }
}