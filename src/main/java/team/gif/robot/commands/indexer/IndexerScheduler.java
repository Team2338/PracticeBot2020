package team.gif.robot.commands.indexer;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.subsystems.Indexer;
import team.gif.robot.subsystems.Intake;

public class IndexerScheduler extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Indexer index = Indexer.getInstance();

    Command stageTwo = new StageTwo();
    Command stageThree = new StageThree();
    Command stageFour = new StageFour();

    public IndexerScheduler() {
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
        if((index.getState()[0] = true) && (index.getState()[1] = false)) {
            stageTwo.schedule();
        }
        if((index.getState()[1] = true) && (index.getState()[2] = false)) {
            stageThree.schedule();
        }
        if((index.getState()[2] = true) && (index.getState()[3] = false)) {
            stageFour.schedule();
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
