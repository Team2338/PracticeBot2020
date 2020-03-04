package team.gif.robot.commands.indexer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.subsystems.Indexer;

public class ServoButton extends CommandBase {

    public int position =0;

    public boolean state = false;

    public ServoButton(/*boolean stateval*/){
    }

    @Override
    public void initialize() {

        System.out.println("moving"+this.position);
        Indexer.getInstance().moveServo(0);
    }


    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        Indexer.getInstance().moveServo(90);
        System.out.println("done");
    }
}
