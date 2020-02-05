package team.gif.robot.commands.indexer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.subsystems.Indexer;

public class StageFour extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Indexer indexer = Indexer.getInstance();

    public StageFour() {
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
        indexer.setSpeedThree(1);
        System.out.println("Run 4");
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        indexer.setSpeedThree(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return indexer.getState()[4];
    }
}
