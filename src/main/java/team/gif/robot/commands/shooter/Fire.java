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
        if (((Shooter.getInstance().getVelocity()) > (Constants.Shooter.RPM - 500)) && (Indexer.getInstance().getState()[5] == true) && (fired<fire || fire ==0)&&(!limelight||(Math.abs(Robot.limelight.getXOffset())<Constants.marginx1))) {
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
        System.out.println("flywheeeling");
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
