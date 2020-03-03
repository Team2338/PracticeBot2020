package team.gif.robot.commands.shooter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.Constants;
import team.gif.robot.OI;
import team.gif.robot.Robot;
import team.gif.robot.subsystems.Indexer;
import team.gif.robot.subsystems.Shooter;

public class RevFlywheel extends CommandBase {
    public RevFlywheel() {
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if (Robot.oi.dRT.get()) {
            Shooter.getInstance().setPID(Constants.Shooter.RPM_HIGH);
            System.out.println("Flywheel High");
        } else {
            Shooter.getInstance().setPID(Constants.Shooter.RPM_LOW);
            System.out.println("Flywheel Low");
        }
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() { return false; }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        Shooter.getInstance().setVoltage(0);
    }

}