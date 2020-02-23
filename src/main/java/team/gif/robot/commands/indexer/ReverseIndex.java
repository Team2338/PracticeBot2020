package team.gif.robot.commands.indexer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.Globals;
import team.gif.robot.subsystems.Indexer;
import team.gif.robot.subsystems.Intake;

public class ReverseIndex extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Indexer indexer = Indexer.getInstance();
    private final Intake intake = Intake.getInstance();

    public ReverseIndex() {
    }

    @Override
    public void initialize() {
        Globals.indexerEnabled = false;
    }

    @Override
    public void execute() {
        intake.setSpeed(-0.5);
        indexer.setSpeedTwo(-0.6);
        indexer.setSpeedThree(-0.5);
    }

    @Override
    public void end(boolean interrupted) {
        intake.setSpeed(0);
        indexer.setSpeedTwo(0);
        indexer.setSpeedThree(0);
        Globals.indexerEnabled = true;
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
