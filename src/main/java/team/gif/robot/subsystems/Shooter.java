package team.gif.robot.subsystems;

import com.revrobotics.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import team.gif.robot.Constants;
import team.gif.robot.Robot;
import team.gif.robot.RobotMap;

public class Shooter extends SubsystemBase {
    private static Shooter instance = null;

    public static Shooter getInstance() {
        if (instance == null) {
            instance = new Shooter();
        }
        return instance;
    }

    private static final CANSparkMax flywheelMotor = new CANSparkMax(RobotMap.FLYWHEEL, CANSparkMaxLowLevel.MotorType.kBrushless);
    private static final CANSparkMax flywheelMotorTwo = new CANSparkMax(RobotMap.FLYWHEEL_TWO, CANSparkMaxLowLevel.MotorType.kBrushless);
    private static final CANPIDController flywheelPIDController = flywheelMotor.getPIDController();
    private static final CANEncoder flywheelEncoder = flywheelMotor.getEncoder();

    private Shooter() {
        super();
        flywheelMotor.restoreFactoryDefaults();
        flywheelMotor.enableVoltageCompensation(12);
        flywheelMotor.setInverted(false); // C:false P:true
        flywheelMotor.setIdleMode(CANSparkMax.IdleMode.kCoast);
        flywheelPIDController.setP(Constants.Shooter.kP);
        flywheelPIDController.setFF(Constants.Shooter.kF);
        flywheelPIDController.setOutputRange(0, 1);

        flywheelMotorTwo.restoreFactoryDefaults();
        flywheelMotorTwo.enableVoltageCompensation(12);
        flywheelMotorTwo.setIdleMode(CANSparkMax.IdleMode.kCoast);
        //flywheelMotorTwo.follow(flywheelMotor);
        flywheelMotorTwo.setInverted(true);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        flywheelMotorTwo.set(flywheelMotor.get());
    }

    public void setVoltage(double voltage) {
        flywheelMotor.setVoltage(voltage);
    }

    public void setPID (double setPoint) {
        flywheelPIDController.setReference(setPoint, ControlType.kVelocity);
    }
    public double getVelocity () {
        double velocity = flywheelEncoder.getVelocity();
        return velocity;
    }

    public double getOutput() {
        double output = flywheelMotor.getAppliedOutput();
        return output;
    }
    public double getOutputTwo() {
        double output = flywheelMotorTwo.getAppliedOutput();
        return output;
    }

    // TEMP
    public void setSpeedMain (double speed) {
        flywheelMotor.set(speed);
        //flywheelMotorTwo.set(speed);
    }
}
