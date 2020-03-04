package team.gif.robot.commands.indexer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.Robot;
import team.gif.robot.subsystems.Indexer;

public class servobutton extends CommandBase {

    public int position =0;

    public boolean state = false;

    public servobutton(/*boolean stateval*/){


    }

    @Override
    public void initialize() {

        System.out.println("moving"+this.position);




    }


    @Override
    public void execute() {
        for(int c =0;c<20;c++){
            System.out.println("wayt 0");
            Indexer.getInstance().MoveServo(0);
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {

        for(int c = 0;c<20;c++){
            System.out.println("wayt 45");
            Indexer.getInstance().MoveServo(45);
        }
        System.out.println("done");
    }
}
