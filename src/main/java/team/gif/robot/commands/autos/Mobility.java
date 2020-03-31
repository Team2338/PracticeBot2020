package team.gif.robot.commands.autos;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import team.gif.robot.commands.drivetrain.forward;

public class Mobility extends SequentialCommandGroup {

    public Mobility() {
        System.out.println("Auto: Mobility Selected");
        addCommands(
                new forward(1.3,.3,.3)
        );
    }
}