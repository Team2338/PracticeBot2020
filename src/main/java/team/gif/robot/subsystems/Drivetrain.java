package team.gif.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import team.gif.robot.Constants;
import team.gif.robot.Robot;
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

    public static DifferentialDriveOdometry driveOdometry;

    public static DifferentialDriveKinematics drivekinematics;

    public static ChassisSpeeds chassisSpeeds;

    public static DifferentialDriveWheelSpeeds wheelSpeeds;

    private static Pigeon _pigeon;

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

        _pigeon = Robot.isCompBot ? new Pigeon( leftTalon2 ) : new Pigeon() ;

        _pigeon.resetPigeonPosition(); // set initial heading to zero degrees
        setupOdometry();
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
        rightSpeedControl.setVoltage(-rightVolts);// this chunk was copied maybe flip dis
        diffDriveTrain.feed();
    }

    public void setMaxOutput(double maxOutput) { // not sure what the units are
        diffDriveTrain.setMaxOutput(maxOutput);
    }

    //<<<<<<<<<------------------------------pose-odometry-pigeon---------------------------------------->>>>>>>>>>>

    public void setupOdometry(){

        drivekinematics = new DifferentialDriveKinematics(Units.inchesToMeters(Constants.drivetrain.TRACK_WIDTH));

        //the max speeds forward,right, and anglar velocity in meters per second or rads/sec
        //right is 0 because we are arcade drive this only applies to meccanum and swerve
        // forward must be characterized and so must rotational
        chassisSpeeds = new ChassisSpeeds(2.0, 0, 1.0);

        wheelSpeeds = drivekinematics.toWheelSpeeds(chassisSpeeds);
        //the individual wheel speeds can be obtained through java but i see no use for it

        System.out.println("odometer setup");
        /*
        leftEncoder = new Encoder(RobotMap.DRIVE_LEFT_ONE,RobotMap.DRIVE_LEFT_TWO);
        rightEncoder = new Encoder(RobotMap.DRIVE_RIGHT_ONE,RobotMap.DRIVE_RIGHT_TWO);

        leftEncoder.setDistancePerPulse(Constants.drivetrain.METERS_PER_TICK_LEFT);
        rightEncoder.setDistancePerPulse(Constants.drivetrain.METERS_PER_TICK_RIGHT);
        */
        resetEncoders();

        if (_pigeon.isActive()){
            driveOdometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(_pigeon.getHeading()));
        }
    }

    /*
    public void resetEncoders() {
        leftEncoder.reset();
        rightEncoder.reset();
    }
    */


    @Override
    public void periodic() {
        // Update the odometry

        if (_pigeon.isActive()) {
            driveOdometry.update(Rotation2d.fromDegrees(_pigeon.getHeading()),
                    getLeftPosMeters(),
                    getRightPosMeters());

            SmartDashboard.putNumber("encoder read left", getLeftEncoderPos());
            SmartDashboard.putNumber("encoder read right", getRightEncoderPos());

            SmartDashboard.putNumber("encoder odometry Y", driveOdometry.getPoseMeters().getTranslation().getY());
            SmartDashboard.putNumber("encoder odometry X", driveOdometry.getPoseMeters().getTranslation().getX());
            SmartDashboard.putNumber("encoder rotate", driveOdometry.getPoseMeters().getRotation().getDegrees());
        } else {
            System.out.println("Cannot set robot odemetry. Pigeon is not in ready state.");
        }
    }

    public Pose2d getPose() {
        return driveOdometry.getPoseMeters();
    }

    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        return new DifferentialDriveWheelSpeeds(getLeftVelMeters(),getRightVelMeters());
    }


    public double getAverageEncoderDistance() {
        return (getLeftPosMeters() +getRightPosMeters()) / 2.0;
    }

    /*    public void resetOdometry(Pose2d pose) {
            resetEncoders();
            driveOdometry.resetPosition(pose, Rotation2d.fromDegrees(getHeading()));
        }

    public double getAngularVelocity(){
        double[] rawGyro = new double[3];
        _pigeon.getRawGyro(rawGyro);
        return rawGyro[0];
    }
    */
    //<<<<---------------imported from cn-odometry------------------------>>>>>>>>>>>>>>>>>>>

    // Mag Encoder methods

    public void resetEncoders() {
        leftTalon1.setSelectedSensorPosition(0, 0, 0);
        rightTalon1.setSelectedSensorPosition(0, 0, 0);
    }

    public double getLeftEncoderPos() {
        return leftTalon1.getSelectedSensorPosition();
    }

    public double getRightEncoderPos() {
        return rightTalon1.getSelectedSensorPosition();
    }

    //encoder positions in meters
    public double getLeftPosMeters() {
        return leftTalon1.getSelectedSensorPosition() * Constants.drivetrain.TICKS_TO_METERS;
    }

    public double getRightPosMeters() {
        return rightTalon1.getSelectedSensorPosition() * Constants.drivetrain.TICKS_TO_METERS;
    }

    //encoder speeds in meters/sec
    public double getLeftVelMeters() {
        return leftTalon1.getSelectedSensorVelocity() * Constants.drivetrain.DPS_TO_MPS;
    }

    public double getRightVelMeters() {
        return rightTalon1.getSelectedSensorVelocity() * Constants.drivetrain.DPS_TO_MPS;
    }

    //encoder speeds in ticks
    public double getRightVel(){
        return rightTalon1.getSelectedSensorVelocity();
    }

    public double getLeftVel(){
        return leftTalon1.getSelectedSensorVelocity();
    }

}