package team.gif.robot.commands.autoaim;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.Constants;
import team.gif.robot.OI;
import team.gif.robot.Robot;

import java.nio.file.Files;

public class Flywheelspeedcontroller extends CommandBase {

    public double yoffset = 0;//degrees

    public static double[] speeddict = new double[1000];

    private ShuffleboardTab tab = Shuffleboard.getTab("shoter tuning");
    private NetworkTableEntry maxSpeed = tab.add("Speed", 0).getEntry();

    public Flywheelspeedcontroller(){
        Shuffleboard.getTab("shoter tuning").add("speeddict",speeddict);

    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {

        int dictval = (int) Math.round((Robot.limelight.getYOffset()*5)+500);

        double RPM = maxSpeed.getDouble(0);

        if(OI.getInstance().aux.getAButton()){
            speeddict[dictval] = RPM;
        }

        Constants.Shooter.RPM = speeddict[dictval];

        if(OI.getInstance().aux.getBButton()){
            System.out.println("-----------new file--------------");
            for(int l =0;l<speeddict.length;l++){
                System.out.println(speeddict[l]+" ,");
            }
        }

    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
