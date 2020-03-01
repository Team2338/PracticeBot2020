package team.gif.robot.commands.autos;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import team.gif.robot.Robot;
import team.gif.robot.commands.autoaim.Pivot;
import team.gif.robot.commands.drivetrain.AutoDrive;
import team.gif.robot.commands.drivetrain.forward;
import team.gif.robot.commands.intake.IntakeDown;
import team.gif.robot.commands.intake.IntakeRun;
import team.gif.robot.commands.shooter.Fire;
import team.gif.robot.commands.shooter.RevFlywheel;

public class ShootCollectShoot extends SequentialCommandGroup {
    public ShootCollectShoot(){
        System.out.println("shootcollectshoot acitivated");
        Robot.limelight.setPipeline(0);
        /*
        * TODO: speed up moveback
        * */

        addCommands(
                new IntakeDown(),
                new ParallelDeadlineGroup(new AutoDrive(2.3,.47,.47),//OG 2.5 ----- time was 2.3
                                          new IntakeRun(true),
                                          new RevFlywheel(false)),
                new IntakeRun(true).withTimeout(0.5),
                //new IntakeRun(false),
                new ParallelCommandGroup(new Pivot(false),
                                         new RevFlywheel(true)).withTimeout(2.5),
                new IntakeRun(false),
                new ParallelCommandGroup(new Pivot(true),
                                         new RevFlywheel(true),
                                         new Fire(0,true))
        );

    }
}