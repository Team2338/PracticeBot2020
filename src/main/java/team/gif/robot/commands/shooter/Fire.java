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
    public void initialize() {}

    @Override
    public void execute() {
        // Fire is used in auto but OI isn't instantiated yet so need to check first
        double speed = (Robot.oi != null && Robot.oi.dRT.get()) ? Constants.Shooter.RPM_HIGH : Constants.Shooter.RPM_LOW;
        if (((Shooter.getInstance().getVelocity()) > (speed - 300))
                && (Indexer.getInstance().getState()[5] == true)
                && (!useLimelight || ((Math.abs(Robot.limelight.getXOffset()) < Constants.Pivot.marginxF) && Robot.limelight.hasTarget()))) {
            Indexer.getInstance().setSpeedFive(0.5);
        } else {
            Indexer.getInstance().setSpeedFive(0);
        }
    }


    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        Indexer.getInstance().setSpeedFive(0);
    }
}
