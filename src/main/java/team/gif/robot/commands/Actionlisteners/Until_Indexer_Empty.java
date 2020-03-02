package team.gif.robot.commands.Actionlisteners;

import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.subsystems.Indexer;

public class Until_Indexer_Empty extends CommandBase {

    public Until_Indexer_Empty(){

    }

    @Override
    public void initialize() {
        System.out.println("until indexer empty listener activated");
    }

    @Override
    public void execute() {
        System.out.println("action listener execute");
    }

    @Override
    public boolean isFinished() {

        boolean end =false;

        //when all balls empty
        end = false&&// all of these statements must be the same and equal to false
                Indexer.getInstance().getState()[5]&&
                Indexer.getInstance().getState()[4]&&
                Indexer.getInstance().getState()[3]&&
                Indexer.getInstance().getState()[2]&&
                Indexer.getInstance().getState()[1];

        System.out.println("indexer empty"+end );
        return end;

    }

    @Override
    public void end(boolean interrupted) {

    }


}
