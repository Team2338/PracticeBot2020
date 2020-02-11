package team.gif.robot.commands.autoaim;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.Robot;
import team.gif.robot.Constants;
import team.gif.robot.subsystems.Drivetrain;

public class Pivot extends CommandBase {
    public Pivot() {
        addRequirements(Drivetrain.getInstance());

    }

    public static double marginx ;
    public static double kPx ;
    public static double kPy;
    public static double kFx;
    public static double kFy;
    public static boolean endthing = false;

    @Override
    public void initialize() {
        System.out.println("pivot");

        marginx  = Constants.marginx;
        kPx  = Constants.kPx;
        kFx = Constants.kFx;
    }

    @Override
    public void execute() {

        double xoffset = Robot.limelight.getXOffset();
        //double yoffset = Robot.limelight.getYOffset();
        double powerL;
        double powerR;
        SmartDashboard.putBoolean("see target",Robot.limelight.hasTarget());
        if(xoffset>marginx ||xoffset<-marginx ) {//aligning to x offset
            //SmartDashboard.putBoolean("see target1",Robot.limelight.hasTarget());
            powerL =-1*kPx*xoffset +kFx;
            powerR = 1*kPx*xoffset+kFx;
            Drivetrain.getInstance().setSpeed(powerL ,powerR);
            SmartDashboard.putBoolean("are we there yet x" , false);
            SmartDashboard.putNumber("PowerL",powerL);
            SmartDashboard.putNumber("PowerR",powerR);
        }else{
            System.out.println("wegot there");
            endthing = true;
            Drivetrain.getInstance().setSpeed(0,0);
            SmartDashboard.putBoolean("are we there yet x", true);
        }
    }

    @Override
    public void end(boolean interrupted) {
        Drivetrain.getInstance().setSpeed(0, 0);
    }

    @Override
    public boolean isFinished() {
        return endthing;
    }
}