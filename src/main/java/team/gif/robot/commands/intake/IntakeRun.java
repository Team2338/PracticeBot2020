package team.gif.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.subsystems.Indexer;
import team.gif.robot.subsystems.Intake;

public class IntakeRun extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Intake intake = Intake.getInstance();
    public boolean buttonState = false;

    public IntakeRun(boolean state) {
        buttonState = state;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(Intake.getInstance());
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        new IntakeDown();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if (!Indexer.getInstance().getState()[1] || !Indexer.getInstance().getState()[2]) {
            intake.setSpeed(0.75);
        } else {
            intake.setSpeed(0);
        }
        if (!buttonState) {
            intake.setSpeed(0);
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {}

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return !buttonState;
    }
}
