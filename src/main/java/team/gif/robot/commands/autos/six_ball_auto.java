package team.gif.robot.commands.autos;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import team.gif.robot.Robot;
import team.gif.robot.commands.Actionlisteners.Until_Indexer_Empty;
import team.gif.robot.commands.autoaim.Pivot;
import team.gif.robot.commands.drivetrain.AutoDrive;
import team.gif.robot.commands.drivetrain.RotateBack;
import team.gif.robot.commands.intake.IntakeDown;
import team.gif.robot.commands.intake.IntakeRun;
import team.gif.robot.commands.shooter.Fire;
import team.gif.robot.commands.shooter.RevFlywheel;

public class six_ball_auto extends SequentialCommandGroup {
    public six_ball_auto(){
        System.out.println("shootcollectshoot acitivated");
        Robot.limelight.setPipeline(0);
        /*
        * TODO: speed up moveback
        * */

        addCommands(
                new IntakeDown(),
                new ParallelDeadlineGroup(new AutoDrive(2.3,.47,.47),//OG 2.5 ----- time was 2.3
                                          new IntakeRun(true),
                                          new RevFlywheel(false)),//drive back and collect
                new IntakeRun(true).withTimeout(0.5),
                //new IntakeRun(false),
                new ParallelCommandGroup(new Pivot(false),//pivot and rev
                                         new RevFlywheel(true)).withTimeout(2.5),
                new IntakeRun(false),//turn off intake
                new ParallelDeadlineGroup(//shoot until indexer empty
                                         new Until_Indexer_Empty(),
                                         new Pivot(true),
                                         new RevFlywheel(true),
                                         new Fire(0,true)),



                //from here it is different from 5 ball auto
                new RotateBack(false),//move back to original angle

                new ParallelDeadlineGroup(
                        new AutoDrive(.9,.47,.47),//OG 2.5 ----- time was 2.3
                        new IntakeRun(true),
                        new RevFlywheel(false)),
                        //move back and collect
                new ParallelDeadlineGroup(
                        new AutoDrive(.9,-.47,-.47),//OG 2.5 ----- time was 2.3
                        new IntakeRun(true),
                        new RevFlywheel(false)),
                        // move back up
                new IntakeRun(false),//turn intake off
                new ParallelDeadlineGroup(
                        new Until_Indexer_Empty(),
                        new Pivot(true),
                        new RevFlywheel(true),
                        new Fire(0,true))
                        //shoot until the indexer is empty
        );

    }
}
