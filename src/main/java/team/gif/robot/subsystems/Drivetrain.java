package team.gif.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import team.gif.robot.Constants;
import team.gif.robot.RobotMap;
import team.gif.robot.commands.drivetrain.Drive;

public class Drivetrain extends SubsystemBase {
    private static Drivetrain instance = null;

    private static final TalonSRX leftMaster = new TalonSRX(RobotMap.DRIVE_LEFT_MASTER);
    private static final TalonSRX leftSlave = new TalonSRX(RobotMap.DRIVE_LEFT_SLAVE);
    private static final TalonSRX rightMaster = new TalonSRX(RobotMap.DRIVE_RIGHT_MASTER);
    private static final TalonSRX rightSlave = new TalonSRX(RobotMap.DRIVE_RIGHT_SLAVE);
    private static final PigeonIMU pigeon = new PigeonIMU(RobotMap.PIGEON_ID);

    private final DifferentialDriveOdometry odometry;

    public static Drivetrain getInstance() {
        if (instance == null) {
            instance = new Drivetrain();
        }

        return instance;
    }

    private Drivetrain() {
        super();

        odometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(getHeadingDegrees()));

        leftMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        rightMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);

        leftMaster.enableVoltageCompensation(true);
        rightMaster.enableVoltageCompensation(true);

        leftMaster.setInverted(true);
        leftSlave.setInverted(true);
        rightMaster.setInverted(true);
        rightSlave.setInverted(true);
//      rightMaster.setInverted(Constants.IS_INVERTED_DRIVE_RIGHT_MASTER);
//      rightSlave.setInverted(Constants.IS_INVERTED_DRIVE_RIGHT_SLAVE);

        leftMaster.setNeutralMode(NeutralMode.Brake);
        leftSlave.setNeutralMode(NeutralMode.Brake);
        rightMaster.setNeutralMode(NeutralMode.Brake);
        rightSlave.setNeutralMode(NeutralMode.Brake);

        leftSlave.follow(leftMaster);
        rightSlave.follow(rightMaster);
    }

    public void setSpeed(double left, double right) {
        leftMaster.set(ControlMode.PercentOutput, left);
        rightMaster.set(ControlMode.PercentOutput, right);
    }

    // These methods will help with odometry
    public void resetEncoders() {
        leftMaster.setSelectedSensorPosition(0, 0, 0);
        rightMaster.setSelectedSensorPosition(0, 0, 0);
    }

    public double getLeftEncoderPos() {
        return leftMaster.getSelectedSensorPosition();
    }

    public double getRightEncoderPos() {
        return rightMaster.getSelectedSensorPosition();
    }

    public double getLeftEncoderVelocity() {
        return leftMaster.getSelectedSensorVelocity();
    }

    public double getRightEncoderVelocity() {
        return rightMaster.getSelectedSensorVelocity();
    }

    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        return new DifferentialDriveWheelSpeeds(leftMaster.getSelectedSensorVelocity() * Constants.Drivetrain.DPS_TO_MPS,
                                                rightMaster.getSelectedSensorVelocity() * Constants.Drivetrain.DPS_TO_MPS);
    }

    public double getLeftDistancePerPulse() {
        return leftMaster.getSelectedSensorPosition() * Constants.Drivetrain.TICKS_TO_METERS;
    }

    public double getRightDistancePerPulse() {
        return rightMaster.getSelectedSensorPosition() * Constants.Drivetrain.TICKS_TO_METERS;
    }

    public double[] getYawPitchRoll() {
        double[] ypr_deg = new double[3];
        pigeon.getYawPitchRoll(ypr_deg);
        return ypr_deg;
    }

    public double getHeadingDegrees() {
        return getYawPitchRoll()[0];
    }

    public void resetHeading() {
        pigeon.setFusedHeading(0);
    }

    public Pose2d getPose() {
        return odometry.getPoseMeters();
    }

    public void updateOdometry() {
        odometry.update(Rotation2d.fromDegrees(getHeadingDegrees()), getLeftDistancePerPulse(), getRightDistancePerPulse());
    }

    public void resetOdometry(Pose2d pose) {
        resetEncoders();
        odometry.resetPosition(pose, Rotation2d.fromDegrees(getHeadingDegrees()));
    }

    public void tankDriveVolts(double leftVolts, double rightVolts) {
        leftMaster.set(ControlMode.PercentOutput, leftVolts / 12);
        rightMaster.set(ControlMode.PercentOutput, -rightVolts / 12);
        System.out.println("Left voltage " + leftVolts);
        System.out.println("Right voltage " + rightVolts);
    }

    /*
    @Override
    //protected void initDefaultCommand() {
    //    setDefaultCommand(new Drive(Drivetrain.getInstance()));
    }
    */
}
