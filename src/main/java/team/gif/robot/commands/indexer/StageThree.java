package team.gif.robot.commands.indexer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.Globals;
import team.gif.robot.subsystems.Indexer;

public class StageThree extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Indexer indexer = Indexer.getInstance();

    public StageThree() {
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
        indexer.setSpeedThree(0.45);
        indexer.setSpeedTwo(0.45);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        indexer.setSpeedTwo(0);
        indexer.setSpeedThree(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        if (!Globals.indexerEnabled) { // stops this command if directed to stop the indexer
            return true;
        } else {
            return indexer.getState()[3];
        }
    }
}
