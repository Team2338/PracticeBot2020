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

public class Mobility extends SequentialCommandGroup {

    public Mobility() {
        System.out.println("Mobility");
        addCommands(
                new forward(5,.3,.3)
                //new forward(3,-.3,-.3)
        );
    }
}