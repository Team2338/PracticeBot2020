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

    private static final CANSparkMax flywheelMotor_ONE = new CANSparkMax(RobotMap.FLYWHEEL_ONE, CANSparkMaxLowLevel.MotorType.kBrushless);
    private static final CANPIDController flywheelPIDController_ONE = flywheelMotor_ONE.getPIDController();
    private static final CANEncoder flywheelEncoder_ONE = flywheelMotor_ONE.getEncoder();

    private static final CANSparkMax flywheelMotor_TWO = new CANSparkMax(RobotMap.FLYWHEEL_TWO, CANSparkMaxLowLevel.MotorType.kBrushless);
    private static final CANPIDController flywheelPIDController_TWO = flywheelMotor_TWO.getPIDController();
    int stallMaxAmps = 40;
    int maxAmps = 20;


    private Shooter() {
        super();




        //flywheelMotor.setSmartCurrentLimit(stallMaxAmps,stallMaxAmps);
//
        //flywheelMotor.burnFlash();

        flywheelMotor_TWO.restoreFactoryDefaults();
        flywheelMotor_ONE.restoreFactoryDefaults();

        flywheelMotor_ONE.enableVoltageCompensation(12);
        flywheelMotor_TWO.enableVoltageCompensation(12);

        flywheelMotor_ONE.setInverted(!Robot.isCompBot); // C:false P:true
        flywheelMotor_TWO.setInverted(!Robot.isCompBot);

        flywheelMotor_ONE.setIdleMode(CANSparkMax.IdleMode.kCoast);
        flywheelMotor_TWO.setIdleMode(CANSparkMax.IdleMode.kCoast);

        flywheelMotor_ONE.setSmartCurrentLimit(maxAmps);

        flywheelPIDController_TWO.setP(Constants.Shooter.kP);
        flywheelPIDController_TWO.setFF(Constants.Shooter.kF);
        flywheelPIDController_TWO.setOutputRange(0, 1);

        flywheelPIDController_ONE.setP(Constants.Shooter.kP);
        flywheelPIDController_ONE.setFF(Constants.Shooter.kF);
        flywheelPIDController_ONE.setOutputRange(0, 1);



        flywheelMotor_TWO.follow(flywheelMotor_ONE);

        flywheelMotor_ONE.burnFlash();
        flywheelMotor_TWO.burnFlash();


        //https://www.chiefdelphi.com/t/spark-max-current-limit/354333/3
    }

    public void setVoltage(double voltage) {
        flywheelMotor_ONE.setVoltage(voltage);
    }

    public void setPID (double setPoint) {
        flywheelPIDController_ONE.setReference(setPoint, ControlType.kVelocity);
    }

    public double getVelocity () { return flywheelEncoder_ONE.getVelocity();}

    public String getVelocity_Shuffleboard(){ return String.format("%12.0f",getVelocity());}
}
