package team.gif.robot.commands.indexer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.subsystems.Indexer;

public class StageThree extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Indexer indexer = Indexer.getInstance();
    private double[] speed = {0.5, 0.5, 0, 0};
    private double[] speedStop = {0, 0, 0, 0};

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
        indexer.setSpeed(speed);
        System.out.println("Run 3");
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        indexer.setSpeed(speedStop);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return indexer.getState()[2];
    }
}
