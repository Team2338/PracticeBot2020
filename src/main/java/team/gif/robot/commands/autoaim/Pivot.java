package team.gif.robot.commands.autoaim;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.OI;
import team.gif.robot.Robot;
import team.gif.robot.Constants;
import team.gif.robot.subsystems.Drivetrain;

public class Pivot extends CommandBase {
    public Pivot() {
        SmartDashboard.putBoolean("trying to get there",false);
        SmartDashboard.putBoolean("are we there yet x" , false);
        OI.getInstance().aux.setRumble(GenericHID.RumbleType.kLeftRumble,0);
        OI.getInstance().aux.setRumble(GenericHID.RumbleType.kRightRumble,0);
        //addRequirements(Drivetrain.getInstance());

    }

    public static double marginx ;
    public static double kPx ;
    public static double kPy;
    public static double kFx;
    public static double kFy;
    public static boolean endthing = false;

    public double looptime = 0;
    public double kI =.00;
    public double Ilooper =0;
    public double looped =0;

    @Override
    public void initialize() {
        looptime = 0;
        looped =0;
        Ilooper = 5;
        SmartDashboard.putBoolean("trying to get there",true);
        System.out.println("pivot");

        marginx  = Constants.marginx;
        kPx  = Constants.kPx;
        //kFx = Constants.kFx;
    }

    @Override
    public void execute() {
        System.out.println("pivoting");

        double xoffset = Robot.limelight.getXOffset();
        //double yoffset = Robot.limelight.getYOffset();
        double powerL;
        double powerR;

        SmartDashboard.putBoolean("see target",Robot.limelight.hasTarget());
        //if(xoffset>marginx ||xoffset<-marginx ) {//aligning to x offset
            //SmartDashboard.putBoolean("see target1",Robot.limelight.hasTarget());

        if(Math.abs(xoffset)<marginx){
            Ilooper += xoffset;
            powerL = -1*kPx*xoffset+ Ilooper*kI;
            powerR = 1*kPx*xoffset+ Ilooper*kI;
            OI.getInstance().aux.setRumble(GenericHID.RumbleType.kLeftRumble,1);
            OI.getInstance().aux.setRumble(GenericHID.RumbleType.kRightRumble,1);
        }else{
            Ilooper = 0;
            powerL = -1*kPx*xoffset;
            powerR = 1*kPx*xoffset;
            OI.getInstance().aux.setRumble(GenericHID.RumbleType.kLeftRumble,0);
            OI.getInstance().aux.setRumble(GenericHID.RumbleType.kRightRumble,0);
        }

        Drivetrain.getInstance().setSpeed(powerR ,powerL);
        SmartDashboard.putNumber("PowerL",powerL);
        SmartDashboard.putNumber("PowerR",powerR);
        //looped =0;




    }

    @Override
    public void end(boolean interrupted) {
        OI.getInstance().aux.setRumble(GenericHID.RumbleType.kLeftRumble,0);
        OI.getInstance().aux.setRumble(GenericHID.RumbleType.kRightRumble,0);
        SmartDashboard.putBoolean("trying to get there",false);
        //Drivetrain.getInstance().setSpeed(0, 0);
    }

    @Override
    public boolean isFinished() {
        return endthing;
    }
}