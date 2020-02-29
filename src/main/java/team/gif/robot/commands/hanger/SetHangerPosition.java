package team.gif.robot.commands.hanger;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.Constants;
import team.gif.robot.OI;
import team.gif.robot.subsystems.Hanger;

public class SetHangerPosition extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Hanger hanger = Hanger.getInstance();
    private final int position;

    public SetHangerPosition(int position) {
        if (position > Constants.Hanger.MAX_POS) { position = Constants.Hanger.MAX_POS; }
        if (position < Constants.Hanger.MIN_POS) { position = Constants.Hanger.MIN_POS; }

        this.position = position;

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(hanger);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        hanger.setF();
        hanger.setPoint(position);
        /*if (position > hanger.getPosition()) {
            hanger.setCruiseVelocity(Constants.Hanger.MAX_VELOCITY);
            hanger.configF(Constants.Hanger.F);
            hanger.setMotionMagic(position, Constants.Hanger.GRAV_FEED_FORWARD);
        } else {
            hanger.setCruiseVelocity(Constants.Hanger.REV_MAX_VELOCITY);
            hanger.configF(Constants.Hanger.REV_F);
            hanger.setMotionMagic(position, Constants.Hanger.REV_GRAV_FEED_FORWARD);
        }*/
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        hanger.setFGravity();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        //return hanger.isFinished();
        return false;
    }
}
