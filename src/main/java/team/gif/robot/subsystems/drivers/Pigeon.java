package team.gif.robot.subsystems.drivers;
<<<<<<< HEAD

=======
>>>>>>> master
import com.ctre.phoenix.sensors.PigeonIMU;
import team.gif.robot.RobotMap;

public class Pigeon {

    public static PigeonIMU pigeon = new PigeonIMU(RobotMap.PIGEON);

    private static Pigeon instance = null;

    public static Pigeon getInstance() {
        if (instance == null) {
            instance = new Pigeon();
        }
        return instance;
    }
    public void setYaw(double yaw){

    }

    public double[] getYPR(){
        double[] ypr = new double[3];
        pigeon.getYawPitchRoll(ypr);
        return ypr;
    }

    public void setYaw(double set){
        pigeon.setYaw(set);
    }

    public double getHeading() {
        return pigeon.getFusedHeading();
    }

    public void resetHeading() {
        pigeon.setYaw(0);
    }

    public double[] getQuaternions() {
        //quaternions
        double[] quaternions = new double[4];
        pigeon.get6dQuaternion(quaternions);
        return quaternions;
    }

    public double getTemp(){
        double stuff = 0;
        stuff = pigeon.getTemp();
        return stuff;
    }

    public double[] getAccumulatedGyro(){
        double[] accumGyro = new double[3];
<<<<<<< HEAD
        pigeon.getAccumGyro(accumGyro);
=======
        pidgeon.getAccumGyro(accumGyro);
>>>>>>> master
        return accumGyro;
    }

    public short[] getBiasedAccel(){
        short[] biasedAccel = new short[3];
        pigeon.getBiasedAccelerometer(biasedAccel);
        return biasedAccel;
    }

    public double[] getrawGyro(){
        double[] rawGyro = new double[3];
<<<<<<< HEAD
        pigeon.getRawGyro(rawGyro);
=======
        pidgeon.getRawGyro(rawGyro);
>>>>>>> master
        return rawGyro;
    }

    public double[] getAccelAngles() {
        double[] accelAngles = new double[3];
        pigeon.getAccelerometerAngles(accelAngles);
        return accelAngles;
    }

    public short[] getBiasedMagnetometer() {
        short[] biasedMagnet = new short[3];
        pigeon.getBiasedMagnetometer(biasedMagnet);
        return biasedMagnet;
    }
        // raw magnetometer
    public short[] getRawMagnet() {
        short[] rawMagnet = new short[3];
        pigeon.getRawMagnetometer(rawMagnet);
        return rawMagnet;
    }

}