package team.gif.robot;

public abstract class RobotMap {

    // Drive Motors
    public static final int DRIVE_LEFT_MASTER = 2; //0
    public static final int DRIVE_LEFT_SLAVE = 3; //1
    public static final int DRIVE_RIGHT_MASTER = 0; //2
    public static final int DRIVE_RIGHT_SLAVE = 1; //3

    // Other Motors
    public static final int INTAKE = 4; //4
    public static final int STAGE_TWO = 5; //5
    public static final int STAGE_THREE = 6; //6
    public static final int STAGE_FOUR = 7; //7
    public static final int STAGE_FIVE = 8; //8
    public static final int FLYWHEEL = 9; //9
    public static final int HANGER = 11;

    // Sensors
    public static final int SENSOR_KNOPF = 0;
    public static final int SENSOR_ONE = 1;
    public static final int SENSOR_TWO = 2;
    public static final int SENSOR_THREE = 3;
    public static final int SENSOR_FOUR = 4;
    public static final int SENSOR_FIVE = 5;
    public static final int PRESSURE_SENSOR = 0;

    // Solenoids
    public static final int SOLENOID_ZERO = 0;
    public static final int SOLENOID_ONE = 1;
    public static final int SOLENOID_TWO = 2;
    public static final int SOLENOID_HANGER = 3;

    // Controllers
    public static final int DRIVER_CONTROLLER_ID = 0;
    public static final int AUX_CONTROLLER_ID = 1;

    //pigeon
    public static final int PIGEON = 2;


}
