package team.gif.robot.subsystems;

import com.revrobotics.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import team.gif.robot.Constants;
import team.gif.robot.RobotMap;

public class Hanger extends SubsystemBase {
    public static Hanger instance = null;

    private static final CANSparkMax hangMotor = new CANSparkMax(RobotMap.HANGER, CANSparkMaxLowLevel.MotorType.kBrushless);
    private static final CANPIDController hangPIDController = hangMotor.getPIDController();
    private static final CANEncoder hangEncoder = hangMotor.getEncoder();
    private static final CANDigitalInput limitSwitch = hangMotor.getReverseLimitSwitch(CANDigitalInput.LimitSwitchPolarity.kNormallyClosed);

    public static Hanger getInstance() {
        if (instance == null) {
            instance = new Hanger();
        }
        return instance;
    }

    public Hanger() {
        super();
        hangMotor.setInverted(false);
        hangMotor.restoreFactoryDefaults();

        // Limit Switch
        limitSwitch.enableLimitSwitch(true);
        // Soft Limits
        hangMotor.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, false);
        hangMotor.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, false);

        hangMotor.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, Constants.Hanger.MAX_POS);
        hangMotor.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, Constants.Hanger.MIN_POS);

        // PID Controller
        hangPIDController.setP(Constants.Hanger.P);
        hangPIDController.setI(Constants.Hanger.I);
        hangPIDController.setD(Constants.Hanger.D);
        hangPIDController.setFF(Constants.Hanger.F);
        hangPIDController.setOutputRange(-1, 1);

        int smartMotionSlot = 0;
        hangPIDController.setSmartMotionMaxVelocity(Constants.Hanger.MAX_VELOCITY, smartMotionSlot);
        hangPIDController.setSmartMotionMinOutputVelocity(Constants.Hanger.MIN_VELOCITY, smartMotionSlot);
        hangPIDController.setSmartMotionMaxAccel(Constants.Hanger.MAX_ACCELERATION, smartMotionSlot);
        hangPIDController.setSmartMotionAllowedClosedLoopError(Constants.Hanger.ALLOWABLE_ERROR, smartMotionSlot);

        hangMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
    }

    public void setVoltage(double speed){
        hangMotor.setVoltage(speed);
    }

    public void setSpeed(double speed) {
        hangMotor.set(speed);
    }

    public void setPoint(int position) {
        hangPIDController.setReference(position, ControlType.kSmartMotion);
    }

    public void setF() {
        hangPIDController.setFF(Constants.Hanger.F);
    }

    public void setFGravity() {
        hangPIDController.setFF(Constants.Hanger.GRAV_FEED_FORWARD);
    }

    public double getPosition() {
        return hangEncoder.getPosition();
    }
}
