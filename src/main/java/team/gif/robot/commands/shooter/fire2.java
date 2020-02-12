package team.gif.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class fire2 extends ParallelCommandGroup {
    public fire2(){

        addCommands(
                new RevFlywheel(),
                new Fire()
        );
    }
}
