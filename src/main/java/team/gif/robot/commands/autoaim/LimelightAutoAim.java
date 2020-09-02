package team.gif.robot.commands.autoaim;

import team.gif.robot.Constants;
import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.Robot;
import team.gif.robot.commands.drivetrain.Drive;
import team.gif.robot.subsystems.Drivetrain;
import team.gif.robot.subsystems.drivers.Pigeon;

public class LimelightAutoAim {

    public double deltaDegrees = 0;
    public double kPVolts = Constants.drivetrain.kPDriveVelLeft;
    public double kSVolts = Constants.drivetrain.ksVolts;
    public double marginDegrees = 1;
    // we will be +- margin degrees degrees of zero

    public double targetHeadingDegrees = 0;  // 0 to 360 counterclockwise
    public double currentHeadingDegrees = 0; // 0 to 360 counterclockwise
    public double initialHeadingDegrees = 0;  // 0 to 360 counterclockwise
    public boolean exitCommand = false;

    // amount of voltage we want to apply to the motors for this test
    public double motorVolts = 9.0;
    public double slowmotorVolts = 4.5;


    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        Drivetrain.getInstance().tankDriveVolts(slowmotorVolts, -slowmotorVolts);
    }

    @Override
    public void end(boolean interrupted) {
        Drivetrain.getInstance().tankDriveVolts(0,0);
    }

    @Override
    public boolean isFinished() {
        double offset = Robot.limelight.getXOffset();
        System.out.println("Offset: " + offset);
        if(offset < 1.0) {
            return true;
        }
        else {
            return false;
        }
    }
}