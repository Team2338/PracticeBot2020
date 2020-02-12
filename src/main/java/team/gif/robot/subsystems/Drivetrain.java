package team.gif.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
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
//  private static final PigeonIMU pigeon = new PigeonIMU();

    public static Drivetrain getInstance() {
        if (instance == null) {
            instance = new Drivetrain();
        }

        return instance;
    }

    private Drivetrain() {
        super();

        leftMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        rightMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);

        leftMaster.setInverted(true);
        leftSlave.setInverted(true);
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

    public double getLeftDistancePerPulse() {
        return leftMaster.getSelectedSensorPosition() * Constants.Drivetrain.TICKS_TO_DPP;
    }

    public double getRightDistancePerPulse() {
        return rightMaster.getSelectedSensorPosition() * Constants.Drivetrain.TICKS_TO_DPP;
    }

    //@Override
    //protected void initDefaultCommand() {
    //    setDefaultCommand(new Drive(Drivetrain.getInstance()));
    //}
}
