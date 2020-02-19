package team.gif.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import team.gif.robot.RobotMap;

public class Intake extends SubsystemBase {
    private static Intake instance = null;

    public static Intake getInstance() {
        if (instance == null) {
            instance = new Intake();
        }
        return instance;
    }

    private static final TalonSRX intakeMotor = new TalonSRX(RobotMap.INTAKE);

    private static final Solenoid solenoidZero = new Solenoid(RobotMap.SOLENOID_ZERO);
    private static final Solenoid solenoidOne = new Solenoid(RobotMap.SOLENOID_ONE);
    private static final Solenoid solenoidTwo = new Solenoid(RobotMap.SOLENOID_TWO);

    private Intake() {
        super();
        intakeMotor.setInverted(true);
    }

    public void setSpeed(double speed) {
        intakeMotor.set(ControlMode.PercentOutput, speed);
    }

    public void setSolenoids(boolean zero, boolean one, boolean two) {
        solenoidZero.set(zero);
        solenoidOne.set(one);
        solenoidTwo.set(two);
    }
    /*
     * Data Table for Solenoid States
     *      Down    Mid     Up
     * 0    on      on      off
     * 1    on      off     off
     * 2    off     on      on
     */
}
