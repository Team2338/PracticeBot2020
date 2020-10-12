package team.gif.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.Constants;
import team.gif.robot.Robot;
import team.gif.robot.commands.indexer.ToggleIndexer;
import team.gif.robot.subsystems.Indexer;
import team.gif.robot.subsystems.Intake;
import team.gif.robot.subsystems.Shooter;

public class RapidFire extends CommandBase {

    boolean useLimelight = false;
    ToggleIndexer indexToggle = new ToggleIndexer();

    public RapidFire(boolean useLimelightVal) {
        useLimelight = useLimelightVal;
    }

    @Override
    public void initialize() {
        indexToggle.initialize();
        // IDK if this works
    }

    @Override
    public void execute() {
        Robot.limelight.setLEDMode(3);

        double speed = (Robot.oi != null && Robot.oi.dRT.get()) ? Constants.Shooter.RPM_HIGH : Constants.Shooter.RPM_LOW;

        if ( ( Shooter.getInstance().getVelocity() > (speed - 20.0) )
                && (Indexer.getInstance().getState()[5] == true)
                && (!useLimelight || ((Math.abs(Robot.limelight.getXOffset()) < Constants.Pivot.marginxF) && Robot.limelight.hasTarget()))) {

            System.out.println("Firing speed " + Shooter.getInstance().getVelocity());

            Indexer.getInstance().setSpeedFive(0.6);
            Indexer.getInstance().setSpeedFour(0.55);
            Indexer.getInstance().setSpeedThree(0.5);
            Indexer.getInstance().setSpeedTwo(0.45);
            Intake.getInstance().setSpeed(0.4);
        } else {
            Indexer.getInstance().setSpeedFive(0);
            Indexer.getInstance().setSpeedFour(0);
            Indexer.getInstance().setSpeedThree(0);
            Indexer.getInstance().setSpeedTwo(0);
            Intake.getInstance().setSpeed(0);
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        indexToggle.end(true);
    }
}
