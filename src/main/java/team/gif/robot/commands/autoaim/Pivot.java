package team.gif.robot.commands.autoaim;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.Robot;
import team.gif.robot.Constants;
import team.gif.robot.subsystems.Drivetrain;

public class Pivot extends CommandBase {
    public Pivot() {
        SmartDashboard.putBoolean("trying to get there",false);
        SmartDashboard.putBoolean("are we there yet x" , false);
        //addRequirements(Drivetrain.getInstance());

    }

    public static double marginx ;
    public static double kPx ;
    public static double kPy;
    public static double kFx;
    public static double kFy;
    public static boolean endthing = false;

    public double looptime = 10;
    public double kI =.001;
    public double Ilooper =0;
    public double looped =0;

    @Override
    public void initialize() {
        looptime = 10;
        looped =0;
        Ilooper = 0;
        SmartDashboard.putBoolean("trying to get there",true);
        System.out.println("pivot");

        marginx  = Constants.marginx;
        kPx  = Constants.kPx;
        kFx = Constants.kFx;
    }

    @Override
    public void execute() {
        System.out.println("pivoting");

        double xoffset = Robot.limelight.getXOffset();
        //double yoffset = Robot.limelight.getYOffset();
        double powerL;
        double powerR;
        int t = 0;
        if(xoffset<0){
            t=-1;
        }else{
            t=1;
        }

        SmartDashboard.putBoolean("see target",Robot.limelight.hasTarget());
        if(xoffset>marginx ||xoffset<-marginx ) {//aligning to x offset
            //SmartDashboard.putBoolean("see target1",Robot.limelight.hasTarget());
            powerL = -1*kPx*xoffset +-t*kFx+ -t*Ilooper*kI;
            powerR = 1*kPx*xoffset+t*kFx+ t*Ilooper*kI;
            Drivetrain.getInstance().setSpeed(powerR ,powerL);
            SmartDashboard.putBoolean("are we there yet x" , false);
            SmartDashboard.putNumber("PowerL",powerL);
            SmartDashboard.putNumber("PowerR",powerR);
            looped =0;
            Ilooper ++;
        }else if(xoffset<marginx ||xoffset>-marginx ){
            looped++;
        }else if(looped >looptime){
            System.out.println("wegot there");
            Drivetrain.getInstance().setSpeed(0,0);
            SmartDashboard.putBoolean("are we there yet x", true);
            endthing = true;
        }
    }

    @Override
    public void end(boolean interrupted) {
        SmartDashboard.putBoolean("trying to get there",false);
        Drivetrain.getInstance().setSpeed(0, 0);
    }

    @Override
    public boolean isFinished() {
        return endthing;
    }
}