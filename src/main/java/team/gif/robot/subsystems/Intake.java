package team.gif.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
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

    private Intake() {
        super();
        // inverted?
    }

    public void setSpeed(double speed) {
        intakeMotor.set(ControlMode.PercentOutput, speed);
    }
}
