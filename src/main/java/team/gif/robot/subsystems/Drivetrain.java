package team.gif.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import team.gif.robot.Constants;
import team.gif.robot.RobotMap;
import team.gif.robot.subsystems.drivers.Pigeon;

public class Drivetrain extends SubsystemBase {

    private static Drivetrain instance = null;

    public static WPI_TalonSRX leftTalon1;
    public static WPI_TalonSRX leftTalon2;
    public static WPI_TalonSRX rightTalon1;
    public static WPI_TalonSRX rightTalon2;

    public static SpeedControllerGroup leftSpeedControl;
    public static SpeedControllerGroup rightSpeedControl;
    public static DifferentialDrive diffDriveTrain;

    public static Encoder leftEncoder;
    public static Encoder rightEncoder;

    public static DifferentialDriveOdometry driveOdometry;

    public static Drivetrain getInstance() {
        if (instance == null) {
            instance = new Drivetrain();
        }
        return instance;
    }

    private Drivetrain() {
        super();

        leftTalon1 = new WPI_TalonSRX(RobotMap.DRIVE_LEFT_ONE);
        leftTalon2 = new WPI_TalonSRX(RobotMap.DRIVE_LEFT_TWO);
        rightTalon1 = new WPI_TalonSRX(RobotMap.DRIVE_RIGHT_ONE);
        rightTalon2 = new WPI_TalonSRX(RobotMap.DRIVE_RIGHT_TWO);

        leftSpeedControl = new SpeedControllerGroup(leftTalon1,leftTalon2);
        rightSpeedControl = new SpeedControllerGroup(rightTalon1,rightTalon2);

        leftTalon1.setInverted(true);
        leftTalon2.setInverted(true);

        leftTalon1.setNeutralMode(NeutralMode.Brake);
        leftTalon2.setNeutralMode(NeutralMode.Brake);
        rightTalon1.setNeutralMode(NeutralMode.Brake);
        rightTalon2.setNeutralMode(NeutralMode.Brake);

        diffDriveTrain = new DifferentialDrive(leftSpeedControl,rightSpeedControl);

        //setupOdometry();
    }

    //<<<<<<<<<----------------driving----------------------->>>>>>>>>>>>>>>>>>>

    public void setSpeed(double leftPercent, double rightPercent) {
        diffDriveTrain.tankDrive(leftPercent,rightPercent);
    }

    public void driveArcade(double y, double x){
        // arcadeDrive appears to flip the parameters.
        // Tried inverting motors and talon assignments
        // but nothing worked. Until we figure it out,
        // flipping the parameters here.
        diffDriveTrain.arcadeDrive(x,y);
    }

    public void tankDriveVolts(double leftVolts, double rightVolts) { // in volts
        leftSpeedControl.setVoltage(leftVolts);
        rightSpeedControl.setVoltage(-rightVolts);
        diffDriveTrain.feed();
    }

    public void setMaxOutput(double maxOutput) { // not sure what the units are
        diffDriveTrain.setMaxOutput(maxOutput);
    }

    //<<<<<<<<<------------------------------pose-odometry-pigeon---------------------------------------->>>>>>>>>>>

    public void setupOdometry(){

        leftEncoder = new Encoder(RobotMap.DRIVE_LEFT_ONE,RobotMap.DRIVE_LEFT_TWO);
        rightEncoder = new Encoder(RobotMap.DRIVE_RIGHT_ONE,RobotMap.DRIVE_RIGHT_TWO);

        leftEncoder.setDistancePerPulse(Constants.drivetrain.MetersPerTickLeft);
        rightEncoder.setDistancePerPulse(Constants.drivetrain.MetersPerTickRight);

        resetEncoders();

        //driveOdometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(getHeading()));
    }

    public void resetEncoders() {
        leftEncoder.reset();
        rightEncoder.reset();
    }
/*
    public double getHeading() {
        return Pigeon.getInstance().getYPR()[0]% 360;// TODO: turning right should be positive we gotta check if it is
    }
*/
/*
    @Override
    public void periodic() {
        // Update the odometry in the periodic block
        driveOdometry.update(Rotation2d.fromDegrees(getHeading()),
                leftEncoder.getDistance(),
                rightEncoder.getDistance());
    }
*/
/*
    public Pose2d getPose() {
        return driveOdometry.getPoseMeters();
    }

    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        return new DifferentialDriveWheelSpeeds(leftEncoder.getRate(), rightEncoder.getRate());
    }

    public void resetOdometry(Pose2d pose) {
        resetEncoders();
        driveOdometry.resetPosition(pose, Rotation2d.fromDegrees(getHeading()));
    }

    public void resetPigeonPosition(){
        Pigeon.getInstance().setYaw(0);
    }

    public double getAverageEncoderDistance() {
        return (leftEncoder.getDistance() + rightEncoder.getDistance()) / 2.0;
    }

    public double getAngularVelocity(){
        return Pigeon.getInstance().getRawGyro()[0];
    }
*/
}
