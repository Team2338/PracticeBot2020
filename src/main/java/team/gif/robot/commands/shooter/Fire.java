package team.gif.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import team.gif.robot.Constants;
import team.gif.robot.subsystems.Indexer;
import team.gif.robot.subsystems.Shooter;

public class Fire extends ParallelCommandGroup {
    public int fire =0;
    public int fired =0;
    public Fire(int fireval) {
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
        if ((Shooter.getInstance().getVelocity()) > (Constants.Shooter.RPM - 500) && Indexer.getInstance().getState()[4] && (fired<fire || fire ==0)) {
            Indexer.getInstance().setSpeedFive(0.5);
            fired++;

        }else {
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
