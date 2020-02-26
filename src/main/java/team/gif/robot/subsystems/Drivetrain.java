package team.gif.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import team.gif.robot.Constants;
import team.gif.robot.Robot;
import team.gif.robot.RobotMap;
import team.gif.robot.commands.drivetrain.Drive;
import team.gif.robot.subsystems.drivers.Pigeon;

public class Drivetrain extends SubsystemBase {
    private static Drivetrain instance = null;

    private static final TalonSRX leftMaster = new TalonSRX(RobotMap.DRIVE_LEFT_MASTER);
    private static final TalonSRX leftSlave = new TalonSRX(RobotMap.DRIVE_LEFT_SLAVE);
    private static final TalonSRX rightMaster = new TalonSRX(RobotMap.DRIVE_RIGHT_MASTER);
    private static final TalonSRX rightSlave = new TalonSRX(RobotMap.DRIVE_RIGHT_SLAVE);

    private final DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(Constants.kTrackWidth);

    public static Drivetrain getInstance() {
        if (instance == null) {
            instance = new Drivetrain();
        }

        return instance;
    }

    private Drivetrain() {
        super();

        leftMaster.setInverted(true);
        leftSlave.setInverted(true);
        //rightMaster.setInverted(true);
        //rightSlave.setInverted(true);

        //leftMaster.enableVoltageCompensation(true);
        //rightMaster.enableVoltageCompensation(true);

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
        SmartDashboard.putNumber("Left Percent", left);
        SmartDashboard.putNumber("Right Percent", right);
    }

    /*
    public void setVoltage(double leftVolts, double rightVolts) {
        leftMaster.set(ControlMode.PercentOutput, leftVolts / 12);
        rightMaster.set(ControlMode.PercentOutput, -rightVolts / 12);
        System.out.println("Left voltage " + leftVolts);
        System.out.println("Right voltage " + rightVolts);
    }

     */

    // Mag Encoder methods
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

    public double getLeftPosMeters() {
        return leftMaster.getSelectedSensorPosition() * Constants.TICKS_TO_METERS;
    }

    public double getRightPosMeters() {
        return rightMaster.getSelectedSensorPosition() * Constants.TICKS_TO_METERS;
    }

    public double getLeftVelMeters() {
        return leftMaster.getSelectedSensorVelocity() * Constants.DPS_TO_MPS;
    }

    public double getRightVelMeters() {
        return rightMaster.getSelectedSensorVelocity() * Constants.DPS_TO_MPS;
    }

    /*
    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        return new DifferentialDriveWheelSpeeds(leftMaster.getSelectedSensorVelocity() * Constants.DPS_TO_MPS,
                                                rightMaster.getSelectedSensorVelocity() * Constants.DPS_TO_MPS);
    }

     */
}
