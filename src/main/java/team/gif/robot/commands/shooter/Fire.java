package team.gif.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import team.gif.robot.Constants;
import team.gif.robot.Robot;
import team.gif.robot.subsystems.Indexer;
import team.gif.robot.subsystems.Shooter;

public class Fire extends CommandBase {

    public boolean endthing = false;
    boolean limelight = false;

    public Fire(boolean limelightval) {

        limelight = limelightval;
        //addRequirements(Shooter.getInstance());
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        if (((Shooter.getInstance().getVelocity()) > (Constants.Shooter.RPM - 500))//speed control
                && (Indexer.getInstance().getState()[5] == true)//dont shoot if no ball
                && (!limelight||(Math.abs(Robot.limelight.getXOffset())<Constants.DriverCommands.marginx))) {// shoot when see

            Indexer.getInstance().setSpeedFive(0.5);
            /*
            if(Indexer.getInstance().getState()[5]== true){
                endthing = false;
            }else{
                endthing = true;
            }*/
            //shoot until some timeout runs out

        }else {
            Indexer.getInstance().setSpeedFive(0);
        }
        //System.out.println("flywheeeling");
    }

    @Override
    public void end(boolean interrupted) {

        //Shooter.getInstance().setPID(0);
        Indexer.getInstance().setSpeedFive(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
