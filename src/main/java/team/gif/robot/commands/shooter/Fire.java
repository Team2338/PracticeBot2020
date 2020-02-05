package team.gif.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.subsystems.Indexer;

public class Fire extends CommandBase {
    public Fire() {
        //addRequirements(Shooter.getInstance());
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        Indexer.getInstance().setSpeedFive(0.5);
        System.out.println("shooting");
    }

    @Override
    public void end(boolean interrupted) {
        //Shooter.getInstance().setSpeed(0);
        //Indexer.getInstance().setSpeed(speedStop);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
