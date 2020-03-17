package team.gif.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import team.gif.robot.Constants;
import team.gif.robot.RobotMap;
import team.gif.robot.subsystems.drivers.Pigeon;

public class Drivetrain extends SubsystemBase {

    private static Drivetrain instance = null;

    public static WPI_TalonSRX leftMaster;
    public static WPI_TalonSRX leftSlave;
    public static WPI_TalonSRX rightMaster;
    public static WPI_TalonSRX rightSlave;

    public static SpeedControllerGroup leftSpeedControl;
    public static SpeedControllerGroup rightSpeedControl;
    public static DifferentialDrive diffDrivetrain;

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

        leftMaster = new WPI_TalonSRX(RobotMap.DRIVE_LEFT_MASTER);
        leftSlave = new WPI_TalonSRX(RobotMap.DRIVE_LEFT_SLAVE);
        rightMaster = new WPI_TalonSRX(RobotMap.DRIVE_RIGHT_MASTER);
        rightSlave = new WPI_TalonSRX(RobotMap.DRIVE_RIGHT_SLAVE);

        leftSpeedControl = new SpeedControllerGroup(leftMaster,leftSlave);
        rightSpeedControl = new SpeedControllerGroup(rightMaster,rightSlave);

        leftMaster.setInverted(true);
        leftSlave.setInverted(true);

        leftMaster.setNeutralMode(NeutralMode.Brake);
        leftSlave.setNeutralMode(NeutralMode.Brake);
        rightMaster.setNeutralMode(NeutralMode.Brake);
        rightSlave.setNeutralMode(NeutralMode.Brake);

        diffDrivetrain = new DifferentialDrive(leftSpeedControl,rightSpeedControl);

        //setupOdometry();

//        leftSlave.follow(leftMaster);
//        rightSlave.follow(rightMaster);
    }

    //<<<<<<<<<----------------driving----------------------->>>>>>>>>>>>>>>>>>>

    public void setSpeed(double left, double right) {/*
        leftMaster.set(ControlMode.PercentOutput, left);
        rightMaster.set(ControlMode.PercentOutput, right);
    */
        diffDrivetrain.tankDrive(left,right);
    }

    public void driveArcade(double speed, double rotation){
        diffDrivetrain.arcadeDrive(speed,rotation);
    }

    public void tankDriveVolts(double leftVolts, double rightVolts) {
        leftSpeedControl.setVoltage(leftVolts);
        rightSpeedControl.setVoltage(-rightVolts);
        diffDrivetrain.feed();
    }

    public void setMaxOutput(double maxOutput) {
        diffDrivetrain.setMaxOutput(maxOutput);
    }

    //<<<<<<<<<------------------------------pose-odometry-pigeon---------------------------------------->>>>>>>>>>>

    public void setupOdometry(){

        // i dont think this chunk gonna work that simply
        leftEncoder = new Encoder(RobotMap.DRIVE_LEFT_MASTER,RobotMap.DRIVE_LEFT_SLAVE);
        rightEncoder = new Encoder(RobotMap.DRIVE_RIGHT_MASTER,RobotMap.DRIVE_RIGHT_SLAVE);

        leftEncoder.setDistancePerPulse(Constants.drivetrain.distancePerTick);
        rightEncoder.setDistancePerPulse(Constants.drivetrain.distancePerTick);



        //resetEncoders();

        //driveOdometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(getHeading()));

    }
/*
    public void resetEncoders() {
        leftEncoder.reset();
        rightEncoder.reset();
    }
*//*
    public double getHeading() {
        return Pigeon.getInstance().getYPR()[0]% 360;// TODO: turning right should be positive we gotta check if it is
    }
*//*
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

    public void resetPigeon(){
        Pigeon.getInstance().setYaw(0);
    }

    public double getAverageEncoderDistance() {
        return (leftEncoder.getDistance() + rightEncoder.getDistance()) / 2.0;
    }

    public double getAngularVelocity(){
        return Pigeon.getInstance().getrawGyro()[0];
    }
*/
}
