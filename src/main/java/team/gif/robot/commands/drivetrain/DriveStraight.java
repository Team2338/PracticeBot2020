package team.gif.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.lib.PIDCalculator;
import team.gif.robot.Constants;
import team.gif.robot.subsystems.drivers.Pigeon;
import team.gif.robot.subsystems.Drivetrain;

public class DriveStraight extends CommandBase {

    public static double setpoint;
    public static double angle;
    // public static double speedCap;
    public static double distLeftError;
    public static double distRightError;
    public static double distError;

    private PIDCalculator distCalc;
    private PIDCalculator angleCalc;

    public DriveStraight(double ticks) {
        System.out.println("Initializing...");
        setpoint = ticks; // Will convert to meters later on (this is for precision)

        distCalc = new PIDCalculator(Constants.DRIVE_P, Constants.DRIVE_I, Constants.DRIVE_D);
        angleCalc = new PIDCalculator(Constants.DRIVE_STRAIGHT_ANGLE_P, 0, 0);

        addRequirements(Drivetrain.getInstance());
    }

    @Override
    public void initialize() {
        Drivetrain.getInstance().resetEncoders();
        angle = Pigeon.getInstance().getHeading();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        System.out.println("                 Driveeeee");
        distLeftError = setpoint - Drivetrain.getInstance().getLeftEncoderPos();
        distRightError = setpoint - Drivetrain.getInstance().getRightEncoderPos();

        double angleError = angle - Pigeon.getInstance().getHeading();

        double distLeftOutput = distCalc.getOutput(distLeftError);
        double distRightOutput = distCalc.getOutput(distRightError);

        double angleOutput = angleCalc.getOutput(angleError);

        double left;
        double right;

        Drivetrain.getInstance().setSpeed(-distLeftOutput - angleOutput, distRightOutput + angleOutput);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        Drivetrain.getInstance().setSpeed(0, 0);
        Drivetrain.getInstance().resetEncoders();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return Math.abs(distRightError) <= Constants.DRIVE_DIST_TOLERANCE;
    }
}
