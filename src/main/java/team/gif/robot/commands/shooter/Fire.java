package team.gif.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.Constants;
import team.gif.robot.subsystems.Indexer;
import team.gif.robot.subsystems.Shooter;

public class Fire extends CommandBase {
    public Fire() {
        //addRequirements(Shooter.getInstance());
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        if (Shooter.getInstance().getVelocity() > Constants.Shooter.RPM) {
            Indexer.getInstance().setSpeedFive(0.5);
        } else {
            Indexer.getInstance().setSpeedFive(0);
        }
    }

    @Override
    public void end(boolean interrupted) {
        Indexer.getInstance().setSpeedFive(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
