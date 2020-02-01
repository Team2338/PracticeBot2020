package team.gif.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.subsystems.Indexer;

public class Fire extends CommandBase {
    public Fire() {
        //addRequirements(Shooter.getInstance());
    }

    private double[] speed = {0, 0, 0, 0.5};
    private double[] speedStop = {0, 0, 0, 0};

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        //Shooter.getInstance().setSpeed(0.5);
        Indexer.getInstance().setSpeed(speed);
        System.out.println("shooting");
    }

    @Override
    public void end(boolean interrupted) {
        //Shooter.getInstance().setSpeed(0);
        Indexer.getInstance().setSpeed(speedStop);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
