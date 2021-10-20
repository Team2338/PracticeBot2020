package team.gif.robot.commands.indexer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.Globals;
import team.gif.robot.subsystems.Indexer;

public class StageFour extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Indexer indexer = Indexer.getInstance();
    private int _timer = 0;

    public StageFour() {
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(Indexer.getInstance());
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        _timer = 0;
        indexer.setSpeedThree(1);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        _timer++;
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        indexer.setSpeedThree(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        if (_timer >= 150 && Globals.autonomousModeActive == false){ // 3 seconds (counter increases every 20ms)
                            // if it's taking longer than 3 seconds to move the power cell, something is wrong
            Globals.indexerEnabled = false; // kill the indexer
            System.out.println("Killing the indexer");
        }
        if (!Globals.indexerEnabled) { // stops this command if directed to stop the indexer
            return true;
        } else {
            return indexer.getState()[4];
        }
    }
}
