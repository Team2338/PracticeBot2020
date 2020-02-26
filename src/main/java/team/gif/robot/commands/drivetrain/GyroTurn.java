package team.gif.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.lib.PIDCalculator;
import team.gif.robot.Constants;
import team.gif.robot.subsystems.Drivetrain;
import team.gif.robot.subsystems.drivers.Pigeon;

public class GyroTurn extends CommandBase {

    private double angle;
    private double angleError;
    private PIDCalculator angleCalc;

    public GyroTurn(double angle) {
        System.out.println("Starting turn....");
        this.angle = angle;
        angleCalc = new PIDCalculator(Constants.DRIVE_ANGLE_P, Constants.DRIVE_ANGLE_I, Constants.DRIVE_ANGLE_D, Constants.DRIVE_ANGLE_I_ZONE);

        addRequirements(Drivetrain.getInstance());

    }

    @Override
    public void initialize() {
        angleCalc.clearIAccum();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        System.out.println("                              Turninggg");
        SmartDashboard.putNumber("GyroTurn Angle Error ", angleError);

        angleError = angle - Pigeon.getInstance().getHeading();
        double angleOutput = angleCalc.getOutput(angleError);

        Drivetrain.getInstance().setSpeed(-angleOutput, angleOutput);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        Drivetrain.getInstance().setSpeed(0, 0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return Math.abs(angleError) <= Constants.DRIVE_ANGLE_TOLERANCE;
    }
}
