package team.gif.robot.commands.hanger;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.Constants;
import team.gif.robot.OI;
import team.gif.robot.subsystems.Hanger;

public class ControlPanelPosition extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

    private final Hanger hanger = Hanger.getInstance();
    private static boolean finished = false;

    public ControlPanelPosition() {
        // Use addRequirements() here to declare subsystem dependencies.
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        hanger.setOpen();
        finished = false;
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if (hanger.getPosition() < Constants.Hanger.COLOR_WHEEL_POSITION) {
            hanger.setSpeed(0.3);
        } else {
            finished = true;
        }
    }


    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return finished;
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        hanger.setSpeed(0);
    }

}
