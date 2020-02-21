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
        SmartDashboard.putBoolean("trying to get there",false);
        SmartDashboard.putBoolean("are we there yet x" , false);
        //OI.getInstance().aux.setRumble(GenericHID.RumbleType.kLeftRumble,0);
        //OI.getInstance().aux.setRumble(GenericHID.RumbleType.kRightRumble,0);
        //addRequirements(Drivetrain.getInstance());

    }

    public static double marginx ;
    public static double kPx ;
    public static double kPy;
    public static double kFx;
    public static double kFy;
    public static boolean endthing = false;

    public double looptime = 0;
    public double kIx =.00;
    public double Ilooper =0;
    public double looped =0;
    public double yaw0 =0;
    public double turned =0;
    public double initial =0;
    public static double marginxI = 0;

    @Override
    public void initialize() {
        endthing = false;
        initial = Pigeon.getInstance().getYPR()[0];
        marginx = Constants.DriverCommands.marginx;
        marginxI = Constants.DriverCommands.marginxI;
        kIx = Constants.DriverCommands.kIx;
        kPx = Constants.DriverCommands.kPx;
        //kFx = Constants.kFx;
    }

    @Override
    public void execute() {
        System.out.println("pivoting");

        double xoffset = Pigeon.getInstance().getYPR()[0]- initial;
        SmartDashboard.putNumber("xoffset",xoffset);
        //double yoffset = Robot.limelight.getYOffset();
        double powerL;
        double powerR;

        SmartDashboard.putBoolean("see target",Robot.limelight.hasTarget());
        //if(xoffset>marginx ||xoffset<-marginx ) {//aligning to x offset
            //SmartDashboard.putBoolean("see target1",Robot.limelight.hasTarget());

        if(Math.abs(xoffset)<marginxI){
            Ilooper += xoffset;
            powerL = -1*kPx*xoffset+ Ilooper*kIx;
            powerR = 1*kPx*xoffset+ Ilooper*kIx;
        }else{
            Ilooper = 0;
            powerL = -1*kPx*xoffset;
            powerR = 1*kPx*xoffset;
        }
        Drivetrain.getInstance().setSpeed(powerR ,powerL);
        SmartDashboard.putNumber("PowerL",powerL);
        SmartDashboard.putNumber("PowerR",powerR);
    }

    @Override
    public void end(boolean interrupted) {
        //OI.getInstance().aux.setRumble(GenericHID.RumbleType.kLeftRumble,0);
        //OI.getInstance().aux.setRumble(GenericHID.RumbleType.kRightRumble,0);
        SmartDashboard.putBoolean("trying to get there",false);
        Constants.DriverCommands.turned = Pigeon.getInstance().getYPR()[0] - initial;

        //Drivetrain.getInstance().setSpeed(0, 0);
    }
    @Override
    public boolean isFinished() {
        return !state;
    }
}