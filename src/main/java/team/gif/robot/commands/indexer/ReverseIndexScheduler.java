package team.gif.robot.commands.indexer;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import team.gif.robot.commands.intake.IntakeUp;

public class ReverseIndexScheduler extends SequentialCommandGroup {
    public ReverseIndexScheduler() {
        addCommands(
                new IntakeUp().withTimeout(0.5),
                new ReverseIndex().withTimeout(1.5)
        );
    }
}