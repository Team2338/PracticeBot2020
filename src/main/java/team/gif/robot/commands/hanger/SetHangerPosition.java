package team.gif.robot.commands.hanger;

import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.Constants;
import team.gif.robot.Robot;


public class SetHangerPosition extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private int position = 0;

    public SetHangerPosition(int positionVal) {
        if (position > Constants.Hanger.MAX_POS) { position = Constants.Hanger.MAX_POS; }
        if (position < Constants.Hanger.MIN_POS) { position = Constants.Hanger.MIN_POS; }

        position = positionVal;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        Robot.hanger.setF();
        Robot.hanger.setPoint(position);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        Robot.hanger.setFGravity();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
