package team.gif.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import team.gif.robot.Constants;
import team.gif.robot.RobotMap;
import team.gif.robot.commands.drivetrain.Drive;

public class Drivetrain extends SubsystemBase {
    private static Drivetrain instance = null;

    private static final WPI_TalonSRX leftMaster = new WPI_TalonSRX(RobotMap.DRIVE_LEFT_MASTER);
    private static final WPI_TalonSRX leftSlave = new WPI_TalonSRX(RobotMap.DRIVE_LEFT_SLAVE);
    private static final WPI_TalonSRX rightMaster = new WPI_TalonSRX(RobotMap.DRIVE_RIGHT_MASTER);
    private static final WPI_TalonSRX rightSlave = new WPI_TalonSRX(RobotMap.DRIVE_RIGHT_SLAVE);

    public static SpeedControllerGroup left = new SpeedControllerGroup(leftMaster,leftSlave);
    public static SpeedControllerGroup right = new SpeedControllerGroup(rightMaster,rightSlave);
    public static DifferentialDrive drivetrain= new DifferentialDrive(left,right);

    public static Drivetrain getInstance() {
        if (instance == null) {
            instance = new Drivetrain();
        }

        return instance;
    }

    private Drivetrain() {
        super();

        leftMaster.setInverted(true);
        leftSlave.setInverted(true);
        //rightMaster.setInverted(true);
        //rightSlave.setInverted(true);
/*
        leftMaster.setNeutralMode(NeutralMode.Brake);
        leftSlave.setNeutralMode(NeutralMode.Brake);
        rightMaster.setNeutralMode(NeutralMode.Brake);
        rightSlave.setNeutralMode(NeutralMode.Brake);

        leftSlave.follow(leftMaster);
        rightSlave.follow(rightMaster);*/
    }

    public void setSpeed(double left, double right) {/*
        leftMaster.set(ControlMode.PercentOutput, left);
        rightMaster.set(ControlMode.PercentOutput, right);
    */
        drivetrain.tankDrive(left,right);
    }
    public void driveArcade(double speed, double rotation){
        drivetrain.arcadeDrive(speed,rotation);
    }
}
