package team.gif.robot.commands.autoaim;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.OI;
import team.gif.robot.Robot;
import team.gif.robot.Constants;
import team.gif.robot.subsystems.Drivetrain;

public class Pivot extends CommandBase {
    public static boolean state = false;
    public Pivot(boolean stateval) {
        state = stateval;
        SmartDashboard.putBoolean("trying to get there",true);
        SmartDashboard.putBoolean("are we there yet x" , false);
        //OI.getInstance().aux.setRumble(GenericHID.RumbleType.kLeftRumble,0);
        //OI.getInstance().aux.setRumble(GenericHID.RumbleType.kRightRumble,0);
        //addRequirements(Drivetrain.getInstance());

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

    @Override
    public void initialize() {
        Robot.limelight.setPipeline(0);
        Robot.limelight.setLEDMode(3);
        if(!state) {
            SmartDashboard.putNumber("Ilooping",0);
            System.out.println("reset pivot");
            Ilooper = 0;
            looptime = 0;
        }
        SmartDashboard.putBoolean("trying to get there",true);
        System.out.println("pivot");

        marginxI  = Constants.Pivot.marginxI;
        kPx  = Constants.Pivot.kPx;
        kIx = Constants.Pivot.kIx;
        //kFx = Constants.kFx;
    }

    @Override
    public void execute() {
        System.out.println("pivoting");
        double xoffset = Robot.limelight.getXOffset();
        //double yoffset = Robot.limelight.getYOffset();
        double powerL;
        double powerR;


        //if(xoffset>marginx ||xoffset<-marginx ) {//aligning to x offset
            //SmartDashboard.putBoolean("see target1",Robot.limelight.hasTarget());

        if((Math.abs(xoffset)<marginxI)&&(looped>=looptime)){
            looped++;
            SmartDashboard.putNumber("Ilooping",1);
            Ilooper += xoffset;
            powerL = -Ilooper*kIx;
            powerR = Ilooper*kIx;

            //OI.getInstance().aux.setRumble(GenericHID.RumbleType.kLeftRumble,1);
            //OI.getInstance().aux.setRumble(GenericHID.RumbleType.kRightRumble,1);
        }else{
            if(Math.abs(xoffset)<marginxI){
                looped++;
            }else{
                looped = 0;
            }
            SmartDashboard.putNumber("Ilooping",0);
            Ilooper = 0;
            powerL = -1*kPx*xoffset;
            powerR = 1*kPx*xoffset;
            //OI.getInstance().aux.setRumble(GenericHID.RumbleType.kLeftRumble,0);
            //OI.getInstance().aux.setRumble(GenericHID.RumbleType.kRightRumble,0);
        }

        Drivetrain.getInstance().setSpeed(-powerR ,-powerL);
        SmartDashboard.putNumber("looptime",looptime);
        SmartDashboard.putNumber("Ilooper",Ilooper);
        SmartDashboard.putNumber("PowerL",-powerL);
        SmartDashboard.putNumber("PowerR",-powerR);
        //looped =0;
    }

    @Override
    public void end(boolean interrupted) {/*
        OI.getInstance().aux.setRumble(GenericHID.RumbleType.kLeftRumble,0);
        OI.getInstance().aux.setRumble(GenericHID.RumbleType.kRightRumble,0);*/
        //Drivetrain.getInstance().setSpeed(0, 0);
    }

    @Override
    public boolean isFinished() {
        return endthing;
    }
}