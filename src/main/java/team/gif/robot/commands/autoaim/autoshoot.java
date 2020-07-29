package team.gif.robot.commands.autoaim;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class autoshoot extends SequentialCommandGroup{


    public autoshoot() {
        System.out.println("autoshoot");
        addCommands(
            //new Pivot()
                /*,
            new RevFlywheel(true),
            new Fire(5)*/
        );
    }

}
