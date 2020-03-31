package team.gif.robot.subsystems.drivers;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import team.gif.robot.RobotMap;

public class Pigeon {

    public static PigeonIMU _pigeon;

    private static Pigeon instance = null;

    private PigeonIMU.GeneralStatus _pigeonGenStatus = new PigeonIMU.GeneralStatus();

    public static Pigeon getInstance() {
        if (instance == null) {
            instance = new Pigeon();
        }
        return instance;
    }

    public Pigeon(){
        _pigeon = new PigeonIMU(RobotMap.PIGEON);
        instance = this;
    }

    public Pigeon(TalonSRX talon){
        _pigeon = new PigeonIMU(talon);
        instance = this;
    }

    public double getHeading() {
        double heading;
        double[] ypr = new double[3];

        _pigeon.getYawPitchRoll(ypr);

        // get the heading. If the value is negative, need to make it relative to 360 (value is already negative so add)
        heading = ypr[0] < 0 ? 360.0 + ypr[0] % 360 : ypr[0] % 360;

        SmartDashboard.putString("Heading", String.format("%.1f",heading));

        return heading;
    }

    public boolean isActive() {
        _pigeon.getGeneralStatus(_pigeonGenStatus);

        return _pigeonGenStatus.state == PigeonIMU.PigeonState.Ready;
    }

    public void resetPigeonPosition() {
        setYaw(0);
    }

    public void setYaw(double yaw){
        _pigeon.setYaw(yaw);
    }

    public double[] getYPR(){
        double[] ypr = new double[3];
        _pigeon.getYawPitchRoll(ypr);
        System.out.format("YPR %.1f %.1f %.1f",ypr[0],ypr[1],ypr[2]);
        return ypr;
    }

    public double[] getQuaternions() {
        //quaternions
        double[] quaternions = new double[4];
        _pigeon.get6dQuaternion(quaternions);
        return quaternions;
    }

    public double getTemp(){
        return _pigeon.getTemp();
    }

    public double[] getAccumulatedGyro(){
        double[] accumGyro = new double[3];
        _pigeon.getAccumGyro(accumGyro);
        return accumGyro;
    }

    public short[] getBiasedAccel(){
        short[] biasedAccel = new short[3];
        _pigeon.getBiasedAccelerometer(biasedAccel);
        return biasedAccel;
    }

    public double[] getRawGyro(){
        double[] rawGyro = new double[3];
        _pigeon.getRawGyro(rawGyro);
        return rawGyro;
    }// angular velocities

    public double[] getAccelAngles() {
        double[] accelAngles = new double[3];
        _pigeon.getAccelerometerAngles(accelAngles);
        return accelAngles;
    }

    public short[] getBiasedMagnetometer() {
        short[] biasedMagnet = new short[3];
        _pigeon.getBiasedMagnetometer(biasedMagnet);
        return biasedMagnet;
    }

    // raw magnetometer
    public short[] getRawMagnet() {
        short[] rawMagnet = new short[3];
        _pigeon.getRawMagnetometer(rawMagnet);
        return rawMagnet;
    }
}