package team.gif.robot.commands.hanger;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.Constants;
import team.gif.robot.OI;
import team.gif.robot.subsystems.Hanger;

public class ControlPanelPosition extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

    private final Hanger hanger = Hanger.getInstance();
    private static boolean isUpVal;
    private static boolean finished;

    public ControlPanelPosition(boolean isUp) {
        // Use addRequirements() here to declare subsystem dependencies.
        isUpVal = isUp;
        finished = false;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        System.out.println("Running opening");
        hanger.setOpen();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        System.out.println("Running Execute");

        System.out.println(hanger.getPosition());
        if (isUpVal) {
            System.out.println(isUpVal);
            if (hanger.getPosition() < Constants.Hanger.COLOR_WHEEL_POSITION) {
                System.out.println("going up");
                hanger.setSpeed(0.3);
            } else {
                hanger.setSpeed(0);
                finished = true;
            }
        }else{
            if (hanger.getPosition() > Constants.Hanger.MIN_POS) {
                hanger.setSpeed(-0.3);
                System.out.println("going down");
            } else {
                hanger.setSpeed(0);
                finished = true;
            }
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        System.out.println("Closing");
//        hanger.setClosed();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return finished;
    }
}
