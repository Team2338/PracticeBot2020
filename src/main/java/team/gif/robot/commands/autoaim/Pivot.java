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
//        SmartDashboard.putBoolean("trying to get there",true);
//        SmartDashboard.putBoolean("are we there yet x" , false);
        Ilooper = 0;
        looptime = 0;
    }

    public double marginx ;
    public double kPx ;
    public double kPy;
    public double kFx;
    public double kFy;
    public boolean endthing = false;

    public static double looptime = 15;
    public double kIx =.00;
    public static double Ilooper =0;
    public static double looped =0;
    public double marginxI=0;
    public double initialx =0;
    public double finalx = 0;
    public double loopedI;
    public double Ilooptime = 10;

    public double powerR=0;
    public double powerL =0;


    @Override
    public void initialize() {
        Robot.limelight.setPipeline(0);
        Robot.limelight.setLEDMode(3);

        System.out.println("pivot");

        marginxI  = Constants.Pivot.marginxI;
        kPx  = Constants.Pivot.kPx;
        kIx = Constants.Pivot.kIx;
    }

    @Override
    public void execute() {
        double xoffset = Robot.limelight.getXOffset();

        if((Math.abs(xoffset)<Constants.Pivot.marginxF)&&(loopedI<Ilooptime)){
            powerR = 0;
            powerL = 0;

        } else if((Math.abs(xoffset)<marginxI)&&(looped>=looptime)){
            loopedI++;
            looped++;
            Ilooper += xoffset;
            powerL = -Ilooper*kIx;
            powerR = Ilooper*kIx;

            //OI.getInstance().aux.setRumble(GenericHID.RumbleType.kLeftRumble,1);
            //OI.getInstance().aux.setRumble(GenericHID.RumbleType.kRightRumble,1);
        }else{
            loopedI =0;
            if(Math.abs(xoffset)<marginxI){
                looped++;
            }else{
                looped = 0;
            }
            Ilooper = 0;
            powerL = -1*kPx*xoffset;
            powerR = 1*kPx*xoffset;
            //OI.getInstance().aux.setRumble(GenericHID.RumbleType.kLeftRumble,0);
            //OI.getInstance().aux.setRumble(GenericHID.RumbleType.kRightRumble,0);
        }

        Drivetrain.getInstance().setSpeed(-powerR ,-powerL);
    }

    @Override
    public void end(boolean interrupted) {/*
        OI.getInstance().aux.setRumble(GenericHID.RumbleType.kLeftRumble,0);
        OI.getInstance().aux.setRumble(GenericHID.RumbleType.kRightRumble,0);*/
    }

    @Override
    public boolean isFinished() {
        return endthing;
    }
}