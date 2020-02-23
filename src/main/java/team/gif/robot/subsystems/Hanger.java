package team.gif.robot.subsystems;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import team.gif.robot.Constants;
import team.gif.robot.RobotMap;

public class Hanger extends SubsystemBase {
    public static Hanger instance = null;

    private static final TalonSRX hangMotor = new TalonSRX(RobotMap.HANGER);

    public static Hanger getInstance() {
        if (instance == null) {
            instance = new Hanger();
        }
        return instance;
    }

    public Hanger() {
        super();
        /* Factory default hardware to prevent unexpected behavior */
        hangMotor.configFactoryDefault();

        //hangMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10);
        //hangMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10);

        /* Configure Sensor Source for Primary PID */
        hangMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);

        hangMotor.enableVoltageCompensation(true);
        hangMotor.setSensorPhase(true);
        hangMotor.setInverted(false);
        hangMotor.setNeutralMode(NeutralMode.Brake);

        /* Set Motion Magic gains in slot0 - see documentation */
        hangMotor.config_kP(0, Constants.Hanger.P);
        hangMotor.config_kI(0, Constants.Hanger.I);
        hangMotor.config_kD(0, Constants.Hanger.D);
        hangMotor.config_kF(0, Constants.Hanger.F);

        /* Set acceleration and vcruise velocity - see documentation */
        hangMotor.configMotionCruiseVelocity(Constants.Hanger.MAX_VELOCITY);
        hangMotor.configMotionAcceleration(Constants.Hanger.MAX_ACCELERATION);
        hangMotor.configNominalOutputForward(0);
        hangMotor.configNominalOutputReverse(0);
        hangMotor.configPeakOutputForward(1);
        hangMotor.configPeakOutputReverse(-1);

        /* limit switches */
        hangMotor.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyClosed);
        hangMotor.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
        hangMotor.configForwardSoftLimitThreshold(Constants.Hanger.MAX_POS);
        hangMotor.configReverseSoftLimitThreshold(Constants.Hanger.MIN_POS);
        hangMotor.overrideLimitSwitchesEnable(false);
        hangMotor.configForwardSoftLimitEnable(true);
        hangMotor.configReverseSoftLimitEnable(true);
        //hangMotor.configClearPositionOnLimitR(true, 0);
    }

    public void setSpeed(double speed) {
        hangMotor.set(ControlMode.PercentOutput, speed);
    }

    public void setMotionMagic(double position, double arbitraryFeedForward) {
        hangMotor.set(ControlMode.MotionMagic, position, DemandType.ArbitraryFeedForward, arbitraryFeedForward);
    }

    public void setCruiseVelocity(int ticksPer100ms) {
        hangMotor.configMotionCruiseVelocity(ticksPer100ms);
    }

    public void configF(double f) {
        hangMotor.config_kF(0, f);
    }

    public int getPosition() {
        return hangMotor.getSelectedSensorPosition();
    }

    public double getOutputPercent() {
        return hangMotor.getMotorOutputPercent();
    }

    public boolean isFinished() {
        return Math.abs(hangMotor.getClosedLoopError()) < Constants.Hanger.ALLOWABLE_ERROR;
    }
}
