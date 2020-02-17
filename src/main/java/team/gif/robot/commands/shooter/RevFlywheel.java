package team.gif.robot.commands.shooter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.Constants;
import team.gif.robot.OI;
import team.gif.robot.subsystems.Indexer;
import team.gif.robot.subsystems.Shooter;

public class RevFlywheel extends CommandBase {
    //@SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    public boolean buttonState = false;

    public RevFlywheel(boolean state){

        buttonState = state;

        addRequirements(Shooter.getInstance());
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        //buttonState is set to:
        //    true when the button is pressed
        //    false when the button is released
        if(buttonState) {
            Shooter.getInstance().setPID(Constants.Shooter.RPM);
        }else{
            Shooter.getInstance().setVoltage(0);
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {}

    // Returns true when the command should end.
    @Override
    public boolean isFinished() { return false; }
}