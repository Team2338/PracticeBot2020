
package team.gif.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.GenericHID;
import team.gif.robot.Robot;
import team.gif.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An example command that uses an example subsystem.
 */
public class Drive extends CommandBase {
    
    private final Runnable driveCommand;
    private final boolean isTank;

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public Drive(Drivetrain subsystem) {
        addRequirements(Drivetrain.getInstance());
        this.isTank = false;
    
        // Arcade drive
        driveCommand = () -> {
            double currSpeed = -Robot.oi.driver.getY(GenericHID.Hand.kLeft);
            double currRotation = Robot.oi.driver.getX(GenericHID.Hand.kRight);
            Drivetrain.getInstance().driveArcade(currSpeed, currRotation);
        };
    }
    
    public Drive(boolean isTank) {
        this.isTank = isTank;
        
        if (isTank) {
            // Tank drive with joysticks
            driveCommand = () -> {
                double left = -Robot.oi.leftStick.getY();
                double right = -Robot.oi.rightStick.getY();
                Drivetrain.getInstance().setSpeed(left, right);
            };
            
            return;
        }
        
        // Arcade drive with controller
        driveCommand = () -> {
            double currSpeed = -Robot.oi.driver.getY(GenericHID.Hand.kLeft);
            double currRotation = Robot.oi.driver.getX(GenericHID.Hand.kRight);
            Drivetrain.getInstance().driveArcade(currSpeed, currRotation);
        };
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        Drivetrain.getInstance().currentLimitingEnable(!isTank);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        this.driveCommand.run();
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        Drivetrain.getInstance().driveArcade(0, 0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
