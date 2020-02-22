package team.gif.robot.commands.autos;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import team.gif.robot.Constants;
import team.gif.robot.commands.shooter.*;
import team.gif.robot.commands.autoaim.*;
import team.gif.robot.commands.drivetrain.*;
import team.gif.robot.commands.indexer.*;
import team.gif.robot.commands.intake.*;
import team.gif.robot.Robot;

public class autonomous extends SequentialCommandGroup {

    public autonomous() {
        System.out.println("autoshoot");
        addCommands(
                new Pivot(true),
                new Fire(true),
                new Pivot(false),
                new rotate(-Constants.DriverCommands.turned),
                new ParallelDeadlineGroup(new forward(150,-.4,-.4),new IntakeRun(true)),
                new forward(150,.4,.4),
                new Fire(false)
        );
    }
}