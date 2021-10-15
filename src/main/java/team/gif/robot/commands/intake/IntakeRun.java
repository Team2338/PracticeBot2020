package team.gif.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.Globals;
import team.gif.robot.subsystems.Indexer;
import team.gif.robot.subsystems.Intake;

public class IntakeRun extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Intake intake = Intake.getInstance();

    public IntakeRun() {
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(Intake.getInstance());
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        // Turn the indexer on if it was turned off
        // There is no reason to ask to run the intake if the indexer
        // is disabled so enable it.
        // It can be disabled either manually (even by accident)
        // or if stage 3 is running longer than 3 seconds (this can happen
        // if we run over a power cell and stage 3 is tripped
        Globals.indexerEnabled = true;
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if (!Indexer.getInstance().getState()[1] || !Indexer.getInstance().getState()[2]) {
            intake.setSpeed(0.75);
        } else {
            intake.setSpeed(0);
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        intake.setSpeed(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
