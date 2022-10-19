
package team.gif.robot.commands.drivetrain;

import team.gif.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An example command that uses an example subsystem.
 */
public class AutoDrive extends CommandBase {
    //@SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    //private final Drivetrain m_subsystem;

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public int time = 0;
    public double powerL = 0;
    public double powerR = 0;
    public boolean endthing = false;

    public AutoDrive(double timeval, double powerLval,double powerRval) {
        this.endthing = false;
        this.time = (int)(timeval*50);

        this.powerL = powerLval;
        this.powerR = powerRval;
        //m_subsystem = subsystem;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(Drivetrain.getInstance());
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {

    }

    // Called every time the scheduler runs while the command is scheduled.
    public int looped;

    @Override
    public void execute() {

        if(this.looped <= this.time){
            Drivetrain.getInstance().setSpeed(this.powerL, this.powerR);
            this.looped++;
        }else{
            this.endthing = true;
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {

        Drivetrain.getInstance().setSpeed(0, 0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return this.endthing;
    }
}