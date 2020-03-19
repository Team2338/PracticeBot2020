package team.gif.robot.subsystems.drivers;
import com.ctre.phoenix.sensors.PigeonIMU;
import team.gif.robot.RobotMap;

public class Pigeon {

    public static PigeonIMU pidgeon;

    private static Pigeon instance = null;

    public static Pigeon getInstance() {
        if (instance == null) {
            instance = new Pigeon();

        }
        System.out.println("pigeon getting instance");
        return instance;
    }

    public Pigeon(){
        pidgeon = new PigeonIMU(RobotMap.PIGEON);
        System.out.println("pigeon constructed");
    }

    public void setYaw(double yaw){
        pidgeon.setYaw(yaw);
    }

    public double[] getYPR(){
        double[] ypr = new double[3];
        pidgeon.getYawPitchRoll(ypr);
        System.out.println("YPR"+ypr[0]+ypr[1]+ypr[2]);
        return ypr;
    }

    public double[] getQuaternions() {
        //quaternions
        double[] quaternions = new double[4];
        pidgeon.get6dQuaternion(quaternions);
        return quaternions;
    }

    public double getTemp(){
        double stuff = 0;
        stuff =pidgeon.getTemp();
        return stuff;
    }

    public double[] getAccumulatedGyro(){
        double[] accumGyro = new double[3];
        pidgeon.getAccumGyro(accumGyro);
        return accumGyro;
    }

    public short[] getBiasedAccel(){
        short[] biasedAccel = new short[3];
        pidgeon.getBiasedAccelerometer(biasedAccel);
        return biasedAccel;
    }

    public double[] getRawGyro(){
        double[] rawGyro = new double[3];
        pidgeon.getRawGyro(rawGyro);
        return rawGyro;
    }// angular velocities

    public double[] getAccelAngles() {
        double[] accelAngles = new double[3];
        pidgeon.getAccelerometerAngles(accelAngles);
        return accelAngles;
    }

    public short[] getBiasedMagnetometer() {
        short[] biasedMagnet = new short[3];
        pidgeon.getBiasedMagnetometer(biasedMagnet);
        return biasedMagnet;
    }

    // raw magnetometer
    public short[] getRawMagnet() {
        short[] rawMagnet = new short[3];
        pidgeon.getRawMagnetometer(rawMagnet);
        return rawMagnet;
    }


}