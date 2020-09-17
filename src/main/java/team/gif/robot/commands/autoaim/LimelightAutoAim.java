package team.gif.robot.commands.autoaim;

import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
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

    private boolean targetLocked = false;
    private boolean robotHasSettled = false;

    // amount of voltage we want to apply to the motors for this test
    private double motorVolts = 3.25;

    @Override
    public void initialize() {
        System.out.println("Auto Aim Start");
        targetLocked = false;
    }

    @Override
    public void execute() {
        Shooter.getInstance().setPID(Constants.Shooter.RPM_LOW);

        // bot must not be moving anymore
        if ( !robotHasSettled ) {
            DifferentialDriveWheelSpeeds wheelSpeeds = Drivetrain.getInstance().getWheelSpeeds();
            if ( wheelSpeeds.leftMetersPerSecond == 0 && wheelSpeeds.rightMetersPerSecond == 0 ){
                robotHasSettled = true;
                System.out.println("AutoFire: Robot has settled");
            }
        }

        if ( robotHasSettled ) {
            if (targetLocked) {
                double targetSpeed = Constants.Shooter.RPM_LOW;
                //System.out.println(Shooter.getInstance().getVelocity());
                if (Shooter.getInstance().getVelocity() > (targetSpeed - 20.0)) {
                    if (Indexer.getInstance().getState()[5] == true) {
                        Indexer.getInstance().setSpeedFive(0.5);
                        System.out.println("Firing at: " + Shooter.getInstance().getVelocity());
                    } else {
                        //Indexer.getInstance().setSpeedFive(0);
                    }
                } else {
                    System.out.println("Firing Complete");
                    Indexer.getInstance().setSpeedFive(0);
                }

            } else {
                double offset = Robot.limelight.getXOffset();
                System.out.println("Offset: " + offset);
                if (offset < 1.0 && offset > -1.0) {
                    Drivetrain.getInstance().tankDriveVolts(0, 0);
                    targetLocked = true;
                } else {
                    if (offset < 0) {
//                        Drivetrain.getInstance().tankDriveVolts(-motorVolts, motorVolts);
                        Drivetrain.getInstance().driveArcade( 0 , 0.4);
                        //Drivetrain.getInstance().driveCurvature(0,0.4,true);
                    } else {
//                        Drivetrain.getInstance().tankDriveVolts(motorVolts, -motorVolts);
                        Drivetrain.getInstance().driveArcade( 0 , -0.4);
                        //Drivetrain.getInstance().driveCurvature(0,-0.4,true);
                    }
                    targetLocked = false;
                }
            }
        }
    }

    //
    // Called as a whileHeld. When user releases the toggle, end() is called
    //
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