package team.gif.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.subsystems.Indexer;
import team.gif.robot.subsystems.Intake;

public class IntakeRun extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Intake intake = Intake.getInstance();
    private final Indexer indexer = Indexer.getInstance();

    public IntakeRun() {
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(Intake.getInstance());
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if (!Indexer.getInstance().getState()[1] || !Indexer.getInstance().getState()[2]) {
//            indexer.setSpeedTwo(0.6);
            intake.setSpeed(0.6);
        } else {
//            indexer.setSpeedTwo(0);
            intake.setSpeed(0);
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
///        indexer.setSpeedTwo(0);
        intake.setSpeed(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
