package team.gif.robot.commands.autos;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import team.gif.robot.commands.drivetrain.forward;

public class MobilityFwd extends SequentialCommandGroup {

    public MobilityFwd() {
        System.out.println("Mobility Forward Selected");
        addCommands(
                new forward(2.0,-.3,-.3)
        );
    }
}