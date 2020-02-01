package team.gif.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import team.gif.robot.RobotMap;

public class Indexer extends SubsystemBase {
    private static Indexer instance = null;

    public static Indexer getInstance() {
        if (instance == null) {
            instance = new Indexer();
        }
        return instance;
    }

    private static final TalonSRX stageTwoMotor = new TalonSRX(RobotMap.STAGE_TWO);
    private static final TalonSRX stageThreeMotor = new TalonSRX(RobotMap.STAGE_THREE);
    private static final TalonSRX stageFourMotor = new TalonSRX(RobotMap.STAGE_FOUR);
    private static final TalonSRX stageFiveMotor = new TalonSRX(RobotMap.STAGE_FIVE);

    private static final DigitalInput knopfSensor = new DigitalInput(RobotMap.SENSOR_KNOPF);

    private static final DigitalInput stageOneSensor = new DigitalInput(RobotMap.SENSOR_ONE);
    private static final DigitalInput stageTwoSensor = new DigitalInput(RobotMap.SENSOR_TWO);
    private static final DigitalInput stageThreeSensor = new DigitalInput(RobotMap.SENSOR_THREE);
    private static final DigitalInput stageFourSensor = new DigitalInput(RobotMap.SENSOR_FOUR);
    private static final DigitalInput stageFiveSensor = new DigitalInput(RobotMap.SENSOR_FIVE);

    private Indexer() {
        super();
        stageFourMotor.setInverted(true);
    }

    public boolean getKnopf() {
        return knopfSensor.get();
    }

    public boolean[] getState() {
        boolean[] sensorStates = {!stageOneSensor.get(), stageTwoSensor.get(), stageThreeSensor.get(), stageFourSensor.get(), stageFiveSensor.get()};
        return sensorStates;
    }

    public void setSpeed(double[] speed) {
        stageTwoMotor.set(ControlMode.PercentOutput, speed[0]);
        stageThreeMotor.set(ControlMode.PercentOutput, speed[1]);
        stageFourMotor.set(ControlMode.PercentOutput, speed[2]);
        stageFiveMotor.set(ControlMode.PercentOutput, speed[3]);
    }
}
