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

public class OppFiveBall extends SequentialCommandGroup {

    public OppFiveBall() {
        System.out.println("********   opp 5 ball auto activated  *******");
        /*
         * TODO: speed up moveback
         * */

        addCommands(
                new IntakeDown(),
                new AutoDrive(.5,.30,.30), // slow rampup to reduce jitter when starting
                new AutoDrive(1,.5,.5),
                new ParallelDeadlineGroup(new AutoDrive(.5,.30,.30), // drive backwards),//OG 1.7 "1.9 @ .47" // 2.75
                                          new IntakeRun()),

                new ParallelDeadlineGroup(new AutoDrive(1.0,0,0), // hold to collect
                                          new IntakeRun()),
                new AutoDrive(0.6,-.5,-.5),//OG 2.5  // drive forward
                new AutoDrive(.55,-.5,.5),//OG 2.5   // turn clockwise //.65
                new AutoDrive(2.1,-.6,-.6),//OG 2.5  // drive forward 2.5 @ .5
                new AutoDrive(.60,.5,-.5),//OG 2.5   // turn counterclockwise
                new AutoDrive(0.8,.3,.3),//OG 2.5    // drive backward
                new ParallelCommandGroup(new Pivot(),                          // let it rip
                                         new RevFlywheel(),
                                         new Fire(true))

        );
    }
}

