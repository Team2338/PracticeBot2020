package team.gif.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.Globals;
import team.gif.robot.Robot;

public class JoystickTestMode extends CommandBase {
    //@SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private int loopCounter;

    public JoystickTestMode() {
        // Use addRequirements() here to declare subsystem dependencies.
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        loopCounter = 0;
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        loopCounter++;
        System.out.println(loopCounter);
        if (loopCounter == 250){
            Globals.gJoystickTestMode = !Globals.gJoystickTestMode;
            System.out.println(Globals.gJoystickTestMode);

            Globals.resetOI = true;
        }
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        System.out.println("Final State " + Globals.gJoystickTestMode);
    }

    @Override
    public boolean runsWhenDisabled() {
        return true;
    }
}
