package team.gif.robot.commands.hanger;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.Globals;
import team.gif.robot.OI;
import team.gif.robot.Robot;
import team.gif.robot.subsystems.ExampleSubsystem;
import team.gif.robot.subsystems.Hanger;

public class HangerManualControl extends CommandBase {

    //@SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

    public static final double ClimberupGain = 1;
    public static final double ClimberdownGain = 1;
    private final Hanger hanger = Hanger.getInstance();
    //private final OI oi = OI.getInstance();
    double speed;

    public HangerManualControl() {

        // Use addRequirements() here to declare subsystem dependencies.
        //addRequirements(hanger);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        hanger.setOpen();
        //Globals.hangerActive = true;
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        speed = -Robot.oi.aux.getY(GenericHID.Hand.kLeft);

        if ( speed > -0.05 && speed < 0.05) {
            speed = 0;
        }

        hanger.setSpeed(speed);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        hanger.setClosed();
        hanger.setSpeed(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
