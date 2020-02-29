package team.gif.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.subsystems.Indexer;
import team.gif.robot.subsystems.Intake;

public class IntakeRun extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Intake intake = Intake.getInstance();
    public boolean buttonState = false;

    public IntakeRun(boolean state) {
        buttonState = state;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(Intake.getInstance());
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        System.out.println("Indexer " + Indexer.getInstance().getState()[1] + "  " + Indexer.getInstance().getState()[2]);
        //System.out.println("Indexer         2 " + Indexer.getInstance().getState()[2]);
        if (!Indexer.getInstance().getState()[1] || !Indexer.getInstance().getState()[2]) {
            intake.setSpeed(0.75);
        } else {
            intake.setSpeed(0);
            System.out.println("Stop Intake");
        }
        if (!buttonState) {
            intake.setSpeed(0);
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        //intake.setSpeed(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return !buttonState;
    }
}
