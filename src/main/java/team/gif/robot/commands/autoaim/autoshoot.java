package team.gif.robot.commands.autoaim;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import team.gif.robot.commands.shooter.Fire;
import team.gif.robot.commands.shooter.RevFlywheel;

public class autoshoot extends SequentialCommandGroup{


    public autoshoot() {
        System.out.println("autoshoot");
        addCommands(
            new Pivot(),
            new RevFlywheel(true),
            new Fire(5)
        );
    }

}
