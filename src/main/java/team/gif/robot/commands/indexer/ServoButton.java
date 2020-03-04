package team.gif.robot.commands.indexer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.subsystems.Indexer;

public class ServoButton extends CommandBase {

    public ServoButton(){
    }

    @Override
    public void initialize() {
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
    }
}
