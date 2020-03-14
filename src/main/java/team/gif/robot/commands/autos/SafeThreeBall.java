package team.gif.robot.commands.autos;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import team.gif.robot.Robot;
import team.gif.robot.commands.autoaim.Pivot;
import team.gif.robot.commands.drivetrain.AutoDrive;
import team.gif.robot.commands.intake.IntakeDown;
import team.gif.robot.commands.intake.IntakeRun;
import team.gif.robot.commands.shooter.Fire;
import team.gif.robot.commands.shooter.RevFlywheel;

public class SafeThreeBall extends SequentialCommandGroup {
    public SafeThreeBall(){
        System.out.println("SafeFiveBallAuto Activated");
        /*
        * TODO: speed up moveback
        * */

        addCommands(
                new AutoDrive(1.0,.47,.47),
                new ParallelCommandGroup(new Pivot(),
                                         new RevFlywheel()).withTimeout(2.5),
                new ParallelCommandGroup(new Pivot(),
                                         new RevFlywheel(),
                                         new Fire(true))
        );
    }
}
