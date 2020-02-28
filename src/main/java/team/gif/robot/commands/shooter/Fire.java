package team.gif.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import team.gif.robot.Constants;
import team.gif.robot.Robot;
import team.gif.robot.subsystems.Indexer;
import team.gif.robot.subsystems.Shooter;

public class Fire extends CommandBase {
    public int fire =0;
    public int fired =0;
    public boolean endthing = false;
    boolean limelight = false;
    public Fire(int fireval,boolean limelightval) {
        limelight = limelightval;
        fire =fire;
        fired =0;
        //addRequirements(Shooter.getInstance());
    }

    @Override
    public void initialize() {
        fired =0;

    }

    @Override
    public void execute() {
        if (    (Shooter.getInstance().getVelocity() > (Constants.Shooter.RPM - 400)) && // Shooter within speed range
                (Indexer.getInstance().getState()[5] == true) &&                         // Stage 5 has ball
                (   !limelight  ||                                                       // no limelight feed
                    (   (Math.abs(Robot.limelight.getXOffset())<Constants.Pivot.marginxF) && // target within range
                         Robot.limelight.hasTarget()))) {                                    // target acquired
            Indexer.getInstance().setSpeedFive(0.5);
            fired++;
            if((fired<fire)&&(Indexer.getInstance().getState()[5]== true)){
                endthing = false;
            }else{
                endthing = true;
            }

        }else {
            Indexer.getInstance().setSpeedFive(0);
        }
        //System.out.println("flywheeeling");
    }

    @Override
    public void end(boolean interrupted) {

        Shooter.getInstance().setPID(0);
        Indexer.getInstance().setSpeedFive(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
