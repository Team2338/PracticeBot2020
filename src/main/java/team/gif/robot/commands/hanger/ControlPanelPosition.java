package team.gif.robot.commands.hanger;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.Constants;
import team.gif.robot.Globals;
import team.gif.robot.Robot;

public class ControlPanelPosition extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

    private static boolean finished = false;
    private int delay;

    public ControlPanelPosition() {
        // Use addRequirements() here to declare subsystem dependencies.
    }

    public void showDisplayGSM() {
        String gameSpecificMsg;
        String displayGSM = "";
        gameSpecificMsg = DriverStation.getGameSpecificMessage();
        if (gameSpecificMsg.length() > 0) {
            switch (gameSpecificMsg.charAt(0)) {
                case 'B' :
                    displayGSM = "      Blue";
                    break;
                case 'G' :
                    displayGSM = "     Green";
                    break;
                case 'R' :
                    displayGSM = "      Red";
                    break;
                case 'Y' :
                    displayGSM = "     Yellow";
                    break;
                default :
                    displayGSM = "      none";
                    break;
            }
        }
        SmartDashboard.putString("Goto", displayGSM);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        showDisplayGSM();
        Robot.hanger.setOpen();
        finished = false;
        Globals.controlPanelMotorEnabled = true;
        delay = 0;

    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        delay++;
        showDisplayGSM();

        if (delay > 25) {
            if (Robot.hanger.getPosition() < Constants.Hanger.COLOR_WHEEL_POSITION) {
                Robot.hanger.setSpeed(0.6);
            } else {
                finished = true;
            }
        }
    }


    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return finished;
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        Robot.hanger.setSpeed(0);
        Globals.controlPanelMotorEnabled = false;
    }
}
