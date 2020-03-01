package team.gif.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import team.gif.robot.Constants;
import team.gif.robot.Robot;
import team.gif.robot.subsystems.Indexer;
import team.gif.robot.subsystems.Shooter;

public class Fire extends CommandBase {
    boolean useLimelight = false;

    public Fire(boolean useLimelightVal) {
        useLimelight = useLimelightVal;
    }

    @Override
    public void initialize() {
        //System.out.println("Firing init");
    }

    @Override
    public void execute() {
        //System.out.println("Firing execute");
        if (((Shooter.getInstance().getVelocity()) > (Constants.Shooter.RPM - 400))
                && (Indexer.getInstance().getState()[5] == true)
                && (!useLimelight || ((Math.abs(Robot.limelight.getXOffset()) < Constants.Pivot.marginxF) && Robot.limelight.hasTarget()))) {
            Indexer.getInstance().setSpeedFive(0.5);
            //System.out.println("Firing setSpeed .5");
        } else {
            //System.out.println("Firing setSpeed 0");
            Indexer.getInstance().setSpeedFive(0);
        }
    }


    @Override
    public boolean isFinished() {
        //System.out.println("Firing isFinished");
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        //System.out.println("Firing interupted");
        Indexer.getInstance().setSpeedFive(0);
    }
}
