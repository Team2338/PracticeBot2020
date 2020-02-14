package team.gif.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import team.gif.robot.RobotMap;

public class Elevator extends SubsystemBase {
    private static Elevator instance = null;

    private static final TalonSRX elevatorMotor = new TalonSRX(RobotMap.ELEVATOR);

    public static Elevator getInstance() {
        if (instance == null) {
            instance = new Elevator();
        }
        return instance;
    }

    private Elevator() {
        super();
        //elevatorMotor.setInverted(true);
        elevatorMotor.setNeutralMode(NeutralMode.Coast);
    }

    public void setspeed(double speed) {
        elevatorMotor.set(ControlMode.PercentOutput, speed);
    }
}
