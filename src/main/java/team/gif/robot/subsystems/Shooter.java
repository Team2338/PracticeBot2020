package team.gif.robot.subsystems;

import com.revrobotics.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import team.gif.robot.Constants;
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
    private static final CANPIDController flywheelPIDController = flywheelMotor.getPIDController();
    private static final CANEncoder flywheelEncoder = flywheelMotor.getEncoder();

    private Shooter() {
        super();
        flywheelMotor.restoreFactoryDefaults();
        flywheelMotor.enableVoltageCompensation(12);
        flywheelMotor.setInverted(true);
        flywheelMotor.setIdleMode(CANSparkMax.IdleMode.kCoast);
        flywheelPIDController.setP(Constants.Shooter.kP);
        flywheelPIDController.setFF(Constants.Shooter.kF);
        flywheelPIDController.setOutputRange(0, 1);
    }

    public void setSpeed(double speed) {
        flywheelMotor.setVoltage(speed);
    }

    public void setPID (double setPoint) {
        SmartDashboard.putNumber("setPoint",setPoint);
        flywheelPIDController.setReference(setPoint, ControlType.kVelocity);
    }

    public double getVelocity () {
        double velocity = flywheelEncoder.getVelocity();
        return velocity;
    }
}
