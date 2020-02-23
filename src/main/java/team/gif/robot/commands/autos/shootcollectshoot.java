package team.gif.robot.commands.autos;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import team.gif.robot.Constants;
import team.gif.robot.commands.autoaim.Pivot;
import team.gif.robot.commands.drivetrain.forward;
import team.gif.robot.commands.drivetrain.rotate;
import team.gif.robot.commands.intake.IntakeRun;
import team.gif.robot.commands.shooter.Fire;

public class shootcollectshoot extends SequentialCommandGroup {

    public shootcollectshoot() {
        System.out.println("shoot collect shoot auto");
        addCommands(
                new Pivot(true),
                new Fire(true).withTimeout(3),
                new Pivot(false),
                new rotate(-Constants.DriverCommands.turned),
                new ParallelDeadlineGroup(new forward(3, -.4, -.4), new IntakeRun(true)),
                new forward(3, .4, .4),
                new Pivot(true),
                new Fire(true).withTimeout(3),
                new Pivot(false)
        );
        //total time accumulated in this code is 12 seconds not including rotate
    }
}