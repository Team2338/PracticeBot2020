package team.gif.robot.commands.hanger;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.OI;
import team.gif.robot.subsystems.ExampleSubsystem;
import team.gif.robot.subsystems.Hanger;

public class HangerManualControl extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Hanger hanger = Hanger.getInstance();
    private final OI oi = OI.getInstance();
    double speed;

    public HangerManualControl() {
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(hanger);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        speed = oi.aux.getY(GenericHID.Hand.kLeft);

        if (speed < 0.05 && speed > -0.05) {
            speed = 0;
        }

        hanger.setSpeed(speed);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        hanger.setSpeed(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
