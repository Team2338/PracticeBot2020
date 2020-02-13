package team.gif.robot.commands.shooter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.Constants;
import team.gif.robot.OI;
import team.gif.robot.subsystems.Indexer;
import team.gif.robot.subsystems.Shooter;

public class RevFlywheel extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Shooter shooter;
    private boolean endwithRPM = false;
    //private final OI oi;

    public RevFlywheel() {
        shooter = Shooter.getInstance();
        //oi = OI.getInstance();
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(Shooter.getInstance());
    }


    public RevFlywheel(boolean endwithRPMval) {
        endwithRPM = endwithRPMval;
        shooter = Shooter.getInstance();
        //oi = OI.getInstance();
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(Shooter.getInstance());
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        shooter.setPID(Constants.Shooter.RPM);


        /*if (OI.getInstance().aux.getXButtonPressed()) {
            Indexer.getInstance().setSpeed(speed);
        } else {
            Indexer.getInstance().setSpeed(speedStop);
        }*/
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {

        // below removed  bc we need the fly wheel to stay spinning when we
        // fire, and it heats up the motor and draws power to stop

        //Shooter.getInstance().setPID(0);


        //Indexer.getInstance().setSpeed(speedStop);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        if(endwithRPM){
            if(OI.getInstance().aux.getBButtonPressed() == true){
                return true;
            }else{
                return Shooter.getInstance().getVelocity() >= Constants.Shooter.RPM;
            }
        }else {
            //return OI.getInstance().aux.getBButtonPressed();
            return false;
        }

    }
}
