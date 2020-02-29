package team.gif.robot.commands.indexer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.Globals;
import team.gif.robot.subsystems.Indexer;
import team.gif.robot.subsystems.Intake;

public class StageTwo extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Indexer indexer = Indexer.getInstance();

    public StageTwo() {
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(Indexer.getInstance());
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        indexer.setSpeedTwo(0.5);
        Intake.getInstance().setSpeed(0.5);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        Intake.getInstance().setSpeed(0);
        indexer.setSpeedTwo(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        if (!Globals.indexerEnabled) { // stops this command if directed to stop the indexer
            return true;
        } else {
            return indexer.getState()[2];
        }
    }
}
