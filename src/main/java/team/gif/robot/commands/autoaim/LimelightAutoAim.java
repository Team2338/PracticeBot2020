package team.gif.robot.commands.autoaim;

import team.gif.robot.Constants;
import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.Robot;
import team.gif.robot.commands.drivetrain.Drive;
import team.gif.robot.commands.shooter.RevFlywheel;
import team.gif.robot.subsystems.Drivetrain;
import team.gif.robot.subsystems.Indexer;
import team.gif.robot.subsystems.Shooter;
import team.gif.robot.subsystems.drivers.Pigeon;

public class LimelightAutoAim extends CommandBase {

    public double deltaDegrees = 0;
    public double kPVolts = Constants.drivetrain.kPDriveVelLeft;
    public double kSVolts = Constants.drivetrain.ksVolts;
    public double marginDegrees = 1;
    // we will be +- margin degrees degrees of zero

    public double targetHeadingDegrees = 0;  // 0 to 360 counterclockwise
    public double currentHeadingDegrees = 0; // 0 to 360 counterclockwise
    public double initialHeadingDegrees = 0;  // 0 to 360 counterclockwise
    public boolean targetLocked = false;

    // amount of voltage we want to apply to the motors for this test
    public double motorVolts = 9.0;
    public double slowmotorVolts = 3.25;


    @Override
    public void initialize() {
        System.out.println("Auto Aim Start");
        targetLocked = false;
    }

    @Override
    public void execute() {
        Shooter.getInstance().setPID(Constants.Shooter.RPM_LOW);

        if( targetLocked == true ) {
            double speed = Constants.Shooter.RPM_LOW;
            //System.out.println(Shooter.getInstance().getVelocity());
            if (Shooter.getInstance().getVelocity() > (speed - 20.0)) {
                if (Indexer.getInstance().getState()[5] == true) {
                    Indexer.getInstance().setSpeedFive(0.5);
                } else {
                    //Indexer.getInstance().setSpeedFive(0);
                }
            } else {
                Indexer.getInstance().setSpeedFive(0);
            }

        } else
        {
            double offset = Robot.limelight.getXOffset();
            System.out.println("Offset: " + offset);
            if(offset < 1.0 && offset > -1.0 ) {
                Drivetrain.getInstance().tankDriveVolts(0,0);
                targetLocked = true;
            }
            else {
                if ( offset < 0 ) {
                    Drivetrain.getInstance().tankDriveVolts(-slowmotorVolts, slowmotorVolts);
                }
                else {
                    Drivetrain.getInstance().tankDriveVolts(slowmotorVolts, -slowmotorVolts);
                }
                targetLocked = false;
            }
        }
    }

    @Override
    public void end(boolean interrupted) {
        Shooter.getInstance().setVoltage(0);
        Indexer.getInstance().setSpeedFive(0);
        System.out.println("Auto Aim Finished");
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}