package team.gif.robot.commands.hanger;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.Globals;
import team.gif.robot.Robot;
import team.gif.robot.subsystems.Hanger;

public class HangerManualControl extends CommandBase {

    //@SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

    public static final double ClimberupGain = 1;
    public static final double ClimberdownGain = 1;

    public HangerManualControl() {
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        Hanger.getInstance().setOpen();
        SmartDashboard.putBoolean("Hang Control", true);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        double speed = -Robot.oi.aux.getY(GenericHID.Hand.kLeft);

        if ( speed > -0.05 && speed < 0.05) {
            speed = 0;
        }

        if (!Globals.controlPanelMotorEnabled) { Hanger.getInstance().setSpeed(speed); }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        Hanger.getInstance().setClosed();
        Hanger.getInstance().setSpeed(0);
        SmartDashboard.putBoolean("Hang Control", false);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
