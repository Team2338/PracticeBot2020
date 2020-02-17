package team.gif.robot.commands.autos;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import team.gif.robot.commands.shooter.*;
import team.gif.robot.commands.autoaim.*;
import team.gif.robot.commands.drivetrain.*;
import team.gif.robot.commands.indexer.*;
import team.gif.robot.commands.intake.*;
import team.gif.robot.Robot;

public class intakebackwards extends ParallelDeadlineGroup {

    public autonomous() {
        System.out.println("autoshoot");
        addCommands(
                new IntakeRun(),
                new forward()


        );
    }
}