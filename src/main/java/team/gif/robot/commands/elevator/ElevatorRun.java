package team.gif.robot.commands.elevator;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.OI;
import team.gif.robot.subsystems.Elevator;
import team.gif.robot.subsystems.ExampleSubsystem;

public class ElevatorRun extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

    public ElevatorRun() {
        addRequirements(Elevator.getInstance());
    }

    private final Elevator elevator = Elevator.getInstance();
    private final OI oi = OI.getInstance();

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        elevator.setspeed(oi.aux.getY(GenericHID.Hand.kLeft));
}

    @Override
    public void end(boolean interrupted) {
        elevator.setspeed(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
