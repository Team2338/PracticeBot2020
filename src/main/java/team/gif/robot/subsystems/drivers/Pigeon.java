package team.gif.robot.subsystems.drivers;
import com.ctre.phoenix.sensors.PigeonIMU;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import team.gif.robot.RobotMap;

public class Pigeon {

    public static PigeonIMU pidgeon = new PigeonIMU(RobotMap.PIGEON);

    private static Pigeon instance = null;

    public static Pigeon getInstance() {
        if (instance == null) {
            instance = new Pigeon();
        }
        return instance;
    }



}