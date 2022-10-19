package team.gif.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.Robot;
import team.gif.robot.subsystems.Indexer;

public class LedModes extends CommandBase {
    public static int state = 0;

    public LedModes() {
    }

    @Override
    public void initialize() {
        state++;
        if (state > 4) {
            state = 0;
        }
        Robot.limelight.setLEDMode(state);
    }

    @Override
    public void execute() {
    }

    @Override
    public void end(boolean interrupted) {
        Indexer.getInstance().setSpeedFive(0);
    }

    @Override
    public boolean isFinished() {
        //if () {
            return true;

        //}

    }

}