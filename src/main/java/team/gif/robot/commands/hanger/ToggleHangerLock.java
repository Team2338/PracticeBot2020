package team.gif.robot.commands.hanger;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.OI;
import team.gif.robot.Robot;
import team.gif.robot.subsystems.Hanger;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ToggleHangerLock extends CommandBase {
    //@SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

    public ToggleHangerLock() {
        // Use addRequirements() here to declare subsystem dependencies.
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        System.out.println("hello");
        Robot.hanger.zeroEncoder();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return true;
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }
}
