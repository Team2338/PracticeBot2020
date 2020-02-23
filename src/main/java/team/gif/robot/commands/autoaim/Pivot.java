package team.gif.robot.commands.autoaim;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.OI;
import team.gif.robot.Robot;
import team.gif.robot.Constants;
import team.gif.robot.subsystems.Drivetrain;
import team.gif.robot.subsystems.drivers.Pigeon;

public class Pivot extends CommandBase {
    public static boolean state = true;

    public Pivot(boolean state/*true to keep going, false to kill*/){
        endthing = false;

        Robot.shootertab.add("we not there yet pivot",true);

        kIx = Constants.DriverCommands.kIx;
        kPx = Constants.DriverCommands.kPx;
        marginx = Constants.DriverCommands.marginx;
        marginxI = Constants.DriverCommands.marginxI;
    }

    public double marginx ;
    public double kPx ;
    //public double kPy;
    //public double kFx;
    //public double kFy;
    public boolean endthing = false;

    //public double looptime = 0;
    public double kIx =.05;
    public double Ilooper =0;
    //public double looped =0;
    //public double yaw0 =0;
    //public double turned =0;
    public double initial =0;
    public double marginxI = 0;
    public double xoffset =0;
    public double target =0;
    /*
    TODO:
        we gotta change margin to be 1/2 of the lower side length before second midwest
    */
    @Override
    public void initialize() {

        System.out.println("pivot start");
        Robot.shootertab.add("pivot trying to get there",true);

        Pigeon.getInstance().setyaw(0);
        initial = Pigeon.getInstance().getYPR()[0];
        xoffset = Robot.limelight.getXOffset();
        target = xoffset + initial;

        Robot.shootertab.add("target",target);
        Robot.shootertab.add("offset from target",xoffset);

    }

    @Override
    public void execute() {
        Robot.shootertab.add("Ilooper",Ilooper);
        System.out.println("pivoting");

        xoffset = Pigeon.getInstance().getYPR()[0]- target;
        Robot.shootertab.add("offset from target",xoffset);
        //double yoffset = Robot.limelight.getYOffset();
        double powerL;
        double powerR;
        //if(xoffset>marginx ||xoffset<-marginx ) {//aligning to x offset
            //SmartDashboard.putBoolean("see target1",Robot.limelight.hasTarget());
        if(Math.abs(xoffset)<marginxI){
            Ilooper += xoffset;
            /**
             * TODO: maybe try removing P in here
             * **/
            powerL = kPx*xoffset+ Ilooper*kIx;
            powerR = -1*kPx*xoffset+ Ilooper*kIx;
            Robot.shootertab.add("Ilooping",true);
        }else{
            Ilooper = 0;
            powerL = kPx*xoffset;
            powerR = -1*kPx*xoffset;
            Robot.shootertab.add("Ilooping",false);
        }
        Drivetrain.getInstance().setSpeed(powerR ,powerL);
        Robot.shootertab.add("PowerL",powerL);
        Robot.shootertab.add("PowerR",powerR);
    }

    @Override
    public void end(boolean interrupted) {
        //OI.getInstance().aux.setRumble(GenericHID.RumbleType.kLeftRumble,0);
        //OI.getInstance().aux.setRumble(GenericHID.RumbleType.kRightRumble,0);
        System.out.println("pivot end");
        Constants.DriverCommands.turned = Pigeon.getInstance().getYPR()[0] - initial;
        Robot.shootertab.add("turned",Constants.DriverCommands.turned);
        Robot.shootertab.add("pivot trying to get there",false);
        Robot.shootertab.add("target",0);
        Robot.shootertab.add("offset from target",0);

        //Drivetrain.getInstance().setSpeed(0, 0);
    }
    @Override
    public boolean isFinished() {
        return !state;
    }
}