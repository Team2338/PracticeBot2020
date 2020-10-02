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
    private double velocitycap = .5;

    // amount of voltage we want to apply to the motors for this test
    private double motorVolts = 3.25;

    @Override
    public void initialize() {

        Drivetrain.getInstance().leftTalon1.enableCurrentLimit(false);
        Drivetrain.getInstance().leftTalon2.enableCurrentLimit(false);
        Drivetrain.getInstance().rightTalon1.enableCurrentLimit(false);
        Drivetrain.getInstance().rightTalon2.enableCurrentLimit(false);

        System.out.println("Auto Aim Start");
        targetLocked = false;
    }

    @Override
    public void execute() {
        if ( Math.abs (Robot.limelight.getXOffset()) < 5 ) {
            Shooter.getInstance().setPID(Constants.Shooter.RPM_LOW);
        }

        // bot must not be moving anymore
        if ( !robotHasSettled ) {
            DifferentialDriveWheelSpeeds wheelSpeeds = Drivetrain.getInstance().getWheelSpeeds();
            if ( Math.abs(wheelSpeeds.leftMetersPerSecond) < velocitycap && Math.abs(wheelSpeeds.rightMetersPerSecond)< velocitycap ){
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
                        System.out.println("no ball");
                        //Indexer.getInstance().setSpeedFive(0);
                    }
                } else {

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
                        Drivetrain.getInstance().tankDriveVolts(-motorVolts, motorVolts);

                    } else {
                        Drivetrain.getInstance().tankDriveVolts(motorVolts, -motorVolts);

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
        robotHasSettled = false;
        Shooter.getInstance().setVoltage(0);
        Indexer.getInstance().setSpeedFive(0);

        Drivetrain.getInstance().leftTalon1.enableCurrentLimit(true);
        Drivetrain.getInstance().leftTalon2.enableCurrentLimit(true);
        Drivetrain.getInstance().rightTalon1.enableCurrentLimit(true);
        Drivetrain.getInstance().rightTalon2.enableCurrentLimit(true);

        System.out.println("Auto Aim Finished");
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}