package team.gif.robot.subsystems.drivers;
import com.ctre.phoenix.sensors.PigeonIMU;
public class Pigeon {



    PigeonIMU _pigeon = new PigeonIMU(0);

System.out.println("new pigeon values");
        switch(whichone) {

        case 1:
            double[] ypr = new double[3];
            pidgeon.getYawPitchRoll(ypr);
            System.out.println("pidgeon ypr " + "y " + ypr[0] + " p " + ypr[1] + " r " + ypr[2]);
            break;

        //quaternions
        case 2:
            double[] quaternions = new double[4];
            pidgeon.get6dQuaternion(quaternions);
            System.out.println("quaternions(6d #s) " + "W" + quaternions[0] + "X" + quaternions[1] + "Y " + quaternions[2] + "Z " + quaternions[3]);
            break;

        //acumulated gyro
        case 3:
            double[] accumGyro = new double[3];
            pidgeon.getAccumGyro(accumGyro);
            System.out.println("accumGyro" + "X" + accumGyro[0] + "Y" + accumGyro[1] + "Z" + accumGyro[2]);
            break;

        // biased accel
        case 4:
            short[] biasedAccel = new short[3];
            pidgeon.getBiasedAccelerometer(biasedAccel);
            System.out.println("biased acceleration" + "X" + biasedAccel[0] + "Y" + biasedAccel[1] + "Z" + biasedAccel[2]);
            break;

        //raw gyro
        case 5:
            double[] rawGyro = new double[3];
            pidgeon.getRawGyro(rawGyro);
            System.out.println("raw gyro" + " X" + rawGyro[0] + "Y" + rawGyro[1] + "Z" + rawGyro[2]);
            break;

        //accelerometer angles
        case 6:
            double[] accelAngles = new double[3];
            pidgeon.getAccelerometerAngles(accelAngles);
            System.out.println("acceleration angles" + "X" + accelAngles[0] + "Y" + accelAngles[1] + "Z" + accelAngles[2]);
            break;

        // biased magnetometer
        case 7:
            short[] biasedMagnet = new short[3];
            pidgeon.getBiasedMagnetometer(biasedMagnet);
            System.out.println("biased magnetometer" + "X" + biasedMagnet[0] + "Y" + biasedMagnet[1] + "Z" + biasedMagnet[2]);
            break;

        // raw magnetometer
        case 8:
            short[] rawMagnet = new short[3];
            pidgeon.getRawMagnetometer(rawMagnet);
            System.out.println("rawMagnetomert " + "X" + rawMagnet[0] + "Y" + rawMagnet[1] + "Z" + rawMagnet[2]);
            break;

    }








































}
