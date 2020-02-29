package team.gif.robot.commands.hanger;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.OI;
import team.gif.robot.subsystems.Hanger;

public class ToggleHangerLock extends CommandBase {
    //@SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private boolean setOpen;
    private final Hanger hanger = Hanger.getInstance();

    public ToggleHangerLock(boolean setOpen) {
        // Use addRequirements() here to declare subsystem dependencies.
        this.setOpen = setOpen;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        //hanger.setLocked(setOpen);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        //hanger.setLocked(!setOpen);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
