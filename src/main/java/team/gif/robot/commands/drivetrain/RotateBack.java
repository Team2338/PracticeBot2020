package team.gif.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.Constants;
import team.gif.robot.Globals;
import team.gif.robot.Robot;
import team.gif.robot.subsystems.Drivetrain;

public class RotateBack extends CommandBase {
    public static boolean state = false;

    public static double target_initial_angle;
    public static double target_Final_angle;

    public RotateBack(boolean stateval) {
        target_initial_angle = Globals.Rotate.initial_angle;
        target_Final_angle = Globals.Rotate.final_angle;
        state = stateval;
        SmartDashboard.putBoolean("trying to get there",true);
        SmartDashboard.putBoolean("are we there yet x" , false);
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

        if(!state) {
            SmartDashboard.putNumber("Ilooping",0);
            System.out.println("reset pivot");
            Ilooper = 0;
            looptime = 0;
        }/**reseter code**/
        SmartDashboard.putBoolean("trying to get there",true);
        System.out.println("RotateBack");

        marginxI  = Constants.Pivot.marginxI;
        kPx  = Constants.Pivot.kPx;
        kIx = Constants.Pivot.kIx;
    }

    @Override
    public void execute() {
        System.out.println("rotating back");
        double xoffset = target_initial_angle - Robot.limelight.getXOffset();
        //double yoffset = Robot.limelight.getYOffset();

        //if(xoffset>marginx ||xoffset<-marginx ) {//aligning to x offset
        //SmartDashboard.putBoolean("see target1",Robot.limelight.hasTarget())
        if((Math.abs(xoffset)<Constants.Pivot.marginxF)&&(loopedI<Ilooptime)){
            endthing = true;
            powerR = 0;
            powerL = 0;
        } else if((Math.abs(xoffset)<marginxI)&&(looped>=looptime)){
            loopedI++;
            looped++;
            SmartDashboard.putNumber("Ilooping",1);
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
    public void end(boolean interrupted) {
        Drivetrain.getInstance().setSpeed(0 ,0);
        /*
        OI.getInstance().aux.setRumble(GenericHID.RumbleType.kLeftRumble,0);
        OI.getInstance().aux.setRumble(GenericHID.RumbleType.kRightRumble,0);*/
        //Drivetrain.getInstance().setSpeed(0, 0);
    }

    @Override
    public boolean isFinished() {
        return endthing;
    }

}