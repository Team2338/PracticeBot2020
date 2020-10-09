package team.gif.robot.subsystems;

import com.revrobotics.*;
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
    private static final CANPIDController flywheelPIDController = flywheelMotor.getPIDController();
    private static final CANEncoder flywheelEncoder = flywheelMotor.getEncoder();

    int stallMaxAmps = 40;


    private Shooter() {
        super();
        flywheelMotor.restoreFactoryDefaults();
        flywheelMotor.enableVoltageCompensation(12);
        flywheelMotor.setInverted(!Robot.isCompBot); // C:false P:true
        flywheelMotor.setIdleMode(CANSparkMax.IdleMode.kCoast);

        flywheelPIDController.setP(Constants.Shooter.kP);
        flywheelPIDController.setFF(Constants.Shooter.kF);
        flywheelPIDController.setOutputRange(0, 1);

        flywheelMotor.setSmartCurrentLimit(stallMaxAmps,stallMaxAmps);

        flywheelMotor.burnFlash();
        //https://www.chiefdelphi.com/t/spark-max-current-limit/354333/3
    }

    public void setVoltage(double voltage) {
        flywheelMotor.setVoltage(voltage);
    }

    public void setPID (double setPoint) {
        flywheelPIDController.setReference(setPoint, ControlType.kVelocity);
    }
    public double getVelocity () { return flywheelEncoder.getVelocity();}

    public String getVelocity_Shuffleboard(){ return String.format("%12.0f",getVelocity());}
}
